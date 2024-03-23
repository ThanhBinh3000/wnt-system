package vn.com.gsoft.system.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.gsoft.system.model.system.Profile;

@FeignClient(name = "wnt-security")
public interface UserProfileFeign {
    @GetMapping("/profile")
    Profile getProfile();
}
