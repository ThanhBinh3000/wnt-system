package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.NhanVienNhaThuocs;
import vn.com.gsoft.system.model.dto.NhanVienNhaThuocsReq;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.NhanVienNhaThuocsRepository;
import vn.com.gsoft.system.service.NhanVienNhaThuocsService;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class NhanVienNhaThuocsServiceImpl extends BaseServiceImpl<NhanVienNhaThuocs, NhanVienNhaThuocsReq,Long> implements NhanVienNhaThuocsService {

	private NhanVienNhaThuocsRepository hdrRepo;
	@Autowired
	public NhanVienNhaThuocsServiceImpl(NhanVienNhaThuocsRepository hdrRepo) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
	}

	@Override
	public List<NhanVienNhaThuocs> findByUserUserId(Long userId) {
		return hdrRepo.findByUserUserId(userId);
	}
}
