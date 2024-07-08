package vn.com.gsoft.system.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.RoleType;
import vn.com.gsoft.system.model.dto.RoleTypeReq;
import vn.com.gsoft.system.repository.RoleTypeRepository;
import vn.com.gsoft.system.service.RoleTypeService;

@Service
@Log4j2
public class RoleTypeServiceImpl extends BaseServiceImpl<RoleType, RoleTypeReq, Long> implements RoleTypeService {
    private RoleTypeRepository hdrRepo;

    @Autowired
    public RoleTypeServiceImpl(RoleTypeRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }
}
