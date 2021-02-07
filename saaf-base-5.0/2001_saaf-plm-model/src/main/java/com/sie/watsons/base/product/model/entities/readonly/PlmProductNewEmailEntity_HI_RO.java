package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.model.dao.CommonDAO_HI_DY;
import com.sie.watsons.base.product.model.entities.PlmProductBpmUserEntity_HI;
import com.sie.watsons.base.product.model.entities.columnNames;
import com.yhg.hibernate.core.dao.ViewObject;

import java.util.Date;

public class PlmProductNewEmailEntity_HI_RO extends CommonDAO_HI_DY {

//    public static final String QUERY = "    SELECT pph.rms_code as rmsCode\n" +
//            "          ,pph.product_name as  productName\n" +
//            "          ,pph.department as  department\n" +
//            "          ,pph.department_descript as departmentDescript\n" +
//            "          ,pph.brandname_en as brandnameEn\n" +
//            "          ,pph.brandname_cn as brandnameCn\n" +
//            "          ,ps.supplier_code as supplierCode\n" +
//            "          ,ps.sx_way  as sxWay \n" +
//            "          ,ps.supplier_name as  supplierName\n" +
//            "          ,ps.area_id as areaId \n" +
//            "          ,nvl(ps.area,ps.area_id) as area \n" +
////            "          ,nvl(ps.sx_store,ps.sx_store_id) as sxStore\n" +
//            "          ,nvl(ps.sx_warehouse,ps.sx_warehouse_id) as sxWarehouse\n" +
//            "          ,pph.creation_date as creationDate\n" +
//            "          ,pph.created_by as createdBy\n" +
//            "          ,pph.created_Emp as createdEmp\n" +
//            "          ,pph.rms_synchr_date as rmsSynchrDate\n" +
//            "      FROM plm_product_head pph\n" +
//            "     INNER JOIN plm_product_supplier_info ps\n" +
//            "        ON pph.product_head_id = ps.product_head_id\n" +
//            "     WHERE 1 = 1\n" +
//            "     and pph.rms_code is not null \n" ;

    public static final String QUERY = " SELECT pph.rms_code            AS rmscode\n" +
            "      ,pph.product_name        AS productname\n" +
            "      ,pph.department          AS department\n" +
            "      ,pph.department_descript AS departmentdescript\n" +
            "      ,pph.brandname_en        AS brandnameen\n" +
            "      ,pph.brandname_cn        AS brandnamecn\n" +
            "      ,ps.supplier_code        AS suppliercode\n" +
            "      ,ps.sx_way               AS sxway\n" +
            "      ,ps.supplier_name        AS suppliername\n" +
            "      ,pph.creation_date   AS creationdate\n" +
            "      ,pph.created_by      AS createdby\n" +
            "      ,pph.created_emp     AS createdemp\n" +
            "      ,pph.rms_synchr_date AS rmssynchrdate\n" +
            "      ,nvl(wh.sxWarehouse, nvl(ps.sx_warehouse,ps.sx_warehouse_id)) as sxWarehouse\n" +
            "      ,nvl(area.area,nvl(ps.area,ps.area_id)) as area \n" +
            "  FROM plm_product_head pph\n" +
            " INNER JOIN plm_product_supplier_info ps\n" +
            "    ON pph.product_head_id = ps.product_head_id\n" +
            " left join (\n" +
            "  select tb1.product_head_id,tb1.supplier_code\n" +
            " ,listagg( decode(tb1.area_id,'1','南区','2','东区','3','北区','4','西区',tb1.area_en),',') within group (order by tb1.area_id asc) as area from (\n" +
            "SELECT pph.product_head_id,vs.area_en, vs.area_id,ps.supplier_code\n" +
            "  FROM plm_product_head pph\n" +
            " INNER JOIN plm_product_supplier_info ps\n" +
            "    ON pph.product_head_id = ps.product_head_id\n" +
            "  LEFT JOIN plm_product_supplierplaceinfo psi1\n" +
            "    ON pph.rms_code = psi1.rms_id\n" +
            "   AND ps.supplier_code = psi1.supplier_code\n" +
            "  LEFT JOIN vmi_shop vs\n" +
            "    ON psi1.location = vs.vs_code\n" +
            "  --  and vs.area_id in ('1','2','3','4')\n" +
            " WHERE 1 = 1\n" +
            "   AND pph.rms_code IS NOT NULL\n" +
            "   AND pph.rms_synchr_date > SYSDATE - 60\n" +
            "   group by pph.product_head_id,vs.area_en,vs.area_id,ps.supplier_code\n" +
            "   )tb1 where 1=1 group by tb1.product_head_id,tb1.supplier_code\n" +
            " ) area \n" +
            " on area.product_head_id= ps.product_head_id\n" +
            " and area.supplier_code = ps.supplier_code\n" +
            " \n" +
            " left join (  select tb2.product_head_id,tb2.supplier_code\n" +
            " ,listagg(tb2.vh_pre_code,',') within group (order by tb2.vh_pre_code asc) as sxWarehouse from (\n" +
            "   SELECT pph.product_head_id,vs.vh_pre_code,ps.supplier_code\n" +
            "  FROM plm_product_head pph\n" +
            " INNER JOIN plm_product_supplier_info ps\n" +
            "    ON pph.product_head_id = ps.product_head_id\n" +
            "  LEFT JOIN plm_product_supplierplaceinfo psi1\n" +
            "    ON pph.rms_code = psi1.rms_id\n" +
            "   AND ps.supplier_code = psi1.supplier_code\n" +
            "  LEFT JOIN vmi_shop vs\n" +
            "    ON psi1.location = vs.vs_code\n" +
            " WHERE 1 = 1\n" +
            "   AND pph.rms_code IS NOT NULL\n" +
            "   AND pph.rms_synchr_date > SYSDATE - 60\n" +
            "   group by pph.product_head_id,vs.vh_pre_code,ps.supplier_code\n" +
            "   ) tb2 where 1=1 group by tb2.product_head_id,tb2.supplier_code\n" +
            "    ) wh \n" +
            " on wh.product_head_id= ps.product_head_id\n" +
            " and wh.supplier_code = ps.supplier_code\n" +
            " WHERE 1 = 1\n" +
            "   AND pph.rms_code IS NOT NULL\n" ;

