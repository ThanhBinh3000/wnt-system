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
import vn.com.gsoft.system.constant.NhanVienRoleConstant;
import vn.com.gsoft.system.constant.RecordStatusContains;
import vn.com.gsoft.system.constant.RoleTypeConstant;
import vn.com.gsoft.system.entity.*;
import vn.com.gsoft.system.model.dto.*;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.RoleRepository;
import vn.com.gsoft.system.repository.UserProfileRepository;
import vn.com.gsoft.system.repository.UserRoleRepository;
import vn.com.gsoft.system.service.*;
import vn.com.gsoft.system.util.system.DataUtils;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile, UserProfileReq, Long> implements UserProfileService {

    private final RoleRepository roleRepository;
    private UserProfileRepository hdrRepo;
    private PasswordEncoder passwordEncoder;
    private NhanVienNhaThuocsService nhanVienNhaThuocsService;
    private NhaThuocsService nhaThuocsService;
    private RoleService roleService;
    private UserRoleService userRoleService;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository hdrRepo, PasswordEncoder passwordEncoder, NhanVienNhaThuocsService nhanVienNhaThuocsService, NhaThuocsService nhaThuocsService, RoleService roleService, UserRoleService userRoleService, RoleRepository roleRepository,UserRoleRepository userRoleRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.passwordEncoder = passwordEncoder;
        this.nhanVienNhaThuocsService = nhanVienNhaThuocsService;
        this.nhaThuocsService = nhaThuocsService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.roleRepository = roleRepository;
        this.userRoleRepository =userRoleRepository;
    }

    @Override
    public Page<UserProfile> searchPage(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusIds(List.of(0L, 1L, 2L));
        req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        Page<UserProfile> userProfiles = hdrRepo.searchPage(req, pageable);
        for(UserProfile up: userProfiles){
            up.setRoles(roleRepository.findByMaNhaThuocAndUserId(getLoggedUser().getNhaThuoc().getMaNhaThuoc(), up.getId()));
        }
        return userProfiles;
    }

    @Override
    public Page<UserProfileRes> searchPageUserManagement(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        Page<UserProfileRes> userProfiles = DataUtils.convertPage(hdrRepo.searchPageUserManagement(req, pageable), UserProfileRes.class);
        for(UserProfileRes up: userProfiles){
            List<NhanVienNhaThuocs> nhanVienNhaThuocs = nhanVienNhaThuocsService.findByUserUserId(up.getId());
            if(!nhanVienNhaThuocs.isEmpty()){
                up.setMaNhaThuocs(nhanVienNhaThuocs.stream().map(NhanVienNhaThuocs::getNhaThuocMaNhaThuoc).toList());
                for(NhanVienNhaThuocs nv: nhanVienNhaThuocs){
                    up.setRoles(roleRepository.findByMaNhaThuocAndUserId(nv.getNhaThuocMaNhaThuoc(), up.getId()));
                }
            }
        }
        return userProfiles;
    }

    @Override
    public Page<UserStaffProfileRes> searchPageStaffManagement(UserProfileReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        Page<UserStaffProfileRes> userProfiles =  DataUtils.convertPage(hdrRepo.searchPageStaffManagement(req, pageable), UserStaffProfileRes.class);
        for(UserStaffProfileRes up: userProfiles){
            up.setRoles(roleRepository.findByMaNhaThuocAndUserId(up.getMaNhaThuoc(), up.getId()));
        }
        return userProfiles;
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
        e.setPassword(this.passwordEncoder.encode(e.getPassword()));
        if (e.getHoatDong() == null) {
            e.setHoatDong(true);
        }
        if (e.getEnableNT() == null) {
            e.setEnableNT(true);
        }
        e = hdrRepo.save(e);
        NhanVienNhaThuocsReq nv = new NhanVienNhaThuocsReq();
        nv.setUserUserId(e.getId());
        nv.setRole(NhanVienRoleConstant.ADMIN);
        nv.setStoreId(req.getStoreId());
        this.nhanVienNhaThuocsService.create(nv);

        Optional<Role> role = this.roleService.findByTypeAndIsDefaultAndRoleName(2, true, RoleTypeConstant.ADMIN);
        if (role.isEmpty()) {
            throw new Exception("Không tìm thấy role mặc định!");
        }
        UserRoleReq ur = new UserRoleReq();
        ur.setUserId(e.getId());
        ur.setRoleId(role.get().getId());
        this.userRoleService.create(ur);

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
        BeanUtils.copyProperties(req, e, "id", "password", "hoatDong", "enableNT", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
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
        String userName = req.getUserName() + "@" + userInfo.getNhaThuoc().getMaNhaThuoc();
        Optional<UserProfile> userProfile = this.hdrRepo.findByUserName(userName);
        if (userProfile.isPresent()) {
            throw new Exception("UserName đã tồn tại!");
        }
        UserProfile e = new UserProfile();
        BeanUtils.copyProperties(req, e, "id");
        e.setMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        e.setUserName(userName);
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        e.setPassword(this.passwordEncoder.encode(e.getPassword()));
        if (e.getHoatDong() == null) {
            e.setHoatDong(true);
        }
        if (e.getEnableNT() == null) {
            e.setEnableNT(true);
        }
        e = hdrRepo.save(e);
        NhanVienNhaThuocsReq nv = new NhanVienNhaThuocsReq();
        nv.setUserUserId(e.getId());
        nv.setRole(NhanVienRoleConstant.USER);
        nv.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        nv.setStoreId(req.getStoreId());
        this.nhanVienNhaThuocsService.create(nv);
        Optional<Role> role = this.roleService.findByMaNhaThuocAndTypeAndIsDefaultAndRoleName(getLoggedUser().getNhaThuoc().getMaNhaThuoc(), 3, true, RoleTypeConstant.USER);
        if (role.isEmpty()) {
            role = this.roleService.findByTypeAndIsDefaultAndRoleName(3, true, RoleTypeConstant.USER);
            if (role.isEmpty()) {
                throw new Exception("Không tìm thấy role mặc định!");
            }
        }
        UserRoleReq ur = new UserRoleReq();
        ur.setUserId(e.getId());
        ur.setRoleId(role.get().getId());
        this.userRoleService.create(ur);
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
        UserProfile e = optional.get();
        BeanUtils.copyProperties(req, e, "id", "password", "userName", "enableNT", "created", "createdByUserId", "modified", "modifiedByUserId", "recordStatusId");
        if (e.getRecordStatusId() == null) {
            e.setRecordStatusId(RecordStatusContains.ACTIVE);
        }
        hdrRepo.save(e);
        return e;
    }

    @Override
    public List<UserProfileRes> searchListUserManagement(UserProfileReq req) {
        List<UserProfileRes> list = DataUtils.convertList(hdrRepo.searchListUserManagement(req), UserProfileRes.class);
        for(UserProfileRes up: list){
            if(req.getMaNhaThuoc() !=null){
                up.setRoles(roleRepository.findByMaNhaThuocAndUserId(req.getMaNhaThuoc(), up.getId()));
            }
        }
        return list;
    }

    @Override
    public List<UserStaffProfileRes> searchListStaffManagement(UserProfileReq req) throws Exception {
        req.setMaNhaThuoc(getLoggedUser().getNhaThuoc().getMaNhaThuoc());
        List<UserStaffProfileRes> list = DataUtils.convertList(hdrRepo.searchListStaffManagement(req), UserStaffProfileRes.class);
        for(UserStaffProfileRes up: list){
            if(req.getMaNhaThuoc() !=null){
                up.setRoles(roleRepository.findByMaNhaThuocAndUserId(getLoggedUser().getNhaThuoc().getMaNhaThuoc(), up.getId()));
            }
        }
        return list;
    }

    @Override
    public Boolean updateThongTinKhuVuc(ThongTinKhuVucReq objReq) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        if (objReq.getId() == null || objReq.getId() == 0) return false;
        Optional<UserProfile> optional = hdrRepo.findById(objReq.getId());
        UserProfile e = optional.get();
        e.setWardId(objReq.getWardId());
        e.setCityId(objReq.getCityId());
        e.setRegionId(objReq.getRegionId());
        e.setAddresses(objReq.getDiaChi());
        e = hdrRepo.save(e);
        return true;
    }

    @Transactional
    @Override
    public Boolean changeRole(ChangeRoleReq objReq) throws Exception {
        if(objReq.getMaNhaThuoc() ==null){
            throw new Exception("Mã nhà thuốc không được để trống!");
        }
        if(objReq.getUserId() ==null){
            throw new Exception("User không được để trống!");
        }
        userRoleRepository.deleteByMaNhaThuocAndUserId(objReq.getMaNhaThuoc(), objReq.getUserId());
        for(Long roleId: objReq.getRoleIds()){
            UserRoleReq ur = new UserRoleReq();
            ur.setUserId(objReq.getUserId());
            ur.setRoleId(roleId);
            userRoleService.create(ur);
        }
        return true;
    }
    @Transactional
    @Override
    public Boolean changeRoleSystem(ChangeRoleReq objReq) throws Exception {
        if(objReq.getUserId() ==null){
            throw new Exception("User không được để trống!");
        }
        userRoleRepository.deleteByUserId(objReq.getUserId());
        for(Long roleId: objReq.getRoleIds()){
            UserRoleReq ur = new UserRoleReq();
            ur.setUserId(objReq.getUserId());
            ur.setRoleId(roleId);
            userRoleService.create(ur);
        }
        return true;
    }
}
