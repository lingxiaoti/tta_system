package com.sie.watsons.base.product.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import java.util.Date;

/**
 * 用于测试数据RO类文件
 */
public class PlmProductHeadEntity_HI_RO2 {
    public static final String QUERY_HEAD = "SELECT\n" +
            "  PRODUCT.PRODUCT_HEAD_ID AS productHeadId,\n" +
            "  PRODUCT.PLM_CODE AS plmCode,\n" +
            "  PRODUCT.RMS_CODE AS rmsCode,\n" +
            "  PRODUCT.OB_ID AS obId,\n" +
            "  PRODUCT.GROUP_BRAND AS groupBrand,\n" +
            "  pgb.plm_group_brand_name AS  plmGroupBrandName,\n" +
            "  PRODUCT.DEPARTMENT AS department,\n" +
            "  PRODUCT.DEPARTMENT_DESCRIPT AS departmentDescript,\n" +
            "  PRODUCT.CLASSES AS classes,\n" +
            "  PRODUCT.CLASS_DESC AS classDesc,\n" +
            "  PRODUCT.SUB_CLASS AS subClass,\n" +
            "  PRODUCT.SUBCLASS_DESC AS subclassDesc,\n" +
            "  PRODUCT.PURCHASE_TYPE AS purchaseType,\n" +
            "  PRODUCT.PRODUCT_NAME AS productName,\n" +
            "  PRODUCT.PRODUCT_ENAME AS productEname,\n" +
            "  PRODUCT.PRODUCT_TYPE AS productType,\n" +
            "  PRODUCT.BRANDNAME_CN AS brandnameCn,\n" +
            "  PRODUCT.BRANDNAME_EN AS brandnameEn,\n" +
            "  PRODUCT.COUNT_UNIT AS countUnit,\n" +
            "  PRODUCT.OTHER_INFO AS otherInfo,\n" +
            "  PRODUCT.MARKER_CHANNEL AS markerChannel,\n" +
            "  PRODUCT.PRODUCT_SHAPE AS productShape,\n" +
            "  PRODUCT.VERSION_NUM AS versionNum,\n" +
            "  PRODUCT.CREATION_DATE AS creationDate,\n" +
            "  PRODUCT.CREATED_BY AS createdBy,\n" +
            "  PRODUCT.LAST_UPDATED_BY AS lastUpdatedBy,\n" +
            "  PRODUCT.LAST_UPDATE_DATE AS lastUpdateDate,\n" +
            "  PRODUCT.LAST_UPDATE_LOGIN AS lastUpdateLogin,\n" +
            "  PRODUCT.GROSS_EARNINGS AS grossEarnings,\n" +
            "  PRODUCT.RATE_CLASS_CODE AS rateClassCode,\n" +
            "  PRODUCT.DAY_DAMAGE AS dayDamage,\n" +
            "  PRODUCT.SPECIAL_LICENCE AS specialLicence,\n" +
            "  PRODUCT.PRODUCT_RESOURCE AS productResource,\n" +
            "  PRODUCT.PRODUCT_CATEGEERY AS productCategeery,\n" +
            "  PRODUCT.UNIQUE_COMMODITIES AS uniqueCommodities,\n" +
            "  PRODUCT.SPECIALTY_PRODUCT AS specialtyProduct,\n" +
            "  PRODUCT.PRODUCT_PROPERTIES AS productProperties,\n" +
            "  PRODUCT.BUYING_LEVEL AS buyingLevel,\n" +
            "  PRODUCT.DANGEROUS_PRODUCT AS dangerousProduct,\n" +
            "  PRODUCT.POS_INFO AS posInfo,\n" +
            "  PRODUCT.INTERNATION_PRODUCT AS internationProduct,\n" +
            "  PRODUCT.SESION_PRODUCT AS sesionProduct,\n" +
            "  PRODUCT.TOP_PRODUCT AS topProduct,\n" +
            "  PRODUCT.MOTHER_COMPANY AS motherCompany,\n" +
            "  uda3.uda_value_desc AS motherCompanyName,\n " +
            "  PRODUCT.BLUECAP_PRODUCT AS bluecapProduct,\n" +
            "  PRODUCT.CROSSBORDER_PRODUCT AS crossborderProduct,\n" +
            "  PRODUCT.VC_PRODUCT AS vcProduct,\n" +
            "  PRODUCT.WAREHOUSE_RESOURCE AS warehouseResource,\n" +
            "  PRODUCT.COMPANY_DELETION AS companyDeletion,\n" +
            "  PRODUCT.ORIGIN_COUNTRY AS originCountry,\n" +
            "  PRODUCT.UNIT AS unit,\n" +
            "  PRODUCT.WAREHOUSE_GET_DAY AS warehouseGetDay,\n" +
            "  PRODUCT.WAREHOUSE_POST_DAY AS warehousePostDay,\n" +
            "  PRODUCT.RANG_OB AS rangOb,\n" +
            "  uda2.uda_value_desc as rangObName,\n" +
            "  PRODUCT.POWER_OB AS powerOb,\n" +
            "  uda1.uda_value_desc as powerObName,\n" +
            "  PRODUCT.TIER AS tier,\n" +
            "  PRODUCT.SPECIAL_REQUIER AS specialRequier,\n" +
            "  PRODUCT.TRANSPORT_STORAGE AS transportStorage,\n" +
            "  PRODUCT.PRODUCT_LICENSE AS productLicense,\n" +
            "  PRODUCT.ORDER_STATUS AS orderStatus,\n" +
            "  PRODUCT.START_DATE AS startDate,\n" +
            "  PRODUCT.IS_UPDATECHECK AS isUpdatecheck,\n" +
            " PRODUCT.PRICEWAR_PRODUCT AS pricewarProduct,\n" +
            " PRODUCT.CONSIGNMENT_TYPE AS consignmentType,\n" +
            " PRODUCT.CONSIGNMENT_RATE AS consignmentRate,\n" +
            " PRODUCT.SALES_QTY AS salesQty,\n" +
            " PRODUCT.CONSULT_PRODUCTNO AS consultProductno,\n" +
            " PRODUCT.IM_PRODUCTNO AS imProductno,\n" +
            " PRODUCT.IM_PRODUCTNAME AS imProductname,\n" +
            " PRODUCT.CONSULT_PRODUCTNAME AS consultProductname,\n" +
            " PRODUCT.PROV_CONDITION AS provCondition,\n" +
            " PRODUCT.CONSULT_DATE AS consultDate,\n" +
            " PRODUCT.CONSULT_ENDDATE AS consultEnddate,\n" +
            " PRODUCT.IS_DIARYPRODUCT AS isDiaryproduct,\n" +
            "   PRODUCT.SUPPLIER_COUNT AS supplierCount,\n" +
            "    PRODUCT.PRODUCT_RETURN AS productReturn,\n" +
            "     PRODUCT.QA_FILE_ID AS qaFileId,\n" +
            "     PRODUCT.QA_FILENAME AS qaFilename, \n" +
            "     PRODUCT.CONDITION AS condition, \n" +
            "     PRODUCT.ALL_TIER As allTier, \n" +
            "     PRODUCT.TIER1 AS tier1, \n" +
            "     PRODUCT.TIER2 AS tier2, \n" +
            "     PRODUCT.TIER345 AS tier345, \n" +
            "     PRODUCT.STORE_TYPE AS storeType, \n" +
            "     PRODUCT.TRADE_ZONE AS tradeZone, \n" +
            "     PRODUCT.MAIN_PRODUCTNAME AS mainProductname, \n" +
            "     PRODUCT.MAIN_PRODUCTID AS mainProductid, \n" +
            "     PRODUCT.PROCESS_INSTANCEID AS processInstanceid, \n" +
            "     PRODUCT.UPDATE_INSTANCEID AS updateInstanceid,  " +
            "PRODUCT.VALID_DAYS AS validDays, " +
            "PRODUCT.SX_DAYS AS sxDays,  PRODUCT.SERIAL_ID AS serialId, " +
            "PRODUCT.SERIAL_NAME AS serialName,  PRODUCT.CLASS_ID AS classId, " +
            "PRODUCT.CLASS_NAME AS className,  PRODUCT.ADD_PROCESSNAME AS addProcessname,  " +
            "PRODUCT.UPDATE_PROCEKEY AS updateProcekey,  PRODUCT.CREATEDSTR AS createdstr, " +
            "nvl(PRODUCT.ISRETURN_SALE,'0') AS isreturnSale, " +
            "nvl(PRODUCT.ISUPDATE_PRICE,'0') AS isupdatePrice, nvl(PRODUCT.ISITERATE_SALES,'0') AS isiterateSales, " +
            "PRODUCT.ORIGIN_COUNTRYID AS originCountryid, PRODUCT.SUG_RETAILPRICE AS sugRetailprice, PRODUCT.RATE_CLASS AS rateClass, " +
            "PRODUCT.CREATED_ENAME AS createdEname, PRODUCT.CREATED_EMP AS createdEmp,  PRODUCT.RMS_STATUS AS rmsStatus, " +
            "PRODUCT.RMS_REMAKE AS rmsRemake,  PRODUCT.USER_DEPT AS userDept, " +
            "PRODUCT.USER_GROUPCODE AS userGroupcode,  PRODUCT.POG_WAYS AS pogWays, " +
            "PRODUCT.POG_DEPARMENT AS pogDeparment,  PRODUCT.RMS_UPDATE AS rmsUpdate,  " +
            "PRODUCT.CONDITIONS as  conditions, PRODUCT.special_requier_name AS specialRequierName, " +
            "PRODUCT.transport_storage_name AS transportStorageName, PRODUCT.product_license_name AS productLicenseName,  " +
            "PRODUCT.standard_of_unit as standardOfUnit, PRODUCT.is_rms_rever AS isRmsRever,  " +
            "PRODUCT.rms_rever_businesskey AS rmsReverBusinesskey,  PRODUCT.brand_cn_uda_id AS BrandCnUdaId, " +
            "PRODUCT.brand_cn_uda_value AS BrandCnUdaValue, " +
            "PRODUCT.brand_en_uda_id AS BrandEnUdaId, PRODUCT.brand_en_uda_value AS BrandEnUdaValue  FROM\n" +
            "  PLM_PRODUCT_HEAD  PRODUCT  \n" +
            "  left join plm_uda_attribute  uda1 on PRODUCT.Power_Ob = uda1.uda_value  and uda1.uda_id=48\n" +
            "  left join plm_uda_attribute  uda2 on PRODUCT.Rang_Ob = uda2.uda_value  and uda2.uda_id=47\n" +
            "  LEFT JOIN plm_uda_attribute uda3   ON product.mother_company = uda3.uda_value AND uda3.uda_id = 49 \n" +
            "  LEFT JOIN plm_group_brand pgb   ON product.group_brand = pgb.plm_group_brand_id \n" +
            "  where 1=1 \n" +
            "  and PRODUCT.IS_UPDATECHECK='0' ";

