package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.entity.Role;
import vn.com.gsoft.system.model.dto.RoleReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, RoleReq, Long> {
    @Query("SELECT c FROM Role c " +
            "WHERE 1=1 "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR  ( c.maNhaThuoc = :#{#param.maNhaThuoc} OR c.isDefault = true)) "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.roleName} IS NULL OR lower(c.roleName) LIKE lower(concat('%',CONCAT(:#{#param.roleName},'%'))))"
            + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
            + " AND (:#{#param.roleTypeId} IS NULL OR c.roleTypeId = :#{#param.roleTypeId}) "
            + " ORDER BY c.id desc"
    )
    Page<Role> searchPage(@Param("param") RoleReq param, Pageable pageable);


    @Query("SELECT c FROM Role c " +
            "WHERE 1=1 "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR  ( c.maNhaThuoc = :#{#param.maNhaThuoc})) "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.roleName} IS NULL OR lower(c.roleName) LIKE lower(concat('%',CONCAT(:#{#param.roleName},'%'))))"
            + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
            + " AND (:#{#param.roleTypeId} IS NULL OR c.roleTypeId = :#{#param.roleTypeId}) "
            + " ORDER BY c.id desc"
    )
    List<Role> searchList(@Param("param") RoleReq param);

    @Query("SELECT c FROM Role c " +
            "join RoleType rt on rt.id = c.roleTypeId " +
            "WHERE 1=1 "
            + " AND (c.roleTypeId = ?1) "
            + " AND (c.recordStatusId = " + RecordStatusContains.ACTIVE + ")"
            + " AND (c.isDefault = ?2)"
            + " AND (rt.roleName = ?3)"
    )
    Optional<Role> findByTypeAndIsDefault(int type, boolean isDefault, String roleName);

    @Query("SELECT c FROM Role c " +
            "join RoleType rt on rt.id = c.roleTypeId " +
            "WHERE 1=1 "
            + " AND (c.maNhaThuoc = ?1) "
            + " AND (c.roleTypeId = ?2) "
            + " AND (c.recordStatusId = " + RecordStatusContains.ACTIVE + ")"
            + " AND (c.isDefault = ?3)"
            + " AND (rt.roleName = ?4)"
    )
    Optional<Role> findByMaNhaThuocAndTypeAndIsDefault(String maNhaThuoc, int type, boolean isDefault, String roleName);
    @Query("SELECT c FROM Role c " +
            "join UserRole ur on ur.roleId = c.id " +
            "WHERE 1=1 "
            + " AND (c.maNhaThuoc = ?1 OR  c.isDefault = true) "
            + " AND (c.recordStatusId = " + RecordStatusContains.ACTIVE + ")"
            + " AND (ur.userId = ?2) "
    )
    List<Role> findByMaNhaThuocAndUserId(String maNhaThuoc, Long userId);
    @Query("SELECT c FROM Role c " +
            "join RoleType rt on rt.id = c.roleTypeId " +
            "WHERE 1=1 AND (rt.roleName = 'SuperUser') "
            + " AND (?1 IS NULL OR c.roleName like lower(concat('%',CONCAT(?1,'%')))) "
    )
    List<Role> searchListSystem(String roleName);

    @Query("SELECT c FROM Role c " +
            "WHERE 1=1 "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR  ( c.maNhaThuoc = :#{#param.maNhaThuoc} OR c.isDefault = true)) "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.roleName} IS NULL OR lower(c.roleName) LIKE lower(concat('%',CONCAT(:#{#param.roleName},'%'))))"
            + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
            + " AND (:#{#param.roleTypeId} IS NULL OR c.roleTypeId = :#{#param.roleTypeId}) "
            + " ORDER BY c.id desc"
    )
    List<Role> searchListStaff(@Param("param") RoleReq param);
}
