package com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaUserDataTypeEntity_HI_RO Entity Object
 * Thu Aug 13 14:22:03 CST 2020  Auto Generate
 */

public class TtaUserDataTypeEntity_HI_RO {

	public static final String TTA_USER_IS_NULL = "SELECT listagg('用户：'||tud.user_name||'-权限类型：'||tud.data_type_name||'- GROUP:'||tud.group_code||'-DEPT:'||tud.dept_code||'-BRAND_CN'||tud.brand_cn,',') within group(order by tud.group_code) valueAll FROM tta_user_data_type tud\n" +
			"left join (SELECT * FROM base_lookup_values blv where blv.lookup_type = 'BASE_USER_DT' ) look on tud.data_type_name = look.meaning\n" +
			" where (tud.user_name is null \n" +
			" or tud.data_type_name is null  \n" +
			" or (look.lookup_code = '1' and tud.group_code is null )\n" +
			" or (look.lookup_code = '2' and ( tud.group_code is null or tud.dept_code is null ) )\n" +
			" or (look.lookup_code = '3' and ( tud.group_code is null or tud.dept_code is null or tud.brand_cn is null ) ))\n" +
			" \n" +
			" and rownum < 20";

	public static final String TTA_USER_IS_EXIT = "select \n" +
			"listagg(tud.user_name,',')within group(order by tud.user_name) valueALL\n" +
			" from tta_user_data_type tud\n" +
			"where  \n" +
			"not exists (select 1 from base_users bu where tud.user_name = bu.user_name)\n" +
			"and rownum < 20 ";

	public static final String TTA_DATA_TYPE_IS_EXIT = "select \n" +
			"listagg(tud.data_type_name,',')within group(order by tud.data_type_name) valueALL\n" +
			" from tta_user_data_type tud\n" +
			"where  \n" +
			"not exists (select 1 from base_lookup_values blv where blv.lookup_type ='BASE_USER_DT' and  blv.meaning = tud.data_type_name)\n" +
			"and rownum < 20 ";

	public static final String TTA_DATA_TYPE_IS_SAME = " SELECT listagg(tud.user_name,',')within group(order by tud.user_name) valueALL FROM tta_user_data_type tud\n" +
			"               join base_users bu on tud.user_name = bu.user_name\n" +
			"               join (SELECT * FROM base_lookup_values blv where blv.lookup_type = 'BASE_USER_DT' ) look on tud.data_type_name = look.meaning\n" +
			"               join tta_user_group_dept_brand tug \n" +
			"               on bu.user_id = tug.user_id \n" +
			"                  and nvl(tug.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tug.end_time,sysdate) >= sysdate\n" +
			"                  and nvl(tud.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tud.end_time,sysdate) >= sysdate\n" +
			"                  and look.lookup_code != tug.data_type and rownum < 20 ";


	public static final String TTA_DATA_IS_SAME = " SELECT listagg('用户：'||user_name||'- GROUP:'||\"GROUP\"||'-DEPT:'||dept||'-BRAND_CN'||brand_cn,',') within group(order by user_name) valueAll FROM (\n" +
			" SELECT tud.user_name,tug.\"GROUP\",null dept,null brand_cn,  count(1) nums FROM  tta_user_data_type tud\n" +
			"                join base_users bu on tud.user_name = bu.user_name \n" +
			"                join tta_user_group_dept_brand tug on bu.user_id = tug.user_id\n" +
			"                  and nvl(tug.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tug.end_time,sysdate) >= sysdate\n" +
			"                  and nvl(tud.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tud.end_time,sysdate) >= sysdate\n" +
			"                  and tug.data_type = '1'\n" +
			"                  and tug.\"GROUP\" = tud.group_code\n" +
			"                  group  by  tud.user_name,tug.\"GROUP\"\n" +
			"   union all                               \n" +
			"  SELECT tud.user_name,tug.\"GROUP\",tug.dept,null brand_cn,count(1) nums FROM  tta_user_data_type tud\n" +
			"                join base_users bu on tud.user_name = bu.user_name \n" +
			"                join tta_user_group_dept_brand tug on bu.user_id = tug.user_id\n" +
			"                  and nvl(tug.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tug.end_time,sysdate) >= sysdate\n" +
			"                  and nvl(tud.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tud.end_time,sysdate) >= sysdate\n" +
			"                  and tug.data_type = '2'\n" +
			"                  and tug.\"GROUP\" = tud.group_code and tug.dept = tud.dept_code \n" +
			"                 group  by tud.user_name,tug.\"GROUP\",tug.dept    \n" +
			"     union all       \n" +
			"                        \n" +
			"     SELECT tud.user_name,tug.\"GROUP\",tug.dept,tug.brand_cn,count(1) nums FROM  tta_user_data_type tud\n" +
			"                join base_users bu on tud.user_name = bu.user_name \n" +
			"                join tta_user_group_dept_brand tug on bu.user_id = tug.user_id\n" +
			"                  and nvl(tug.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tug.end_time,sysdate) >= sysdate\n" +
			"                  and nvl(tud.start_time,sysdate) < sysdate \n" +
			"                  and  nvl(tud.end_time,sysdate) >= sysdate\n" +
			"                  and tug.data_type = '3'\n" +
			"                  and tug.\"GROUP\" = tud.group_code and tug.dept = tud.dept_code and tug.brand_cn = tud.brand_cn\n" +
			"                 group  by tud.user_name,tug.\"GROUP\",tug.dept,tug.brand_cn    ) tta_user_all   where rownum <20       ";


