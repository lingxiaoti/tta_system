package com.sie.watsons.base.proposal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaProposalHeaderEntity_HI Entity Object
 * Tue Jun 04 08:38:52 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_proposal_header")
public class TtaProposalHeaderEntity_HI {
    private Integer proposalId;
    private String orderNbr;
    private String proposalNbr;
    private String vendorNbr;
    private String vendorName;
    private String proposalYear;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private String status;
    private BigDecimal versionCode;
    private String isTransdepart;
    private String newPaymentMethod;
    private BigDecimal newStoreQty;
    private BigDecimal plnAdjustScale;
    private String isPlnConfirm;
    private String isTermsConfirm;
    private String isDepartConfirm;
    private String isNewConfirm;
    private String isQuestConfirm;
    private String isCrossYear;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String isChange;
    private Integer sourceId;
    private String sourceCode;
    private String newOrExisting;
	private String deptCode1;
	private String deptDesc1;
	private String deptCode2;
	private String deptDesc2;
	private String deptCode3;
	private String deptDesc3;
	private String deptCode4;
	private String deptDesc4;
	private String deptCode5;
	private String deptDesc5;
	private String majorDeptDesc;//主部门
	private Integer majorDeptId;//主部门id
	private String  majorDeptCode;//主部门编号
	private String saleType;//销售方式
	private String isSkipApprove; //是否自动跳过GM审批,Y是跳过,N不跳过
	private Integer lastYearProposalId ;

	private String versionStatus;
	private Integer originProposalId;

	private String userGroupCode;//用户group
	private String userGroupName;
	private String isSplit;

    private Integer operatorUserId;

    private String isCollection;//是否是补录单



	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_PROPOSAL_HEADER", sequenceName = "SEQ_TTA_PROPOSAL_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PROPOSAL_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="proposal_id", nullable=false, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	@Column(name="order_nbr", nullable=false, length=50)	
	public String getOrderNbr() {
		return orderNbr;
	}

	public void setProposalNbr(String proposalNbr) {
		this.proposalNbr = proposalNbr;
	}

