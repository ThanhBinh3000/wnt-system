package vn.com.gsoft.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.gsoft.system.model.dto.RoleReq;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.model.dto.UserProfileRes;
import vn.com.gsoft.system.model.system.NhaThuocs;
import vn.com.gsoft.system.model.system.PaggingReq;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.model.system.Role;
import vn.com.gsoft.system.service.RoleService;
import vn.com.gsoft.system.service.UserProfileService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserProfileServiceImplTest {
    @Autowired
    private UserProfileService userProfileService;

    @BeforeAll
    static void beforeAll() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Profile p = new Profile();
        Role role = new Role();
        role.setRoleName("Super User");
        p.setRoles(List.of(role));
        NhaThuocs nhaThuocs = new NhaThuocs();
        nhaThuocs.setMaNhaThuoc("0010");
        p.setNhaThuoc(nhaThuocs);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(p, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @Test
    void searchPageUserManagement() throws Exception {
        UserProfileReq noteMedicalsReq = new UserProfileReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        Page<UserProfileRes>  res = userProfileService.searchPageUserManagement(noteMedicalsReq);
        assert res != null;
    }
}