    public static final String QUERY = " SELECT ppd.drug_ins drugIns,ppbt.barCode, ppd.drug_id drugId, p.* from PLM_PRODUCT_HEAD p "
            + "   left join PLM_PRODUCT_DRUG ppd on ppd.product_head_id= p.product_head_id\n "
            + "   left join plm_product_barcode_table ppbt on ppd.product_head_id = ppbt.product_head_id and ppbt.is_main ='1'\n "
            + " where 1=1 \n"
//            + "  and p.order_status = '6' \n"
//            + "  and p.rms_code is null \n"
//            + "  and nvl(p.rms_status,'N') != 'Y'  \n"
			+ "  AND p.plm_code='202006190001' \n"
//	 + "  AND p.plm_code IN (''\n"
//	 + "                       ,'202006080008'\n" +
//	  "                       ,'202006080009'\n" +
            // // "                       ,'202004300001'\n" +
            // // "                       ,'202004300006'\n" +
            // // "                       ,'202004130011'\n" +
            // // "                       ,'202004110003'\n"
            // // + "                     ,'202004140007'\n" +
            // // "                       ,'202004100008'\n" +
            // // "                       ,'202004140003'\n" +
            // // "                       ,'202004070007'\n" +
            // // "                       ,'202004160002'\n" +
            // // // "                    ,'202004140008'\n" +
            // // "                       ,'202004140010'\n" +
            // // // "                    ,'202004100011'\n" +
            // // "                       ,'202004130015'\n" +
            // // "                       ,'202004100004'\n" +
            // // "                       ,'202004110002'\n" +
//	 "                       )"

