package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.*;

import java.util.List;

public interface UserProfileService extends BaseService<UserProfile, UserProfileReq, Long> {


    Page<UserProfileRes> searchPageUserManagement(UserProfileReq objReq) throws Exception;

    Page<UserStaffProfileRes> searchPageStaffManagement(UserProfileReq objReq) throws Exception;

    Boolean changePassword(ChangePasswordReq objReq) throws Exception;

    Boolean resetPassword(ChangePasswordReq objReq) throws Exception;

    UserProfile insertUser(UserProfileReq objReq) throws Exception;

    UserProfile updateUser(UserProfileReq objReq) throws Exception;

    UserProfile insertStaff(UserProfileReq objReq) throws Exception;

    UserProfile updateStaff(UserProfileReq objReq) throws Exception;

    List<UserProfileRes> searchListUserManagement(UserProfileReq objReq);

    List<UserStaffProfileRes> searchListStaffManagement(UserProfileReq objReq) throws Exception;

    Boolean updateThongTinKhuVuc(ThongTinKhuVucReq objReq) throws Exception;

    Boolean changeRole(ChangeRoleReq objReq) throws Exception;
}