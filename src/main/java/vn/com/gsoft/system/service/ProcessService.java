package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.Process;
import vn.com.gsoft.system.model.dto.ProcessReq;

import java.util.List;

public interface ProcessService {
    Page<Process> searchPage(ProcessReq req) throws Exception;

    List<Process> searchList(ProcessReq req) throws Exception;
    Process detail(Long id) throws Exception;
}
