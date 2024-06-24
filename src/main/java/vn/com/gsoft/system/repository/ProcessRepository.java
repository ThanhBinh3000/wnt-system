package vn.com.gsoft.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.entity.Process;
import vn.com.gsoft.system.model.dto.ProcessReq;

import java.util.List;

public interface ProcessRepository extends BaseRepository<Process, ProcessReq, Long> {
    @Query("SELECT c FROM Process c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR  c.maNhaThuoc = :#{#param.maNhaThuoc}) "
            + " ORDER BY c.id desc"
    )
    Page<Process> searchPage(@Param("param") ProcessReq param, Pageable pageable);


    @Query("SELECT c FROM Process c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR  c.maNhaThuoc = :#{#param.maNhaThuoc}) "
            + " ORDER BY c.id desc"
    )
    List<Process> searchList(@Param("param") ProcessReq param);
}
