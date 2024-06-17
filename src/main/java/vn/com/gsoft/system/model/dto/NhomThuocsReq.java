package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
@NoArgsConstructor
public class NhomThuocsReq extends BaseRequest {
    private String tenNhomThuoc;
    private String kyHieuNhomThuoc;
    private String maNhaThuoc;
    private Boolean active;
    private Integer referenceId;
    private Integer archivedId;
    private Integer storeId;
    private Integer typeGroupProduct;
}
