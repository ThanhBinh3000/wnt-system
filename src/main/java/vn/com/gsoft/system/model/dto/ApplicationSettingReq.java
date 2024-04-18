package vn.com.gsoft.system.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class ApplicationSettingReq extends BaseRequest {
    private String settingKey;
    private String drugStoreId;
    private String settingValue;
    private String settingDisplayName;
    private String description;
    private Boolean isReadOnly;
    private Boolean activated;
    private Integer roleId;
    private Integer typeId;
}
