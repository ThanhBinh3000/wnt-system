package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.ApplicationSetting;
import vn.com.gsoft.system.model.dto.ApplicationSettingReq;

import java.util.List;

@Repository
public interface ApplicationSettingRepository extends BaseRepository<ApplicationSetting, ApplicationSettingReq, Long> {
  @Query("SELECT c FROM ApplicationSetting c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.settingKey} IS NULL OR c.settingKey = :#{#param.settingKey}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR c.drugStoreId = :#{#param.drugStoreId}) "
          + " AND (:#{#param.activated} IS NULL OR c.activated = :#{#param.activated}) "
          + " ORDER BY c.id asc"
  )
  Page<ApplicationSetting> searchPage(@Param("param") ApplicationSettingReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM ApplicationSetting c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.settingKey} IS NULL OR c.settingKey = :#{#param.settingKey}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR c.drugStoreId = :#{#param.drugStoreId}) "
          + " AND (:#{#param.activated} IS NULL OR c.activated = :#{#param.activated}) "
          + " ORDER BY c.id asc"
  )
  List<ApplicationSetting> searchList(@Param("param") ApplicationSettingReq param);

  List<ApplicationSetting> findByDrugStoreIdAndSettingKey(String storeCode, String notAllowDeliverOverQuantity);
}
