package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import java.math.BigDecimal;
import java.util.Date;

public class TtaHwbCheckingEntity_HI_MODEL {
    @ExcelProperty(value ="唯一标识")
    @FieldDesc(isUpdateWhereKey = true)
    private Integer hwbId;
    @ExcelIgnore
    private String promotionSection;
    @ExcelIgnore
    private String descriptionCn;
    @ExcelIgnore
    private String awardDescription;
    @ExcelIgnore
    private String groupDesc;
    @ExcelIgnore
    private String deptDesc;
    @ExcelIgnore
    private String deptCode;
    @ExcelIgnore
    private String groupCode;
    @ExcelIgnore
    private String brandCn;
    @ExcelIgnore
    private String brandEn;
    @ExcelIgnore
    private String content;
    @ExcelIgnore
    private String priorVendorCode;
    @ExcelIgnore
    private String priorVendorName;
    @ExcelIgnore
    private BigDecimal companyStandard;
    @ExcelIgnore
    private BigDecimal agreementStandard;
    @ExcelIgnore
    private Integer num;
    @ExcelIgnore
    private String chargeStandards;
    @ExcelIgnore
    private BigDecimal noReceiveAmount;
    @ExcelIgnore
    private BigDecimal receiveAmount;
    @ExcelIgnore
    private String collect;
    @ExcelIgnore
    private BigDecimal noUnconfirmAmount;
    @ExcelIgnore
    private BigDecimal unconfirmAmount;
    @ExcelIgnore
    private BigDecimal difference;
    @ExcelIgnore
    private String purchase;
    @ExcelIgnore
    private String exemptionReason;
    @ExcelIgnore
    private String exemptionReason2;
    @ExcelProperty(value ="借记单编号")
    @FieldDesc(isUpdateWhereKey = false)
    private String debitOrderCode;
    @ExcelProperty(value ="实收金额")
    @FieldDesc(isUpdateWhereKey = false)
    private BigDecimal receipts;
    @ExcelIgnore
    private String remark;
    @ExcelIgnore
    private Integer status;
    @ExcelIgnore
    private Integer parentId;
    @ExcelIgnore
    private String parentVendorCode;
    @ExcelIgnore
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date creationDate;
    @ExcelIgnore
    private Integer createdBy;
    @ExcelIgnore
    private Integer lastUpdatedBy;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @ExcelIgnore
    private Integer lastUpdateLogin;
    @ExcelIgnore
    private Integer transferOutPerson;
    @ExcelIgnore
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date transferOutDate;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    @ExcelIgnore
    private BigDecimal additionRate;
    @ExcelIgnore
    private Integer processId;
    @ExcelIgnore
    private Integer hwbYear;
    @ExcelIgnore
    private String batchCode;
    @ExcelIgnore
    private Integer operatorUserId;
    @ExcelIgnore
    private Integer  transferInPerson ;
    @ExcelIgnore
    private String promotionLocation;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private Date promotionStartDate;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    @ExcelIgnore
    private String hwbType ;
}
