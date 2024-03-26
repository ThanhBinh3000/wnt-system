package vn.com.gsoft.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Privilege")
public class Privilege {
    @Id
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Code")
    private String code;
    @Column(name = "ParentCode")
    private String parentCode;
    @Column(name = "Name")
    private String name;
    @Column(name = "Enable")
    private Boolean enable;
}