    public static final String QUERY_GROUP_BY = " group by pph.rms_code,pph.product_name ,pph.department ,pph.department_descript ,pph.brandname_en    ,pph.brandname_cn  ,ps.sx_way\n" +
            "     ,ps.supplier_name ,ps.supplier_code,pph.creation_date ,pph.created_by ,pph.created_Emp,ps.sx_store,ps.sx_store_id\n" +
            "    ,ps.sx_warehouse,ps.sx_warehouse_id ";

    public static final String QUERY_PLACE = "SELECT vs.vh_pre_code as vhPreCode\n" +
            "      ,decode(vs.area_en\n" +
            "             ,'South Area'\n" +
            "             ,'1'\n" +
            "             ,'East Area'\n" +
            "             ,'2'\n" +
            "             ,'North Area'\n" +
            "             ,'3'\n" +
            "             ,'West Area'\n" +
            "             ,'4')  as areaEn\n" +
            "  FROM plm_product_supplierplaceinfo pps\n" +
            "  LEFT JOIN vmi_shop vs\n" +
            "    ON pps.location = vs.vs_code\n" +
            " WHERE loc_type = 'S'\n" +
            "   AND vs.vh_pre_code IS NOT NULL\n" +
            "   AND pps.status = '0'\n" +
            "   AND rms_id = '100000330'\n" +
            "   AND vs.area_en IN ('East Area'\n" +
            "                     ,'North Area'\n" +
            "                     ,'South Area'\n" +
            "                     ,'West Area')\n" +
            " GROUP BY vs.vh_pre_code ,vs.area_en";

    public static void main(String[] args) {
        System.out.println("  QUERY   :: "+QUERY);
    }


    @columnNames(name = "RMS编码  -PLM_PRODUCT_HEAD")
    private String rmsCode;
    @columnNames(name = "货品名称  -PLM_PRODUCT_HEAD")
    private String productName;
    @columnNames(name = "部门  -PLM_PRODUCT_HEAD")
    private String department;
    @columnNames(name = "部门描述  -PLM_PRODUCT_HEAD")
    private String departmentDescript;
    @columnNames(name = "品牌中文  -PLM_PRODUCT_HEAD")
    private String brandnameCn;
    @columnNames(name = "品牌英文  -PLM_PRODUCT_HEAD")
    private String brandnameEn;
    @columnNames(name = "供应商编码 -PLM_PRODUCT_SUPPLIER_INFO")
    private String supplierCode;
    @columnNames(name = "供应商名称 -PLM_PRODUCT_SUPPLIER_INFO")
    private String supplierName;
    @columnNames(name = "区域  -PLM_PRODUCT_SUPPLIER_INFO")
    private String area;
    @columnNames(name = "生效方式 -PLM_PRODUCT_SUPPLIER_INFO")
    private String sxWay;
    @columnNames(name = "生效仓库 -PLM_PRODUCT_SUPPLIER_INFO")
    private String sxWarehouse;
    @columnNames(name = "生效门店 -PLM_PRODUCT_SUPPLIER_INFO")
    private String sxStore;
    @columnNames(name = "创建日期 -PLM_PRODUCT_SUPPLIER_INFO")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @columnNames(name = "创建人 -PLM_PRODUCT_HEAD")
    private Integer createdBy;
    @columnNames(name = "创建人工号 -PLM_PRODUCT_HEAD")
    private String createdEmp;
    @columnNames(name = "区域IDS -areaId")
    private String areaId;
    @columnNames(name = "RMS同步实践 -rmsSynchrDate")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date rmsSynchrDate;


    public String getAreaId() {return areaId;}

    public void setAreaId(String areaId) {this.areaId = areaId;}

    public String getCreatedEmp() {
        return createdEmp;
    }

    public void setCreatedEmp(String createdEmp) {
        this.createdEmp = createdEmp;
    }

    public String getRmsCode() {
        return rmsCode;
    }

    public void setRmsCode(String rmsCode) {
        this.rmsCode = rmsCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentDescript() {
        return departmentDescript;
    }

    public void setDepartmentDescript(String departmentDescript) {
        this.departmentDescript = departmentDescript;
    }

    public String getBrandnameCn() {
        return brandnameCn;
    }

    public void setBrandnameCn(String brandnameCn) {
        this.brandnameCn = brandnameCn;
    }

    public String getBrandnameEn() {
        return brandnameEn;
    }

    public void setBrandnameEn(String brandnameEn) {
        this.brandnameEn = brandnameEn;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSxWay() {
        return sxWay;
    }

    public void setSxWay(String sxWay) {
        this.sxWay = sxWay;
    }

    public String getSxWarehouse() {
        return sxWarehouse;
    }

    public void setSxWarehouse(String sxWarehouse) {
        this.sxWarehouse = sxWarehouse;
    }

    public String getSxStore() {
        return sxStore;
    }

    public void setSxStore(String sxStore) {
        this.sxStore = sxStore;
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
}
