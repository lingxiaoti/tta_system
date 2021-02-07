package com.sie.saaf.base.user.model.entities.readonly;

public class BaseOrganization_HI_RO {
    public static final String QUEYR_ORG_SQL = "SELECT\n" +
            "             BOV.org_id AS organizationId,\n" +
            "             BOV.org_name AS organizationName,\n" +
            "             BOV.organization_id AS orgId,\n" +
            "             BDC.meaning AS name\n" +
            "            FROM\n" +
            "             base_organization BOV,\n" +
            "             base_lookup_values BDC\n" +
            "             \n" +
            "            WHERE\n" +
            "             BDC.LOOKUP_TYPE = 'BASE_OU'\n" +
            "             AND BOV.organization_id = BDC.lookup_code";

    public static final String QUERY_OPRSUBINV_RECORD = "select " +
            "bwm.WAREHOUSE_CODE oprSubInv \n" +
            "from base_organization e\n" +
            "join base_warehouse_mapping bwm on e.org_id = bwm.ORGANIZATION_ID\n" +
            "where ( 1=1 \n";

    private String organizationId;
    private String organizationName;
    private int orgId;
    private String name;

    private String oprSubInv; //操作子库编码


    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOprSubInv() {
        return oprSubInv;
    }

    public void setOprSubInv(String oprSubInv) {
        this.oprSubInv = oprSubInv;
    }
}
