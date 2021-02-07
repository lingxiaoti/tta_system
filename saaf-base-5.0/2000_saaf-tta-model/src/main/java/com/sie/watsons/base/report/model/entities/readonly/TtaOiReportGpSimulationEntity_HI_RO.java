package com.sie.watsons.base.report.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaOiReportGpSimulationEntity_HI_RO Entity Object
 * Thu Apr 30 09:53:34 CST 2020  Auto Generate
 */

public class TtaOiReportGpSimulationEntity_HI_RO {

    private Integer simulationId;
    private String orderNbr;
    private Integer versionCode;
    private String vendorNbr;
    private String vendorName;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptDesc;
    private String brandCn;
    private String brandEn;
    private String majorDeptCode;
    private String majorDeptDesc;
    private String salesType;
    private BigDecimal gp;
    private BigDecimal salesExcludeAmt;
    private String remark;
    private String actionCode;
    private String gpReasonCode;
    private String actionPlan;
    private String targetDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String salesTypeName;
    private String batchCode;
    private BigDecimal gpRate;
	private String createUserName;
	private String updateUserName;
	private BigDecimal actGpValue; //Act.GP%
	private BigDecimal gpVsValue; //-- Actual GP% vs TTA GP%
	private BigDecimal gpLoss; //GP$ Loss
	private String actionName;
	private String gpReasonName;
	private String confirmStatus;//是否确认,Y已确认,N待确认
	private Integer confirmUser;//确认人id
	private String confirmUserName;//确认人名称
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;//确认日期
	private BigDecimal propGpAmt;  //预估gp金额
	private BigDecimal propSalesAmt; //预估salse金额


	public final static String getUpDateConfirmStatus(String batchCode, Integer operatorUserId) {
		String sql = "update tta_oi_report_gp_simulation_line t\n" +
				"   set t.confirm_status = 'N',\n" +
				"       t.confirm_user =" + operatorUserId + ",\n" +
				"       t.confirm_date = sysdate,\n" +
				"       t.last_update_date = sysdate,\n" +
				"       t.last_updated_by=" + operatorUserId + "\n" +
				" where t.batch_code ='" + batchCode + "'\n" +
				"  and t.confirm_status = 'Y'";
		return sql;
	}


