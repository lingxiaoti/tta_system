package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseDepartmentEntity_HI_RO Entity Object
 * Tue Sep 04 17:28:45 CST 2018  Auto Generate
 */

public class BaseDepartmentEntity_HI_RO {
	//最顶层的parentDepartmentId设置成0，特殊处理
	public static final String QUERY_DEPT_TREE_LIST = "SELECT department.ou_id ouId\n" +
			"      ,(case when department.parent_department_id = department.department_id then  0 else department.parent_department_id end) parentDepartmentId\n" +
			"      ,department.department_id departmentId\n" +
			"      ,department.department_code departmentCode\n" +
			"      ,department.department_name departmentName\n" +
			"      ,department.department_level departmentLevel\n" +
			"      ,department.department_seq departmentSeq\n" +
			"\t\t\t,department.suffix departmentSuffix\n" +
			"\t\t\t,(nvl(department.department_name,'') || '.' || nvl(department.suffix,'')) departmentFullName\n" +
			"      ,department.channel channel\n" +
			"      ,channel.CHANNEL_NAME channelName\n" +
			"  FROM base_department department\n" +
			"      ,base_channel channel\n" +
			" WHERE 1 = 1\n" +
			"   AND department.date_from <= trunc(sysdate)\n" +
			"   AND nvl(department.date_to,sysdate) >= trunc(sysdate)\n" +
			"   AND department.enable_flag = 'Y'\n" +
			"   AND department.delete_flag = 0\n" +
			"   AND department.channel = channel.CHANNEL_CODE\n";

	public static final String QUERY_DEPT_PAGINATION_SQL = "SELECT department.ou_id ouId\n" +
			"      ,ouBlv.meaning ouName\n" +
			"      ,department.department_id departmentId\n" +
			"      ,department.department_code departmentCode\n" +
			"      ,department.department_name departmentName\n" +
			"      ,department.department_type departmentType\n" +
			"      ,department.inventory_enable inventoryEnable\n" +
			"	  ,(case when department.inventory_enable = 'Y' then '是' else '否' end) inventoryEnableName\n" +
			"      ,blv2.meaning departmentTypeName\n" +
			"\t\t\t,department.suffix departmentSuffix\n" +
			"\t\t\t,department.suffix suffix\n" +
			"\t\t\t,(department.department_name || '.' || nvl(department.suffix,'')) departmentFullName\n" +
			"      ,department.department_level departmentLevel\n" +
			"      ,department.department_seq departmentSeq\n" +
			"\t\t\t,department.parent_department_id parentDepartmentId\n" +
			"\t\t\t,parentDepartment.department_name parentDepartmentName\n" +
			"      ,department.channel channel\n" +
			"      ,channel.CHANNEL_NAME channelName\n" +
			"	   ,department.cost_center costCenter\n" +
			"\t\t\t,department.biz_line_type bizLineType\n" +
			"\t\t\t,blv.meaning bizLineTypeName\n" +
			"\t\t\t,department.enable_flag enableFlag\n" +
			"	  ,(case when department.enable_flag = 'Y' then '是' else '否' end) enableFlagName\n" +
			"\t\t\t,department.date_from dateFrom\n" +
			"\t\t\t,department.date_to dateTo\n" +
			"  FROM base_department department\n" +
			"\t\t\t LEFT JOIN base_lookup_values blv ON blv.lookup_code = department.biz_line_type AND blv.lookup_type = 'BUSINESS_TYPE' AND blv.system_code = 'BASE'\n" +
			"       LEFT JOIN base_lookup_values blv2 ON blv2.lookup_code = department.department_type AND blv2.lookup_type = 'DEPARTMENT_TYPE' AND blv2.system_code = 'BASE'\n" +
			"\t\t\t,base_department parentDepartment\n" +
			"      ,base_channel channel\n" +
			"      ,base_lookup_values ouBlv\n" +
			" WHERE 1 = 1\n" +
			"   AND department.delete_flag = 0\n" +
			"\t AND department.parent_department_id = parentDepartment.department_id\n" +
			"   AND department.channel = channel.CHANNEL_CODE\n" +
			"   AND department.ou_id = ouBlv.lookup_code\n" +
			"   AND ouBlv.lookup_type = 'BASE_OU'\n" +
			"   AND ouBlv.system_code = 'BASE'\n";

