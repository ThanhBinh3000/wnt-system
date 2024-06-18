package vn.com.gsoft.system.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.RoleReq;
import vn.com.gsoft.system.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.service.RoleService;

import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleReq, Long> implements RoleService {

    private RoleRepository hdrRepo;

    @Autowired
    public RoleServiceImpl(RoleRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public List<Role> searchList(RoleReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        if(req.getMaNhaThuoc() == null){
            req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        return hdrRepo.searchList(req);
    }

    @Override
    public Page<Role> searchPage(RoleReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        if(req.getMaNhaThuoc() == null){
            req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        }
        return hdrRepo.searchPage(req, pageable);
    }

    @Override
    public Optional<Role> findByTypeAndIsDefaultAndRoleName(int type, boolean isDefault, String roleName) {
        return this.hdrRepo.findByTypeAndIsDefault(type, isDefault, roleName);
    }

    @Override
    public Optional<Role> findByMaNhaThuocAndTypeAndIsDefaultAndRoleName(String maNhaThuoc, int type, boolean isDefault, String roleName) {
        return this.hdrRepo.findByMaNhaThuocAndTypeAndIsDefault(maNhaThuoc, type, isDefault, roleName);
    }
}
