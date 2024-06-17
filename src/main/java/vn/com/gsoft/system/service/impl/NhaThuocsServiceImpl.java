package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.constant.*;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.*;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.*;
import vn.com.gsoft.system.service.NhaThuocsService;
import vn.com.gsoft.system.service.RoleService;
import vn.com.gsoft.system.service.UserRoleService;
import vn.com.gsoft.system.util.system.DataUtils;
import vn.com.gsoft.system.util.system.StoreHelper;
import vn.com.gsoft.system.util.system.ZNSUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Log4j2
public class NhaThuocsServiceImpl extends BaseServiceImpl<NhaThuocs, NhaThuocsReq, Long> implements NhaThuocsService {

    private NhaThuocsRepository hdrRepo;

    @Autowired
    public UserProfileRepository userProfileRepository;

    @Autowired
    public TrienKhaisRepository trienKhaisRepository;

    @Autowired
    public OrderStoreMappingRepository orderStoreMappingRepository;

    @Autowired
    public ApplicationSettingRepository applicationSettingRepository;

    @Autowired
    public ThuocsRepository thuocsRepository;

    @Autowired
    public NhanVienNhaThuocsRepository nhanVienNhaThuocsRepository;

    @Autowired
    public NhomKhachHangsRepository nhomKhachHangsRepository;

    @Autowired
    public KhachHangsRepository khachHangsRepository;

    @Autowired
    public NhomNhaCungCapsRepository nhomNhaCungCapsRepository;

    @Autowired
    public NhaCungCapsRepository nhaCungCapsRepository;

    @Autowired
    public NhomThuocsRepository nhomThuocsRepository;

    @Autowired
    private DonViTinhsRepository donViTinhsRepository;

