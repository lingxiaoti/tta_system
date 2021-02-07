package com.sie.saaf.base.user.model.entities.readonly;

import java.util.List;

/**
 * @author xiangyipo
 * @date 2018/2/8
 */


public class BaseUserPDAProOrgChannel_HI_RO {

    public static final String QUERY_CHANNEL_IS_UPDATE_SQL ="SELECT \n" +
            "                  f.orgId as orgId \n" +
            "              FROM base_users A,\n" +
            "                   base_PDA_INV_CFG B,\n" +
            "                   base_product_info C,\n" +
            "                   base_organization D,\n" +
            "                   base_warehouse_mapping E,\n" +
            "                   (select ORG_ID as orgId,TRANSACTION_TYPE_ID as itemId,ACCESS_TYPE as accessType,VALID_FLAG AS enabled from base_channel_privilege where ACCESS_TYPE='6' and VALID_FLAG='Y') F\n" +
            "             WHERE A.USER_ID = B.USER_ID  \n" +
            "               AND E.ORGANIZATION_ID = D.org_id\n" +
            "               AND B.SUB_INV_CODE = E.WAREHOUSE_CODE\n" +
            "               AND C.ITEM_ID = F.itemId\n" +
            "               AND A.USER_ID = :userId\n" +
            "               AND C.LAST_UPDATE_DATE > :lastDate";


    //用于电商子库，只要用户配了子库10120，则返回指定的品优先电商子库
    public static final String QUERY_USER_IF_10120_WAREHOUSE_SQL ="SELECT COUNT(1) as count FROM \n" +
            "                     base_users A,\n" +
            "           base_PDA_INV_CFG B,\n" +
            "           base_warehouse_mapping C \n" +
            "        WHERE A.USER_ID = B.USER_ID\n" +
            "          AND A.USER_ID = :userId\n" +
            "          AND B.SUB_INV_CODE = C.WAREHOUSE_CODE\n" +
            "          AND C.WAREHOUSE_CODE = '10120'\n" +
            "          AND C.ORGANIZATION_ID  ='262'";

