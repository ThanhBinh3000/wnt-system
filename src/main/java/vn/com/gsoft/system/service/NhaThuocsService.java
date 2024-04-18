package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocDongBoPhieuReq;
import vn.com.gsoft.system.model.dto.NhaThuocDongBoPhieuRes;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;

import java.util.Optional;

public interface NhaThuocsService extends BaseService<NhaThuocs, NhaThuocsReq, Long> {

    NhaThuocs detail (String code) throws Exception;
    Page<NhaThuocsRes> searchPageNhaThuoc(NhaThuocsReq objReq) throws Exception;

    Optional<NhaThuocs> findByMaNhaThuoc(String maNhaThuoc);

    String getNewStoreCode() throws Exception;

    Page<NhaThuocDongBoPhieuRes> searchPageNhaThuocDongBoPhieu(NhaThuocDongBoPhieuReq req) throws Exception;
    Page<NhaThuocs> searchPageNhaThuocTong(NhaThuocsReq req) throws Exception;

}