package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.ChangePasswordReq;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.model.dto.UserProfileRes;
import vn.com.gsoft.system.model.dto.UserStaffProfileRes;

public interface UserProfileService extends BaseService<UserProfile, UserProfileReq, Long> {


    Page<UserProfileRes> searchPageUserManagement(UserProfileReq objReq) throws Exception;

    Page<UserStaffProfileRes> searchPageStaffManagement(UserProfileReq objReq) throws Exception;

    Boolean changePassword(ChangePasswordReq objReq) throws Exception;
}