    public static final String QUERY_USER_10120_WAREHOUSE_SQL = "SELECT DISTINCT\n" +
            "    F.lookup_code AS codeHead,\n" +
            "    A.ITEM_CODE,\n" +
            "    A.INNER_ITEM_CODE,\n" +
            "    '262' ORGANIZATION_ID,\n" +
            "    '261' ORG_ID,\n" +
            "    A.ITEM_TYPE,\n" +
            "    A.ITEM_DESC,\n" +
            "    A.ITEM_NAME,\n" +
            "    A.BOX_UNIT,\n" +
            "    A.TRAY_UNIT\n" +
            "FROM\n" +
            "    base_product_info A,\n" +
            "    (\n" +
            "        SELECT\n" +
            "            B.ITEM_CODE,\n" +
            "            B.ORG_ID\n" +
            "        FROM\n" +
            "            (\n" +
            "                SELECT\n" +
            "                    CP.ORG_ID,\n" +
            "                    CP.ACCESS_TYPE,\n" +
            "                    CP.TRANSACTION_TYPE_ID as ITEM_ID,\n" +
            "                    CP.ITEM_CODE\n" +
            "                FROM\n" +
            "                    base_channel_privilege CP\n" +
            "                WHERE\n" +
            "                    CP.ACCESS_TYPE = 6  and CP.valid_flag = 'Y' \n" +
            "            ) B,\n" +
            "            base_product_info C\n" +
            "        WHERE\n" +
            "            1 = 1\n" +
            "        AND B.ITEM_ID = C.ITEM_ID\n" +
            "        AND C.ITEM_TYPE = 'FG'\n" +
            "    ) E,\n" +
            "    (\n" +
            "        SELECT\n" +
            "            A.meaning,\n" +
            "            A.lookup_code\n" +
            "        FROM\n" +
            "            base_lookup_values A\n" +
            "        WHERE\n" +
            "            A.LOOKUP_TYPE = 'BRC_CODE_OU'\n" +
            "    ) F\n" +
            "WHERE\n" +
            "    A.ITEM_CODE = E.ITEM_CODE\n" +
            "AND E.ORG_ID = F.meaning\n" +
            "UNION\n" +
            "    SELECT\n" +
            "        DICT.lookup_code AS codeHead,\n" +
            "        C.ITEM_CODE,\n" +
            "        C.INNER_ITEM_CODE,\n" +
            "        C.ORGANIZATION_ID,\n" +
            "        F.ORG_ID,\n" +
            "        C.ITEM_TYPE,\n" +
            "        C.ITEM_DESC,\n" +
            "        C.ITEM_DESC,\n" +
            "        C.BOX_UNIT,\n" +
            "        C.TRAY_UNIT\n" +
            "    FROM\n" +
            "        base_users A,\n" +
            "        base_PDA_INV_CFG B,\n" +
            "        base_product_info C,\n" +
            "        base_organization D,\n" +
            "        base_warehouse_mapping E,\n" +
            "        (\n" +
            "            SELECT\n" +
            "                CP.ORG_ID,\n" +
            "                CP.ACCESS_TYPE,\n" +
            "                CP.TRANSACTION_TYPE_ID as itemId,\n" +
            "                CP.ITEM_CODE\n" +
            "            FROM\n" +
            "                    base_channel_privilege CP\n" +
            "            WHERE\n" +
            "                CP.ACCESS_TYPE = 6  and CP.valid_flag = 'Y' \n" +
            "        ) F,\n" +
            "        (\n" +
            "            SELECT\n" +
            "                A.meaning,\n" +
            "                A.lookup_code\n" +
            "            FROM\n" +
            "                base_lookup_values A\n" +
            "            WHERE\n" +
            "                A.LOOKUP_TYPE = 'BRC_CODE_OU'\n" +
            "        ) DICT\n" +
            "    WHERE\n" +
            "        A.USER_ID = B.USER_ID\n" +
            "    AND A.USER_ID = :userId\n" +
            "    AND E.WAREHOUSE_CODE <> 10120\n" +
            "AND B.SUB_INV_CODE = E.WAREHOUSE_CODE\n" +
            "AND E.ORGANIZATION_ID = D.org_id\n" +
            "AND D.organization_id = F.org_id\n" +
            "AND C.ORGANIZATION_ID = B.ORGANIZATION_ID\n" +
            "AND C.ITEM_ID = F.itemId\n" +
            "AND d.organization_id = f.org_id\n"+
            "AND F.ORG_ID = DICT.meaning and C.BOX_UNIT>0 and C.TRAY_UNIT>0 and C.ITEM_TYPE='FG' " ;




    //判断是否为库存组织为262的成品仓,针对能力多,1897组织,美纳多事业部,KA组织,美优高组织,电商组织 由公司总部统一发货
    public static final String QUERY_USER_IF_1897_WAREHOUSE_SQL = "SELECT COUNT(1) as count  FROM \n" +
            "           base_users  A,\n" +
            "           base_PDA_INV_CFG B,\n" +
            "           base_warehouse_mapping C \n" +
            "        WHERE A.USER_ID = B.USER_ID\n" +
            "          AND A.USER_ID = :userId\n" +
            "          AND B.SUB_INV_CODE = C.WAREHOUSE_CODE\n" +
            "          AND C.WAREHOUSE_CODE <> '10120'\n" +
            "          AND C.ORGANIZATION_ID  ='262'";

