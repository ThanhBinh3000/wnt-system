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
@Table(name = "TinhThanhs")
public class TinhThanhs extends BaseEntity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "MaTinhThanh")
    private String maTinhThanh;
    @Column(name = "TenTinhThanh")
    private String tenTinhThanh;
}

