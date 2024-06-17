package vn.com.gsoft.system.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChangeRoleReq {
    private String maNhaThuoc;
    private Long userId;
    private List<Long> roleIds;
}
