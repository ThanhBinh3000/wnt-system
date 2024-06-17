package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.NhomNhaCungCaps;
import vn.com.gsoft.system.model.dto.NhomNhaCungCapsReq;

import java.util.List;

@Repository
public interface NhomNhaCungCapsRepository extends BaseRepository<NhomNhaCungCaps, NhomNhaCungCapsReq, Long> {
  @Query("SELECT c FROM NhomNhaCungCaps c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.tenNhomNhaCungCap} IS NULL OR lower(c.tenNhomNhaCungCap) LIKE lower(concat('%',CONCAT(:#{#param.tenNhomNhaCungCap},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " ORDER BY c.id desc"
  )
  Page<NhomNhaCungCaps> searchPage(@Param("param") NhomNhaCungCapsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM NhomNhaCungCaps c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.tenNhomNhaCungCap} IS NULL OR lower(c.tenNhomNhaCungCap) LIKE lower(concat('%',CONCAT(:#{#param.tenNhomNhaCungCap},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " ORDER BY c.id desc"
  )
  List<NhomNhaCungCaps> searchList(@Param("param") NhomNhaCungCapsReq param);

}
