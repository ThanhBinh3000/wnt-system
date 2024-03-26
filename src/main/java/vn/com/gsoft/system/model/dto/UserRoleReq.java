package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class UserRoleReq extends BaseRequest {
    private Long roleId;
    private Long userId;
}
