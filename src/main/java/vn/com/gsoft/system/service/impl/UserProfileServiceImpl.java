package vn.com.gsoft.system.service.impl;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.constant.NhanVienRole;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.*;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.UserProfileRepository;
import vn.com.gsoft.system.service.NhaThuocsService;
import vn.com.gsoft.system.service.NhanVienNhaThuocsService;
import vn.com.gsoft.system.service.UserProfileService;
import vn.com.gsoft.system.util.system.DataUtils;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile, UserProfileReq, Long> implements UserProfileService {

    private UserProfileRepository hdrRepo;
    private PasswordEncoder passwordEncoder;
    private NhanVienNhaThuocsService nhanVienNhaThuocsService;
    private NhaThuocsService nhaThuocsService;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository hdrRepo, PasswordEncoder passwordEncoder, NhanVienNhaThuocsService nhanVienNhaThuocsService, NhaThuocsService nhaThuocsService) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.passwordEncoder = passwordEncoder;
        this.nhanVienNhaThuocsService = nhanVienNhaThuocsService;
        this.nhaThuocsService = nhaThuocsService;
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
        Long userId = getLoggedUser().getId();
        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            throw new Exception("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
        }
        Optional<UserProfile> userProfile = this.hdrRepo.findById(userId);
        if (userProfile.isPresent()) {
            if (this.passwordEncoder.matches(req.getOldPassword(), userProfile.get().getPassword())) {
                userProfile.get().setPassword(this.passwordEncoder.encode(req.getNewPassword()));
                return true;
            } else {
                throw new Exception("Mật khẩu cũ không đúng!");
            }
        }

        return false;
    }

    @Override
    public Boolean resetPassword(ChangePasswordReq req) throws Exception {
        Long userId = null;
        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            throw new Exception("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
        }
        if (req.getUserId() == null) {
            throw new Exception("Không được để trống userId!");
        } else {
            userId = req.getUserId();
        }
        Optional<UserProfile> userProfile = this.hdrRepo.findById(userId);
        if (userProfile.isPresent()) {
            userProfile.get().setPassword(this.passwordEncoder.encode(req.getNewPassword()));
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public UserProfile insertUser(UserProfileReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (req.getMaNhaThuoc() == null)
            throw new Exception("maNhaThuoc không được để trống!");
        Optional<NhaThuocs> nhaThuocs = this.nhaThuocsService.findByMaNhaThuoc(req.getMaNhaThuoc());
        if (nhaThuocs.isEmpty()) {
            throw new Exception("Không tìm thấy maNhaThuoc!");
        }
        Optional<UserProfile> userProfile = this.hdrRepo.findByUserName(req.getUserName());
        if (userProfile.isPresent()) {
            throw new Exception("UserName đã tồn tại!");
        }
        UserProfile e = new UserProfile();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e = hdrRepo.save(e);
        NhanVienNhaThuocsReq nv = new NhanVienNhaThuocsReq();
        nv.setUserUserId(e.getCreatedByUserId());
        nv.setRole(NhanVienRole.ADMIN);
        nv.setNhaThuocMaNhaThuoc(req.getMaNhaThuoc());
        nv.setStoreId(req.getStoreId());
        this.nhanVienNhaThuocsService.create(nv);

        return e;
    }

    @Override
    @Transactional
    public UserProfile updateUser(UserProfileReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<UserProfile> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getUserName().equals(req.getUserName())) {
            Optional<UserProfile> userProfile = this.hdrRepo.findByUserName(req.getUserName());
            if (userProfile.isPresent()) {
                throw new Exception("UserName đã tồn tại!");
            }
        }
        UserProfile e = optional.get();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        hdrRepo.save(e);
        return e;
    }

    @Override
    @Transactional
    public UserProfile insertStaff(UserProfileReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<UserProfile> userProfile = this.hdrRepo.findByUserName(req.getUserName());
        if (userProfile.isPresent()) {
            throw new Exception("UserName đã tồn tại!");
        }
        UserProfile e = new UserProfile();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e = hdrRepo.save(e);
        NhanVienNhaThuocsReq nv = new NhanVienNhaThuocsReq();
        nv.setUserUserId(e.getCreatedByUserId());
        nv.setRole(NhanVienRole.USER);
        nv.setNhaThuocMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        nv.setStoreId(req.getStoreId());
        this.nhanVienNhaThuocsService.create(nv);
        return e;
    }

    @Override
    @Transactional
    public UserProfile updateStaff(UserProfileReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<UserProfile> optional = hdrRepo.findById(req.getId());
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        if (!optional.get().getUserName().equals(req.getUserName())) {
            Optional<UserProfile> userProfile = this.hdrRepo.findByUserName(req.getUserName());
            if (userProfile.isPresent()) {
                throw new Exception("UserName đã tồn tại!");
            }
        }
        UserProfile e = optional.get();
        BeanUtils.copyProperties(req, e, "id");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        hdrRepo.save(e);
        return e;
    }
}
