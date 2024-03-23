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
public class NhanVienNhaThuocsServiceImpl extends BaseServiceImpl implements NhanVienNhaThuocsService {

	@Autowired
	private NhanVienNhaThuocsRepository hdrRepo;

	@Override
	public Page<NhanVienNhaThuocs> searchPage(NhanVienNhaThuocsReq req) throws Exception {
		Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
		return hdrRepo.searchPage(req, pageable);
	}

	@Override
	public List<NhanVienNhaThuocs> searchList(NhanVienNhaThuocsReq req) throws Exception {
		return hdrRepo.searchList(req);
	}

	@Override
	public NhanVienNhaThuocs create(NhanVienNhaThuocsReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");
		NhanVienNhaThuocs nhaThuocs = new NhanVienNhaThuocs();
		BeanUtils.copyProperties(req, nhaThuocs, "id");
		hdrRepo.save(nhaThuocs);
		return nhaThuocs;
	}

	@Override
	public NhanVienNhaThuocs update(NhanVienNhaThuocsReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<NhanVienNhaThuocs> optional = hdrRepo.findById(req.getId());
		if (optional.isEmpty()) {
			throw new Exception("Không tìm thấy dữ liệu.");
		}

		NhanVienNhaThuocs nhaThuocs = optional.get();
		BeanUtils.copyProperties(req, nhaThuocs, "id");
		hdrRepo.save(nhaThuocs);
		return nhaThuocs;
	}

	@Override
	public NhanVienNhaThuocs detail(Long id) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<NhanVienNhaThuocs> optional = hdrRepo.findById(id);
		if (optional.isEmpty()) {
			throw new Exception("Không tìm thấy dữ liệu.");
		}
		return optional.get();
	}

	@Override
	public void delete(Long id) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<NhanVienNhaThuocs> optional = hdrRepo.findById(id);
		if (optional.isEmpty()) {
			throw new Exception("Không tìm thấy dữ liệu.");
		}
		hdrRepo.delete(optional.get());
	}

}
