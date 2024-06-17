package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

import java.util.Date;

@Data
@NoArgsConstructor
public class NhomKhachHangsReq extends BaseRequest {
    private String tenNhomKhachHang;
    private String ghiChu;
    private String nhaThuocMaNhaThuoc;
    private Boolean active;
    private Integer groupTypeId;
    private String fullName;
    private String idCard;
    private Date birthDate;
    private String classId;
    private String mobile;
    private Integer archivedId;
    private Integer storeId;
}
