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
@Table(name = "NhomThuocs")
public class NhomThuocs extends BaseEntity{
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "TenNhomThuoc")
    private String tenNhomThuoc;
    @Column(name = "KyHieuNhomThuoc")
    private String kyHieuNhomThuoc;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "ReferenceId")
    private Integer referenceId;
    @Column(name = "ArchivedId")
    private Integer archivedId;
    @Column(name = "StoreId")
    private Integer storeId;
    @Column(name = "TypeGroupProduct")
    private Integer typeGroupProduct;
}

