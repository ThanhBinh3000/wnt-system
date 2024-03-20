package vn.com.gsoft.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "NhaThuocs")
public class NhaThuocs extends BaseEntity {

    @Id
    private String MaNhaThuoc;
    private String TenNhaThuoc;
    private String DiaChi;
    private String SoKinhDoanh;
    private String DienThoai;
    private String NguoiDaiDien;
    private String Email;
    private String Mobile;
    private String DuocSy;
    private boolean HoatDong;
    private boolean Active;
    private boolean IsReportDataGenerating;
    private Long TinhThanhId;
    private String MaNhaThuocCha;
    private Long id;
    private boolean IsConnectivity;
    private String ConnectivityCode;
    private String ConnectivityUserName;
    private String ConnectivityPassword;
    private String GeneralPharmacyId;
    private boolean IsGeneralPharmacy;
    private BigDecimal PaidAmount;
    private String Description;
    private Long DrugStoreTypeId;
    private boolean IsPaid;
    private Long ChainLinkId;
    private Long RegionId;
    private Long CityId;
    private Long WardId;
    private LocalDateTime LastTransDate;
    private String SupportPhones;
    private String DeliveryPolicy;
    private String ContentThankYou;
    private Long RecordStatusId;
    private Long IdTypeBasic;
    private String GhiChu;
    private boolean IsNationalDBConnected;
    private String ImagePreviewUrl;
    private String ImageThumbUrl;
    private int TotalNumberInvoices;
    private String ConnEInvoiceUserName;
    private String ConnEInvoicePassword;
    private String SymbolCodeInvocie;
    private String FormNumberInvoice;
    private int TypeInvoice;
    private int PaymentStatus;
    private BigDecimal PaidMoney;
    private boolean IsNationalSampleConnected;
    private String ConnectivityCodeMeidcal;
    private String ConnectivityPasswordMedical;
    private LocalDateTime ExpiredDate;
    private String BusinessDescription;
    private LocalDateTime PaidDate;
    private boolean IsUploading;
    private String ConnEInvoiceSerialCert;
    private String LinkConnectEInvoice;
    private String NameServiceEInvoice;
    private String PassServiceEInvoice;
    private String SignedString;
    private int TypeSendEinvocie;
    private String Slug_CustomerWebsite;
    private String GoogleLocation_CustomerWebsite;
    private Long ThemeId_CustomerWebsite;
    private String TokenZalo;
    private String ZaloKey;
    private String AppId;
    private String RefreshTokenZalo;
    private String ImageOrderThumbUrl;
    private String ImageOrderPreviewUrl;
    private int TypeMessage;
    private String Banner_CustomerWebsite;
    private String MainSlogan_CustomerWebsite;
    private String SubSlogan_CustomerWebsite;
    private String QRDeviceName;
    private String QRDeviceToken;
    private String SimData;
    private boolean Mapped;
    private boolean ZNS_StatusSendCreateAccount;
    private String ZNS_TrackingIdCreateAccount;
    private boolean ZNS_StatusSendPayment;
    private String ZNS_TrackingIdPayment;
    private Long BusinessId;
    private int CodeErrorConfirmPayment_ZNS;
    private int CodeErrorCreateAccount_ZNS;
    private String FooterPrint;
    private int Classify;
    private int Evaluate;
    private Long SupporterId;
    private Boolean UpgradeToPlus;

}
