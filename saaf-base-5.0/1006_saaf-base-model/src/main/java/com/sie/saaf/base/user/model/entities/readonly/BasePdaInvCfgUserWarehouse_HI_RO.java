package com.sie.saaf.base.user.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-03-15 10:07
 * @description
 */
public class BasePdaInvCfgUserWarehouse_HI_RO {

	public static final String QUERY_SQL = "SELECT\n" +
			" A.CFG_ID AS cfgId,\n" +
			" A.SUB_INV_CODE AS subInvCode,\n" +
			" A.ORGANIZATION_ID AS organizationId,\n" +
			" A.CHANNEL_CODE AS channelCode,\n" +
			" A.ROLE_ID AS roleId,\n" +
			" A.USER_ID AS userId,\n" +
			" A.DELETE_FLAG AS deleteFlag,\n" +
			" A.version_num AS versionNum,\n" +
			" F.WAREHOUSE_NAME AS subInvName,\n" +
			" B.USER_NAME AS userName,\n" +
			" C.ORG_NAME AS organizationName,\n" +
			" D.CHANNEL_NAME AS channelName,\n" +
			" A.CREATION_DATE AS creationDate,\n" +
			" E.ROLE_NAME AS roleName \n" +
			"from   base_pda_inv_cfg A       \n" +
			"\t\t\tLEFT JOIN   base_warehouse_mapping F   on  A.SUB_INV_CODE = F.WAREHOUSE_CODE\n" +
			"\t\t\tLEFT JOIN   base_users B               on  A.user_id = B.user_id  \n" +
			"\t\t\tLEFT JOIN   base_organization C        on  C.org_id = A.organization_id\n" +
			"\t\t\tLEFT JOIN   BASE_ROLE E                on  A.ROLE_ID = E.ROLE_ID \n" +
			"\t\t\tLEFT JOIN base_channel D \t\t\t\t\ton  D.CHANNEL_CODE = A.CHANNEL_CODE \n" +
			"\t\t\tWHERE   1=1 ";

	private Integer cfgId; //主键Id
	private String subInvCode;
	private Integer organizationId; //库存组织ID
	private String channelCode;
	private Integer roleId;
	private Integer userId; //用户ID
	private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
	private Integer versionNum; //版本号
	private Integer operatorUserId;

	private String userName;
	private String organizationName;
	private String subInvName;
	private String roleName;
	private String channelName;

	public Integer getCfgId() {
		return cfgId;
	}

	public void setCfgId(Integer cfgId) {
		this.cfgId = cfgId;
	}

	public String getSubInvCode() {
		return subInvCode;
	}

	public void setSubInvCode(String subInvCode) {
		this.subInvCode = subInvCode;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getSubInvName() {
		return subInvName;
	}

	public void setSubInvName(String subInvName) {
		this.subInvName = subInvName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
