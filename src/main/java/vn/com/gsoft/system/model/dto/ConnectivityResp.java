package vn.com.gsoft.system.model.dto;

import lombok.Data;

@Data
public class ConnectivityResp<T> {
    private T data;
}
