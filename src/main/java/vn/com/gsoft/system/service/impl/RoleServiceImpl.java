package vn.com.gsoft.system.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.RoleReq;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.service.RoleService;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleReq, Long> implements RoleService {

    private RoleRepository hdrRepo;
    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

    @Override
    public List<Role> searchList(RoleReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchList(req);
    }

    @Override
    public Page<Role> searchPage(RoleReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        if (req.getMaNhaThuoc() == null) {
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

    @Override
    public List<Role> searchListSystem(RoleReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchListSystem(req.getRoleName());
    }

    @Override
    public Role create(RoleReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Role e = new Role();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setIsDeleted(false);
        e.setRoleTypeId(3l);
        e.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        for (Long p : req.getPrivileges()) {
            RolePrivilege privilege = new RolePrivilege();
            privilege.setRoleId(e.getId());
            privilege.setPrivilegeId(p);
            rolePrivilegeRepository.save(privilege);
        }
        return e;
    }

    @Override
    public Role update(RoleReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<Role> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }

        Role e = optional.get();
        req.setRoleTypeId(e.getRoleTypeId());
        req.setMaNhaThuoc(e.getMaNhaThuoc());
        BeanUtils.copyProperties(req, e, "id", "created", "createdByUserId");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setIsDeleted(false);
        e.setRoleTypeId(3l);
        e.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        rolePrivilegeRepository.deleteByRoleId(e.getId());
        for (Long p : req.getPrivileges()) {
            RolePrivilege privilege = new RolePrivilege();
            privilege.setRoleId(e.getId());
            privilege.setPrivilegeId(p);
            rolePrivilegeRepository.save(privilege);
        }
        return e;
    }

    @Override
    public Role createSystem(RoleReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Role e = new Role();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setIsDeleted(false);
        e.setCreated(new Date());
        e.setCreatedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        for (Long p : req.getPrivileges()) {
            RolePrivilege privilege = new RolePrivilege();
            privilege.setRoleId(e.getId());
            privilege.setPrivilegeId(p);
            rolePrivilegeRepository.save(privilege);
        }
        return e;
    }

    @Override
    @Transactional
    public Role updateSystem(RoleReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<Role> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }

        Role e = optional.get();
        req.setRoleTypeId(e.getRoleTypeId());
        req.setMaNhaThuoc(e.getMaNhaThuoc());
        BeanUtils.copyProperties(req, e, "id", "created", "createdByUserId");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setIsDeleted(false);
        e.setModified(new Date());
        e.setModifiedByUserId(getLoggedUser().getId());
        e = hdrRepo.save(e);
        rolePrivilegeRepository.deleteByRoleId(e.getId());
        for (Long p : req.getPrivileges()) {
            RolePrivilege privilege = new RolePrivilege();
            privilege.setRoleId(e.getId());
            privilege.setPrivilegeId(p);
            rolePrivilegeRepository.save(privilege);
        }
        return e;
    }

    @Override
    public Role detail(Long id) throws Exception {
        Role detail = super.detail(id);
        detail.setPrivileges(privilegeRepository.findByRoleId(detail.getId()));
        return detail;
    }

    @Override
    public List<Role> searchListStaff(RoleReq objReq) {
        return hdrRepo.searchListStaff(objReq);
    }
}
