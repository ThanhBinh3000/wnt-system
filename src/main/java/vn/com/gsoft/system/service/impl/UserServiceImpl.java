package vn.com.gsoft.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import vn.com.gsoft.system.constant.CachingConstant;
import vn.com.gsoft.system.constant.UserStatus;
import vn.com.gsoft.system.entity.Department;
import vn.com.gsoft.system.entity.Role;
import vn.com.gsoft.system.entity.User;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.repository.DepartmentRepository;
import vn.com.gsoft.system.repository.RoleRepository;
import vn.com.gsoft.system.repository.UserRepository;
import vn.com.gsoft.system.service.UserService;
import vn.com.gsoft.system.util.system.JwtTokenUtil;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Cacheable(value = CachingConstant.USER)
    public Optional<Profile> findUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        log.warn("Cache findUserByToken missing: {}", username);
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new BadCredentialsException("Không tìm thấy username!");
        }
        Long entityTypeId = user.get().getEntityTypeId();
        Set<SimpleGrantedAuthority> privileges = new HashSet<>();
        List<Department> departments = departmentRepository.findByUserId(user.get().getId());
        return Optional.of(new Profile(
                user.get().getId(),
                user.get().getFullName(),
                entityTypeId,
                null,
                null,
                departments,
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().getStatus() == UserStatus.ACTIVE,
                true,
                true,
                true,
                privileges
        ));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
