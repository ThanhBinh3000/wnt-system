package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.NhomKhachHangs;
import vn.com.gsoft.system.model.dto.NhomKhachHangsReq;

import java.util.List;

@Repository
public interface NhomKhachHangsRepository extends BaseRepository<NhomKhachHangs, NhomKhachHangsReq, Long> {
  @Query("SELECT c FROM NhomKhachHangs c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.tenNhomKhachHang} IS NULL OR lower(c.tenNhomKhachHang) LIKE lower(concat('%',CONCAT(:#{#param.tenNhomKhachHang},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.groupTypeId} IS NULL OR c.groupTypeId = :#{#param.groupTypeId}) "
          + " AND (:#{#param.fullName} IS NULL OR lower(c.fullName) LIKE lower(concat('%',CONCAT(:#{#param.fullName},'%'))))"
          + " AND (:#{#param.idCard} IS NULL OR lower(c.idCard) LIKE lower(concat('%',CONCAT(:#{#param.idCard},'%'))))"
          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate >= :#{#param.birthDateFrom}) "
          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate <= :#{#param.birthDateTo}) "
          + " AND (:#{#param.classId} IS NULL OR lower(c.classId) LIKE lower(concat('%',CONCAT(:#{#param.classId},'%'))))"
          + " AND (:#{#param.mobile} IS NULL OR lower(c.mobile) LIKE lower(concat('%',CONCAT(:#{#param.mobile},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " ORDER BY c.id desc"
  )
  Page<NhomKhachHangs> searchPage(@Param("param") NhomKhachHangsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM NhomKhachHangs c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.tenNhomKhachHang} IS NULL OR lower(c.tenNhomKhachHang) LIKE lower(concat('%',CONCAT(:#{#param.tenNhomKhachHang},'%'))))"
          + " AND (:#{#param.ghiChu} IS NULL OR lower(c.ghiChu) LIKE lower(concat('%',CONCAT(:#{#param.ghiChu},'%'))))"
          + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
          + " AND (:#{#param.modified} IS NULL OR c.modified >= :#{#param.modifiedFrom}) "
          + " AND (:#{#param.modified} IS NULL OR c.modified <= :#{#param.modifiedTo}) "
          + " AND (:#{#param.createdByUserId} IS NULL OR c.createdByUserId = :#{#param.createdByUserId}) "
          + " AND (:#{#param.modifiedByUserId} IS NULL OR c.modifiedByUserId = :#{#param.modifiedByUserId}) "
          + " AND (:#{#param.groupTypeId} IS NULL OR c.groupTypeId = :#{#param.groupTypeId}) "
          + " AND (:#{#param.fullName} IS NULL OR lower(c.fullName) LIKE lower(concat('%',CONCAT(:#{#param.fullName},'%'))))"
          + " AND (:#{#param.idCard} IS NULL OR lower(c.idCard) LIKE lower(concat('%',CONCAT(:#{#param.idCard},'%'))))"
          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate >= :#{#param.birthDateFrom}) "
          + " AND (:#{#param.birthDate} IS NULL OR c.birthDate <= :#{#param.birthDateTo}) "
          + " AND (:#{#param.classId} IS NULL OR lower(c.classId) LIKE lower(concat('%',CONCAT(:#{#param.classId},'%'))))"
          + " AND (:#{#param.mobile} IS NULL OR lower(c.mobile) LIKE lower(concat('%',CONCAT(:#{#param.mobile},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
          + " ORDER BY c.id desc"
  )
  List<NhomKhachHangs> searchList(@Param("param") NhomKhachHangsReq param);

}
