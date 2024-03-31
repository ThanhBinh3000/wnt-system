package vn.com.gsoft.system.service;

import org.springframework.data.domain.Page;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.model.dto.UserProfileRes;

public interface UserProfileService extends BaseService<UserProfile, UserProfileReq, Long> {


    Page<UserProfileRes> searchPageUserManagement(UserProfileReq objReq) throws Exception;

}