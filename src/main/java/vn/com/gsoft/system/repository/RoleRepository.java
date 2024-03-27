package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.Role;
import vn.com.gsoft.system.model.dto.RoleReq;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<Role, RoleReq, Long> {
  @Query("SELECT c FROM Role c " +
         "WHERE 1=1 "
          + " AND (:#{#param.roleId} IS NULL OR c.id = :#{#param.roleId}) "
          + " AND (:#{#param.roleName} IS NULL OR lower(c.roleName) LIKE lower(concat('%',CONCAT(:#{#param.roleName},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.type} IS NULL OR c.type = :#{#param.type}) "
          + " ORDER BY c.id desc"
  )
  Page<Role> searchPage(@Param("param") RoleReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM Role c " +
         "WHERE 1=1 "
          + " AND (:#{#param.roleId} IS NULL OR c.id = :#{#param.roleId}) "
          + " AND (:#{#param.roleName} IS NULL OR lower(c.roleName) LIKE lower(concat('%',CONCAT(:#{#param.roleName},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.type} IS NULL OR c.type = :#{#param.type}) "
          + " ORDER BY c.id desc"
  )
  List<Role> searchList(@Param("param") RoleReq param);

}
