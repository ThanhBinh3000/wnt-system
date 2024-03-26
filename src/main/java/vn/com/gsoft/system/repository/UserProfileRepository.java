package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.UserProfileReq;

import java.util.List;

@Repository
public interface UserProfileRepository extends BaseRepository<UserProfile, UserProfileReq, Long> {
  @Query("SELECT c FROM UserProfile c " +
         "WHERE 1=1 "
          + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
          + " AND (:#{#param.userName} IS NULL OR lower(c.userName) LIKE lower(concat('%',CONCAT(:#{#param.userName},'%'))))"
          + " AND (:#{#param.tenDayDu} IS NULL OR lower(c.tenDayDu) LIKE lower(concat('%',CONCAT(:#{#param.tenDayDu},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.soCMT} IS NULL OR lower(c.soCMT) LIKE lower(concat('%',CONCAT(:#{#param.soCMT},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.regionId} IS NULL OR c.regionId = :#{#param.regionId}) "
          + " AND (:#{#param.cityId} IS NULL OR c.cityId = :#{#param.cityId}) "
          + " AND (:#{#param.wardId} IS NULL OR c.wardId = :#{#param.wardId}) "
          + " AND (:#{#param.addresses} IS NULL OR lower(c.addresses) LIKE lower(concat('%',CONCAT(:#{#param.addresses},'%'))))"
          + " AND (:#{#param.tokenDevice} IS NULL OR lower(c.tokenDevice) LIKE lower(concat('%',CONCAT(:#{#param.tokenDevice},'%'))))"
          + " AND (:#{#param.tokenBrowser} IS NULL OR lower(c.tokenBrowser) LIKE lower(concat('%',CONCAT(:#{#param.tokenBrowser},'%'))))"
          + " AND (:#{#param.tokenDevice2} IS NULL OR lower(c.tokenDevice2) LIKE lower(concat('%',CONCAT(:#{#param.tokenDevice2},'%'))))"
          + " AND (:#{#param.mobileDeviceId} IS NULL OR lower(c.mobileDeviceId) LIKE lower(concat('%',CONCAT(:#{#param.mobileDeviceId},'%'))))"
          + " AND (:#{#param.password} IS NULL OR lower(c.password) LIKE lower(concat('%',CONCAT(:#{#param.password},'%'))))"
          + " AND (:#{#param.entityId} IS NULL OR c.entityId = :#{#param.entityId}) "
          + " ORDER BY c.userId desc"
  )
  Page<UserProfile> searchPage(@Param("param") UserProfileReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM UserProfile c " +
         "WHERE 1=1 "
          + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
          + " AND (:#{#param.userName} IS NULL OR lower(c.userName) LIKE lower(concat('%',CONCAT(:#{#param.userName},'%'))))"
          + " AND (:#{#param.tenDayDu} IS NULL OR lower(c.tenDayDu) LIKE lower(concat('%',CONCAT(:#{#param.tenDayDu},'%'))))"
          + " AND (:#{#param.email} IS NULL OR lower(c.email) LIKE lower(concat('%',CONCAT(:#{#param.email},'%'))))"
          + " AND (:#{#param.soDienThoai} IS NULL OR lower(c.soDienThoai) LIKE lower(concat('%',CONCAT(:#{#param.soDienThoai},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.soCMT} IS NULL OR lower(c.soCMT) LIKE lower(concat('%',CONCAT(:#{#param.soCMT},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " AND (:#{#param.regionId} IS NULL OR c.regionId = :#{#param.regionId}) "
          + " AND (:#{#param.cityId} IS NULL OR c.cityId = :#{#param.cityId}) "
          + " AND (:#{#param.wardId} IS NULL OR c.wardId = :#{#param.wardId}) "
          + " AND (:#{#param.addresses} IS NULL OR lower(c.addresses) LIKE lower(concat('%',CONCAT(:#{#param.addresses},'%'))))"
          + " AND (:#{#param.tokenDevice} IS NULL OR lower(c.tokenDevice) LIKE lower(concat('%',CONCAT(:#{#param.tokenDevice},'%'))))"
          + " AND (:#{#param.tokenBrowser} IS NULL OR lower(c.tokenBrowser) LIKE lower(concat('%',CONCAT(:#{#param.tokenBrowser},'%'))))"
          + " AND (:#{#param.tokenDevice2} IS NULL OR lower(c.tokenDevice2) LIKE lower(concat('%',CONCAT(:#{#param.tokenDevice2},'%'))))"
          + " AND (:#{#param.mobileDeviceId} IS NULL OR lower(c.mobileDeviceId) LIKE lower(concat('%',CONCAT(:#{#param.mobileDeviceId},'%'))))"
          + " AND (:#{#param.password} IS NULL OR lower(c.password) LIKE lower(concat('%',CONCAT(:#{#param.password},'%'))))"
          + " AND (:#{#param.entityId} IS NULL OR c.entityId = :#{#param.entityId}) "
          + " ORDER BY c.userId desc"
  )
  List<UserProfile> searchList(@Param("param") UserProfileReq param);

}
