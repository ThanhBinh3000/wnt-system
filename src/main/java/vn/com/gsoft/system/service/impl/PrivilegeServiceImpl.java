package vn.com.gsoft.system.service.impl;

import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.PrivilegeReq;
import vn.com.gsoft.system.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.service.PrivilegeService;


@Service
@Log4j2
public class PrivilegeServiceImpl extends BaseServiceImpl<Privilege, PrivilegeReq, Long> implements PrivilegeService {

    private PrivilegeRepository hdrRepo;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
