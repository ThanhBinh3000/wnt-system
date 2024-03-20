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
    private String maNhaThuoc;
    private String tenNhaThuoc;
    private String diaChi;
    private String soKinhDoanh;
    private String dienThoai;
    private String nguoiDaiDien;
    private String email;
    private String mobile;
    private String duocSy;
    private boolean hoatDong;
    private boolean active;
    private boolean isReportDataGenerating;
    private Long tinhThanhId;
    private String maNhaThuocCha;
    private Long id;
    private boolean isConnectivity;
    private String connectivityCode;
    private String connectivityUserName;
    private String connectivityPassword;
    private String generalPharmacyId;
    private boolean isGeneralPharmacy;
    private BigDecimal paidAmount;
    private String description;
    private Long drugStoreTypeId;
    private boolean isPaid;
    private Long chainLinkId;
    private Long regionId;
    private Long cityId;
    private Long wardId;
    private LocalDateTime lastTransDate;
    private String supportPhones;
    private String deliveryPolicy;
    private String contentThankYou;
    private Long recordStatusId;
    private Long idTypeBasic;
    private String ghiChu;
    private boolean isNationalDBConnected;
    private String imagePreviewUrl;
    private String imageThumbUrl;
    private int totalNumberInvoices;
    private String connEInvoiceUserName;
    private String connEInvoicePassword;
    private String symbolCodeInvocie;
    private String formNumberInvoice;
    private int typeInvoice;
    private int paymentStatus;
    private BigDecimal paidMoney;
    private boolean isNationalSampleConnected;
    private String connectivityCodeMeidcal;
    private String connectivityPasswordMedical;
    private LocalDateTime expiredDate;
    private String businessDescription;
    private LocalDateTime paidDate;
    private boolean isUploading;
    private String connEInvoiceSerialCert;
    private String linkConnectEInvoice;
    private String nameServiceEInvoice;
    private String passServiceEInvoice;
    private String signedString;
    private int typeSendEinvocie;
    private String slugCustomerWebsite;
    private String googleLocationCustomerWebsite;
    private Long themeIdCustomerWebsite;
    private String tokenZalo;
    private String zaloKey;
    private String appId;
    private String refreshTokenZalo;
    private String imageOrderThumbUrl;
    private String imageOrderPreviewUrl;
    private int typeMessage;
    private String bannerCustomerWebsite;
    private String mainSloganCustomerWebsite;
    private String subSloganCustomerWebsite;
    private String qRDeviceName;
    private String qRDeviceToken;
    private String simData;
    private boolean mapped;
    private boolean zNSStatusSendCreateAccount;
    private String zNSTrackingIdCreateAccount;
    private boolean zNSStatusSendPayment;
    private String zNSTrackingIdPayment;
    private Long businessId;
    private int codeErrorConfirmPayment_ZNS;
    private int codeErrorCreateAccount_ZNS;
    private String footerPrint;
    private int classify;
    private int evaluate;
    private Long supporterId;
    private Boolean upgradeToPlus;

}
