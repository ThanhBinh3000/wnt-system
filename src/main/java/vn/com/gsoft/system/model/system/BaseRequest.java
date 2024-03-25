package vn.com.gsoft.system.model.system;

import lombok.Data;

@Data
public class BaseRequest {
	private Long id;
	private PaggingReq paggingReq;
}