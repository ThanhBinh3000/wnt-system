package vn.com.gsoft.system.service;

import vn.com.gsoft.system.entity.NhanVienNhaThuocs;
import vn.com.gsoft.system.model.dto.NhanVienNhaThuocsReq;

import java.util.List;

public interface NhanVienNhaThuocsService extends BaseService<NhanVienNhaThuocs, NhanVienNhaThuocsReq, Long> {
    List<NhanVienNhaThuocs> findByUserUserId(Long userId);
}