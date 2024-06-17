package vn.com.gsoft.system.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.model.dto.UserProfileRes;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserProfile")
public class UserProfile extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "Password")
    private String password;
    @Column(name = "TenDayDu")
    private String tenDayDu;
    @Column(name = "Email")
    private String email;
    @Column(name = "SoDienThoai")
    private String soDienThoai;
    @Column(name = "MaNhaThuoc")
    private String maNhaThuoc;
    @Column(name = "HoatDong")
    private Boolean hoatDong;
    @Column(name = "SoCMT")
    private String soCMT;
    @Column(name = "Enable_NT")
    private Boolean enableNT;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "RegionId")
    private Long regionId;
    @Column(name = "CityId")
    private Long cityId;
    @Column(name = "WardId")
    private Long wardId;
    @Column(name = "Addresses")
    private String addresses;
    @Column(name = "TokenDevice")
    private String tokenDevice;
    @Column(name = "TokenBrowser")
    private String tokenBrowser;
    @Column(name = "IsVerificationAccount")
    private Boolean isVerificationAccount;
    @Column(name = "TokenDevice2")
    private String tokenDevice2;
    @Column(name = "MobileDeviceId")
    private String mobileDeviceId;
    @Column(name = "EntityId")
    private Long entityId;

    @Transient
    private List<Role> roles;
}

