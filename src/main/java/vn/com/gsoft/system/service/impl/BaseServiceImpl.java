package vn.com.gsoft.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.gsoft.system.config.MessageTemplate;
import vn.com.gsoft.system.model.system.Profile;
import vn.com.gsoft.system.service.BaseService;

import java.io.Serializable;

@Service
@Slf4j
public class BaseServiceImpl  {
    @Autowired
    private MessageTemplate messageTemplate;

    public Profile getLoggedUser() throws Exception {
        try {
            return (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex) {
            throw new Exception(messageTemplate.message("error.token.invalid"));
        }
    }

}
