package vn.com.gsoft.system.repository;

import jakarta.persistence.Tuple;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.constant.*;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaThuocsRepository extends BaseRepository<NhaThuocs, NhaThuocsReq, Long> {
    @Query(value = "SELECT * FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.RecordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.MaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.TenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = " + ETypeDate.NGAY_TAO + " AND (:#{#param.fromDate} IS NULL OR c.Created >= :#{#param.fromDate}) AND (:#{#param.toDate} IS NULL OR c.Created <= :#{#param.toDate}))"
            + " OR (:#{#param.typeDate} = " + ETypeDate.NGAY_THU_TIEN + " AND (:#{#param.fromDate} IS NULL OR c.PaidDate >= :#{#param.fromDate}) AND (:#{#param.toDate} IS NULL OR c.PaidDate <= :#{#param.toDate}))"
            + " OR (:#{#param.typeDate} = " + ETypeDate.NGAY_TAO_VA_NGAY_THU_TIEN + " AND (:#{#param.fromDate} IS NULL OR (c.Created >= :#{#param.fromDate} AND c.PaidDate >= :#{#param.fromDate})) AND (:#{#param.toDate} IS NULL OR (c.Created <= :#{#param.toDate} AND c.PaidDate <= :#{#param.toDate})))))"
            + " AND ((:#{#param.storeTypeId} IS NULL)"
            + " OR ((:#{#param.storeTypeId} = " + EStoreType.Connectivity + " AND  c.IsConnectivity = 1)"
            + " OR (:#{#param.storeTypeId} = " + EStoreType.Management + " AND  c.IsConnectivity = 0 AND c.MaNhaThuoc not in (select d.StoreCode from OrderStoreMapping d))"
            + " OR (:#{#param.storeTypeId} = " + EStoreType.Order + " AND c.MaNhaThuoc in (select d.StoreCode from OrderStoreMapping d))"
            + " OR (:#{#param.storeTypeId} = " + EStoreType.Company + " AND  c.IsGeneralPharmacy = 1)"
            + " OR (:#{#param.storeTypeId} = " + EStoreType.Clinic + " AND c.MaNhaThuoc in (select d.DrugStoreId from ApplicationSetting d where d.SettingKey = '" + StoreSettingKeys.UseClinicIntegration + "' AND d.Activated = 1))"
            + " OR (:#{#param.storeTypeId} = " + EStoreType.GeneralStoreOrder + " AND  c.MaNhaThuoc in (select d.TargetStoreCode from OrderStoreMapping d)))) "
            + " AND ((:#{#param.storePaymentTypeId} IS NULL)"
            + " OR ((:#{#param.storePaymentTypeId} = " + EStorePaymentType.UnPaid + " AND  c.PaidMoney = 0 AND  c.PaidAmount >= 0)"
            + " OR (:#{#param.storePaymentTypeId} = " + EStorePaymentType.UnderPayment + " AND  c.PaidMoney > 0 AND c.PaidMoney < c.PaidAmount AND c.PaidAmount >0)"
            + " OR (:#{#param.storePaymentTypeId} = " + EStorePaymentType.IsPaid + " AND c.PaidMoney = c.PaidAmount AND c.PaidAmount >0))) "
            + " AND ((:#{#param.typeZNS} IS NULL)"
            + " OR ((:#{#param.typeZNS} = " + EZNSType.ConfirmPayment + " AND c.ZNS_StatusSendPayment = 1)"
            + " OR (:#{#param.typeZNS} = " + EZNSType.CreateAccount + " AND c.ZNS_StatusSendCreateAccount = 1))) "
            + " AND ((:#{#param.active} IS NULL) OR (c.Active = :#{#param.active} )) "
            + " AND ((:#{#param.hoatDong} IS NULL) OR (c.HoatDong = :#{#param.hoatDong} )) "
            + " AND ((:#{#param.createdByUserId} IS NULL) OR (c.CreatedBy_UserId = :#{#param.createdByUserId} )) "
            + " AND ((:#{#param.tinhThanhId} IS NULL) OR (c.TinhThanhId = :#{#param.tinhThanhId} )) "
            + " AND ((:#{#param.numDaysNoTrans} IS NULL) OR (c.LastTransDate IS NULL OR c.LastTransDate <= :#{#param.dateBeforeNumDaysNoTrans})) "
            + " AND ((:#{#param.expiredType} IS NULL)"
            + " OR ((:#{#param.expiredType} = " + StoreExpiredTypeConstant.DonHaveExpiredType + " AND c.ExpiredDate IS NULL)"
            + " OR (:#{#param.expiredType} = " + StoreExpiredTypeConstant.HaveExpiredType + " AND c.ExpiredDate IS NOT NULL)"
            + " OR (:#{#param.expiredType} = " + StoreExpiredTypeConstant.LessThan30Days + " AND c.ExpiredDate IS NOT NULL AND c.ExpiredDate > :#{#param.dateNow} AND :#{#param.dateDiff} <= 30)"
            + " OR (:#{#param.expiredType} = " + StoreExpiredTypeConstant.LessThan7Days + " AND c.ExpiredDate IS NOT NULL AND c.ExpiredDate > :#{#param.dateNow} AND :#{#param.dateDiff} <= 7)"
            + " OR (:#{#param.expiredType} = " + StoreExpiredTypeConstant.OverExpired + " AND c.ExpiredDate IS NOT NULL AND c.ExpiredDate <= :#{#param.dateNow})))"
            + " AND ((:#{#param.storeDeployTypeId} IS NULL) OR (c.MaNhaThuoc in (select d.NhaThuoc_MaNhaThuoc from TrienKhais d where d.TieuChiTrienKhai_Id = :#{#param.storeDeployTypeId} and d.Active = 1))) "
            + " AND ((:#{#param.idTypeBasic} IS NULL) OR (c.IdTypeBasic = :#{#param.idTypeBasic} )) "
            + " AND ((:#{#param.supporterId} IS NULL) OR (c.SupporterId = :#{#param.supporterId} )) "
            + " AND ((:#{#param.outOfInvoice} IS NULL)"
            + " OR (:#{#param.outOfInvoice} = 0)"
            + " OR ((:#{#param.outOfInvoice} = 1"
            + " AND (c.totalNumberInvoices > 0"
            + " AND c.totalNumberInvoices < "
            + "(select count(*) from PhieuNhaps n where n.NhaThuoc_MaNhaThuoc = :#{#param.maNhaThuoc} AND n.NgayNhap > '2019-1-1' AND n.Id >= 0 AND n.RecordStatusID = " + RecordStatusContains.ACTIVE + "AND n.LoaiXuatNhap_MaLoaiXuatNhap IN (" + NoteTypeConstant.Receipt + "," + NoteTypeConstant.ReturnFromCustomer + "," + NoteTypeConstant.InventoryAdjustment + ") AND (n.ConnectivityStatusID = " + ConnectivityStatusConstant.Connected + " OR n.ConnectivityStatusID = " + ConnectivityStatusConstant.BillConnected + ")) +"
            + "(select count(*) from PhieuXuats x where x.NhaThuoc_MaNhaThuoc = :#{#param.maNhaThuoc} AND x.NgayXuat > '2019-1-1' AND x.Id >= 0 AND x.RecordStatusID = " + RecordStatusContains.ACTIVE + "AND x.MaLoaiXuatNhap IN (" + NoteTypeConstant.Delivery + "," + NoteTypeConstant.ReturnToSupplier + "," + NoteTypeConstant.InventoryAdjustment + "," + NoteTypeConstant.CancelDelivery + ") AND (x.ConnectivityStatusID = " + ConnectivityStatusConstant.Connected + " OR x.ConnectivityStatusID = " + ConnectivityStatusConstant.BillConnected + " ) )))))"
            + " AND ((:#{#param.excludeCurrentStore} IS NULL)"
            + " OR (:#{#param.excludeCurrentStore} = 0)"
            + " OR (:#{#param.excludeCurrentStore} = 1 AND (:#{#param.currentStoreCode} IS NULL OR c.MaNhaThuoc != :#{#param.currentStoreCode}))) "
            + " AND ((:#{#param.storeClassifyId} IS NULL) OR (c.Classify = :#{#param.storeClassifyId})) "
            + " AND ((:#{#param.storeEvaluateId} IS NULL) OR (c.Evaluate = :#{#param.storeEvaluateId})) "
            + " AND (:#{#param.storeMobileAppUsedStatusId} IS NULL"
            + " OR (:#{#param.storeMobileAppUsedStatusId} = 0 AND c.MaNhaThuoc NOT IN (SELECT nv.NhaThuoc_MaNhaThuoc FROM NhanVienNhaThuocs nv JOIN UserProfile up ON nv.User_UserId = up.ID WHERE nv.Role = 'Admin' AND up.TokenDevice IS NOT NULL AND up.TokenDevice != ''))"
            + " OR (:#{#param.storeMobileAppUsedStatusId} = 1 AND c.MaNhaThuoc IN (SELECT nv.NhaThuoc_MaNhaThuoc FROM NhanVienNhaThuocs nv JOIN UserProfile up ON nv.User_UserId = up.ID WHERE nv.Role = 'Admin' AND up.TokenDevice IS NOT NULL AND up.TokenDevice != '')))"
            + " AND ((:#{#param.textSearch} IS NULL OR lower(c.MaNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.TenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.DiaChi) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))"
            + " AND (:#{#param.maNhaThuocCha} IS NULL OR c.MaNhaThuocCha = :#{#param.maNhaThuocCha})"
            + " AND (:#{#param.isConnectivity} IS NULL OR c.IsConnectivity = :#{#param.isConnectivity})"
            + " AND (:#{#param.hoatDong} IS NULL OR c.HoatDong = :#{#param.hoatDong})"
            + " ORDER BY c.Id desc", nativeQuery = true
    )
    Page<NhaThuocs> searchPage(@Param("param") NhaThuocsReq param, Pageable pageable);


    @Query("SELECT c FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuocCha} IS NULL OR c.maNhaThuocCha = :#{#param.maNhaThuocCha})"
            + " AND (:#{#param.isConnectivity} IS NULL OR c.isConnectivity = :#{#param.isConnectivity})"
            + " AND (:#{#param.hoatDong} IS NULL OR c.hoatDong = :#{#param.hoatDong})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " ORDER BY c.id desc"
    )
    List<NhaThuocs> searchList(@Param("param") NhaThuocsReq param);

    @Query(value = "SELECT c.id, c.maNhaThuoc, c.tenNhaThuoc, " +
            " (SELECT ua.tenDayDu From UserProfile ua JOIN  NhanVienNhaThuocs nva on nva.User_UserId = ua.id where nva.role = 'Admin' and nva.NhaThuoc_MaNhaThuoc = c.maNhaThuoc  ) as  nguoiPhuTrach " +
            " FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.NhaThuoc_MaNhaThuoc " +
            " join UserProfile u on nv.User_UserId = u.id " +
            " WHERE 1=1 " +
            " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})" +
            " AND (:#{#param.userIdQueryData} IS NULL OR u.id = :#{#param.userIdQueryData} )" +
            " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))" +
            " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))" +
            " AND (:#{#param.maNhaThuocCha} IS NULL OR c.MaNhaThuocCha = :#{#param.maNhaThuocCha})" +
            " AND (:#{#param.isConnectivity} IS NULL OR c.IsConnectivity = :#{#param.isConnectivity})" +
            " AND (:#{#param.hoatDong} IS NULL OR c.HoatDong = :#{#param.hoatDong})" +
            " AND ((:#{#param.textSearch} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))" +
            " OR (:#{#param.textSearch} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))" +
            " GROUP BY c.id, c.maNhaThuoc, c.tenNhaThuoc" +
            " ORDER BY c.maNhaThuoc asc",
            countQuery =
                    "SELECT COUNT(*) FROM ( " +
                    " SELECT c.id, c.maNhaThuoc, c.tenNhaThuoc, " +
                    " (SELECT ua.tenDayDu From UserProfile ua JOIN  NhanVienNhaThuocs nva on nva.User_UserId = ua.id where nva.role = 'Admin' and nva.NhaThuoc_MaNhaThuoc = c.maNhaThuoc  ) as  nguoiPhuTrach " +
                    " FROM NhaThuocs c " +
                    " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.NhaThuoc_MaNhaThuoc " +
                    " join UserProfile u on nv.User_UserId = u.id " +
                    " WHERE 1=1 " +
                    " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})" +
                    " AND (:#{#param.userIdQueryData} IS NULL OR u.id = :#{#param.userIdQueryData} )" +
                    " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))" +
                    " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))" +
                    " AND (:#{#param.maNhaThuocCha} IS NULL OR c.MaNhaThuocCha = :#{#param.maNhaThuocCha})" +
                    " AND (:#{#param.isConnectivity} IS NULL OR c.IsConnectivity = :#{#param.isConnectivity})" +
                    " AND (:#{#param.hoatDong} IS NULL OR c.HoatDong = :#{#param.hoatDong})" +
                    " AND ((:#{#param.textSearch} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))" +
                    " OR (:#{#param.textSearch} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))" +
                    " GROUP BY c.id, c.maNhaThuoc, c.tenNhaThuoc" +
                    " ) AS countMaNhaThuoc",
            nativeQuery = true
    )
    Page<Tuple> searchPageNhaThuoc(@Param("param") NhaThuocsReq req, Pageable pageable);

    Optional<NhaThuocs> findByMaNhaThuoc(String maNhaThuoc);


    @Query("SELECT new vn.com.gsoft.system.model.dto.NhaThuocsRes(c.id, c.maNhaThuoc, c.tenNhaThuoc, u.userName ) FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.nhaThuocMaNhaThuoc " +
            " join UserProfile u on nv.userUserId = u.id " +
            " WHERE 1=1 AND nv.role = 'Admin' " +
            " AND (c.maNhaThuoc  = ?1)"
    )
    Optional<NhaThuocsRes> findNguoiPhuTrachByMaNhaThuoc(String maNhaThuoc);

    @Query("SELECT c FROM NhaThuocs c " +
            " WHERE 1=1 " +
            " ORDER BY c.created DESC" +
            " LIMIT 1")
    Optional<NhaThuocs> findLatestRecord();

    @Query(value = "SELECT a.SettingValue FROM ApplicationSetting a " +
            "WHERE a.DrugStoreId = :storeCode AND a.SettingKey = 'TARGET_STORE_FOR_SYN_NOTES'" +
            "AND a.Activated = 1"
            , nativeQuery = true)
    Optional<String> findNhaThuocDongBoPhieuByMaNhaThuoc(@Param("storeCode") String storeCode);

    //lấy ra danh sách nhà thuốc theo mã
    @Query(value = "SELECT c.ID AS id, c.MaNhaThuoc AS maNhaThuoc, c.TenNhaThuoc AS tenNhaThuoc" +
            " FROM NhaThuocs c WHERE c.HoatDong = 1" +
            " AND c.MaNhaThuoc in (:storeCodes)"
            , nativeQuery = true)
    Page<Tuple> searchPageNhaThuocDongBoPhieu(@Param("storeCodes") String[] storeCodes, Pageable pageable);

}
