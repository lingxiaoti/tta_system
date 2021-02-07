package com.sie.saaf.base.sso.model.entities.readonly;

/**
 * Created by Administrator on 2017/12/12.
 */
public class BaseSystemSsoRepEntity_HI_RO {
  /*查询职责绑定的系统*/
  //public static final String SQL_ROLE_SYSTEM = "SELECT   blv.description AS description,  bss.order_no AS orderNo,  bss.system_name AS systemName,   bss.system_code AS systemCode,   bss.image_url AS imageUrl   ,brs.responsibility_id AS responsibilityId  FROM base_responsibility brs JOIN base_system_sso bss ON brs.system_code = bss.system_code AND bss.enable_flag = 'Y' LEFT JOIN base_lookup_values blv ON blv.lookup_type='SYSTEM_CODE' AND blv.system_code='PUBLIC' AND bss.system_code=blv.lookup_code where 1=1 ";

  public static final String SQL_ROLE_SYSTEM ="SELECT\n" +
    "\tDISTINCT\n" +
    "\tblv.description AS description,\n" +
    "\tbss.order_no AS orderNo,\n" +
    "\tbss.system_name AS systemName,\n" +
    "\tbss.system_code AS systemCode,\n" +
    "\tbss.image_url AS imageUrl\n" +
    "FROM\n" +
    "\tbase_responsibility brs\n" +
    "\tJOIN base_system_sso bss ON brs.system_code = bss.system_code \n" +
    "\tAND bss.enable_flag = 'Y'\n" +
    "\tLEFT JOIN base_lookup_values blv ON blv.lookup_type = 'SYSTEM_CODE' \n" +
    "\tAND blv.system_code = 'PUBLIC' \n" +
    "\tAND bss.system_code = blv.lookup_code \n" +
    "WHERE\n" +
    "\t1 =1 ";
  /*查询用户绑定的系统*/
  public static final String SQL_USER_SYSTEM = "SELECT   bur.user_id AS userId,   role.role_id AS roleId,   role.role_name AS roleName,   bss.system_code AS systemCode,   bss.system_name AS systemName,   bss.client_id AS clientId,   bss.sys_sso_id AS sysSsoId,   bss.request_url AS requestUrl,   bss.request_method AS requestMethod,   bss.params AS params,   bss.image_url AS imageUrl,   bss.description AS description,   bss.order_no AS orderNo  FROM base_user_responsibility bur JOIN base_responsibility_role brr ON bur.responsibility_id = brr.responsibility_id  AND trunc(nvl(bur.start_date_active,sysdate)) <= trunc(sysdate)   AND trunc(nvl(bur.end_date_active,sysdate)) >= trunc(sysdate)   JOIN base_responsibility br  ON br.responsibility_id = bur.responsibility_id                                   AND date(nvl(br.start_date_active,sysdate)) <= trunc(sysdate)                                   AND date(nvl(br.end_date_active,sysdate)) >= trunc(sysdate)   JOIN base_role role ON brr.role_id = role.role_id                          AND date(nvl(role.start_date_active,sysdate)) <=trunc(sysdate)                          AND date(nvl(role.end_date_active,sysdate)) >= trunc(sysdate)   JOIN base_role_system brs ON brs.role_Id = brr.role_id   JOIN base_system_sso bss ON bss.system_code = brs.system_code AND bss.enable_flag = '1' where 1=1 ";
  private Integer userId;
  private String systemName;
  private String systemCode;
  private String imageUrl;
  private String clientId;
  private String params;
  private String requestMethod;
  private String requestUrl;
  private String description;
  private Integer orderNo;
  private String roleName;
  private Integer roleId;
  private Integer sysSsoId;
  private Integer responsibilityId;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getSystemName() {
    return systemName;
  }

  public void setSystemName(String systemName) {
    this.systemName = systemName;
  }

  public String getSystemCode() {
    return systemCode;
  }

  public void setSystemCode(String systemCode) {
    this.systemCode = systemCode;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getSysSsoId() {
    return sysSsoId;
  }

  public void setSysSsoId(Integer sysSsoId) {
    this.sysSsoId = sysSsoId;
  }

  public Integer getResponsibilityId() {
    return responsibilityId;
  }

  public void setResponsibilityId(Integer responsibilityId) {
    this.responsibilityId = responsibilityId;
  }
}
