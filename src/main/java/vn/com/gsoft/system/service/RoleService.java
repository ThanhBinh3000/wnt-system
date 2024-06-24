package vn.com.gsoft.system.service;

import vn.com.gsoft.system.entity.Role;
import vn.com.gsoft.system.model.dto.RoleReq;

import java.util.List;
import java.util.Optional;

public interface RoleService extends BaseService<Role, RoleReq, Long> {


    Optional<Role> findByTypeAndIsDefaultAndRoleName(int type, boolean isDefault, String roleName);

    Optional<Role> findByMaNhaThuocAndTypeAndIsDefaultAndRoleName(String maNhaThuoc, int type, boolean isDefault, String roleName);

    List<Role> searchListSystem(RoleReq objReq) throws Exception;
}