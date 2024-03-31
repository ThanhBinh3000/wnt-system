package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class NhanVienNhaThuocsReq extends BaseRequest {
    private String role;
    private String nhaThuocMaNhaThuoc;
    private Long userUserId;
    private Long archivedId;
    private Long storeId;
}

