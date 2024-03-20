package vn.com.gsoft.system.repository;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.Department;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.system.NhaThuocsReq;

import java.util.List;

@Repository
public interface NhaThuocsRepository extends CrudRepository<NhaThuocs, Long> {
//    List<NhaThuocs> findByUserId(Long id);

    @Query("SELECT c FROM NhaThuocs c " +
            " WHERE 1=1 " +
            "ORDER BY c.modified desc , c.created desc, c.id desc"
    )
    Page<NhaThuocs> searchPage(@Param("param") NhaThuocsReq param, Pageable pageable);
}