    public static final String QUERY_USER_1897_WAREHOUSE_SQL = "SELECT DISTINCT\n" +
            "    A.ITEM_CODE,\n" +
            "    F.lookup_code AS codeHead,\n" +
            "    A.INNER_ITEM_CODE,\n" +
            "    '262' ORGANIZATION_ID,\n" +
            "    '261' ORG_ID,\n" +
            "    A.ITEM_TYPE,\n" +
            "    A.ITEM_NAME AS ITEM_DESC,\n" +
            "    A.ITEM_NAME,\n" +
            "    A.BOX_UNIT,\n" +
            "    A.TRAY_UNIT \n" +
            "FROM\n" +
            "    base_product_info A,\n" +
            "    (\n" +
            "    SELECT DISTINCT\n" +
            "        B.ITEM_CODE,\n" +
            "        B.ORG_ID,\n" +
            "      C.ITEM_NAME    \n" +
            "    FROM\n" +
            "        (\n" +
            "        SELECT\n" +
            "            CP.ORG_ID,\n" +
            "            CP.ACCESS_TYPE,\n" +
            "            CP.TRANSACTION_TYPE_ID AS ITEM_ID,\n" +
            "            CP.ITEM_CODE \n" +
            "        FROM\n" +
            "            base_channel_privilege CP \n" +
            "        WHERE\n" +
            "            CP.ACCESS_TYPE = 6 \n" +
            "            AND CP.valid_flag = 'Y' \n" +
            "        ) B,\n" +
            "        base_product_info C \n" +
            "    WHERE\n" +
            "        1 = 1 \n" +
            "        AND B.ITEM_ID = C.ITEM_ID \n" +
            "        AND C.ITEM_TYPE = 'FG' \n" +
            "    ) E,\n" +
            "    (\n" +
            "    SELECT\n" +
            "        A.lookup_code,\n" +
            "        A.meaning \n" +
            "    FROM\n" +
            "        base_lookup_values A \n" +
            "    WHERE\n" +
            "        A.LOOKUP_TYPE = 'BRC_CODE_OU' \n" +
            "    ) F \n" +
            "WHERE\n" +
            "    A.ITEM_CODE = E.ITEM_CODE \n" +
            "    AND E.ORG_ID = F.meaning \n" +
            "    AND E.ORG_ID IN ('81','161','181','182','542','221','241','1124' ) \n" +
            "    AND A.ORGANIZATION_ID=101 \n" +
            "    AND A.BOX_UNIT > 0 \n" +
            "    AND A.TRAY_UNIT > 0 \n" +
            "    AND A.ITEM_TYPE = 'FG' \n" +
            "UNION\n" +
            "SELECT DISTINCT\n" +
            "    C.ITEM_CODE,\n" +
            "    DICT.lookup_code AS codeHead,\n" +
            "    C.INNER_ITEM_CODE,\n" +
            "    C.ORGANIZATION_ID,\n" +
            "    F.ORG_ID,\n" +
            "    C.ITEM_TYPE,\n" +
            "    C.ITEM_DESC,\n" +
            "    C.ITEM_NAME,\n" +
            "    C.BOX_UNIT,\n" +
            "    C.TRAY_UNIT \n" +
            "FROM\n" +
            "    base_users A,\n" +
            "    base_PDA_INV_CFG B,\n" +
            "    base_product_info C,\n" +
            "    base_organization D,\n" +
            "    base_warehouse_mapping E,\n" +
            "    (\n" +
            "    SELECT\n" +
            "        CP.ORG_ID,\n" +
            "        CP.ACCESS_TYPE,\n" +
            "        CP.TRANSACTION_TYPE_ID AS itemId,\n" +
            "        CP.ITEM_CODE \n" +
            "    FROM\n" +
            "        base_channel_privilege CP \n" +
            "    WHERE\n" +
            "        CP.ACCESS_TYPE = 6 \n" +
            "        AND CP.valid_flag = 'Y' \n" +
            "    ) F,\n" +
            "    (\n" +
            "    SELECT\n" +
            "        A.lookup_code,\n" +
            "        A.meaning \n" +
            "    FROM\n" +
            "        base_lookup_values A \n" +
            "    WHERE\n" +
            "        A.LOOKUP_TYPE = 'BRC_CODE_OU' \n" +
            "    ) DICT \n" +
            "WHERE\n" +
            "    A.USER_ID = B.USER_ID \n" +
            "    AND A.USER_ID = :userId \n" +
            "    AND B.SUB_INV_CODE = E.WAREHOUSE_CODE \n" +
            "    AND E.ORGANIZATION_ID = D.ORG_ID \n" +
            "    AND D.organization_id = F.org_id \n" +
            "    AND C.ORGANIZATION_ID = B.ORGANIZATION_ID \n" +
            "    AND C.ITEM_ID = F.itemId \n" +
            "    AND F.ORG_ID = DICT.meaning \n" +
            "    AND C.BOX_UNIT > 0 \n" +
            "    AND C.TRAY_UNIT > 0 \n" +
            "    AND C.ITEM_TYPE = 'FG' ";



