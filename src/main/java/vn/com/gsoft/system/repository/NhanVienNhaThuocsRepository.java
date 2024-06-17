package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.NhanVienNhaThuocs;
import vn.com.gsoft.system.model.dto.NhanVienNhaThuocsReq;

import java.util.List;

@Repository
public interface NhanVienNhaThuocsRepository extends BaseRepository<NhanVienNhaThuocs, NhanVienNhaThuocsReq, Long> {
    @Query("SELECT c FROM NhanVienNhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.role} IS NULL OR lower(c.role) LIKE lower(concat('%',CONCAT(:#{#param.role},'%'))))"
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
            + " AND (:#{#param.userUserId} IS NULL OR c.userUserId = :#{#param.userUserId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " ORDER BY c.id desc"
    )
    Page<NhanVienNhaThuocs> searchPage(@Param("param") NhanVienNhaThuocsReq param, Pageable pageable);


    @Query("SELECT c FROM NhanVienNhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.role} IS NULL OR lower(c.role) LIKE lower(concat('%',CONCAT(:#{#param.role},'%'))))"
            + " AND (:#{#param.nhaThuocMaNhaThuoc} IS NULL OR lower(c.nhaThuocMaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.nhaThuocMaNhaThuoc},'%'))))"
            + " AND (:#{#param.userUserId} IS NULL OR c.userUserId = :#{#param.userUserId}) "
            + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
            + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
            + " ORDER BY c.id desc"
    )
    List<NhanVienNhaThuocs> searchList(@Param("param") NhanVienNhaThuocsReq param);

    NhanVienNhaThuocs findByNhaThuocMaNhaThuocAndRole(String maNhaThuoc, String role);
}
