package vn.com.gsoft.system.repository;

import org.springframework.data.jpa.repository.Modifying;
import vn.com.gsoft.system.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.model.dto.UserRoleReq;

import java.util.List;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, UserRoleReq, Long> {
    @Query("SELECT c FROM UserRole c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
            + " AND (:#{#param.roleId} IS NULL OR c.roleId = :#{#param.roleId}) "
            + " ORDER BY c.id desc"
    )
    Page<UserRole> searchPage(@Param("param") UserRoleReq param, Pageable pageable);


    @Query("SELECT c FROM UserRole c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
            + " AND (:#{#param.roleId} IS NULL OR c.roleId = :#{#param.roleId}) "
            + " ORDER BY c.id desc"
    )
    List<UserRole> searchList(@Param("param") UserRoleReq param);

    UserRole findByUserIdAndRoleId(Long userId, Long roleId);
    @Modifying
    @Query("delete from UserRole b where b.roleId in (select r.id from Role r where (r.maNhaThuoc =?1 or r.isDefault = true) and r.roleTypeId != 1) and b.userId =?2")
    void deleteByMaNhaThuocAndUserId(String maNhaThuoc, Long userId);

    @Modifying
    @Query("delete from UserRole b where b.roleId in (select r.id from Role r where (r.maNhaThuoc =?1 or r.isDefault = true) and r.roleTypeId = 1) and b.userId =?2")
    void deleteByMaNhaThuocAndUserIdSystem(String maNhaThuoc, Long userId);
}
