package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.TypeBasis;
import vn.com.gsoft.system.model.dto.TypeBasisReq;

import java.util.List;

@Repository
public interface TypeBasisRepository extends BaseRepository<TypeBasis, TypeBasisReq, Long> {
  @Query("SELECT c FROM TypeBasis c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.idType} IS NULL OR c.idType = :#{#param.idType}) "
          + " AND (:#{#param.nameType} IS NULL OR lower(c.nameType) LIKE lower(concat('%',CONCAT(:#{#param.nameType},'%'))))"
          + " ORDER BY c.idType asc"
  )
  Page<TypeBasis> searchPage(@Param("param") TypeBasisReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM TypeBasis c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.idType} IS NULL OR c.idType = :#{#param.idType}) "
          + " AND (:#{#param.nameType} IS NULL OR lower(c.nameType) LIKE lower(concat('%',CONCAT(:#{#param.nameType},'%'))))"
          + " ORDER BY c.idType asc"
  )
  List<TypeBasis> searchList(@Param("param") TypeBasisReq param);

}
