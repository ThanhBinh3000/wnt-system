package vn.com.gsoft.system.repository;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.NhanVienNhaThuocs;
import vn.com.gsoft.system.model.dto.NhanVienNhaThuocsReq;

import java.util.List;

@Repository
public interface NhanVienNhaThuocsRepository extends BaseRepository<NhanVienNhaThuocs,NhanVienNhaThuocsReq, Long> {
//    List<NhaThuocs> findByUserId(Long id);

    @Query("SELECT c FROM NhanVienNhaThuocs c " +
            " WHERE 1=1 " +
            "ORDER BY c.id"
    )
    Page<NhanVienNhaThuocs> searchPage(@Param("param") NhanVienNhaThuocsReq param, Pageable pageable);

    @Query("SELECT c FROM NhanVienNhaThuocs c " +
            " WHERE 1=1 " +
            "ORDER BY c.id desc"
    )
    List<NhanVienNhaThuocs> searchList(@Param("param") NhanVienNhaThuocsReq param);

}
