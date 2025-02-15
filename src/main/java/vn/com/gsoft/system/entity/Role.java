package vn.com.gsoft.system.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
public class Role extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "RoleName")
    private String roleName;
    @Column(name = "IsDeleted")
    private Boolean isDeleted;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "Description")
    private String description;
    @Column(name = "RoleTypeId")
    private Long roleTypeId;
    @Column(name = "IsDefault")
    private Boolean isDefault;  // true là mặc định
    @Transient
    private List<Privilege> privileges;
}

