package vn.com.gsoft.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.RolePrivilege;
import vn.com.gsoft.system.model.dto.RolePrivilegeReq;
import vn.com.gsoft.system.repository.RolePrivilegeRepository;
import vn.com.gsoft.system.service.RolePrivilegeService;

@Service
public class RolePrivilegeServiceImpl extends BaseServiceImpl<RolePrivilege, RolePrivilegeReq, Long> implements RolePrivilegeService {
    private RolePrivilegeRepository hdrRepo;

    @Autowired
    public RolePrivilegeServiceImpl(RolePrivilegeRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }
}
