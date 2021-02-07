package com.sie.saaf.base.sso.model.entities.readonly;

import java.util.Date;

/**
 * BaseFunctionCollectionEntity_HI_RO Entity Object
 * Tue Jan 30 16:51:31 CST 2018  Auto Generate
 */

public class BaseFunctionCollectionEntity_HI_RO {

	//查询所有配置的快捷菜单
	public static final String SQL_QUERY_FUNCTION="SELECT\n" +
			"\tbfc.function_collection_id AS functionCollectionId,\n" +
			"\tbfc.function_name AS functionName,\n" +
			"\tbfc.function_url AS functionUrl,\n" +
			"\tbfc.request_method AS requestMethod,\n" +
			"\tbfc.menu_id AS menuId,\n" +
			"\tbfc.resp_id AS respId,\n" +
			"\tbfc.version_num AS versionNum,\n" +
			"\tbfc.system_code AS systemCode,\n" +
			"\tblt.meaning AS systemName \n" +
			" FROM\n" +
			"\t base_function_collection bfc\n" +
			"\t LEFT JOIN base_lookup_values blt ON blt.lookup_type = 'SYSTEM_CODE' AND blt.system_code='PUBLIC' \n" +
			"\t AND blt.lookup_code = bfc.system_code WHERE 1=1 ";

	//查询用户配置的快捷菜单
	public static final String SQL_QUERY_USER_COLLECTION="SELECT\n" +
			"\tbfc.function_collection_id AS functionCollectionId,\n" +
			"\tbfc.function_name AS functionName,\n" +
			"\tbfc.function_url AS functionUrl,\n" +
			"\tbfc.request_method AS requestMethod,\n" +
			"\tbfc.menu_id AS menuId,\n" +
			"\tbfc.resp_id AS respId,\n" +
			"\tbfc.version_num AS versionNum,\n" +
			"\tbfc.system_code AS systemCode \n" +
			" FROM\n" +
			"\tbase_function_collection bfc\n" +
			"\t where 1=1 ";
	//查询内部收藏夹菜单
	public static final String SQL_QUERY_IN_COLLECTION="SELECT\n" +
			"\tmenu.menu_id AS menuId,\n" +
			"\tmenu.menu_code AS memuCode,\n" +
			"\tcollection.function_collection_id AS functionCollectionId,\n" +
			"\t menu.menu_name AS functionName,\n" +
			"\t menu.html_url AS functionUrl,\n" +
			"\t collection.user_id AS userId,\n" +
			"\t collection.resp_id AS respId,\n" +
			"\t collection.system_code AS systemCode" +
			" FROM \n" +
			"\tbase_function_collection collection\n" +
			"\tJOIN base_menu menu \n" +
			"\tON menu.menu_id = collection.menu_id \n" +
			"\tAND menu.enabled = 'Y' \n" +
			"\tAND ( nvl(menu.start_date_active, sysdate) = sysdate or menu.start_date_active <=SYSDATE)\n" +
			"\tAND ( nvl(menu.end_date_active, sysdate) = sysdate   or menu.end_date_active >SYSDATE)\n" +
			"\tWHERE 1 = 1 \n";

	private Integer userId;
    private Integer functionCollectionId;//主键
    private String collectionType; //收藏类型 (外部菜单/内部菜单)
    private String functionName; //菜单名称
    private String functionUrl; //菜单url
    private String requestMethod; //请求方式 get/post
    private Integer menuId; //菜单id
    private Integer respId; //职责id
    private String systemCode; //系统编码
	private String systemName; //系统名称
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
	private String memuCode;
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;
	private Integer functionCollectionUserId;
	private String bltSystemCode;

	public String getMemuCode() {
		return memuCode;
	}

	public void setMemuCode(String memuCode) {
		this.memuCode = memuCode;
	}

	public Integer getFunctionCollectionUserId() {
		return functionCollectionUserId;
	}

	public void setFunctionCollectionUserId(Integer functionCollectionUserId) {
		this.functionCollectionUserId = functionCollectionUserId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setFunctionCollectionId(Integer functionCollectionId) {
		this.functionCollectionId = functionCollectionId;
	}

	
	public Integer getFunctionCollectionId() {
		return functionCollectionId;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	
	public String getCollectionType() {
		return collectionType;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	
	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	
	public String getRequestMethod() {
		return requestMethod;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	
	public Integer getMenuId() {
		return menuId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	
	public Integer getRespId() {
		return respId;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	
	public String getSystemCode() {
		return systemCode;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
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

	public String getBltSystemCode() {
		return bltSystemCode;
	}

	public void setBltSystemCode(String bltSystemCode) {
		this.bltSystemCode = bltSystemCode;
	}
}
