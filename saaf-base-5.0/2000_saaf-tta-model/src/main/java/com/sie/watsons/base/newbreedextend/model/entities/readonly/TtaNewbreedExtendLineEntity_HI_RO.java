package com.sie.watsons.base.newbreedextend.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaNewbreedExtendLineEntity_HI_RO Entity Object
 * Wed Jun 26 14:15:21 CST 2019  Auto Generate
 */

public class TtaNewbreedExtendLineEntity_HI_RO {
	/**
	 * 开始时插入行数据
	 */
	public static final String TTA_NBEL_INSERT = "       select \n" +
			"             blv.lookup_code breadName\n" +
			"       from \n" +
			"             base_lookup_values      blv\n" +
			"       where blv.lookup_type = 'NB_NAME' \n" +
			"       and blv.system_code = 'BASE'";
	/**
	 * 查询数据
	 */
	public static final String TTA_NBEL_LIST ="       select tnel.newbreed_extend_l_id,\n" +
			"              tnel.newbreed_extend_h_id,\n" +
			"              blv.meaning  breadNameName,\n" +
			"              tnel.bread_name  breadName,\n" +
			"              tnel.standard,\n" +
			"              tnel.charge_method,\n" +
			"              tnel.fee_standard,\n" +
			"              tnel.unit,\n" +
			"              tnel.bread_qty,\n" +
			"              tnel.beyond_charge_method,\n" +
			"              tnel.beyond_fee_standard,\n" +
			"              tnel.beyond_unit,\n" +
			"              tnel.version_num,\n" +
			"              tnel.creation_date,\n" +
			"              tnel.created_by,\n" +
			"              tnel.last_updated_by,\n" +
			"              tnel.last_update_date,\n" +
			"              tnel.last_update_login\n" +
			"              \n" +
			"               from  tta_newbreed_extend_line tnel\n" +
			"               left join  base_lookup_values      blv \n" +
			"               on  tnel.bread_name = blv.lookup_code \n" +
			"               and  blv.lookup_type = 'NB_NAME' \n" +
			"               and  blv.system_code = 'BASE'\n" +
			"               where 1=1 ";
	/**
	 * 查询
	 */
	public static final String TTA_NBE_STANDARD = "select tnsh.breed_name breadName, tnsl.standard standard\n" +
			"  from tta_newbreed_extend_header tneh,\n" +
			"       tta_newbreed_set_line      tnsl,\n" +
			"       tta_newbreed_set_header    tnsh,\n" +
			"       tta_proposal_terms_header  tpth,\n" +
			"       tta_proposal_header tph,\n" +
			"       (select lookup_type, lookup_code, meaning,description\n" +
			"          from base_lookup_values\n" +
			"         where lookup_type = 'SALE_WAY'\n" +
			"           and enabled_flag = 'Y'\n" +
			"           and delete_flag = 0\n" +
			"           and start_date_active < sysdate\n" +
			"           and (end_date_active >= sysdate or end_date_active is null)\n" +
			"           and system_code = 'BASE')  blv\n" +
			" where tneh.collect_range = tnsl.range\n" +
			"   and (( nvl(tneh.store_style,'-1') = tnsl.store_type and tnsl.range = '3') or\n" +
			"       (tnsl.purchase_type =\n" +
			"       decode( trim(blv.description), 'A', '1', '2') and\n" +
			"       tnsl.range != '3'))\n" +
			"       \n" +
			"   and tneh.proposal_id = tpth.proposal_id\n" +
			"   and tnsh.dept_code = tph.major_dept_code\n" +
			"   and tph.sale_type = blv.lookup_code(+)\n" +
			"   and tnsl.dept_code = tpth.dept_code\n" +
			"   and tneh.proposal_id = tpth.proposal_id\n" +
			"   and tnsl.newbreed_set_id = tnsh.newbreed_set_id\n" +
			"   and nvl(tnsh.delete_flag, 0) = 0\n" +
			"   and nvl(tnsl.delete_flag, 0) = 0\n" +
			"   and nvl(tnsl.is_enable, 'N') = 'Y'\n" +
			"   and nvl(tnsh.is_effective, 0) = '1'" +
			"   and tnsh.newbreed_year = to_number(tph.proposal_year)";


    private Integer newbreedExtendLId;
    private Integer newbreedExtendHId;
    private String breadName;
	private String breadNameName;
    private java.math.BigDecimal standard;
    private String chargeMethod;
    private String feeStandard;
    private String unit;
    private Integer breadQty;
    private String beyondChargeMethod;
    private String beyondFeeStandard;
    private String beyondUnit;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setNewbreedExtendLId(Integer newbreedExtendLId) {
		this.newbreedExtendLId = newbreedExtendLId;
	}

	
	public Integer getNewbreedExtendLId() {
		return newbreedExtendLId;
	}

	public void setNewbreedExtendHId(Integer newbreedExtendHId) {
		this.newbreedExtendHId = newbreedExtendHId;
	}

	
	public Integer getNewbreedExtendHId() {
		return newbreedExtendHId;
	}

	public void setBreadName(String breadName) {
		this.breadName = breadName;
	}

	
	public String getBreadName() {
		return breadName;
	}

	public void setStandard(java.math.BigDecimal standard) {
		this.standard = standard;
	}

	
	public java.math.BigDecimal getStandard() {
		return standard;
	}

	public void setChargeMethod(String chargeMethod) {
		this.chargeMethod = chargeMethod;
	}

	
	public String getChargeMethod() {
		return chargeMethod;
	}

	public void setFeeStandard(String feeStandard) {
		this.feeStandard = feeStandard;
	}

	
	public String getFeeStandard() {
		return feeStandard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setBreadQty(Integer breadQty) {
		this.breadQty = breadQty;
	}

	
	public Integer getBreadQty() {
		return breadQty;
	}

	public void setBeyondChargeMethod(String beyondChargeMethod) {
		this.beyondChargeMethod = beyondChargeMethod;
	}

	
	public String getBeyondChargeMethod() {
		return beyondChargeMethod;
	}

	public void setBeyondFeeStandard(String beyondFeeStandard) {
		this.beyondFeeStandard = beyondFeeStandard;
	}

	
	public String getBeyondFeeStandard() {
		return beyondFeeStandard;
	}

	public void setBeyondUnit(String beyondUnit) {
		this.beyondUnit = beyondUnit;
	}

	
	public String getBeyondUnit() {
		return beyondUnit;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getBreadNameName() {
		return breadNameName;
	}

	public void setBreadNameName(String breadNameName) {
		this.breadNameName = breadNameName;
	}
}