            // todo: 过滤已经上传RMS 待处理的货品
//            + " AND p.plm_code not IN\n" +
//            "            (SELECT DISTINCT pal.item\n" +
//            "               FROM plm_api_log pal\n" +
//            "              WHERE 1 = 1\n" +
//            "                AND pal.status IS NULL\n" +
//            "                AND length(pal.request_id) = 10\n" +
//            "                and pal.return_flag IS NULL )"
            ;

    public static final String QUERY2 = "SELECT * from PLM_PRODUCT_HEAD p "
            + " where 1 = 1 \n"
//            + " and p.order_status = '6' \n "
////			+ " and p.purchase_type = ':purchaseType' \n"
//            + " and nvl( p.rms_status ,'N') != 'Y' \n"
//            + "  and p.rms_code is null \n"
			+ "  AND p.plm_code='202006190001' \n"
//	 + "  AND p.plm_code IN (''\n"
//	 + "                       ,'202006080008'\n" +
//	 "                       ,'202006080009'\n" +
//	 "                       )"

            //todo: 过滤已经上传RMS 待处理的货品
//            + " AND p.plm_code not IN\n" +
//            "            (SELECT DISTINCT pal.item\n" +
//            "               FROM plm_api_log pal\n" +
//            "              WHERE 1 = 1\n" +
//            "                AND pal.status IS NULL\n" +
//            "                AND length(pal.request_id) = 10\n" +
//            "                and pal.return_flag IS NULL )"
            ;

    public static final String ORIGIN_COUNTRY_QUERY = "select v.name_abbreviation originCountry from PLM_COUNTRY_OF_ORIGIN v  "
            + "left join PLM_PRODUCT_HEAD h on h.origin_country = v.area_cn  "
            + "where h.PRODUCT_HEAD_ID = ':headId' ";
    // private String validDays;

    public static final String QUERY3 = "select prh.product_head_id productHeadId, prh.rms_code rmsCode, PRH.PRODUCT_NAME productName,PRH.DEPARTMENT department FROM PLM_PRODUCT_HEAD prh where 1=1 ";

    public static void main(String[] args) {
        System.out.println("  SQL:::  " + QUERY_HEAD);
    }

}
