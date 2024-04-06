package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaThuocsRepository extends BaseRepository<NhaThuocs, NhaThuocsReq, Long> {
    @Query("SELECT c FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " ORDER BY c.id desc"
    )
    Page<NhaThuocs> searchPage(@Param("param") NhaThuocsReq param, Pageable pageable);


    @Query("SELECT c FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " ORDER BY c.id desc"
    )
    List<NhaThuocs> searchList(@Param("param") NhaThuocsReq param);

    @Query("SELECT new vn.com.gsoft.system.model.dto.NhaThuocsRes(c.id, c.maNhaThuoc, c.tenNhaThuoc, u.tenDayDu ) " +
            " FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.nhaThuocMaNhaThuoc " +
            " join UserProfile u on nv.userUserId = u.id " +
            " WHERE 1=1 AND nv.role = 'Admin' " +
            " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})" +
            " AND u.id = :#{#param.userIdQueryData} " +
            " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))" +
            " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))" +
            " AND ((:#{#param.textSearch} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))" +
            " OR (:#{#param.textSearch} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))" +
            " ORDER BY c.id desc"
    )
    Page<NhaThuocsRes> searchPageNhaThuoc(@Param("param") NhaThuocsReq req, Pageable pageable);

    Optional<NhaThuocs> findByMaNhaThuoc(String maNhaThuoc);


    @Query("SELECT new vn.com.gsoft.system.model.dto.NhaThuocsRes(c.id, c.maNhaThuoc, c.tenNhaThuoc, u.tenDayDu ) FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.nhaThuocMaNhaThuoc " +
            " join UserProfile u on nv.userUserId = u.id " +
            " WHERE 1=1 AND nv.role = 'Admin' " +
            " AND (c.maNhaThuoc  = ?1)"
    )
    Optional<NhaThuocsRes> findNguoiPhuTrachByMaNhaThuoc(String maNhaThuoc);
}
