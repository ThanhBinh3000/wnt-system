package vn.com.gsoft.system.service;

import vn.com.gsoft.system.model.system.Profile;

public interface BaseService {
    Profile getLoggedUser() throws Exception;

}