	public static final String QUERY_IS_EXIST_REPEAT_DEPT = "SELECT department.department_name\n" +
			"\tFROM base_department department\n" +
			" WHERE 1 = 1\n" +
			"\t AND department.delete_flag = 0\n" +
			"\t AND department.ou_id = :ouId\n" +
			"\t AND (nvl(department.department_name,'') || '.' || nvl(department.suffix,'')) = :departmentFullName \n";

	public static final String QUERY_BASE_DEPT_SYNC_SQL = "SELECT department.ou_id ouId\n" +
			"\t\t\t,department.department_id departmentId\n" +
			"\t\t\t,department.department_code departmentCode\n" +
			"\t\t\t,department.department_name departmentName\n" +
			"\t\t\t,department.suffix departmentSuffix\n" +
			"\t\t\t,department.department_type departmentType\n" +
			"\t\t\t,department.cost_center costCenter\n" +
			"\t\t\t,department.department_level departmentLevel\n" +
			"\t\t\t,department.department_seq departmentSeq\n" +
			"\t\t\t,department.parent_department_id parentDepartmentId\n" +
			"\t\t\t,department.date_from dateFrom\n" +
			"\t\t\t,department.date_to dateTo\n" +
			"\t\t\t,department.enable_flag enableFlag\n" +
			"\t\t\t,department.biz_line_type bizLineType\n" +
			"\t\t\t,department.channel channelCode\n" +
			"\t\t\t,department.created_by createdBy\n" +
			"\t\t\t,department.creation_date creationDate\n" +
			"\t\t\t,department.last_update_login lastUpdateLogin\n" +
			"\t\t\t,department.last_update_date lastUpdateDate\n" +
			"\t\t\t,department.last_updated_by lastUpdatedBy\n" +
			"\t\t\t,department.inventory_enable inventoryEnable\n" +
			"\t\t\t,(case when department.inventory_enable = 'Y' then '是' else '否' end) inventoryEnableName\n" +
			"  FROM base_department department\n" +
			" WHERE 1 = 1\n" +
			"\t AND department.delete_flag = 0\n";

	public static final String INSERT_DEPARTMENT_SQL = "INSERT INTO base_department\n" +
			"  (department_id\n" +
			"  ,department_name\n" +
			"  ,department_structure_id\n" +
			"  ,department_level_id\n" +
			"  ,org_id\n" +
			"  ,parent_department_id\n" +
			"  ,cost_center\n" +
			"  ,department_seq\n" +
			"  ,enabled_flag\n" +
			"  ,last_update_login\n" +
			"  ,last_updated_by\n" +
			"  ,last_update_date\n" +
			"  ,created_by\n" +
			"  ,creation_date\n" +
			"  ,attribute_category\n" +
			"  ,attribute1\n" +
			"  ,attribute2\n" +
			"  ,attribute3\n" +
			"  ,attribute4\n" +
			"  ,attribute5\n" +
			"  ,attribute6\n" +
			"  ,attribute7\n" +
			"  ,attribute8\n" +
			"  ,attribute9\n" +
			"  ,attribute10\n" +
			"  ,attribute11\n" +
			"  ,attribute12\n" +
			"  ,attribute13\n" +
			"  ,attribute14\n" +
			"  ,attribute15\n" +
			"  ,effective_beg_date\n" +
			"  ,effective_end_date\n" +
			"  ,inventory_enable\n" +
			"  ,department_level\n" +
			"  ,channel_type\n" +
			"  ,biz_line_type\n" +
			"  ,suffix\n" +
			"  ,department_type)\n" +
			"VALUES\n" +
			"  (:departmentId\n" +
			"  ,':departmentName'\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,:orgId\n" +
			"  ,:parentDepartmentId\n" +
			"  ,':costCenter'\n" +
			"  ,:departmentSeq\n" +
			"  ,':enabledFlag'\n" +
			"  ,:lastUpdateLogin\n" +
			"  ,:lastUpdatedBy\n" +
			"  ,sysdate\n" +
			"  ,:createdBy\n" +
			"  ,sysdate\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,NULL\n" +
			"  ,to_date(':dateFrom','yyyy-mm-dd')\n" +
			"  ,to_date(':dateTo','yyyy-mm-dd')\n" +
			"  ,NULL" +
			"  ,:departmentLevel\n" +
			"  ,':channelType'\n" +
			"  ,':bizLineType'\n" +
			"  ,':departmentSuffix'\n" +
			"  ,':departmentType')\n";

