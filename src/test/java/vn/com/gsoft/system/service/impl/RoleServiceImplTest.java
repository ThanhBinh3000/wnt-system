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
import vn.com.gsoft.system.model.system.NhaThuocs;
import vn.com.gsoft.system.model.system.PaggingReq;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.model.system.Role;
import vn.com.gsoft.system.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RoleServiceImplTest {
    @Autowired
    private RoleService roleService;

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
    void searchList() throws Exception {
        RoleReq noteMedicalsReq = new RoleReq();
        List<vn.com.gsoft.system.entity.Role> sampleNotes = roleService.searchList(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void searchPage() throws Exception {
        RoleReq noteMedicalsReq = new RoleReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        Page<vn.com.gsoft.system.entity.Role> sampleNotes = roleService.searchPage(noteMedicalsReq);
        assert sampleNotes != null;
    }
}