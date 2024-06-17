package vn.com.gsoft.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhomNhaCungCaps")
public class NhomNhaCungCaps extends BaseEntity{
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "TenNhomNhaCungCap")
    private String tenNhomNhaCungCap;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "IsDefault")
    private Boolean isDefault;
    @Column(name = "ArchivedId")
    private Integer archivedId;
    @Column(name = "StoreId")
    private Integer storeId;
}

