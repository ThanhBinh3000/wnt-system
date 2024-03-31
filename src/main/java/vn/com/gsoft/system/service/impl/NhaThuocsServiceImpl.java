package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.NhaThuocsRepository;
import vn.com.gsoft.system.service.NhaThuocsService;

import java.util.Optional;

@Service
@Log4j2
public class NhaThuocsServiceImpl extends BaseServiceImpl<NhaThuocs, NhaThuocsReq, Long> implements NhaThuocsService {

    private NhaThuocsRepository hdrRepo;

    @Autowired
    public NhaThuocsServiceImpl(NhaThuocsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public Page<NhaThuocsRes> searchPageNhaThuoc(NhaThuocsReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setUserIdQueryData(getLoggedUser().getId());
        req.setRecordStatusId(0l);
        return hdrRepo.searchPageNhaThuoc(req, pageable);
    }
}
