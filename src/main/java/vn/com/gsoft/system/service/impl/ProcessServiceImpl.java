package vn.com.gsoft.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.model.dto.ProcessReq;
import vn.com.gsoft.system.repository.ProcessDtlRepository;
import vn.com.gsoft.system.repository.ProcessRepository;
import vn.com.gsoft.system.service.ProcessService;
import vn.com.gsoft.system.entity.Process;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private ProcessRepository hdrRepo;
    @Autowired
    private ProcessDtlRepository processDtlRepository;

    @Override
    public Page<Process> searchPage(ProcessReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchPage(req, pageable);
    }

    @Override
    public List<Process> searchList(ProcessReq req) throws Exception {
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        return hdrRepo.searchList(req);
    }

    @Override
    public Process detail(Long id) throws Exception {
        Optional<Process> process = hdrRepo.findById(id);
        if (process.isPresent()) {
            process.get().setDtls(processDtlRepository.findByHdrId(process.get().getId()));
            return process.get();
        }
        return null;
    }
}