	public static String getInsertReportBase(Integer userId){
		return "insert into tta_user_group_dept_brand  tug\n" +
				"(tug.\"GROUP\",\n" +
				" tug.group_desc,\n" +
				" tug.dept,\n" +
				" tug.dept_desc,\n" +
				" tug.brand_cn,\n" +
				" tug.start_time,\n" +
				" tug.end_time,\n" +
				" tug.user_group_dept_brand_id,\n" +
				" tug.user_id,\n" +
				" tug.data_type,\n" +
				" tug.version_num,\n" +
				" tug.creation_date,\n" +
				" tug.created_by,\n" +
				" tug.last_updated_by,\n" +
				" tug.last_update_date)\n" +
				" \n" +
				"SELECT \n" +
				" tudt.group_code,\n" +
				" tudt.group_name,\n" +
				" decode(blv.lookup_code,'1',null,tudt.dept_code),\n" +
				" decode(blv.lookup_code,'1',null,tudt.dept_name),\n" +
				" decode(blv.lookup_code,'3',tudt.brand_cn,null),\n" +
				" tudt.start_time,\n" +
				" tudt.end_time,\n" +
				" seq_tta_user_group_dept_brand.nextval,\n" +
				" bu.user_id,\n" +
				" blv.lookup_code,\n" +
				" 0,\n" +
				" sysdate,\n" +
				userId + ",\n"+
				userId +",\n"+
				" sysdate\n" +
				"\n" +
				" FROM \n" +
				"\n" +
				"\n" +
				"tta_user_data_type tudt\n" +
				"join base_lookup_values blv on blv.lookup_type = 'BASE_USER_DT' and tudt.data_type_name = blv.meaning\n" +
				"join base_users bu on tudt.user_name = bu.user_name ";
	}


	public static String updateBaseUsers(){
		return "update base_users bu\n" +
				"   set bu.data_type =\n" +
				"       (  \n" +
				"        \n" +
				"        SELECT blv.lookup_code\n" +
				"          FROM tta_user_data_type tud\n" +
				"          join base_lookup_values blv\n" +
				"            on blv.lookup_type = 'BASE_USER_DT'\n" +
				"           and tud.data_type_name = blv.meaning and tud.data_type_name = blv.meaning\n" +
				"        \n" +
				"         where not exists (SELECT 1\n" +
				"                  FROM tta_user_group_dept_brand tug\n" +
				"                  join  base_users bu on tug.user_id = bu.user_id\n" +
				"                 where bu.user_name = tud.user_name\n" +
				"                   and nvl(tug.start_time, sysdate) < sysdate\n" +
				"                   and nvl(tug.end_time, sysdate) >= sysdate) and\n" +
				"       bu.user_name = tud.user_name group by tud.user_name, blv.lookup_code)\n" +
				"\n" +
				" where exists (SELECT 1\n" +
				"          FROM tta_user_data_type tuds\n" +
				"         where tuds.user_name = bu.user_name\n" +
				"              \n" +
				"           and not exists\n" +
				"         (SELECT 1\n" +
				"                  FROM tta_user_group_dept_brand tug\n" +
				"                   join  base_users bu on tug.user_id = bu.user_id\n" +
				"                 where bu.user_name = tuds.user_name\n" +
				"                   and nvl(tug.start_time, sysdate) < sysdate\n" +
				"                   and nvl(tug.end_time, sysdate) >= sysdate))";
	}
	private String userName;
    private Integer ttaUserDataTypeId;
    private String dataTypeName;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptName;
    private String brandCn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer operatorUserId;
	private String valueAll;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setTtaUserDataTypeId(Integer ttaUserDataTypeId) {
		this.ttaUserDataTypeId = ttaUserDataTypeId;
	}

	
	public Integer getTtaUserDataTypeId() {
		return ttaUserDataTypeId;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	
	public String getDataTypeName() {
		return dataTypeName;
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

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public Date getEndTime() {
		return endTime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getValueAll() {
		return valueAll;
	}

	public void setValueAll(String valueAll) {
		this.valueAll = valueAll;
	}
}
