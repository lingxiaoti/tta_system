package com.sie.watsons.base.pon.itquotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPonQuoContentItEntity_HI Entity Object
 * Tue Dec 17 18:21:56 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pon_quo_content_it")
public class EquPonQuoContentItEntity_HI {
    private Integer quotationContentId;
    private Integer quotationId;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal amountOfMoney;
    private BigDecimal amount;
    private BigDecimal taxRate;
    private String quotationRemark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private Integer relevanceId;
    private BigDecimal bargainUnitPrice;
    private BigDecimal bargainDiscount;
    private BigDecimal bargainAmountOfMoney;
    private BigDecimal bargainTaxRate;

    public void setQuotationContentId(Integer quotationContentId) {
		this.quotationContentId = quotationContentId;
	}

	@Id	
	@SequenceGenerator(name="EQU_PON_QUO_CONTENT_IT_SEQ", sequenceName="EQU_PON_QUO_CONTENT_IT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="EQU_PON_QUO_CONTENT_IT_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="quotation_content_id", nullable=false, length=22)	
	public Integer getQuotationContentId() {
		return quotationContentId;
	}
    @Column(name="bargain_unit_price", nullable=true, length=22)
    public BigDecimal getBargainUnitPrice() {
        return bargainUnitPrice;
    }

    public void setBargainUnitPrice(BigDecimal bargainUnitPrice) {
        this.bargainUnitPrice = bargainUnitPrice;
    }
    @Column(name="bargain_discount", nullable=true, length=22)
    public BigDecimal getBargainDiscount() {
        return bargainDiscount;
    }

    public void setBargainDiscount(BigDecimal bargainDiscount) {
        this.bargainDiscount = bargainDiscount;
    }
    @Column(name="bargain_amount_of_money", nullable=true, length=22)
    public BigDecimal getBargainAmountOfMoney() {
        return bargainAmountOfMoney;
    }

    public void setBargainAmountOfMoney(BigDecimal bargainAmountOfMoney) {
        this.bargainAmountOfMoney = bargainAmountOfMoney;
    }
    @Column(name="bargain_tax_rate", nullable=true, length=22)
    public BigDecimal getBargainTaxRate() {
        return bargainTaxRate;
    }

    public void setBargainTaxRate(BigDecimal bargainTaxRate) {
        this.bargainTaxRate = bargainTaxRate;
    }

    @Column(name="quotation_id", nullable=false, length=22)
    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }
    @Column(name="relevance_id", nullable=true, length=22)
    public Integer getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(Integer relevanceId) {
        this.relevanceId = relevanceId;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name="unit_price", nullable=true, length=22)	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(name="discount", nullable=true, length=22)	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setAmountOfMoney(BigDecimal amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}

	@Column(name="amount_of_money", nullable=true, length=22)	
	public BigDecimal getAmountOfMoney() {
		return amountOfMoney;
	}

	@Column(name="amount", nullable=true, length=22)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name="tax_rate", nullable=true, length=22)	
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setQuotationRemark(String quotationRemark) {
		this.quotationRemark = quotationRemark;
	}

	@Column(name="quotation_remark", nullable=true, length=4000)	
	public String getQuotationRemark() {
		return quotationRemark;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
