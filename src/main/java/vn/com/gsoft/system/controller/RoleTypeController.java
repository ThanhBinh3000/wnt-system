package vn.com.gsoft.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.system.constant.PathConstant;
import vn.com.gsoft.system.model.dto.RoleTypeReq;
import vn.com.gsoft.system.model.system.BaseResponse;
import vn.com.gsoft.system.service.RoleTypeService;
import vn.com.gsoft.system.util.system.ResponseUtils;

@Slf4j
@RestController
@RequestMapping("/role-type")
public class RoleTypeController {
	
  @Autowired
  private RoleTypeService service;


  @PostMapping(value = PathConstant.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody RoleTypeReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
  }


  @PostMapping(value = PathConstant.URL_SEARCH_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionList(@RequestBody RoleTypeReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchList(objReq)));
  }
}
