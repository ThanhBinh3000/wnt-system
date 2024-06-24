package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

import java.util.List;

@Data
public class RoleReq extends BaseRequest {
    private String roleName;
    private Boolean isDeleted;
    private String maNhaThuoc;
    private String description;
    private Long roleTypeId;

    private List<Long> privileges;
}
