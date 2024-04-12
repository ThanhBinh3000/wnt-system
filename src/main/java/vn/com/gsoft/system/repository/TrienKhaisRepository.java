package vn.com.gsoft.system.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.TrienKhais;
import vn.com.gsoft.system.model.dto.TrienKhaisReq;

import java.util.List;

@Repository
public interface TrienKhaisRepository extends BaseRepository<TrienKhais, TrienKhaisReq, Long> {
  @Query("SELECT c FROM TrienKhais c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc}) "
          + " AND (:#{#param.tieuChiTrienKhaiId} IS NULL OR c.tieuChiTrienKhaiId = :#{#param.tieuChiTrienKhaiId}) "
          + " AND (:#{#param.active} IS NULL OR c.active = :#{#param.active}) "
          + " ORDER BY c.id asc"
  )
  Page<TrienKhais> searchPage(@Param("param") TrienKhaisReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM TrienKhais c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc}) "
          + " AND (:#{#param.tieuChiTrienKhaiId} IS NULL OR c.tieuChiTrienKhaiId = :#{#param.tieuChiTrienKhaiId}) "
          + " AND (:#{#param.active} IS NULL OR c.active = :#{#param.active}) "
          + " ORDER BY c.id asc"
  )
  List<TrienKhais> searchList(@Param("param") TrienKhaisReq param);

  @Query("SELECT  tctk.id as id, tctk.name as name, tctk.type AS type , tk.maNhaThuoc AS maNhaThuoc, tk.active as active "
          + "FROM TrienKhais tk "
          + "JOIN TieuChiTrienKhais tctk ON  tctk.id = tk.tieuChiTrienKhaiId "
          + " WHERE 1 = 1"
          + " AND (:#{#param.maNhaThuoc} IS NULL OR tk.maNhaThuoc = :#{#param.maNhaThuoc}) "
          + " AND (:#{#param.tieuChiTrienKhaiId} IS NULL OR tk.tieuChiTrienKhaiId = :#{#param.tieuChiTrienKhaiId}) "
          + " AND (:#{#param.active} IS NULL OR tk.active = :#{#param.active}) "
          + " AND (:#{#param.type} IS NULL OR tctk.type = :#{#param.type}) "
          + " AND (:#{#param.textSearch} IS NULL OR lower(tctk.name) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
          + " ORDER BY tctk.id asc")
  List<Tuple> searchListTrienKhaiManagement(@Param("param") TrienKhaisReq param);
}
