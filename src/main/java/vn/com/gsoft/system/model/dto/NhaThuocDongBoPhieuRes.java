package vn.com.gsoft.system.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NhaThuocDongBoPhieuRes {
    private Long id;
    private String maNhaThuoc;
    private String tenNhaThuoc;
}
