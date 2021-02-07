package com.sie.saaf.base.user.model.entities.readonly;

/**
 * Created by huangminglin on 2018/3/26.
 */
public class BaseUsersPDAAutoCreate_HI_RO {

    //直连ERP数据库，根据SQL抓取数据
    public static final String PDA_USER_QUERY_ORACLE = "SELECT " +
            "     HCS.ATTRIBUTE1 invCode,\n" +
            "     INV.ORGANIZATION_ID organizationId,\n" +
            "     INV.CHANNEL_CODE channelCode\n" +
            "FROM HZ_CUST_SITE_USES_ALL HCS,\n" +
            "     BRC.BRC_INV_V         INV\n" +
            "WHERE TRUNC(HCS.LAST_UPDATE_DATE) >= TRUNC(SYSDATE - :pHours /24)\n" +
            "      AND HCS.ATTRIBUTE1 IS NOT NULL\n" +
            "      AND HCS.ATTRIBUTE4 = 'Y'\n" +
            "      AND HCS.PRIMARY_FLAG = 'Y'\n" +
            "      AND HCS.ATTRIBUTE1 = INV.INV_CODE";

    //直连BRC数据库，执行以下判断是否已创建
    public static final String BASE_USERS_IS_HAS = "SELECT\n" +
            "  count(1) as count \n" +
            "FROM\n" +
            "  BASE_users E\n" +
            "WHERE\n" +
            "  E.USER_NAME = concat(:invCode,'01')";



    private String invCode;   //子库编码
    private Integer organizationId;   //组织Id
    private String channelCode;      //渠道编码
    private Integer count;


    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
