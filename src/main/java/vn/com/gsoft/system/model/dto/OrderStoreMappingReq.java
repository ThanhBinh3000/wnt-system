package vn.com.gsoft.system.model.dto;

import lombok.Data;
import vn.com.gsoft.system.model.system.BaseRequest;

import java.util.List;

@Data
public class OrderStoreMappingReq extends BaseRequest {
    private String storeCode;
    private String targetStoreCode;
    private Boolean isDefault;
    private Long mappingTypeId;
    private List<String> targetStoreCodes;
}
