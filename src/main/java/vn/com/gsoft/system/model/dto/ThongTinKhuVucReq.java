package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

@Data
public class ThongTinKhuVucReq extends BaseRequest {
    private Long id;
    private Long cityId;
    private Long wardId;
    private Long regionId;
}
