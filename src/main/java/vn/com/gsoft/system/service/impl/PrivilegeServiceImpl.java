package vn.com.gsoft.system.service.impl;

import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.PrivilegeReq;
import vn.com.gsoft.system.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.service.PrivilegeService;

import java.util.List;


@Service
@Log4j2
public class PrivilegeServiceImpl extends BaseServiceImpl<Privilege, PrivilegeReq, Long> implements PrivilegeService {

    private PrivilegeRepository hdrRepo;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public List<Privilege> searchList(PrivilegeReq req) throws Exception {
        List<Privilege> privileges = super.searchList(req);
        for (Privilege privilege : privileges) {
            privilege.setEntitys(entityRepository.findByPrivilegeId(privilege.getId()));
        }
        return privileges;
    }

    @Override
    public Privilege detail(Long id) throws Exception {
        Privilege detail = super.detail(id);
        detail.setEntitys(entityRepository.findByPrivilegeId(detail.getId()));
        return detail;
    }
}
