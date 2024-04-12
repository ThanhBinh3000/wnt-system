package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class TrienKhaisReq extends BaseRequest {
    private Long tieuChiTrienKhaiId;
    private Boolean active;
    private Long type;
}
