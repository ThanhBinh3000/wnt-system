package vn.com.gsoft.system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.system.model.dto.UserProfileReq;
import vn.com.gsoft.system.response.BaseResponse;
import vn.com.gsoft.system.service.UserProfileService;
import vn.com.gsoft.system.util.system.PathContains;
import vn.com.gsoft.system.util.system.ResponseUtils;

@RestController
@RequestMapping(value = PathContains.URL_NGUOI_DUNG)
@Slf4j
@Tag(name  = "Thông tin người dùng ( Quản trị/Chủ/nhân viên)")
public class UserProfileController {

  @Autowired
  UserProfileService service;

  @Operation(summary = "Tra cứu", description = "Tra cứu")
  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody UserProfileReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
  }

  @Operation(summary = "Tra cứu", description = "Tra cứu")
  @PostMapping(value = "/search-page-user-management", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionPageUserManagement(@RequestBody UserProfileReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPageUserManagement(objReq)));
  }

  @Operation(summary = "Tra cứu", description = "Tra cứu")
  @PostMapping(value = "/search-page-staff-management", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionPageStaffManagement(@RequestBody UserProfileReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPageStaffManagement(objReq)));
  }

  @Operation(summary = "Tạo mới", description = "Tạo mới")
  @PostMapping(value = PathContains.URL_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> insert(@Valid @RequestBody UserProfileReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.create(objReq)));
  }

  @Operation(summary = "Lấy chi tiết", description = "Lấy chi tiết")
  @GetMapping(value = PathContains.URL_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> detail(@PathVariable("id") Long id) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.detail(id)));
  }



  @Operation(summary = "Xóa thông tin", description = "Xóa thông tin")
  @PostMapping(value = PathContains.URL_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> delete(@Valid @RequestBody UserProfileReq idSearchReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.delete(idSearchReq.getId())));
  }

}
