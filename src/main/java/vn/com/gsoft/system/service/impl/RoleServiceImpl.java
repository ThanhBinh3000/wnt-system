package vn.com.gsoft.system.service.impl;

import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.RoleReq;
import vn.com.gsoft.system.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.service.RoleService;


@Service
@Log4j2
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleReq, Long> implements RoleService {

    private RoleRepository hdrRepo;

    @Autowired
    public RoleServiceImpl(RoleRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
