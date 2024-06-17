package vn.com.gsoft.system.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
@NoArgsConstructor
public class SettingsReq extends BaseRequest {
    private String key;
    private String value;
    private Boolean active;
    private Long id;
    private String maNhaThuoc;
    private Integer storeId;
}
