package vn.com.gsoft.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
public class Role extends BaseEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "RoleName")
    private String roleName;
    @Column(name = "IsDeleted")
    private Boolean isDeleted;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "Description")
    private String description;
    @Column(name = "Type")
    private Integer type;
}

