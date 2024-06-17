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
@Table(name = "Settings")
public class Settings {
    @Column(name = "Key")
    private String key;
    @Column(name = "Value")
    private String value;
    @Column(name = "Active")
    private Boolean active;
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "StoreId")
    private Integer storeId;

    public Settings(String key, String value, String maNhaThuoc) {
        this.key = key;
        this.value = value;
        this.maNhaThuoc = maNhaThuoc;
    }
}

