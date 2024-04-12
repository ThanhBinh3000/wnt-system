package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class TieuChiTrienKhaisReq extends BaseRequest {
    private String name;
    private Long type;
}