    @Autowired
    private WarehouseLocationRepository warehouseLocationRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    public NhaThuocsServiceImpl(NhaThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public NhaThuocs create(NhaThuocsReq req) throws Exception {
        Optional<UserProfile> tkQuanLy = null;
        // check administrator account.
        if (req.getNguoiPhuTrach() == null || req.getNguoiPhuTrach() <= 0) {
            throw new Exception("Chưa chọn tài khoản quản lý");
        } else {
            tkQuanLy = userProfileRepository.findById(req.getNguoiPhuTrach());
        }
        if (tkQuanLy.isEmpty()) {
            throw new Exception("Tài khoản quản lý không tồn tại");
        }
        Optional<NhaThuocs> byMaNhaThuoc = hdrRepo.findByMaNhaThuoc(req.getMaNhaThuoc());
        if (byMaNhaThuoc.isPresent()) {
            req.setMaNhaThuoc(getNewStoreCode());
        }
        NhaThuocs nhaThuoc = new NhaThuocs();
        BeanUtils.copyProperties(req, nhaThuoc, "id");
        if (nhaThuoc.getRecordStatusId() == null) {
            nhaThuoc.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        nhaThuoc.setCreated(new Date());
        nhaThuoc.setCreatedByUserId(getLoggedUser().getId());
        nhaThuoc.setHoatDong(true); // Mặc định là true khi tạo mới
        if (haveRole(RoleConstant.SUPERUSER)) {
            updateStatusNotAllowDeliverOverQuantity(req.getMaNhaThuoc(), req.getIsConnectivity());
        }
        nhaThuoc = hdrRepo.save(nhaThuoc);
        NhanVienNhaThuocs nhanVienNhaThuoc = new NhanVienNhaThuocs();
        nhanVienNhaThuoc.setRole(RoleConstant.ADMIN);
        nhanVienNhaThuoc.setNhaThuocMaNhaThuoc(nhaThuoc.getMaNhaThuoc());
        nhanVienNhaThuoc.setStoreId(nhaThuoc.getId());
        nhanVienNhaThuoc.setUserUserId(tkQuanLy.get().getId());
        nhanVienNhaThuocsRepository.save(nhanVienNhaThuoc);

        // default entities
        taoDuLieuBanDauChoNhaThuoc(nhaThuoc);

        //cập nhật trạng thái không cho phép thanh toán cho từng phiếu
        updateEnableNoteDebtPaymentAll(nhaThuoc.getMaNhaThuoc());
        //Tích hợp tủ kho cho nhà thuốc
        updateEnableWarehouseLocation(nhaThuoc.getMaNhaThuoc());
        // Cập nhật trạng thái không cho phép bán trong qua khứ
        updateAutoLockNotesAfterDays(nhaThuoc.getMaNhaThuoc());
        //Bật tính năng chiết khẩu tổng ở phiếu bán
        updateDeliveryNoteDiscountTotalByValue(nhaThuoc.getMaNhaThuoc());
        //Bật tính tự động LT đơn thuốc
        autoConnectSampleNote(nhaThuoc.getMaNhaThuoc());
        addDefaultSettingForAdmin(nhaThuoc.getMaNhaThuoc());

        // Xử lý role cho use quản lý
        Optional<Role> role = this.roleService.findByTypeAndIsDefaultAndRoleName(0, true, RoleTypeConstant.ADMIN);
        if (role.isEmpty()) {
            throw new Exception("Không tìm thấy role mặc định!");
        }
        UserRoleReq ur = new UserRoleReq();
        ur.setUserId(tkQuanLy.get().getId());
        ur.setRoleId(role.get().getId());
        this.userRoleService.create(ur);
        return nhaThuoc;
    }

    private void addDefaultSettingForAdmin(String maNhaThuoc) {
        List<Settings> byMaNhaThuoc = settingsRepository.findByMaNhaThuoc(maNhaThuoc);
        if (byMaNhaThuoc.isEmpty()) {
            String soNgayHetHan = "SoNgayHetHan";
            String soNgayHetHan_Value = "30";
            String soNgayKhongCoGiaoDich = "SoNgayKhongCoGiaoDich";
            String soNgayKhongCoGiaoDich_Value = "60";
            String tuDongTaoMaThuoc = "TuDongTaoMaThuoc";
            String tuDongTaoMaThuoc_Value = "Yes";
            String capNhatGiaPN = "CapNhatGiaPN";
            String capNhatGiaPN_value = "Auto";
            String tuDongTaoMaVachThuoc = "TuDongTaoMaVachThuoc";
            String tuDongTaoMaVachThuoc_Value = "Yes";
            String taoPhieuNhapXuatQuaKhu = "TaoPhieuNhapXuatQuaKhu";
            String taoPhieuNhapXuatQuaKhu_Value = "Allowed";
            String allowToChangeTotalAmountInDeliveryNoteKey = "Cho phép thay đổi tổng tiền trên phiếu bán hàng với mã vạch";

            Settings item1 = new Settings(soNgayHetHan, soNgayHetHan_Value, maNhaThuoc);
            Settings item2 = new Settings(soNgayKhongCoGiaoDich, soNgayKhongCoGiaoDich_Value, maNhaThuoc);
            Settings item3 = new Settings(tuDongTaoMaThuoc, tuDongTaoMaThuoc_Value, maNhaThuoc);
            Settings item4 = new Settings(capNhatGiaPN, capNhatGiaPN_value, maNhaThuoc);
            Settings item5 = new Settings(tuDongTaoMaVachThuoc, tuDongTaoMaVachThuoc_Value, maNhaThuoc);
            Settings item6 = new Settings(allowToChangeTotalAmountInDeliveryNoteKey, "Không", maNhaThuoc);
            Settings item7 = new Settings(taoPhieuNhapXuatQuaKhu, taoPhieuNhapXuatQuaKhu_Value, maNhaThuoc);

            settingsRepository.save(item1);
            settingsRepository.save(item2);
            settingsRepository.save(item3);
            settingsRepository.save(item4);
            settingsRepository.save(item5);
            settingsRepository.save(item6);
            settingsRepository.save(item7);
        }
    }

    private void autoConnectSampleNote(String maNhaThuoc) {
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setSettingKey(StoreSettingKeys.EnableAutoConnectSampleNote);
        applicationSetting.setActivated(true);
        applicationSetting.setDrugStoreId(maNhaThuoc);
        applicationSetting.setSettingValue("");
        applicationSetting.setSettingDisplayName("Tự động LT đơn thuốc");
        applicationSetting.setDescription("Tích: Áp dụng tham số này. Chỉ áp dụng cho phòng khám");
        applicationSetting.setRoleId((int) UserRoleType.SuperUser);
        applicationSettingRepository.save(applicationSetting);
    }

    private void updateDeliveryNoteDiscountTotalByValue(String maNhaThuoc) {
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setSettingKey(StoreSettingKeys.DeliveryNoteDiscountTotalByValue);
        applicationSetting.setActivated(true);
        applicationSetting.setDrugStoreId(maNhaThuoc);
        applicationSetting.setSettingValue("");
        applicationSetting.setSettingDisplayName("Chiết khấu tổng phiếu bán hàng theo giá trị tiền");
        applicationSetting.setDescription("Tích: Áp dụng tham số này");
        applicationSetting.setRoleId((int) UserRoleType.SuperUser);
        applicationSettingRepository.save(applicationSetting);
    }

    private void updateAutoLockNotesAfterDays(String maNhaThuoc) {
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setSettingKey(StoreSettingKeys.AutoLockNotesAfterDays);
        applicationSetting.setActivated(true);
        applicationSetting.setDrugStoreId(maNhaThuoc);
        applicationSetting.setSettingValue("0");
        applicationSetting.setSettingDisplayName("Tự động khóa phiếu sau (n) ngày");
        applicationSetting.setDescription("Tự động khóa phiếu sau (n) ngày");
        applicationSetting.setRoleId((int) UserRoleType.Admin);
        applicationSettingRepository.save(applicationSetting);
    }

    private void updateEnableWarehouseLocation(String maNhaThuoc) {
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setSettingKey(StoreSettingKeys.EnableWarehouseLocation);
        applicationSetting.setActivated(true);
        applicationSetting.setDrugStoreId(maNhaThuoc);
        applicationSetting.setSettingValue("");
        applicationSetting.setSettingDisplayName("Tích hợp vị trí tủ/ kho cho quầy");
        applicationSetting.setDescription("Tích hợp vị trí tủ/ kho ở danh mục thuốc, báo cáo");
        applicationSettingRepository.save(applicationSetting);
    }

    private void updateEnableNoteDebtPaymentAll(String maNhaThuoc) {
        ApplicationSetting applicationSetting = new ApplicationSetting();
        applicationSetting.setSettingKey(StoreSettingKeys.EnableNoteDebtPaymentAll);
        applicationSetting.setActivated(false);
        applicationSetting.setDrugStoreId(maNhaThuoc);
        applicationSetting.setSettingValue("");
        applicationSetting.setSettingDisplayName("Cho phép thanh toán công nợ cho từng các phiếu");
        applicationSetting.setDescription("Cho phép thanh toán công nợ cho từng các phiếu");
        applicationSettingRepository.save(applicationSetting);
    }

    @Override
    public NhaThuocs update(NhaThuocsReq req) throws Exception {
        Optional<NhaThuocs> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        Optional<UserProfile> tkQuanLy = null;
        // check administrator account.
        if (req.getNguoiPhuTrach() == null || req.getNguoiPhuTrach() <= 0) {
            throw new Exception("Chưa chọn tài khoản quản lý");
        } else {
            tkQuanLy = userProfileRepository.findById(req.getNguoiPhuTrach());
        }
        if (tkQuanLy.isEmpty()) {
            throw new Exception("Tài khoản quản lý không tồn tại");
        }
        NhaThuocs nhaThuoc = optional.get();
        BeanUtils.copyProperties(req, nhaThuoc, "id", "created", "createdByUserId");
        if(nhaThuoc.getRecordStatusId() == null){
            nhaThuoc.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        nhaThuoc.setModified(new Date());
        nhaThuoc.setModifiedByUserId(getLoggedUser().getId());
        if (haveRole(RoleConstant.SUPERUSER)) {
            updateStatusNotAllowDeliverOverQuantity(req.getMaNhaThuoc(), req.getIsConnectivity());
        }
        nhaThuoc = hdrRepo.save(nhaThuoc);
        NhanVienNhaThuocs nhanVienNhaThuocs = nhanVienNhaThuocsRepository.findByNhaThuocMaNhaThuocAndRole(nhaThuoc.getMaNhaThuoc(), RoleConstant.ADMIN);
        Long tkQuanLyCu =null;
        if(nhanVienNhaThuocs ==null){
            NhanVienNhaThuocs nhanVienNhaThuoc = new NhanVienNhaThuocs();
            nhanVienNhaThuoc.setRole(RoleConstant.ADMIN);
            nhanVienNhaThuoc.setNhaThuocMaNhaThuoc(nhaThuoc.getMaNhaThuoc());
            nhanVienNhaThuoc.setStoreId(nhaThuoc.getId());
            nhanVienNhaThuoc.setUserUserId(tkQuanLy.get().getId());
            nhanVienNhaThuocsRepository.save(nhanVienNhaThuoc);
        }else {
            // Xử lý nhân viên cũ thành user thường
            NhanVienNhaThuocs nhanVienNhaThuocCu = new NhanVienNhaThuocs();
            nhanVienNhaThuocCu.setRole(RoleConstant.USER);
            nhanVienNhaThuocCu.setNhaThuocMaNhaThuoc(nhaThuoc.getMaNhaThuoc());
            nhanVienNhaThuocCu.setStoreId(nhaThuoc.getId());
            nhanVienNhaThuocCu.setUserUserId(nhanVienNhaThuocs.getUserUserId());
            tkQuanLyCu = nhanVienNhaThuocs.getUserUserId();
            nhanVienNhaThuocsRepository.save(nhanVienNhaThuocCu);

            nhanVienNhaThuocs.setUserUserId(tkQuanLy.get().getId());
            nhanVienNhaThuocsRepository.save(nhanVienNhaThuocs);
        }

        // Xử lý role cho use quản lý
        Optional<Role> roleAdmin = this.roleService.findByTypeAndIsDefaultAndRoleName(0, true, RoleTypeConstant.ADMIN);
        if (roleAdmin.isEmpty()) {
            throw new Exception("Không tìm thấy role mặc định!");
        }
        UserRole userRole = userRoleService.findByUserIdAndRoleId(tkQuanLyCu, roleAdmin.get().getId());
        if(userRole == null){
            UserRoleReq ur = new UserRoleReq();
            ur.setUserId(tkQuanLy.get().getId());
            ur.setRoleId(roleAdmin.get().getId());
            this.userRoleService.create(ur);
        }else {
            // xử lý role user cũ
            Optional<Role> roleUser = this.roleService.findByMaNhaThuocAndTypeAndIsDefaultAndRoleName(getLoggedUser().getNhaThuoc().getMaNhaThuoc(), 1, true, RoleTypeConstant.USER);
            if (roleUser.isEmpty()) {
                roleUser = this.roleService.findByTypeAndIsDefaultAndRoleName(0, true, RoleTypeConstant.USER);
                if (roleUser.isEmpty()) {
                    throw new Exception("Không tìm thấy role mặc định!");
                }
            }
            UserRoleReq ur = new UserRoleReq();
            ur.setUserId(tkQuanLyCu);
            ur.setRoleId(roleUser.get().getId());
            this.userRoleService.create(ur);

            userRole.setUserId(tkQuanLy.get().getId());
            this.userRoleRepository.save(userRole);
        }

        return nhaThuoc;
    }

    @Override
    public NhaThuocs detail(String code) throws Exception {
        Optional<NhaThuocs> byMaNhaThuoc = hdrRepo.findByMaNhaThuoc(code);
        NhaThuocs nhaThuocs = byMaNhaThuoc.get();
        nhaThuocs.setNguoiPhuTrach(hdrRepo.findNguoiPhuTrachByMaNhaThuoc(code).get().getNguoiPhuTrach());
        if (nhaThuocs.getMaNhaThuocCha() != null && !nhaThuocs.getMaNhaThuocCha().isEmpty() && !Objects.equals(nhaThuocs.getMaNhaThuoc(), nhaThuocs.getMaNhaThuocCha())) {
            nhaThuocs.setNhaThuocQuanLy(hdrRepo.findByMaNhaThuoc(nhaThuocs.getMaNhaThuocCha()).get().getTenNhaThuoc());
        }
        return nhaThuocs;
    }

    @Override
    public Page<NhaThuocs> searchPage(NhaThuocsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        LocalDate now = LocalDate.now();
        if (req.getNumDaysNoTrans() != null) {
            req.setDateBeforeNumDaysNoTrans(Date.from(now.minusDays(req.getNumDaysNoTrans()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if (req.getExpiredType() != null && req.getExpiredDate() != null && (req.getExpiredType() == StoreExpiredTypeConstant.LessThan30Days || req.getExpiredType() == StoreExpiredTypeConstant.LessThan7Days)) {
            req.setDateDiff(ChronoUnit.DAYS.between(now, req.getExpiredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        req.setDateNow(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Page<NhaThuocs> nhaThuocs = hdrRepo.searchPage(req, pageable);
        nhaThuocs.getContent().forEach(item -> {
            List<ApplicationSetting> settings = getApplicationSetting(item);
            getLoaiCoSo(item, settings);
            getTrangThaiTrienKhai(item);
            if (item.getCodeErrorConfirmPaymentZNS() != null) {
                item.setZNSConfirmPaymentResult(ZNSUtils.getResultByZNSCode(item.getCodeErrorConfirmPaymentZNS()));
            }
        });
        return nhaThuocs;
    }

    @Override
    public Page<NhaThuocsRes> searchPageNhaThuoc(NhaThuocsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setHoatDong(true);
        // check is user hệ thống
        Optional<vn.com.gsoft.system.model.system.Role> role = getLoggedUser().getRoles().stream().filter(item -> "Hệ thống".equalsIgnoreCase(item.getRoleType())).findFirst();
        if (role.isEmpty()) {
            req.setUserIdQueryData(getLoggedUser().getId());
        }

        return DataUtils.convertPage(hdrRepo.searchPageNhaThuoc(req, pageable), NhaThuocsRes.class);
    }

    @Override
    public Optional<NhaThuocs> findByMaNhaThuoc(String maNhaThuoc) {
        return hdrRepo.findByMaNhaThuoc(maNhaThuoc);
    }

    @Override
    public String getNewStoreCode() throws Exception {
        String code = "0000";
        Optional<NhaThuocs> optional = hdrRepo.findLatestRecord();
        if (optional.isEmpty()) return code;
        NhaThuocs lastDs = optional.get();
        Long lastDsIdNumber;
        try {
            lastDsIdNumber = Long.parseLong(lastDs.getMaNhaThuoc());
        } catch (NumberFormatException e) {
            lastDsIdNumber = 0L;
        }
        if (lastDsIdNumber == 0) lastDsIdNumber = lastDs.getId();
        lastDsIdNumber++;
        code = StoreHelper.getCodeBasedOnNumber(lastDsIdNumber);
        while (true) {
            Optional<NhaThuocs> byMaNT = hdrRepo.findByMaNhaThuoc(code);
            if (byMaNT.isEmpty()) break;
            lastDsIdNumber++;
            code = StoreHelper.getCodeBasedOnNumber(lastDsIdNumber);
        }
        return code;
    }

    @Override
    public Page<NhaThuocDongBoPhieuRes> searchPageNhaThuocDongBoPhieu(NhaThuocDongBoPhieuReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        var storeCode = userInfo.getNhaThuoc().getMaNhaThuoc();
        Optional<String> e = hdrRepo.findNhaThuocDongBoPhieuByMaNhaThuoc(storeCode);
        if (e.get().isEmpty()) return null;

        //lấy ra danh sách mã dồng bộ
        var storeCodes = e.get().split(",");
        if (storeCodes.length == 0) return null;

        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        return DataUtils.convertPage(hdrRepo.searchPageNhaThuocDongBoPhieu(storeCodes, req.getTenNhaThuoc(), pageable), NhaThuocDongBoPhieuRes.class);
    }

    @Override
    public Integer updateThongTinKhuVuc(ThongTinKhuVucReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getId() < 0) return 0;
        Optional<NhaThuocs> optional = hdrRepo.findById(req.getId());
        NhaThuocs e = optional.get();
        e.setWardId(req.getWardId());
        e.setCityId(req.getCityId());
        e.setRegionId(req.getRegionId());
        e.setDiaChi(req.getDiaChi());
        e = hdrRepo.save(e);
        return 1;
    }

    @Override
    public Page<NhaThuocs> searchPageNhaThuocTong(NhaThuocsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        req.setHoatDong(true);
        req.setExcludeCurrentStore(true);
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        Page<NhaThuocs> nhaThuocs = hdrRepo.searchPage(req, pageable);
        List<String> maNhaThuocs = nhaThuocs.getContent().stream()
                .map(NhaThuocs::getMaNhaThuoc)
                .toList();
        OrderStoreMappingReq orderStoreMappingReq = new OrderStoreMappingReq();
        orderStoreMappingReq.setStoreCode(req.getMaNhaThuoc());
        orderStoreMappingReq.setTargetStoreCodes(maNhaThuocs);
        orderStoreMappingReq.setRecordStatusId(RecordStatusContains.ACTIVE);
        List<OrderStoreMapping> orderStoreMappingList = orderStoreMappingRepository.searchList(orderStoreMappingReq);
        nhaThuocs.getContent().forEach(item -> {
            Optional<OrderStoreMapping> orderStoreMapping = orderStoreMappingList.stream()
                    .filter(osm -> osm.getTargetStoreCode().equals(item.getMaNhaThuoc()))
                    .findFirst();
            if (orderStoreMapping.isPresent()) {
                item.setOrderingActivated(true);
                item.setPreOrderingActivated(true);
                item.setIsDefault(orderStoreMapping.get().getIsDefault());
            }
        });
        return nhaThuocs;
    }

    @Override
    public Page<NhaThuocs> searchPageNhaThuocTrienKhai(NhaThuocsReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        Page<NhaThuocs> nhaThuocs = hdrRepo.searchPage(req, pageable);
        nhaThuocs.getContent().forEach(item -> {
            List<ApplicationSetting> settings = getApplicationSetting(item);
            getLoaiCoSo(item, settings);
            if (item.getCodeErrorConfirmPaymentZNS() != null) {
                item.setZNSConfirmPaymentResult(ZNSUtils.getResultByZNSCode(item.getCodeErrorConfirmPaymentZNS()));
            }
            getTrangThaiTaiApp(item);
            getThamChieuDanhMuc(item, settings);
            getTotalDrug(item);
            getTotalNote(item);
            getTotalVisit(item);
            getTrangThaiTrienKhai(item);
            getIsScoreRate(item, settings);
            getTotalStaff(item);
            getLevel(item);
        });
        return nhaThuocs;
    }

    //region Private Methods
    private List<ApplicationSetting> getApplicationSetting(NhaThuocs item) {
        ApplicationSettingReq applicationSettingReq = new ApplicationSettingReq();
        applicationSettingReq.setDrugStoreId(item.getMaNhaThuoc());
        applicationSettingReq.setActivated(true);
        return applicationSettingRepository.searchList(applicationSettingReq);
    }

    private void getLoaiCoSo(NhaThuocs item, List<ApplicationSetting> settings) {
        if (!settings.isEmpty()) {
            String storeHat = "";
            String storeHatTitle = "";
            if (item.getIsConnectivity()) {
                if (item.getUpgradeToPlus()) {
                    storeHat = "LT+";
                    storeHatTitle = "Liên thông PLUS";
                } else if (settings.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.EnableConnectivityStoreToManageStore)).findFirst().isPresent()) {
                    storeHat = "QL + LT";
                    storeHatTitle = "Quản lý và Liên thông";
                } else {
                    storeHat = "LT";
                    storeHatTitle = "Liên thông";
                }
            } else if (item.getIsGeneralPharmacy()) {
                if (settings.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.UseClinicIntegration)).findFirst().isPresent()) {
                    storeHat = "PK";
                    storeHatTitle = "Phòng khám";
                } else {
                    storeHat = "CTY";
                    storeHatTitle = "Công ty";
                }
            } else {
                if (settings.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.UseClinicIntegration)).findFirst().isPresent()) {
                    storeHat = "PK";
                    storeHatTitle = "Phòng khám";
                } else {
                    storeHat = "QL";
                    storeHatTitle = "Quản lý";
                }
            }
            if (item.getExpiredDate() != null) {
                storeHat = "PLUS";
                storeHatTitle = "PLUS";
            }
            item.setStoreHat(storeHat);
            item.setStoreHatTitle(storeHatTitle);
        }
    }

    private void getTrangThaiTrienKhai(NhaThuocs item) {
        TrienKhaisReq trienKhaisReq = new TrienKhaisReq();
        trienKhaisReq.setMaNhaThuoc(item.getMaNhaThuoc());
        trienKhaisReq.setType(0L);
        trienKhaisReq.setActive(true);
        List<TrienKhaisRes> trienKhaisResList = DataUtils.convertList(trienKhaisRepository.searchListTrienKhaiManagement(trienKhaisReq), TrienKhaisRes.class);
        if (!trienKhaisResList.isEmpty()) {
            item.setResultBusinessId(trienKhaisResList.get(0).getId());
        }
    }

    private void getTrangThaiTaiApp(NhaThuocs item) {
        UserProfileReq userProfileReq = new UserProfileReq();
        userProfileReq.setMaNhaThuoc(item.getMaNhaThuoc());
        userProfileReq.setRoleName("Admin");
        List<UserProfile> userProfiles = userProfileRepository.searchList(userProfileReq);
        item.setIsUsedMobileApp(false);
        if (!userProfiles.isEmpty()) {
            for (UserProfile u : userProfiles) {
                if (u.getTokenDevice() != null && !u.getTokenDevice().isEmpty()) {
                    item.setIsUsedMobileApp(true);
                    break;
                }
            }
        }
    }

    private void getThamChieuDanhMuc(NhaThuocs item, List<ApplicationSetting> settings) {
        if (!settings.isEmpty()) {
            ApplicationSetting referenceStoreForProducts = settings.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.ReferenceStoreForProducts)).findFirst().orElse(null);
            if (referenceStoreForProducts != null) {
                item.setThamChieuDanhMuc(referenceStoreForProducts.getSettingValue());
            }
        }
    }

    private void getTotalDrug(NhaThuocs item) {
        ThuocsReq thuocsReq = new ThuocsReq();
        thuocsReq.setMaNhaThuoc(item.getMaNhaThuocCha());
        thuocsReq.setRecordStatusId(RecordStatusContains.ACTIVE);
        List<Thuocs> thuocsList = thuocsRepository.searchList(thuocsReq);
        item.setTotalDrug((long) thuocsList.size());
        item.setTotalDrugConnectivity(thuocsList.stream().filter(thuoc -> thuoc.getConnectivityDrugID() > 0).count());
        item.setIsAdviseStaff(thuocsList.stream().anyMatch(Thuocs::getDiscountByRevenue));
    }

    private void getTotalNote(NhaThuocs item) {
        // Xem lai
    }

    private void getTotalVisit(NhaThuocs item) {
        // DB MedReport
    }

    private void getIsScoreRate(NhaThuocs item, List<ApplicationSetting> settings) {
        if (!settings.isEmpty()) {
            settings.stream().filter(s -> s.getSettingKey().equals(StoreSettingKeys.ScoreRate)).findFirst().ifPresent(setting -> item.setIsScoreRate(Boolean.parseBoolean(setting.getSettingValue())));
        }
    }

    private void getTotalStaff(NhaThuocs item) {
        NhanVienNhaThuocsReq nhanVienNhaThuocsReq = new NhanVienNhaThuocsReq();
        nhanVienNhaThuocsReq.setNhaThuocMaNhaThuoc(item.getMaNhaThuoc());
        nhanVienNhaThuocsReq.setRecordStatusId(RecordStatusContains.ACTIVE);
        nhanVienNhaThuocsReq.setRole("User");
        List<NhanVienNhaThuocs> nhanVienNhaThuocsList = nhanVienNhaThuocsRepository.searchList(nhanVienNhaThuocsReq);
        item.setTotalStaff((long) nhanVienNhaThuocsList.size());
    }

    private void getLevel(NhaThuocs item) {
        // Xem lai
    }


    private void updateStatusNotAllowDeliverOverQuantity(String storeCode, Boolean status) {
        List<ApplicationSetting> settingItem = applicationSettingRepository.findByDrugStoreIdAndSettingKey(storeCode, StoreSettingKeys.NotAllowDeliverOverQuantity);
        if (settingItem != null && !settingItem.isEmpty()) {
            for (ApplicationSetting as : settingItem) {
                as.setActivated(status);
                as.setSettingValue(status ? "1" : "0");
            }
        } else {
            var item = new ApplicationSetting();
            item.setSettingKey(StoreSettingKeys.NotAllowDeliverOverQuantity);
            item.setActivated(status);
            item.setDrugStoreId(storeCode);
            item.setSettingValue(status ? "1" : "0");
            item.setSettingDisplayName("Không cho phép bán âm kho");
            item.setDescription("Không cho phép bán nhiều hơn số lượng tồn hiện tại");
            applicationSettingRepository.save(item);
        }
    }

    private void taoDuLieuBanDauChoNhaThuoc(NhaThuocs nhathuoc) throws Exception {
        Profile loggedUser = getLoggedUser();
        List<NhomKhachHangs> nhomKhachHangList = new ArrayList<>();
        Arrays.stream(NhomKhachHangsConstant.NHOM_KHACH_HANGS).forEach(item -> {
            NhomKhachHangs nhomKhachHangs = new NhomKhachHangs();
            nhomKhachHangs.setTenNhomKhachHang(item);
            nhomKhachHangs.setCreated(new Date());
            nhomKhachHangs.setCreatedByUserId(loggedUser.getId());
            nhomKhachHangs.setNhaThuocMaNhaThuoc(nhathuoc.getMaNhaThuoc());
            nhomKhachHangs.setGroupTypeId(CustomerGroupTypeConstant.DEFAULT);
            nhomKhachHangs = nhomKhachHangsRepository.save(nhomKhachHangs);
            nhomKhachHangList.add(nhomKhachHangs);

        });
        List<KhachHangs> khachHangList = new ArrayList<>();
        Arrays.stream(KhachHangsConstant.KHACH_HANGS).forEach(item -> {
            KhachHangs khachHang = new KhachHangs();
            khachHang.setTenKhachHang(item);
            khachHang.setCreated(new Date());
            khachHang.setCreatedByUserId(loggedUser.getId());
            khachHang.setMaNhomKhachHang(nhomKhachHangList.get(0).getId());
            khachHang.setMaNhaThuoc(nhathuoc.getMaNhaThuoc());
            khachHang.setCustomerTypeId(KhachHangsConstant.KHACH_HANG_LE.equals(item) ? (int) CustomerTypeConstant.Retail : (int) CustomerTypeConstant.InventoryAdjustment);
            khachHang = khachHangsRepository.save(khachHang);
            khachHangList.add(khachHang);

        });
        List<NhomNhaCungCaps> nhomNhaCungCapList = new ArrayList<>();
        Arrays.stream(NhomNhaCungCapsConstant.NHOM_NHA_CUNG_CAPS).forEach(item -> {
            NhomNhaCungCaps nhomNhaCungCaps = new NhomNhaCungCaps();
            nhomNhaCungCaps.setTenNhomNhaCungCap(item);
            nhomNhaCungCaps.setCreated(new Date());
            nhomNhaCungCaps.setCreatedByUserId(loggedUser.getId());
            nhomNhaCungCaps.setMaNhaThuoc(nhathuoc.getMaNhaThuoc());
            nhomNhaCungCaps.setIsDefault(true);
            nhomNhaCungCaps = nhomNhaCungCapsRepository.save(nhomNhaCungCaps);
            nhomNhaCungCapList.add(nhomNhaCungCaps);

        });
        List<NhaCungCaps> nhaCungCapList = new ArrayList<>();
        Arrays.stream(NhaCungCapsConstant.NHA_CUNG_CAPS).forEach(item -> {
            NhaCungCaps nhaCungCaps = new NhaCungCaps();
            nhaCungCaps.setTenNhaCungCap(item);
            nhaCungCaps.setCreated(new Date());
            nhaCungCaps.setCreatedByUserId(loggedUser.getId());
            nhaCungCaps.setMaNhomNhaCungCap(nhomNhaCungCapList.get(0).getId());
            nhaCungCaps.setMaNhaThuoc(nhathuoc.getMaNhaThuoc());
            nhaCungCaps.setSupplierTypeId(NhaCungCapsConstant.HANG_NHAP_LE.equals(item) ? (int) SupplierTypeConstant.Retail : (int) SupplierTypeConstant.InventoryAdjustment);
            nhaCungCaps = nhaCungCapsRepository.save(nhaCungCaps);
            nhaCungCapList.add(nhaCungCaps);
        });

        // nếu là nhà thuốc con thì không tạo nhóm thuốc và đơn vị tính (do đã có từ nhà thuốc cha)
        if (nhathuoc.getMaNhaThuocCha().equals(nhathuoc.getMaNhaThuoc())) {
            List<NhomThuocs> nhomThuocsList = new ArrayList<>();
            Arrays.stream(NhomThuocsConstant.NHOM_THUOCS).forEach(item -> {
                NhomThuocs nhomThuocs = new NhomThuocs();
                nhomThuocs.setTenNhomThuoc(item);
                nhomThuocs.setCreated(new Date());
                nhomThuocs.setCreatedByUserId(loggedUser.getId());
                nhomThuocs.setMaNhaThuoc(nhathuoc.getMaNhaThuoc());
                nhomThuocs = nhomThuocsRepository.save(nhomThuocs);
                nhomThuocsList.add(nhomThuocs);
            });

            List<DonViTinhs> donViTinhsList = new ArrayList<>();
            Arrays.stream(DonViTinhsConstant.DON_VI_TINHS).forEach(item -> {
                DonViTinhs donViTinhs = new DonViTinhs();
                donViTinhs.setTenDonViTinh(item);
                donViTinhs.setCreated(new Date());
                donViTinhs.setCreatedByUserId(loggedUser.getId());
                donViTinhs.setMaNhaThuoc(nhathuoc.getMaNhaThuoc());
                donViTinhs = donViTinhsRepository.save(donViTinhs);
                donViTinhsList.add(donViTinhs);
            });
            WarehouseLocation warehouseLocation = new WarehouseLocation();
            warehouseLocation.setCode(WarehouseLocationsConstant.WAREHOUSE_LOCATION[0]);
            warehouseLocation.setNameWarehouse(WarehouseLocationsConstant.WAREHOUSE_LOCATION[0]);
            warehouseLocation.setStoreCode(nhathuoc.getMaNhaThuoc());
            warehouseLocation.setRecordStatusId(RecordStatusContains.ACTIVE);
            warehouseLocationRepository.save(warehouseLocation);
        }
    }
}
