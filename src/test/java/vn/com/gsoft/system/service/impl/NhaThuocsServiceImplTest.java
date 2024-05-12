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
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;
import vn.com.gsoft.system.model.system.PaggingReq;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.model.system.Role;
import vn.com.gsoft.system.service.NhaThuocsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class NhaThuocsServiceImplTest {


    @Autowired
    private NhaThuocsService nhaThuocsService;

    @BeforeAll
    static void beforeAll() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Profile p = new Profile();
        Role role = new Role();
        role.setRoleName("Super User");
        p.setRoles(List.of(role));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(p, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void searchPageNhaThuoc() throws Exception {
        NhaThuocsReq noteMedicalsReq = new NhaThuocsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        Page<NhaThuocsRes> sampleNotes = nhaThuocsService.searchPageNhaThuoc(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void searchPage() throws Exception {
        NhaThuocsReq noteMedicalsReq = new NhaThuocsReq();
        PaggingReq paggingReq = new PaggingReq();
        paggingReq.setPage(0);
        paggingReq.setLimit(10);
        noteMedicalsReq.setPaggingReq(paggingReq);
        Page<NhaThuocs> sampleNotes = nhaThuocsService.searchPage(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void searchList() throws Exception {
        NhaThuocsReq noteMedicalsReq = new NhaThuocsReq();
        List<NhaThuocs> sampleNotes = nhaThuocsService.searchList(noteMedicalsReq);
        assert sampleNotes != null;
    }

    @Test
    void detail() throws Exception {
        NhaThuocs detail = nhaThuocsService.detail(1l);
        assert detail != null;
    }

}