package vn.com.gsoft.system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.system.constant.EStorePaymentType;
import vn.com.gsoft.system.constant.EStoreType;
import vn.com.gsoft.system.constant.ETypeDate;
import vn.com.gsoft.system.constant.StoreSettingKeys;
import vn.com.gsoft.system.constant.EZNSType;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.model.dto.NhaThuocsReq;
import vn.com.gsoft.system.model.dto.NhaThuocsRes;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaThuocsRepository extends BaseRepository<NhaThuocs, NhaThuocsReq, Long> {
    @Query("SELECT c FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_TAO +" AND :#{#param.fromDate} IS NULL OR c.created >= :#{#param.fromDate}) OR 1=1))"
            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_TAO +" AND :#{#param.toDate} IS NULL OR c.created <= :#{#param.toDate}) OR 1=1))"
//            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_GIAO_DICH +" AND :#{#param.fromDate} IS NULL OR c.created >= :#{#param.fromDate}) OR 1=1))" //todo
//            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_GIAO_DICH +" AND :#{#param.toDate} IS NULL OR c.created <= :#{#param.toDate}) OR 1=1))" //todo
            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_THU_TIEN +" AND :#{#param.fromDate} IS NULL OR c.paidDate >= :#{#param.fromDate}) OR 1=1))"
            + " AND (:#{#param.typeDate} IS NULL OR ((:#{#param.typeDate} = "+ ETypeDate.NGAY_THU_TIEN +" AND :#{#param.toDate} IS NULL OR c.paidDate <= :#{#param.toDate}) OR 1=1))"
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.Connectivity + " AND  c.isConnectivity = true) OR 1=1)) "
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.Management + " AND  c.isConnectivity = false AND c.maNhaThuoc not in (select d.storeCode from OrderStoreMapping d)) OR 1=1)) "
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.Order + " AND c.maNhaThuoc in (select d.storeCode from OrderStoreMapping d)) OR 1=1)) "
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.Company + " AND  c.isGeneralPharmacy = true) OR 1=1)) "
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.Clinic + " AND c.maNhaThuoc in (select d.drugStoreId from ApplicationSetting d where d.settingKey = '" + StoreSettingKeys.UseClinicIntegration + "' AND d.activated = true)) OR 1=1)) "
            + " AND ((:#{#param.storeTypeId} IS NULL) OR ((:#{#param.storeTypeId} = " + EStoreType.GeneralStoreOrder + " AND  c.maNhaThuoc in (select d.targetStoreCode from OrderStoreMapping d)) OR 1=1)) "
            + " AND ((:#{#param.storePaymentTypeId} IS NULL) OR ((:#{#param.storePaymentTypeId} = " + EStorePaymentType.UnPaid + " AND  c.paidMoney = 0 AND  c.paidAmount >= 0) OR 1=1)) "
            + " AND ((:#{#param.storePaymentTypeId} IS NULL) OR ((:#{#param.storePaymentTypeId} = " + EStorePaymentType.UnderPayment + " AND  c.paidMoney > 0 AND c.paidMoney < c.paidAmount AND c.paidAmount >0) OR 1=1)) "
            + " AND ((:#{#param.storePaymentTypeId} IS NULL) OR ((:#{#param.storePaymentTypeId} = " + EStorePaymentType.IsPaid + " AND c.paidMoney = c.paidAmount AND c.paidAmount >0) OR 1=1)) "
            + " AND ((:#{#param.typeZNS} IS NULL) OR ((:#{#param.typeZNS} = " + EZNSType.ConfirmPayment + " AND c.zNSStatusSendPayment = true) OR 1=1)) "
            + " AND ((:#{#param.typeZNS} IS NULL) OR ((:#{#param.typeZNS} = " + EZNSType.CreateAccount + " AND c.zNSStatusSendCreateAccount = true) OR 1=1)) "
            + " AND ((:#{#param.active} IS NULL) OR (c.active = :#{#param.active} )) "
            + " AND ((:#{#param.createdByUserId} IS NULL) OR (c.createdByUserId = :#{#param.createdByUserId} )) "
            + " AND ((:#{#param.tinhThanhId} IS NULL) OR (c.tinhThanhId = :#{#param.tinhThanhId} )) "
//            + " AND ((:#{#param.numDaysNoTrans} IS NULL) OR (c.lastTransDate < CURRENT_DATE + :#{#param.numDaysNoTrans} )) " //todo
            + " AND ((:#{#param.storeDeployTypeId} IS NULL) OR (c.maNhaThuoc in (select d.maNhaThuoc from TrienKhais d where d.tieuChiTrienKhaiId = :#{#param.storeDeployTypeId}))) "
            + " AND ((:#{#param.idTypeBasic} IS NULL) OR (c.idTypeBasic = :#{#param.idTypeBasic} )) "
            + " AND ((:#{#param.supporterId} IS NULL) OR (c.supporterId = :#{#param.supporterId} )) "
//            + " AND ((:#{#param.outOfInvoice} IS NULL) OR ((:#{#param.outOfInvoice} = true AND (c.totalNumberInvoices < ((select count(n) from PhieuNhaps n) + (select count(x) from PhieuXuats x))  ) ) OR 1=1)) " //todo
            + " AND ((:#{#param.textSearch} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))"
            + " OR (:#{#param.textSearch} IS NULL OR lower(c.diaChi) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))"
            + " ORDER BY c.id desc"
    )
    Page<NhaThuocs> searchPage(@Param("param") NhaThuocsReq param, Pageable pageable);


    @Query("SELECT c FROM NhaThuocs c " +
            "WHERE 1=1 "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})"
            + " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))"
            + " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))"
            + " ORDER BY c.id desc"
    )
    List<NhaThuocs> searchList(@Param("param") NhaThuocsReq param);

    @Query("SELECT new vn.com.gsoft.system.model.dto.NhaThuocsRes(c.id, c.maNhaThuoc, c.tenNhaThuoc, u.tenDayDu ) " +
            " FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.nhaThuocMaNhaThuoc " +
            " join UserProfile u on nv.userUserId = u.id " +
            " WHERE 1=1 AND nv.role = 'Admin' " +
            " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId})" +
            " AND u.id = :#{#param.userIdQueryData} " +
            " AND (:#{#param.maNhaThuoc} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.maNhaThuoc},'%'))))" +
            " AND (:#{#param.tenNhaThuoc} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.tenNhaThuoc},'%'))))" +
            " AND ((:#{#param.textSearch} IS NULL OR lower(c.maNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%'))))" +
            " OR (:#{#param.textSearch} IS NULL OR lower(c.tenNhaThuoc) LIKE lower(concat('%',CONCAT(:#{#param.textSearch},'%')))))" +
            " ORDER BY c.id desc"
    )
    Page<NhaThuocsRes> searchPageNhaThuoc(@Param("param") NhaThuocsReq req, Pageable pageable);

    Optional<NhaThuocs> findByMaNhaThuoc(String maNhaThuoc);


    @Query("SELECT new vn.com.gsoft.system.model.dto.NhaThuocsRes(c.id, c.maNhaThuoc, c.tenNhaThuoc, u.tenDayDu ) FROM NhaThuocs c " +
            " join NhanVienNhaThuocs nv  on c.maNhaThuoc = nv.nhaThuocMaNhaThuoc " +
            " join UserProfile u on nv.userUserId = u.id " +
            " WHERE 1=1 AND nv.role = 'Admin' " +
            " AND (c.maNhaThuoc  = ?1)"
    )
    Optional<NhaThuocsRes> findNguoiPhuTrachByMaNhaThuoc(String maNhaThuoc);
}
