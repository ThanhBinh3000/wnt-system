package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.ChangePasswordReq;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.model.dto.UserProfileRes;
import vn.com.gsoft.system.model.dto.UserStaffProfileRes;
import vn.com.gsoft.system.repository.UserProfileRepository;
import vn.com.gsoft.system.service.UserProfileService;
import vn.com.gsoft.system.util.system.DataUtils;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile, UserProfileReq, Long> implements UserProfileService {

    private UserProfileRepository hdrRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository hdrRepo, PasswordEncoder passwordEncoder) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserProfile> searchPage(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusIds(List.of(0L, 1L, 2L));
        req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        return hdrRepo.searchPage(req, pageable);
    }

    @Override
    public Page<UserProfileRes> searchPageUserManagement(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        return DataUtils.convertPage(hdrRepo.searchPageUserManagement(req, pageable), UserProfileRes.class);
    }

    @Override
    public Page<UserStaffProfileRes> searchPageStaffManagement(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        return DataUtils.convertPage(hdrRepo.searchPageStaffManagement(req, pageable), UserStaffProfileRes.class);
    }

    @Override
    public Boolean changePassword(ChangePasswordReq req) throws Exception {
        Long userId = null;
        if(!req.getNewPassword().equals(req.getConfirmPassword())){
            throw new Exception("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
        }
        if (req.getUserId() == null) {
            userId = getLoggedUser().getId();
        } else {
            userId = req.getUserId();
        }
        Optional<UserProfile> userProfile = this.hdrRepo.findById(userId);
        if (userProfile.isPresent()) {
            if (this.passwordEncoder.matches(req.getOldPassword(), userProfile.get().getPassword())) {
                userProfile.get().setPassword(this.passwordEncoder.encode(req.getNewPassword()));
                return true;
            }else {
                throw new Exception("Mật khẩu cũ không đúng!");
            }
        }

        return false;
    }
}
