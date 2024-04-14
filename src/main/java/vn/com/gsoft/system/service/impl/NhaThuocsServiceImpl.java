package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.*;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.*;
import vn.com.gsoft.system.service.NhaThuocsService;
import vn.com.gsoft.system.util.system.DataUtils;

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
    public NhaThuocsServiceImpl(NhaThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public NhaThuocs detail(String code) throws Exception {
        Optional<NhaThuocs> byMaNhaThuoc = hdrRepo.findByMaNhaThuoc(code);
        NhaThuocs nhaThuocs = byMaNhaThuoc.get();
        nhaThuocs.setNguoiPhuTrach(hdrRepo.findNguoiPhuTrachByMaNhaThuoc(code).get().getNguoiPhuTrach());
        if(nhaThuocs.getMaNhaThuocCha() != null && !nhaThuocs.getMaNhaThuocCha().isEmpty() && !Objects.equals(nhaThuocs.getMaNhaThuoc(), nhaThuocs.getMaNhaThuocCha())){
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
        Page<NhaThuocs> nhaThuocs = hdrRepo.searchPage(req, pageable);
        nhaThuocs.getContent().forEach( item -> {
            if(item.getTinhThanhId() != null){
                Optional<TinhThanhs> byTTId = tinhThanhsRepository.findById(item.getTinhThanhId());
                byTTId.ifPresent(tt -> item.setTenTinhThanh(tt.getTenTinhThanh()));
            }
            if(item.getCreatedByUserId() != null){
                Optional<UserProfile> byUserId = userProfileRepository.findById(item.getCreatedByUserId());
                byUserId.ifPresent(user -> item.setCreatedByUserName(user.getTenDayDu()));
            }
            if(item.getIdTypeBasic() != null){
                Optional<TypeBasis> byTBId = typeBasisRepository.findById(item.getIdTypeBasic());
                byTBId.ifPresent(type -> item.setNameTypeBasis(type.getNameType()));
            }
            TrienKhaisReq trienKhaisReq = new TrienKhaisReq();
            trienKhaisReq.setMaNhaThuoc(item.getMaNhaThuoc());
            trienKhaisReq.setType(0L);
            trienKhaisReq.setActive(true);
            List<TrienKhaisRes> trienKhaisResList = DataUtils.convertList(trienKhaisRepository.searchListTrienKhaiManagement(trienKhaisReq), TrienKhaisRes.class);
            if(!trienKhaisResList.isEmpty()){
                item.setResultBusinessId(trienKhaisResList.get(0).getId());
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
}
