package vn.com.gsoft.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(name="Created")
    private LocalDateTime created;

    @CreatedBy
    @Column(name="CreatedBy_UserId")
    private Long createdByUserId;

    @LastModifiedDate
    @Column(name="Modified")
    private LocalDateTime modified;

    @LastModifiedBy
    @Column(name="ModifiedBy_UserId")
    private Long modifiedByUserId;
}
