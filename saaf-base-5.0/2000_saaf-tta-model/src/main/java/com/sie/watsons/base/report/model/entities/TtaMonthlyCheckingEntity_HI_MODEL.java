package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import java.math.BigDecimal;
import java.util.Date;

public class TtaMonthlyCheckingEntity_HI_MODEL {
    @ExcelProperty(value ="唯一标识")
    @FieldDesc(isUpdateWhereKey = true)
    private Integer osdId;
    @ExcelIgnore
    private String promotionSection;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    @ExcelIgnore
    private String promotionLocation;
    @ExcelIgnore
    private Integer timeDimension;
    @ExcelIgnore
    private Integer storesNum;
    @ExcelIgnore
    private String groupDesc;
    @ExcelIgnore
    private String deptDesc;
    @ExcelIgnore
    private String deptCode;
    @ExcelIgnore
    private String groupCode;
    @ExcelIgnore
    private String content;
    @ExcelIgnore
    private String itemCode;
    @ExcelIgnore
    private String cnName;
    @ExcelIgnore
    private String brandCn;
    @ExcelIgnore
    private String brandEn;
    @ExcelIgnore
    private String priorVendorCode;
    @ExcelIgnore
    private String priorVendorName;
    @ExcelIgnore
    private String contractYear;
    @ExcelIgnore
    private String contractStatus;
    @ExcelIgnore
    private String chargeStandards;
    @ExcelIgnore
    private BigDecimal chargeMoney;
    @ExcelIgnore
    private String chargeUnit;
    @ExcelIgnore
    private BigDecimal receiveAmount;
    @ExcelIgnore
    private BigDecimal originalAmount;
    @ExcelIgnore
    private BigDecimal unconfirmAmount;
    @ExcelIgnore
    private BigDecimal difference;
    @ExcelIgnore
    private String collect;
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
    private String batchCode;
    @ExcelIgnore
    private Integer transferInPerson;
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
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
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
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @ExcelIgnore
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    @ExcelIgnore
    private String chargeUnitName;
    @ExcelIgnore
    private BigDecimal originalBeforeAmount;
    @ExcelIgnore
    private BigDecimal additionRate;
    @ExcelIgnore
    private String referenceVendorCode;
    @ExcelIgnore
    private Integer processId;
    @ExcelIgnore
    private Integer osdYear;
    @ExcelIgnore
    private BigDecimal noReceiveAmount;
    @ExcelIgnore
    private BigDecimal noUnconfirmAmount;
    @ExcelIgnore
    private Integer operatorUserId;
}
