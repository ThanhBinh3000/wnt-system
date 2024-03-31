package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;
public interface NhaThuocsService extends BaseService<NhaThuocs, NhaThuocsReq, Long> {


    Page<NhaThuocsRes> searchPageNhaThuoc(NhaThuocsReq objReq) throws Exception;

}