    public static final String  getGpSimulationSql (String userType, Integer operatorUserId, String batchCode){
    	String table = "";
				if ("45".equalsIgnoreCase(userType)) {
					table = "tta_oi_report_gp_simulation_line";
				} else {
				    //非bic用户
					table = "select t1.*\n" +
							"          from tta_oi_report_gp_simulation_line t1\n" +
							"         inner join tta_oi_report_header th\n" +
							"            on t1.batch_code = th.batch_code\n" +
							"         where exists (select group_desc\n" +
							"                  from (select nvl(dp.group_desc, '-1') as group_desc\n" +
							"                          from base_users bu\n" +
							"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
							"                            on bu.user_id = dp.user_id\n" +
							"                           and bu.data_type = dp.data_type\n" +
							"                         where bu.user_id =" + operatorUserId + "\n" +
							"                           and bu.data_type = 1\n" +
							"                         group by nvl(dp.group_desc, '-1')) t2\n" +
							"                 where t2.group_desc = t1.group_name)\n" +
							"           and nvl(t1.batch_code, '-1') ='" + batchCode + "'\n" +
							"           and nvl(th.is_publish, 'N') = 'Y'\n" +
							"        union\n" +
							"        select t1.*\n" +
							"          from tta_oi_report_gp_simulation_line t1\n" +
							"         inner join tta_oi_report_header th\n" +
							"            on t1.batch_code = th.batch_code\n" +
							"         where exists (select group_desc, dept_desc\n" +
							"                  from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
							"                               nvl(dp.dept_desc, '-1') as dept_desc\n" +
							"                          from base_users bu\n" +
							"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
							"                            on bu.user_id = dp.user_id\n" +
							"                           and bu.data_type = dp.data_type\n" +
							"                         where bu.user_id =" + operatorUserId + "\n" +
							"                           and bu.data_type = 2\n" +
							"                         group by nvl(dp.group_desc, '-1'),\n" +
							"                                  nvl(dp.dept_desc, '-1')) t2\n" +
							"                 where t2.group_desc = t1.group_name\n" +
							"                   and t2.dept_desc = t1.dept_desc)\n" +
							"           and nvl(t1.batch_code, '-1') = '" + batchCode + "'\n" +
							"           and nvl(th.is_publish, 'N') = 'Y'\n" +
							"        union\n" +
							"        select t1.*\n" +
							"          from tta_oi_report_gp_simulation_line t1\n" +
							"         inner join tta_oi_report_header th\n" +
							"            on t1.batch_code = th.batch_code\n" +
							"         where exists (select group_desc, dept_desc\n" +
							"                  from (select nvl(dp.group_desc, '-1') as group_desc,\n" +
							"                               nvl(dp.dept_desc, '-1') as dept_desc,\n" +
							"                               nvl(dp.brand_cn, '-1') as brand_cn\n" +
							"                          from base_users bu\n" +
							"                         inner join tta_user_group_dept_brand_eft_v dp\n" +
							"                            on bu.user_id = dp.user_id\n" +
							"                           and bu.data_type = dp.data_type\n" +
							"                         where bu.user_id =" + operatorUserId + "\n" +
							"                           and bu.data_type = 3\n" +
							"                         group by nvl(dp.group_desc, '-1'),\n" +
							"                                  nvl(dp.dept_desc, '-1'),\n" +
							"                                  nvl(dp.brand_cn, '-1')) t2\n" +
							"                 where t2.group_desc = t1.group_name\n" +
							"                   and t2.dept_desc = t1.dept_desc\n" +
							"                   and t2.brand_cn = t1.brand_cn)\n" +
							"           and nvl(t1.batch_code, '-1') = '" + batchCode + "'\n" +
							"           and nvl(th.is_publish, 'N') = 'Y'\n" +
							"        union\n" +
							"        select t1.*\n" +
							"          from tta_oi_report_gp_simulation_line t1\n" +
							"         inner join tta_oi_report_header th\n" +
							"            on t1.batch_code = th.batch_code\n" +
							"         where t1.transfer_in_person =" + operatorUserId + "\n" +
							"           and nvl(th.is_publish, 'N') = 'Y'  and nvl(t1.batch_code, '-1') = '" + batchCode + "'\n";
				}
		String sql = "select \n" +
				"        decode(nvl(tr.sales_exclude_amt,0),0,0,round(tr.gp/tr.sales_exclude_amt * 100,2)) as act_gp_value, -- Act.GP%\n" +
				"        decode(nvl(tr.sales_exclude_amt,0),0,0,round(tr.gp/tr.sales_exclude_amt * 100,2)) - tr.gp_rate as gp_vs_value, -- Actual GP% vs TTA GP%\n" +
				"        round(((decode(nvl(tr.sales_exclude_amt,0),0,0,round(tr.gp/tr.sales_exclude_amt * 100,2)) - tr.gp_rate) * tr.sales_exclude_amt)/100,0) as gp_loss, -- GP$ Loss\n" +
				"        tr.*,\n" +
				"        blv.meaning as action_name,\n" +
				"        blv2.meaning as gp_reason_name,\n" +
				"        bu.user_full_name      as create_user_name,\n" +
				"        bu_last.user_full_name as update_user_name,\n" +
				"        cbu.user_full_name as confirm_user_name\n" +
				"   from (" +  table + ")  tr\n" +
				"   left join base_users bu\n" +
				"     on bu.user_id = tr.created_by\n" +
				"   left join base_users bu_last\n" +
				"     on tr.last_updated_by = bu_last.user_id\n" +
				"   left join base_lookup_values blv\n" +
				"     on blv.lookup_code = tr.action_code\n" +
				"    and blv.lookup_type = 'TTA_ACTION_CODE'\n" +
				"   left join base_lookup_values blv2\n" +
				"     on blv2.lookup_code = tr.gp_reason_code\n" +
				"    and blv2.lookup_type = 'TTA_REASON_GP'\n" +
				"    left join base_users cbu on cbu.user_id = tr.confirm_user\n" +
				"  where 1 = 1 ";
    	return sql;
	}

	public static void main(String[] args) {
		String sql = getGenericSql ("xxxxx", 1, "202001");
		System.out.println(sql);
	}