	public static final String UPDATE_DEPARTMENT_SQL = "UPDATE base_department\n" +
			"   SET department_name = :departmentName\n" +
			"      ,org_id = :orgId\n" +
			"      ,parent_department_id = :parentDepartmentId\n" +
			"      ,cost_center = :costCenter\n" +
			"      ,department_seq = :departmentSeq\n" +
			"      ,enabled_flag = :enabledFlag\n" +
			"	   ,last_update_login = :lastUpdateLogin\n" +
			"      ,last_updated_by = :lastUpdatedBy\n" +
			"      ,last_update_date = sysdate\n" +
			"      ,effective_beg_date = to_date(:dateFrom,'yyyy-mm-dd')\n" +
			"      ,effective_end_date = to_date(:dateTo,'yyyy-mm-dd')\n" +
			"      ,department_level = :departmentLevel\n" +
			"      ,channel_type = :channelType\n" +
			"      ,biz_line_type = :bizLineType\n" +
			"      ,suffix = :departmentSuffix\n" +
			"      ,department_type = :departmentType\n" +
			" WHERE department_id = :departmentId";

	public static final String REFRESH_DEPARTMENT_HIERARCHIES="{call AUPORTAL.BASE_DEPARTMENT_PKG.REFRESH_DEPARTMENT_HIERARCHIES(?,?)}";

	public static final String SELECT_DEPT_LIST = "select \n" +
			"\n" +
			"bd.department_id departmentId,\n" +
			"bd.department_code departmentCode,\n" +
			"bd.department_full_name departmentName,\n" +
			"bd.enable_flag enableFlag,\n" +
			"bd.parent_department_id parentDepartmentId,\n" +
			"bd.department_Type  departmentType\n" +
			"\n" +
			"from   base_department  bd\n" +
			"\n" +
			"where 1=1" ;

	//根据GroupId(部门id) 和部门类型找到部门
	/*public static final String SELECT_DEPT_LIST_BYGROUPID = "select t.department_id departmentId,\n" +
			"       t.department_code departmentCode,\n" +
			"       t.department_name departmentName,\n" +
			"       t.enable_flag enableFlag,\n" +
			"       t.parent_department_id parentDepartmentId,\n" +
			"       t.department_full_name departmentFullName,\n" +
			"       t.department_type departmentType\n" +
			"  from base_department t\n" +
			" where t.department_type = 40 and t.enable_flag = 'Y'\n" +
			"   and t.parent_department_id in\n" +
			"       (select bd.department_id\n" +
			"          from base_department bd\n" +
			"         where bd.department_type = 30\n" +
			"           and bd.parent_department_id = :parentDepartmentId and bd.enable_flag = 'Y') and  1=1 ";*/

	public static final String SELECT_DEPT_LIST_BYGROUPID = "select t.department_id departmentId,\n" +
			"       t.department_code departmentCode,\n" +
			"       t.department_name departmentName,\n" +
			"       t.enable_flag enableFlag,\n" +
			"       t.parent_department_id parentDepartmentId,\n" +
			"       t.department_full_name departmentFullName,\n" +
			"       t.department_type departmentType\n" +
			"  from base_department t\n" +
			" where t.department_type = 40 and t.enable_flag = 'Y'\n" +
			"   and t.parent_department_id = :parentDepartmentId\n" +
			"   and  1=1 ";


	//通过部门id递归查找顶级部门
	public static final String SELECT_DEPTID_BY_RECIESION =
			"select " +
			"bd.department_id departmentId," +
					"bd.department_code departmentCode,\n" +
			" bd.department_full_name departmentFullName," +
					" bd.department_name departmentName\n" +
			"  from (select t.department_id, t.department_full_name, t.department_name,\n" +
			"t.department_code\n" +
			"          from base_department t\n" +
			"         start with t.department_id = :departmentId\n" +
			"        connect by nocycle prior t.parent_department_id = t.department_id\n" +
			"               and t.parent_department_id != 0\n" +
			"         order by level desc\n" +
			"        \n" +
			"        ) bd\n" +
			" where rownum = 1 ";

