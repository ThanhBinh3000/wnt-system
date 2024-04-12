package vn.com.gsoft.system.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TrienKhais")
public class TrienKhais extends BaseEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NhaThuoc_MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "TieuChiTrienKhai_Id")
    private Long tieuChiTrienKhaiId;
    @Column(name = "Active")
    private Boolean active;
}