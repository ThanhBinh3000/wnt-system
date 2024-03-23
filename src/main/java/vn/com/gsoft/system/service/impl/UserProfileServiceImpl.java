package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.model.system.UserProfileReq;
import vn.com.gsoft.system.repository.UserProfileRepository;
import vn.com.gsoft.system.service.UserProfileService;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserProfileServiceImpl extends BaseServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository hdrRepo;

	@Override
	public Page<UserProfile> searchPage(UserProfileReq req) throws Exception {
		Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
		return hdrRepo.searchPage(req, pageable);
	}

	@Override
	public List<UserProfile> searchList(UserProfileReq req) throws Exception {
		return hdrRepo.searchList(req);
	}

	@Override
	public UserProfile create(UserProfileReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");
		UserProfile nhaThuocs = new UserProfile();
		BeanUtils.copyProperties(req, nhaThuocs, "id");
		hdrRepo.save(nhaThuocs);
		return nhaThuocs;
	}

	@Override
	public UserProfile update(UserProfileReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<UserProfile> optional = hdrRepo.findById(req.getUserId());
		if (optional.isEmpty()) {
			throw new Exception("Không tìm thấy dữ liệu.");
		}

		UserProfile nhaThuocs = optional.get();
		BeanUtils.copyProperties(req, nhaThuocs, "id");
		hdrRepo.save(nhaThuocs);
		return nhaThuocs;
	}

	@Override
	public UserProfile detail(Long id) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null)
			throw new Exception("Bad request.");

		Optional<UserProfile> optional = hdrRepo.findById(id);
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

		Optional<UserProfile> optional = hdrRepo.findById(id);
		if (optional.isEmpty()) {
			throw new Exception("Không tìm thấy dữ liệu.");
		}
		hdrRepo.delete(optional.get());
	}

}
