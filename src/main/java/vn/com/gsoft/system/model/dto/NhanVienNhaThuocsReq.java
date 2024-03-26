package vn.com.gsoft.system.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class NhanVienNhaThuocsReq extends BaseRequest {

    private String role;
    private String nhaThuocMaNhaThuoc;
    private Integer userUserId;
    private Integer archivedId;
    private Integer storeId;
}

