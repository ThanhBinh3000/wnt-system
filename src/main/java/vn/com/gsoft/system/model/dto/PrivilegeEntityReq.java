package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class PrivilegeEntityReq extends BaseRequest {
    private Long privilegeId;
    private Long entityId;
}
