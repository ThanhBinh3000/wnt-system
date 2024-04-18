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
@Table(name = "ApplicationSetting")
public class ApplicationSetting extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SettingKey")
    private String settingKey;

    @Column(name = "DrugStoreId")
    private String drugStoreId;

    @Column(name = "SettingValue")
    private String settingValue;

    @Column(name = "SettingDisplayName")
    private String settingDisplayName;

    @Column(name = "Description")
    private String description;

    @Column(name = "IsReadOnly")
    private Boolean isReadOnly;

    @Column(name = "Activated")
    private Boolean activated;

    @Column(name = "RoleId")
    private Integer roleId;

    @Column(name = "TypeId")
    private Integer typeId;

}
