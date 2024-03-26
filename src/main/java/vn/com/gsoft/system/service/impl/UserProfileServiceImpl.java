package vn.com.gsoft.system.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.repository.UserProfileRepository;
import vn.com.gsoft.system.service.UserProfileService;

@Service
@Log4j2
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile, UserProfileReq, Long> implements UserProfileService {

    private UserProfileRepository hdrRepo;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
