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
import vn.com.gsoft.system.enums.EnumResponse;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.response.BaseResponse;
import vn.com.gsoft.system.service.NhaThuocsService;
import vn.com.gsoft.system.util.system.PathContains;

@RestController
@RequestMapping(value = PathContains.URL_NHAN_VIEN_NHA_THUOC)
@Slf4j
@Tag(name  = "Nhân viên nhà thuốc ( Chủ/nhân viên)")
public class NhanVienNhaThuocsController {

  @Autowired
  NhaThuocsService service;

  @Operation(summary = "Tra cứu", description = "Tra cứu")
  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody NhaThuocsReq objReq) {
    BaseResponse resp = new BaseResponse();
    try {
      resp.setData(service.searchList(objReq));
      resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
      resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
    } catch (Exception e) {
      resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
      resp.setMsg(e.getMessage());
      log.error("Tra cứu thông tin : {}", e);
    }
    return ResponseEntity.ok(resp);
  }


  @Operation(summary = "Tạo mới", description = "Tạo mới")
  @PostMapping(value = PathContains.URL_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> insert(@Valid @RequestBody NhaThuocsReq objReq) {
    BaseResponse resp = new BaseResponse();
    try {
      resp.setData(service.create(objReq));
      resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
      resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
    } catch (Exception e) {
      resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
      resp.setMsg(e.getMessage());
      log.error("Tạo mới thông tin  : {}", e);
    }
    return ResponseEntity.ok(resp);
  }

  @Operation(summary = "Lấy chi tiết", description = "Lấy chi tiết")
  @GetMapping(value = PathContains.URL_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> detail(@PathVariable("id") Long id) {
    BaseResponse resp = new BaseResponse();
    try {
      resp.setData(service.detail(id));
      resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
      resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
    } catch (Exception e) {
      resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
      resp.setMsg(e.getMessage());
      log.error("Lấy chi tiết thông tin : {}", e);
    }
    return ResponseEntity.ok(resp);
  }


  @Operation(summary = "Xoá thông tin", description = "Xoá thông tin")
  @PostMapping(value = PathContains.URL_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> delete(@Valid @RequestBody NhaThuocsReq idSearchReq) {
    BaseResponse resp = new BaseResponse();
    try {
      service.delete(idSearchReq.getId());
      resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
      resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
    } catch (Exception e) {
      resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
      resp.setMsg(e.getMessage());
      log.error("Xoá thông tin : {}", e);
    }

    return ResponseEntity.ok(resp);
  }


}
