package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class TinhThanhsReq extends BaseRequest {
    private String maTinhThanh;
    private String tenTinhThanh;
}
