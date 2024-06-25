package vn.com.gsoft.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.ProcessDtl;

import java.util.List;

@Repository
public interface ProcessDtlRepository extends CrudRepository<ProcessDtl, Long> {
    List<ProcessDtl> findByHdrId(Long hdrId);
}
