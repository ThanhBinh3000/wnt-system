package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
@NoArgsConstructor
public class DonViTinhsReq extends BaseRequest {
    private String tenDonViTinh;
    private String maNhaThuoc;
    private Long referenceId;
    private Long archivedId;
    private Long storeId;
}
