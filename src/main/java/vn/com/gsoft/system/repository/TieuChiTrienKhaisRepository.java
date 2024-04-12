package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.TieuChiTrienKhais;
import vn.com.gsoft.system.model.dto.TieuChiTrienKhaisReq;

import java.util.List;

@Repository
public interface TieuChiTrienKhaisRepository extends BaseRepository<TieuChiTrienKhais, TieuChiTrienKhaisReq, Long> {
  @Query("SELECT c FROM TieuChiTrienKhais c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.type} IS NULL OR c.type = :#{#param.type}) "
          + " AND (:#{#param.name} IS NULL OR lower(c.name) LIKE lower(concat('%',CONCAT(:#{#param.name},'%'))))"
          + " ORDER BY c.id asc"
  )
  Page<TieuChiTrienKhais> searchPage(@Param("param") TieuChiTrienKhaisReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM TieuChiTrienKhais c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.type} IS NULL OR c.type = :#{#param.type}) "
          + " AND (:#{#param.name} IS NULL OR lower(c.name) LIKE lower(concat('%',CONCAT(:#{#param.name},'%'))))"
          + " ORDER BY c.id asc"
  )
  List<TieuChiTrienKhais> searchList(@Param("param") TieuChiTrienKhaisReq param);
}