    // 用于海普诺凯,纽莱可等其他事业部 PDA产品同步
    public static final String  QUERY_USER_ELSE_WAREHOUSE_SQL = " SELECT DISTINCT\n" +
            "    DICT.lookup_code AS codeHead,\n" +
            "    C.ITEM_CODE,\n" +
            "    C.INNER_ITEM_CODE,\n" +
            "    C.ORGANIZATION_ID,\n" +
            "    F.ORG_ID,\n" +
            "    C.ITEM_TYPE,\n" +
            "    C.ITEM_DESC AS ITEM_NAME,\n" +
            "    C.ITEM_DESC,\n" +
            "    C.BOX_UNIT,\n" +
            "    C.TRAY_UNIT\n" +
            "FROM\n" +
            "    base_users A,\n" +
            "    base_PDA_INV_CFG B,\n" +
            "    base_product_info C,\n" +
            "    base_organization D,\n" +
            "    base_warehouse_mapping E,\n" +
            "    (\n" +
            "        SELECT\n" +
            "            ORG_ID AS org_Id,\n" +
            "            TRANSACTION_TYPE_ID AS itemId,\n" +
            "            ACCESS_TYPE AS accessType,\n" +
            "            VALID_FLAG AS enabled\n" +
            "        FROM\n" +
            "            base_channel_privilege\n" +
            "        WHERE\n" +
            "            ACCESS_TYPE = '6'\n" +
            "        AND VALID_FLAG = 'Y'\n" +
            "    ) F ,\n" +
            "(\n" +
            "        SELECT\n" +
            "            A.lookup_code,\n" +
            "            A.meaning\n" +
            "        FROM\n" +
            "            base_lookup_values A\n" +
            "        WHERE\n" +
            "            A.LOOKUP_TYPE = 'BRC_CODE_OU'\n" +
            "    ) DICT\n" +
            "WHERE\n" +
            "    A.USER_ID = B.USER_ID\n" +
            "AND A.USER_ID = :userId\n" +
            "AND B.SUB_INV_CODE = E.WAREHOUSE_CODE\n" +
            "AND D.organization_id = F.org_id\n" +
            "AND C.ORGANIZATION_ID = B.ORGANIZATION_ID\n" +
            "AND C.ITEM_ID = F.itemId\n" +
            "AND d.organization_id = f.org_id\n"+
            "AND F.ORG_ID = DICT.meaning and C.BOX_UNIT>0 and C.TRAY_UNIT>0 and C.ITEM_TYPE='FG' " ;

    private String codeHead;
    private String orgId;
    private String organizationId;
    private String itemName; //产品名称
    private String itemDesc; //产品描述
    private String itemCode;//产品编号
    private String innerItemCode; //产品内部编码
    private String itemType; //产品类型
    private String boxUnit; //单位数量/箱
    private String trayUnit; //单位数量/托盘
    private Integer userId;
    private Integer count;
    private List items;
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }


    public List getItems() {
        return items; }

    public String getCodeHead() {
        return codeHead;
    }

    public void setCodeHead(String codeHead) {
        this.codeHead = codeHead;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInnerItemCode() {
        return innerItemCode;
    }

    public void setInnerItemCode(String innerItemCode) {
        this.innerItemCode = innerItemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBoxUnit() {
        return boxUnit;
    }

    public void setBoxUnit(String boxUnit) {
        this.boxUnit = boxUnit;
    }

    public String getTrayUnit() {
        return trayUnit;
    }

    public void setTrayUnit(String trayUnit) {
        this.trayUnit = trayUnit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
