package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.Entity;
import vn.com.gsoft.system.model.dto.EntityReq;

import java.util.List;

@Repository
public interface EntityRepository extends BaseRepository<Entity, EntityReq, Long> {
  @Query("SELECT c FROM Entity c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
          + " ORDER BY c.id desc"
  )
  Page<Entity> searchPage(@Param("param") EntityReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM Entity c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
          + " ORDER BY c.id desc"
  )
  List<Entity> searchList(@Param("param") EntityReq param);

  @Query("SELECT c FROM Entity c " +
          " JOIN PrivilegeEntity d on d.entityId = c.id " +
          "WHERE 1=1 "
          + " AND d.privilegeId = ?1 "
          + " ORDER BY c.id desc"
  )
  List<Entity> findByPrivilegeId(Long privilegeId);

}
