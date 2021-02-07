package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by huangminglin on 2018/3/28.
 */
public class BasePdaRoleCfg_HI_RO {

    public static final String Query_params_sql = "SELECT * from(\n" +
            "select \n" +
            "pdaRole.pda_role_cfg_id as pdaRoleCfgId,\n" +
            "pdaRole.role_id as roleId,\n" +
            "role.role_name as roleName,\n" +
            "pdaRole.organization_id as organizationId,\n" +
            "org.org_name as orgName,\n" +
            "pdaRole.channel_code as channelCode,\n" +
            "channel.CHANNEL_NAME as channelName,\n" +
            "pdaRole.menu_id as menuId,\n" +
            "pdaRole.inv_name as invName,\n" +
            "pdaRole.inv_code as invCode,\n" +
            "menu.menu_name as menuName,\n" +
            "pdaRole.DESCRIPTION as description,\n" +
            "pdaRole.enabled as enabled,\n" +
            "pdaRole.version_num as versionNum,\n" +
            "(case pdaRole.enabled\n" +
            "when 'Y' THEN '有效'\n" +
            "WHEN 'N' THEN '无效'\n" +
            "END) as enabledMeaning,\n" +
            "pdaRole.creation_date as creationDate,\n" +
            "pdaRole.last_update_date as lastUpdateDate,\n" +
            "pdaRole.created_by as createdBy\n" +
            "from base_pda_role_cfg pdaRole\n" +
            "LEFT JOIN base_menu menu on menu.menu_id = pdaRole.MENU_ID\n" +
            "left join base_organization org on pdaRole.ORGANIZATION_ID = org.org_id\n" +
            "LEFT JOIN base_role role on role.role_id = pdaRole.ROLE_ID\n" +
            "LEFT JOIN base_channel channel on pdaRole.channel_code = channel.channel_code \n" +
            ") QUERY\n" +
            "where 1=1";

    private Integer pdaRoleCfgId; //主键
    private   Integer roleId; //角色权限ID
    private   String roleName;  //角色名称
    private   Integer organizationId; //库存组织ID
    private   String  orgName;   //组织名称
    private   String  channelCode; //渠道编码
    private   String  channelName;  //渠道名称
    private   Integer menuId; //菜单ID
    private   String  menuName;  //菜单名称
    private   String invName;  //子库名称
    private   String  invCode; //子库编码
    private   String  description;   //备注
    private   String  enabled; //是否启用（Y：有效；N：无效）
    private   String  enabledMeaning;  //是否有效字典值
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private   Date    creationDate; //创建日期
    private   Integer createdBy; //创建人
    private   Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private   Date    lastUpdateDate; //更新日期
    private   Integer versionNum; //版本号
    private   Integer lastUpdateLogin; //last_update_login
    private   Integer operatorUserId;

    public Integer getPdaRoleCfgId() {
        return pdaRoleCfgId;
    }

    public void setPdaRoleCfgId(Integer pdaRoleCfgId) {
        this.pdaRoleCfgId = pdaRoleCfgId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
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

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getEnabledMeaning() {
        return enabledMeaning;
    }

    public void setEnabledMeaning(String enabledMeaning) {
        this.enabledMeaning = enabledMeaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
