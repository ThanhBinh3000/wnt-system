package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.system.BaseRequest;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class NhaCungCapsReq extends BaseRequest {
    private String tenNhaCungCap;
    private String diaChi;
    private String soDienThoai;
    private String soFax;
    private String maSoThue;
    private String nguoiDaiDien;
    private String nguoiLienHe;
    private String email;
    private BigDecimal noDauKy;
    private String maNhaThuoc;
    private Integer maNhomNhaCungCap;
    private Boolean active;
    private Integer supplierTypeId;
    private String barCode;
    private String diaBanHoatDong;
    private String website;
    private Integer archivedId;
    private Integer referenceId;
    private Integer storeId;
    private Integer masterId;
    private Integer metadataHash;
    private Integer preMetadataHash;
    private String code;
    private Integer mappingStoreId;
    private Integer isOrganization;
}
