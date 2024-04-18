package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.constant.StoreExpiredTypeConstant;
import vn.com.gsoft.system.constant.StoreSettingKeys;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.*;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.*;
import vn.com.gsoft.system.service.NhaThuocsService;
import vn.com.gsoft.system.util.system.DataUtils;
import vn.com.gsoft.system.util.system.StoreHelper;
import vn.com.gsoft.system.util.system.ZNSUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class NhaThuocsServiceImpl extends BaseServiceImpl<NhaThuocs, NhaThuocsReq, Long> implements NhaThuocsService {

    private NhaThuocsRepository hdrRepo;

    @Autowired
    public TinhThanhsRepository tinhThanhsRepository;

    @Autowired
    public UserProfileRepository userProfileRepository;

    @Autowired
    public TypeBasisRepository typeBasisRepository;

    @Autowired
    public TrienKhaisRepository trienKhaisRepository;

    @Autowired
    public OrderStoreMappingRepository orderStoreMappingRepository;

    @Autowired
    public ApplicationSettingRepository applicationSettingRepository;

    @Autowired
    public NhaThuocsServiceImpl(NhaThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
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
            ApplicationSettingReq applicationSettingReq = new ApplicationSettingReq();
            applicationSettingReq.setDrugStoreId(item.getMaNhaThuoc());
            applicationSettingReq.setActivated(true);
            List<ApplicationSetting> applicationSettingList = applicationSettingRepository.searchList(applicationSettingReq);
            if (!applicationSettingList.isEmpty()) {
                String storeHat = "";
                String storeHatTitle = "";
                if(item.getIsConnectivity()){
                    if(item.getUpgradeToPlus()){
                        storeHat = "LT+";
                        storeHatTitle = "Liên thông PLUS";
                    }
                    else if (applicationSettingList.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.EnableConnectivityStoreToManageStore)).findFirst().isPresent()){
                        storeHat = "QL + LT";
                        storeHatTitle = "Quản lý và Liên thông";
                    }
                    else {
                        storeHat = "LT";
                        storeHatTitle = "Liên thông";
                    }
                } else if (item.getIsGeneralPharmacy()){
                    if(applicationSettingList.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.UseClinicIntegration)).findFirst().isPresent()){
                        storeHat = "PK";
                        storeHatTitle = "Phòng khám";
                    }
                    else {
                        storeHat = "CTY";
                        storeHatTitle = "Công ty";
                    }
                } else {
                    if(applicationSettingList.stream().filter(setting -> setting.getSettingKey().equals(StoreSettingKeys.UseClinicIntegration)).findFirst().isPresent()){
                        storeHat = "PK";
                        storeHatTitle = "Phòng khám";
                    }
                    else {
                        storeHat = "QL";
                        storeHatTitle = "Quản lý";
                    }
                }
                item.setStoreHat(storeHat);
                item.setStoreHatTitle(storeHatTitle);
            }
            if (item.getTinhThanhId() != null) {
                Optional<TinhThanhs> byTTId = tinhThanhsRepository.findById(item.getTinhThanhId());
                byTTId.ifPresent(tt -> item.setTenTinhThanh(tt.getTenTinhThanh()));
            }
            if (item.getCreatedByUserId() != null) {
                Optional<UserProfile> byUserId = userProfileRepository.findById(item.getCreatedByUserId());
                byUserId.ifPresent(user -> item.setCreatedByUserName(user.getTenDayDu()));
            }
            if (item.getIdTypeBasic() != null) {
                Optional<TypeBasis> byTBId = typeBasisRepository.findById(item.getIdTypeBasic());
                byTBId.ifPresent(type -> item.setNameTypeBasis(type.getNameType()));
            }
            TrienKhaisReq trienKhaisReq = new TrienKhaisReq();
            trienKhaisReq.setMaNhaThuoc(item.getMaNhaThuoc());
            trienKhaisReq.setType(0L);
            trienKhaisReq.setActive(true);
            List<TrienKhaisRes> trienKhaisResList = DataUtils.convertList(trienKhaisRepository.searchListTrienKhaiManagement(trienKhaisReq), TrienKhaisRes.class);
            if (!trienKhaisResList.isEmpty()) {
                item.setResultBusinessId(trienKhaisResList.get(0).getId());
            }
            if(item.getCodeErrorConfirmPaymentZNS() != null){
                item.setZNSConfirmPaymentResult(ZNSUtils.getResultByZNSCode(item.getCodeErrorConfirmPaymentZNS()));
            }
        });
        return nhaThuocs;
    }

    @Override
    public Page<NhaThuocsRes> searchPageNhaThuoc(NhaThuocsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setUserIdQueryData(getLoggedUser().getId());
        req.setRecordStatusId(0l);
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
        return DataUtils.convertPage(hdrRepo.searchPageNhaThuocDongBoPhieu(storeCodes, pageable), NhaThuocDongBoPhieuRes.class);
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
}