	public static final String SELECT_DEPT_LIST_BYUSERID = "select bd.department_id        as departmentId,\n" +
			"       bd.department_name      as departmentName,\n" +
			"       bd.department_full_name as departmentFullName,\n" +
			"       bd.department_code      as departmentCode\n" +
			"  from base_person        bp,\n" +
			"       base_person_assign bpa,\n" +
			"       base_department    bd,\n" +
			"       base_position      bp1,\n" +
			"       base_users         bu\n" +
			" where bp.person_id = bpa.person_id\n" +
			"   and bpa.position_id = bp1.position_id\n" +
			"   and bpa.enable_flag = 'Y'\n" +
			"   and bp1.department_id = bd.department_id\n" +
			"   and bp.person_id = bu.person_id\n" +
			"   and bd.enable_flag = 'Y'\n" +
			"   and bd.delete_flag = '0'\n" +
			"   and bu.user_id = :userId and 1=1 ";

	private Integer departmentId; //部门ID
	private String departmentCode; //部门代码
	private String departmentName; //部门名称
	private String suffix; //部门名称后缀
	private String departmentType;
	private String departmentTypeName;
	private Integer departmentLevel; //部门层级
	private Integer ouId; //事业部
	private String ouName;
	private Integer parentDepartmentId; //父部门ID
	private String parentDepartmentName;//父部门名称
	private Integer departmentSeq; //部门序号
	private String departmentSuffix;//部门后缀
	private String departmentFullName;//部门全称
	private String costCenter;
	@JSONField(format="yyyy-MM-dd")
	private Date dateFrom; //生效日期
	@JSONField(format="yyyy-MM-dd")
	private Date dateTo; //失效日期
	private String enableFlag; //生效标识
	private String enableFlagName;
	private String bizLineType; //业务线类型
	private String bizLineTypeName;//业务线名称
	private String channel; //渠道
	private String channelCode;//渠道编号
	private String channelName;//渠道名称
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建时间
	private Integer createdBy; //创建人
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新时间
	private Integer lastUpdatedBy; //更新人
	private Integer lastUpdateLogin; //最后登录ID
	private String inventoryEnable;
	private String inventoryEnableName;

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	public String getDepartmentTypeName() {
		return departmentTypeName;
	}

	public void setDepartmentTypeName(String departmentTypeName) {
		this.departmentTypeName = departmentTypeName;
	}

	public Integer getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	public Integer getOuId() {
		return ouId;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public Integer getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(Integer parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public String getParentDepartmentName() {
		return parentDepartmentName;
	}

	public void setParentDepartmentName(String parentDepartmentName) {
		this.parentDepartmentName = parentDepartmentName;
	}

	public Integer getDepartmentSeq() {
		return departmentSeq;
	}

	public void setDepartmentSeq(Integer departmentSeq) {
		this.departmentSeq = departmentSeq;
	}

	public String getDepartmentSuffix() {
		return departmentSuffix;
	}

	public void setDepartmentSuffix(String departmentSuffix) {
		this.departmentSuffix = departmentSuffix;
	}

	public String getDepartmentFullName() {
		return departmentFullName;
	}

	public void setDepartmentFullName(String departmentFullName) {
		this.departmentFullName = departmentFullName;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getEnableFlagName() {
		return enableFlagName;
	}

	public void setEnableFlagName(String enableFlagName) {
		this.enableFlagName = enableFlagName;
	}

	public String getBizLineType() {
		return bizLineType;
	}

	public void setBizLineType(String bizLineType) {
		this.bizLineType = bizLineType;
	}

	public String getBizLineTypeName() {
		return bizLineTypeName;
	}

	public void setBizLineTypeName(String bizLineTypeName) {
		this.bizLineTypeName = bizLineTypeName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getInventoryEnable() {
		return inventoryEnable;
	}

	public void setInventoryEnable(String inventoryEnable) {
		this.inventoryEnable = inventoryEnable;
	}

	public String getInventoryEnableName() {
		return inventoryEnableName;
	}

	public void setInventoryEnableName(String inventoryEnableName) {
		this.inventoryEnableName = inventoryEnableName;
	}
}