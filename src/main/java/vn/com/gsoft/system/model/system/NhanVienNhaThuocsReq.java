package vn.com.gsoft.system.model.system;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
public class NhanVienNhaThuocsReq extends BaseRequest {

    private Long id;
    private String role;
    private String nhaThuocMaNhaThuoc;
    private Integer userUserId;
    private Integer archivedId;
    private Integer storeId;
}

