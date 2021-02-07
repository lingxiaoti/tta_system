package com.sie.saaf.base.user.model.entities.readonly;

public class BaseProductInfoEntity_HI_RO {
    public static final String QUERY_PRODUCT_INFO_SQL = "SELECT A.ITEM_CODE as itemCode,\n" +
            "       A.INNER_ITEM_CODE as innerItemCode,\n" +
            "       A.ORGANIZATION_ID as organizationId,\n" +
            "       A.ITEM_TYPE as itemType,\n" +
            "       A.ITEM_NAME as itemName,\n" +
            "       A.ITEM_DESC as itemDesc,\n" +
            "       A.BOX_UNIT as boxUnit,\n" +
            "       A.TRAY_UNIT as trayUnit,\n" +
            "       A.series\n" +
            " FROM base_product_info A WHERE 1=1 ";
    public static final String QUERY_PRODUCT_INFO_ITEMNAME_SQL = "SELECT\n" +
            "\tA.ITEM_NAME itemName,\n" +
            "\tA.ITEM_CODE itemCode\n" +
            "FROM\n" +
            "\tbase_product_info A" +
            "        WHERE A.ORGANIZATION_ID = 101 ";
    public static final String QUERY_PRODUCT_INFO_ITEMDESC_SQL = "SELECT  A.ITEM_CODE as itemCode,A.UNIT_ as unit,\n" +
    "   A.ITEM_DESC as itemDesc  FROM base_product_info A\n" +
    "        WHERE A.ORGANIZATION_ID = 101 ";

    //交易汇总相关查询（仓库发货确认）
    public static final String QUERY_BOX_UNIT="SELECT \n" +
            "\t a.ITEM_NAME AS itemName, \n" +
            "\t CAST(:unitTraQuantity / a.BOX_UNIT AS signed ) AS boxTraQuantity, \n" +
            "\t MOD (:unitTraQuantity, a.BOX_UNIT) AS fracTraQuantity \n" +
            "\t FROM base_product_info a \n" +
            "\t WHERE a.ORGANIZATION_ID = '101' \n" +
            "\t AND a.ITEM_CODE = :itemCode \n";


    //部分盘点查询品规信息
    public static final String QUERY_PRODUCT_ITEM_INFO="SELECT DISTINCT\n" +
            "\tproduct.ITEM_ID,\n" +
            "\tproduct.ITEM_CODE AS itemCode,\n" +
            "\tproduct.ITEM_NAME AS itemName,\n" +
            "\tpri.CHANNEL_TYPE AS channelCode,\n" +
            "\tpri.ORG_ID AS orgId \n" +
            "FROM\n" +
            "\tbase_product_info product\n" +
            "\tJOIN base_channel_privilege pri ON pri.ACCESS_TYPE = '6' \n" +
            "\tAND VALID_FLAG = 'Y' \n" +
            "\tAND pri.ITEM_CODE = product.ITEM_CODE \n" +
            "WHERE  product.item_type='FG' ";

    private Integer boxTraQuantity;
    private Integer fracTraQuantity;


    public Integer getBoxTraQuantity() {
        return boxTraQuantity;
    }

    public void setBoxTraQuantity(Integer boxTraQuantity) {
        this.boxTraQuantity = boxTraQuantity;
    }

    public Integer getFracTraQuantity() {
        return fracTraQuantity;
    }

    public void setFracTraQuantity(Integer fracTraQuantity) {
        this.fracTraQuantity = fracTraQuantity;
    }

    private String unit;
    private String itemCode;
    private String innerItemCode;
    private int organizationId;
    private String itemType;
    private String itemName;
    private String itemDesc;
    private String boxUnit;
    private String trayUnit;
    private String channelCode;
    private Integer orgId;
    private String series;//物料系列:47-办公用品,66-礼品

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