	@Column(name="proposal_nbr", nullable=true, length=50)	
	public String getProposalNbr() {
		return proposalNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=false, length=30)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=230)	
	public String getVendorName() {
		return vendorName;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=false, length=30)	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	@Column(name="approve_date", nullable=true, length=7)	
	public Date getApproveDate() {
		return approveDate;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVersionCode(BigDecimal versionCode) {
		this.versionCode = versionCode;
	}

	@Column(name="version_code")
	public BigDecimal getVersionCode() {
		return versionCode;
	}

	public void setIsTransdepart(String isTransdepart) {
		this.isTransdepart = isTransdepart;
	}

	@Column(name="is_transdepart", nullable=false, length=1)	
	public String getIsTransdepart() {
		return isTransdepart;
	}

	public void setNewPaymentMethod(String newPaymentMethod) {
		this.newPaymentMethod = newPaymentMethod;
	}

	@Column(name="new_payment_method")
	public String getNewPaymentMethod() {
		return newPaymentMethod;
	}

	@Column(name="new_store_qty")
	public BigDecimal getNewStoreQty() {
		return newStoreQty;
	}

	public void setNewStoreQty(BigDecimal newStoreQty) {
		this.newStoreQty = newStoreQty;
	}

	@Column(name="pln_adjust_scale")
	public BigDecimal getPlnAdjustScale() {
		return plnAdjustScale;
	}

	public void setPlnAdjustScale(BigDecimal plnAdjustScale) {
		this.plnAdjustScale = plnAdjustScale;
	}

	public void setIsPlnConfirm(String isPlnConfirm) {
		this.isPlnConfirm = isPlnConfirm;
	}

	@Column(name="is_pln_confirm", nullable=true, length=1)	
	public String getIsPlnConfirm() {
		return isPlnConfirm;
	}

	public void setIsTermsConfirm(String isTermsConfirm) {
		this.isTermsConfirm = isTermsConfirm;
	}

	@Column(name="is_terms_confirm", nullable=true, length=1)	
	public String getIsTermsConfirm() {
		return isTermsConfirm;
	}

	public void setIsDepartConfirm(String isDepartConfirm) {
		this.isDepartConfirm = isDepartConfirm;
	}

	@Column(name="is_depart_confirm", nullable=true, length=1)	
	public String getIsDepartConfirm() {
		return isDepartConfirm;
	}

	public void setIsNewConfirm(String isNewConfirm) {
		this.isNewConfirm = isNewConfirm;
	}

	@Column(name="is_new_confirm", nullable=true, length=1)	
	public String getIsNewConfirm() {
		return isNewConfirm;
	}

	public void setIsQuestConfirm(String isQuestConfirm) {
		this.isQuestConfirm = isQuestConfirm;
	}

	@Column(name="is_quest_confirm", nullable=true, length=1)	
	public String getIsQuestConfirm() {
		return isQuestConfirm;
	}

	public void setIsCrossYear(String isCrossYear) {
		this.isCrossYear = isCrossYear;
	}

	@Column(name="is_cross_year", nullable=false, length=1)	
	public String getIsCrossYear() {
		return isCrossYear;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name="begin_date", nullable=true, length=7)	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	@Column(name="is_change", nullable=true, length=1)	
	public String getIsChange() {
		return isChange;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name="source_id", nullable=true, length=22)	
	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(name="source_code", nullable=true, length=50)	
	public String getSourceCode() {
		return sourceCode;
	}

	@Column(name="new_or_existing")
	public String getNewOrExisting() {
		return newOrExisting;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	@Column(name="dept_code1")
	public String getDeptCode1() {
		return deptCode1;
	}

	public void setDeptCode1(String deptCode1) {
		this.deptCode1 = deptCode1;
	}

	@Column(name="dept_desc1")
	public String getDeptDesc1() {
		return deptDesc1;
	}

	public void setDeptDesc1(String deptDesc1) {
		this.deptDesc1 = deptDesc1;
	}

	@Column(name="dept_code2")
	public String getDeptCode2() {
		return deptCode2;
	}

	public void setDeptCode2(String deptCode2) {
		this.deptCode2 = deptCode2;
	}


	@Column(name="dept_desc2")
	public String getDeptDesc2() {
		return deptDesc2;
	}

	public void setDeptDesc2(String deptDesc2) {
		this.deptDesc2 = deptDesc2;
	}

	@Column(name="dept_code3")
	public String getDeptCode3() {
		return deptCode3;
	}

	public void setDeptCode3(String deptCode3) {
		this.deptCode3 = deptCode3;
	}

	@Column(name="dept_desc3")
	public String getDeptDesc3() {
		return deptDesc3;
	}

	public void setDeptDesc3(String deptDesc3) {
		this.deptDesc3 = deptDesc3;
	}

	@Column(name="dept_code4")
	public String getDeptCode4() {
		return deptCode4;
	}

	public void setDeptCode4(String deptCode4) {
		this.deptCode4 = deptCode4;
	}

	@Column(name="dept_desc4")
	public String getDeptDesc4() {
		return deptDesc4;
	}

	public void setDeptDesc4(String deptDesc4) {
		this.deptDesc4 = deptDesc4;
	}

	@Column(name="dept_code5")
	public String getDeptCode5() {
		return deptCode5;
	}

	public void setDeptCode5(String deptCode5) {
		this.deptCode5 = deptCode5;
	}

	@Column(name="dept_desc5")
	public String getDeptDesc5() {
		return deptDesc5;
	}

	public void setDeptDesc5(String deptDesc5) {
		this.deptDesc5 = deptDesc5;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name="version_status")
	public String getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(String versionStatus) {
		this.versionStatus = versionStatus;
	}

	@Column(name="origin_proposal_id")
	public Integer getOriginProposalId() {
		return originProposalId;
	}

	public void setOriginProposalId(Integer originProposalId) {
		this.originProposalId = originProposalId;
	}

	@Column(name="major_dept_desc")
	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	@Column(name="sale_type")
	public String getSaleType() {
		return saleType;
	}


	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	@Column(name="major_dept_id")
	public Integer getMajorDeptId() {
		return majorDeptId;
	}

	public void setMajorDeptId(Integer majorDeptId) {
		this.majorDeptId = majorDeptId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}


	@Column(name="major_dept_code", nullable=false, length=30)
	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}


	public void setIsSkipApprove(String isSkipApprove) {
		this.isSkipApprove = isSkipApprove;
	}

	@Column(name="is_skip_approve", nullable=true, length=2)
	public String getIsSkipApprove() {
		return isSkipApprove;
	}

	@Column(name="last_year_proposal_id")
	public Integer getLastYearProposalId() {
		return lastYearProposalId;
	}

	public void setLastYearProposalId(Integer lastYearProposalId) {
		this.lastYearProposalId = lastYearProposalId;
	}

	@Column(name="user_group_code", nullable=false, length=30)
	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	@Column(name="user_group_name", nullable=false, length=200)
	public String getUserGroupName() {
		return userGroupName;
	}

	@Column(name="IS_SPLIT", nullable=false, length=10)
	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Column(name="IS_COLLECTION")
	public String getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}
}
