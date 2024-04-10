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
    @Column(name = "DrugStoreId")
    private String drugStoreId;

    @Column(name = "Activated")
    private Boolean activated;

    @Column(name = "SettingKey")
    private String settingKey;
}