	public static String getGenericSql(String batchCode, Integer operatorUserId, String endYearMonth) {
    	String startYearMonth = endYearMonth.substring(0, 4) + "01";
    	String sql = "insert into tta_oi_report_gp_simulation_line\n" +
				"  (simulation_id,\n" +
				"   batch_code,\n" +
				"   order_nbr,\n" +
				"   version_code,\n" +
				"   vendor_nbr,\n" +
				"   vendor_name,\n" +
				"   group_code,\n" +
				"   group_name,\n" +
				"   dept_code,\n" +
				"   dept_desc,\n" +
				"   brand_cn,\n" +
				"   brand_en,\n" +
				"   major_dept_code,\n" +
				"   major_dept_desc,\n" +
				"   sales_type,\n" +
				"   sales_type_name,\n" +
				"   gp_rate,\n" +
				"   gp,\n" +
				"   sales_exclude_amt,\n" +
				"   created_by,\n" +
				"   last_updated_by,\n" +
				"   last_update_date,\n" +
				"   creation_date,\n" +
				"   last_update_login,\n" +
				"   version_num,\n" +
				"	prop_sales_amt,\n" +
				"	prop_gp_amt)\n" +
				"  select seq_tta_oi_report_gp_simulation_line.nextval,\n" +
				"		  '"+ batchCode + "' as batch_code,\n" +
				"         tph.order_nbr,\n" +
				"         tph.version_code,\n" +
				"         nvl(tph.vendor_nbr, v.vendor_nbr) as vendor_nbr,\n" +
				"         ts.supplier_name,\n" +
				"         nvl(tbl.group_code, v.group_code) as group_code,\n" +
				"         nvl(tbl.group_desc, v.group_desc) as group_desc,\n" +
				"         nvl(tbl.dept_code, v.dept_code) as dept_code,\n" +
				"         nvl(tbl.dept_desc, v.dept_desc) as dept_desc,\n" +
				"         nvl(tbl.brand_cn, v.brand_cn) as brand_cn,\n" +
				"         nvl(tbl.brand_en, v.brand_en) as brand_en,\n" +
				"         tth.dept_code as major_dept_code,\n" +
				"         tth.dept_desc as major_dept_desc,\n" +
				"         tth.sales_type,\n" +
				"         saletypelookup.meaning as sales_type_name,\n" +
				"         tbl.gp as gp_rate,\n" +
				"         v.gp,\n" +
				"         v.sales_exclude_amt,\n" +
				operatorUserId + " as created_by,\n" +
				operatorUserId + " as last_updated_by,\n" +
				"         sysdate as last_update_date,\n" +
				"         sysdate as creation_date,\n" +
				operatorUserId + "  as last_update_login,\n" +
				"         0 as version_num,\n" +
				"	tbl.fcs_sales as prop_sales_amt, " +
				"	round(tbl.gp * tbl.fcs_sales/100,0) as prop_gp_amt" +
				"    from tta_proposal_header_new_version_v tph\n" +
				"   inner join tta_proposal_terms_header tth\n" +
				"      on tph.proposal_id = tth.proposal_id\n" +
				"   inner join tta_brandpln_line tbl\n" +
				"      on tbl.proposal_id = tph.proposal_id\n" +
				"    left join (select lookup_type, lookup_code, meaning\n" +
				"                 from base_lookup_values\n" +
				"                where lookup_type = 'SALE_WAY'\n" +
				"                  and enabled_flag = 'Y'\n" +
				"                  and delete_flag = 0\n" +
				"                  and start_date_active < sysdate\n" +
				"                  and (end_date_active >= sysdate or end_date_active is null)\n" +
				"                  and system_code = 'BASE') saletypelookup\n" +
				"      on tph.sale_type = saletypelookup.lookup_code\n" +
				"    full join (select tsvi.vendor_nbr,\n" +
				"                      tsvi.group_code,\n" +
				"                      tsvi.dept_code,\n" +
				"                      tsvi.brand_en,\n" +
				"                      tsvi.brand_cn,\n" +
				"                      max(tsvi.dept_desc) as dept_desc,\n" +
				"                      max(tsvi.group_desc) as group_desc,\n" +
				"                      sum(sales_exclude_amt) as sales_exclude_amt,\n" +
				"                      sum(gp) as gp\n" +
				"                 from tta_sale_vender_item_sum_v tsvi\n" +
				"                where sales_exclude_amt != 0  " +
				"						and  tsvi.tran_date >=" + startYearMonth +
				" 						and  tsvi.tran_date <=" + endYearMonth +
				"                group by tsvi.vendor_nbr,\n" +
				"                         tsvi.group_code,\n" +
				"                         tsvi.dept_code,\n" +
				"                         tsvi.brand_en,\n" +
				"                         tsvi.brand_cn) v\n" +
				"      on v.group_code = tbl.group_code\n" +
				"     and v.dept_code = tbl.dept_code\n" +
				"     and v.brand_en = tbl.brand_en\n" +
				"     and v.brand_cn = tbl.brand_cn\n" +
				"     and v.vendor_nbr = tph.vendor_nbr\n" +
				"    left join tta_supplier ts\n" +
				"    on ts.supplier_code = nvl(tph.vendor_nbr, v.vendor_nbr)" +
				" where tph.proposal_year=" + endYearMonth.substring(0, 4) +
				" and tph.status = 'C' ";
    	return sql;
	}


