package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
@NoArgsConstructor
public class WarehouseLocationReq extends BaseRequest {
    private String code;
    private String nameWarehouse;
    private String storeCode;
    private String descriptions;
}
