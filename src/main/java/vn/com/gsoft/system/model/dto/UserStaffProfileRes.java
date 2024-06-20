package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.entity.Role;

import java.util.List;

@Data
@NoArgsConstructor
public class UserStaffProfileRes {
    private Long id;
    private String userName;
    private String soDienThoai;
    private String role;
    private String email;
    private Boolean HoatDong;
    private String tenDayDu;
    private String maNhaThuoc;

    private List<Role> roles;
}
