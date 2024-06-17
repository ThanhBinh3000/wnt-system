package vn.com.gsoft.system.model.dto;

import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.system.entity.Role;

import java.util.List;

@Data
@NoArgsConstructor
public class UserProfileRes {
    private Long id;
    private String userName;
    private String nhaThuocs;
    private String nhomQuyens;
    private String email;
    private Boolean HoatDong;
    private String tenDayDu;
    private String soDienThoai;
    private String soCMT;

    private List<Role> roles;
}
