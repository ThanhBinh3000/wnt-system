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
@Table(name = "TypeBasis")
public class TypeBasis extends BaseEntity {
    @Id
    @Column(name = "Id")
    private Long id;
    @Column(name = "IdType")
    private Long idType;
    @Column(name = "NameType")
    private String nameType;
}

