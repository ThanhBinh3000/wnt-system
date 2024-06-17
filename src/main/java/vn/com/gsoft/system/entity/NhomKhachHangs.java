package vn.com.gsoft.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhomKhachHangs")
public class NhomKhachHangs extends BaseEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "TenNhomKhachHang")
    private String tenNhomKhachHang;
    @Column(name = "GhiChu")
    private String ghiChu;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String nhaThuocMaNhaThuoc;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "GroupTypeId")
    private Integer groupTypeId;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "IdCard")
    private String idCard;
    @Column(name = "BirthDate")
    private Date birthDate;
    @Column(name = "ClassId")
    private String classId;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "ArchivedId")
    private Integer archivedId;
    @Column(name = "StoreId")
    private Integer storeId;
}