	public static final String getSummarySql(String batchCode) {
        String sql = "select \n" +
				"       tab.group_name,\n" +
				"		tab.action_name,\n" +
				"       tab.action_name || '@' || tab.group_name as key,\n" +
				"       round(sum(tab.gp_loss),0) as value\n" +
				"  from (select round(decode(nvl(t.sales_exclude_amt, 0),\n" +
				"                      0,\n" +
				"                      -gp_rate * sales_exclude_amt / 100,\n" +
				"                      (t.gp / sales_exclude_amt * 100 - gp_rate) *\n" +
				"                      sales_exclude_amt / 100),0) as gp_loss,\n" +
				"               t.group_name,\n" +
				"               t.action_code,\n" +
				"               blv.meaning as action_name\n" +
				"          from tta_oi_report_gp_simulation_line t\n" +
				"          left join base_lookup_values blv\n" +
				"            on t.action_code = blv.lookup_code\n" +
				"           and blv.lookup_type = 'TTA_ACTION_CODE'\n" +
				"           and blv.system_code = 'BASE'\n" +
				"         where t.batch_code='" + batchCode + "'\n" +
				"         order by to_number(t.action_code)\n" +
				"        ) tab\n" +
				" group by tab.action_name, tab.group_name order by to_number(max(tab.action_code)) ";
        return sql;
    }


	public void setSimulationId(Integer simulationId) {
		this.simulationId = simulationId;
	}

	
	public Integer getSimulationId() {
		return simulationId;
	}

	public void setOrderNbr(String orderNbr) {
		this.orderNbr = orderNbr;
	}

	
	public String getOrderNbr() {
		return orderNbr;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	
	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
	}

	public void setMajorDeptCode(String majorDeptCode) {
		this.majorDeptCode = majorDeptCode;
	}

	
	public String getMajorDeptCode() {
		return majorDeptCode;
	}

	public void setMajorDeptDesc(String majorDeptDesc) {
		this.majorDeptDesc = majorDeptDesc;
	}

	
	public String getMajorDeptDesc() {
		return majorDeptDesc;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	
	public String getSalesType() {
		return salesType;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	
	public BigDecimal getGp() {
		return gp;
	}

	public void setSalesExcludeAmt(BigDecimal salesExcludeAmt) {
		this.salesExcludeAmt = salesExcludeAmt;
	}

	
	public BigDecimal getSalesExcludeAmt() {
		return salesExcludeAmt;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	
	public String getActionCode() {
		return actionCode;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	
	public String getActionPlan() {
		return actionPlan;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	
	public String getTargetDate() {
		return targetDate;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setSalesTypeName(String salesTypeName) {
		this.salesTypeName = salesTypeName;
	}

	public String getSalesTypeName() {
		return salesTypeName;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public BigDecimal getGpRate() {
		return gpRate;
	}

	public void setGpRate(BigDecimal gpRate) {
		this.gpRate = gpRate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getGpReasonCode() {
		return gpReasonCode;
	}

	public void setGpReasonCode(String gpReasonCode) {
		this.gpReasonCode = gpReasonCode;
	}


	public BigDecimal getActGpValue() {
		return actGpValue;
	}

	public void setActGpValue(BigDecimal actGpValue) {
		this.actGpValue = actGpValue;
	}

	public BigDecimal getGpVsValue() {
		return gpVsValue;
	}

	public void setGpVsValue(BigDecimal gpVsValue) {
		this.gpVsValue = gpVsValue;
	}

	public BigDecimal getGpLoss() {
		return gpLoss;
	}

	public void setGpLoss(BigDecimal gpLoss) {
		this.gpLoss = gpLoss;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getGpReasonName() {
		return gpReasonName;
	}

	public void setGpReasonName(String gpReasonName) {
		this.gpReasonName = gpReasonName;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public Integer getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Integer confirmUser) {
		this.confirmUser = confirmUser;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public BigDecimal getPropGpAmt() {
		return propGpAmt;
	}

	public void setPropGpAmt(BigDecimal propGpAmt) {
		this.propGpAmt = propGpAmt;
	}

	public BigDecimal getPropSalesAmt() {
		return propSalesAmt;
	}

	public void setPropSalesAmt(BigDecimal propSalesAmt) {
		this.propSalesAmt = propSalesAmt;
	}
}
