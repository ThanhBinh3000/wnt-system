package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.OrderStoreMapping;
import vn.com.gsoft.system.model.dto.OrderStoreMappingReq;

import java.util.List;

@Repository
public interface OrderStoreMappingRepository extends BaseRepository<OrderStoreMapping, OrderStoreMappingReq, Long> {
  @Query("SELECT c FROM OrderStoreMapping c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.storeCode} IS NULL OR c.storeCode = :#{#param.storeCode}) "
          + " AND (:#{#param.targetStoreCode} IS NULL OR c.targetStoreCode = :#{#param.targetStoreCode}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.isDefault} IS NULL OR c.isDefault = :#{#param.isDefault}) "
          + " AND (:#{#param.mappingTypeId} IS NULL OR c.mappingTypeId = :#{#param.mappingTypeId}) "
          + " AND (:#{#param.targetStoreCodes} IS NULL OR c.targetStoreCode IN (:#{#param.targetStoreCodes}))"
          + " ORDER BY c.id asc"
  )
  Page<OrderStoreMapping> searchPage(@Param("param") OrderStoreMappingReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM OrderStoreMapping c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.storeCode} IS NULL OR c.storeCode = :#{#param.storeCode}) "
          + " AND (:#{#param.targetStoreCode} IS NULL OR c.targetStoreCode = :#{#param.targetStoreCode}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.isDefault} IS NULL OR c.isDefault = :#{#param.isDefault}) "
          + " AND (:#{#param.mappingTypeId} IS NULL OR c.mappingTypeId = :#{#param.mappingTypeId}) "
          + " AND (:#{#param.targetStoreCodes} IS NULL OR c.targetStoreCode IN (:#{#param.targetStoreCodes}))"
          + " ORDER BY c.id asc"
  )
  List<OrderStoreMapping> searchList(@Param("param") OrderStoreMappingReq param);
}
