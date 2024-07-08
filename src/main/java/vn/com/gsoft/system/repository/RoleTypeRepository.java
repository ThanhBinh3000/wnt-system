package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.Entity;
import vn.com.gsoft.system.entity.RoleType;
import vn.com.gsoft.system.model.dto.EntityReq;
import vn.com.gsoft.system.model.dto.RoleTypeReq;

import java.util.List;

@Repository
public interface RoleTypeRepository extends BaseRepository<RoleType, RoleTypeReq, Long> {
    @Query("SELECT c FROM RoleType c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " ORDER BY c.id desc"
    )
    Page<RoleType> searchPage(@Param("param") RoleTypeReq param, Pageable pageable);


    @Query("SELECT c FROM RoleType c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " ORDER BY c.id desc"
    )
    List<RoleType> searchList(@Param("param") RoleTypeReq param);
}
