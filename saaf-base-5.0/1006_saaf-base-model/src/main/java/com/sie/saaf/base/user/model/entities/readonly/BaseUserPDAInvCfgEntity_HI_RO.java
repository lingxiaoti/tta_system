package com.sie.saaf.base.user.model.entities.readonly;


public class BaseUserPDAInvCfgEntity_HI_RO {
    public static final String QUERY_USER_PDA_INV_CFG_SQL = "SELECT  A.USER_ID as userId  FROM base_users A,\n" +
            "              BASE_pda_inv_cfg B,\n" +
            "              base_warehouse_mapping C\n" +
            "         WHERE A.USER_ID = B.USER_ID\n" +
            "           AND A.USER_ID = :userId \n" +
            "           AND B.SUB_INV_CODE = C.WAREHOUSE_CODE\n" +
            "           AND C.WAREHOUSE_CODE = '10120'\n" +
            "           AND C.ORGANIZATION_ID = '262'";


    public static final String QUERY_WAREHOUSE_MAPPING_SQL = "SELECT '1189' accountNumber,\n" +
            "\t\t\t\t\t\t\t C.ACCOUNT_NAME accountName,\n" +
            "\t\t\t\t\t\t\t C.WAREHOUSE_CODE invCode,\n" +
            "\t\t\t\t\t\t\t C.WAREHOUSE_NAME invName,\n" +
            "\t\t\t\t\t\t\t C.CHANNEL_CODE channelCode,\n" +
            "\t\t\t\t\t\t\t/* TO_CHAR(C.ORGANIZATION_ID) */ '262' organizationId,\n" +
            "\t\t\t\t\t\t\t C.WAREHOUSE_TYPE invType\n" +
            "\t\t\t\t\tFROM base_warehouse_mapping C\n" +
            "\t\t\t\tWHERE C.WAREHOUSE_CODE= '10120' OR C.WAREHOUSE_CODE = '203270001';";

    public static final String QUERY_WAREHOUSE_MAPPING_SQL_2 = "SELECT\n" +
            "\tC.account_code accountNumber,\n" +
            "\t(\n" +
            "\t\tCASE\n" +
            "\t\tWHEN C.account_name = '苍南县新佳食品贸易有限公司（AP）' THEN\n" +
            "\t\t\t'苍南县新佳食品贸易有限公司'\n" +
            "\t\tELSE\n" +
            "\t\t\tC.ACCOUNT_NAME\n" +
            "\t\tEND\n" +
            "\t) accountName,\n" +
            "\tC.WAREHOUSE_CODE invCode,\n" +
            "\tC.WAREHOUSE_NAME invName,\n" +
            "\tC.CHANNEL_CODE channelCode,\n" +
            "\tC.ORGANIZATION_ID organizationId,\n" +
            "\tC.WAREHOUSE_TYPE invType\n" +
            "FROM\n" +
            "\tbase_warehouse_mapping C\n" +
            "WHERE\n" +
            "\tEXISTS (\n" +
            "\t\tSELECT\n" +
            "\t\t\t1\n" +
            "\t\tFROM\n" +
            "\t\t\tbase_warehouse_mapping A,\n" +
            "\t\t\tBASE_pda_inv_cfg B\n" +
            "\t\tWHERE\n" +
            "\t\t\tA.WAREHOUSE_CODE = B.SUB_INV_CODE\n" +
            "\t\tAND B.USER_ID = :userId\n" +
            "\t\tAND A.account_code = C.account_code\n" +
            "\t)\n" +
            "AND C.DELETE_FLAG <> 1\n" +
            "AND c.START_DATE_ACTIVE <= sysdate and (c.END_DATE_ACTIVE > DATE_SUB(sysdate,INTERVAL 1 day) or c.END_DATE_ACTIVE is NULL)";

    private int rowCount;
    private Integer userId;
    private String accountNumber;
    private String accountName;
    private String invCode;
    private String invName;
    private String channelCode;
    private String organizationId;
    private String invType;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
