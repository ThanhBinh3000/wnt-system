package vn.com.gsoft.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.feign.UserProfileFeign;
import vn.com.gsoft.system.service.UserService;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserProfileFeign userProfileFeign;

    @Override
    public Optional<Profile> findUserByToken(String token) {
        return Optional.of(userProfileFeign.getProfile());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
