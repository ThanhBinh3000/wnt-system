package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;

import java.util.List;

@Repository
public interface NhaThuocsRepository extends BaseRepository<NhaThuocs, NhaThuocsReq, Long> {
  @Query("SELECT c FROM NhaThuocs c " +
         "WHERE 1=1 "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"

          + " ORDER BY c.id desc"
  )
  Page<NhaThuocs> searchPage(@Param("param") NhaThuocsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM NhaThuocs c " +
         "WHERE 1=1 "
          + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
          + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"

          + " ORDER BY c.id desc"
  )
  List<NhaThuocs> searchList(@Param("param") NhaThuocsReq param);

}
