package vn.com.gsoft.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.com.gsoft.system.entity.Process;
import vn.com.gsoft.system.model.system.NhaThuocs;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.model.system.Role;
import vn.com.gsoft.system.service.ProcessService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProcessServiceImplTest {
    @Autowired
    private ProcessService processService;
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
    void detail() throws Exception {
        Process detail = processService.detail(13l);
        assert  detail != null;
    }
}