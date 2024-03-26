package vn.com.gsoft.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RolePrivilege")
public class RolePrivilege {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "RoleId")
    private Integer roleId;

    @Column(name = "PrivilegeId")
    private Long privilegeId;
}
