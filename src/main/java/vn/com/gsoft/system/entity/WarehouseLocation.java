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
@Table(name = "WarehouseLocation")
public class WarehouseLocation extends BaseEntity {
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "Code")
    private String code;
    @Column(name = "NameWarehouse")
    private String nameWarehouse;
    @Column(name = "StoreCode")
    private String storeCode;
    @Column(name = "Descriptions")
    private String descriptions;
}

