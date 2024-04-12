package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class TypeBasisReq extends BaseRequest {
    private Long idType;
    private String nameType;
}
