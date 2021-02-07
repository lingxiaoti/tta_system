package com.sie.watsons.base.product.model.entities.readonly;

import com.sie.watsons.base.product.model.entities.PlmProductObLicenseEntity_HI;

/**
 * PlmProductFileEntity_HI_RO Entity Object Thu Aug 29 10:51:48 CST 2019 Auto
 * Generate
 */

public class PlmProductObLicenseEntity_HI_RO extends PlmProductObLicenseEntity_HI {

    public static final String querySql = " SELECT ppol.ob_license_id\n" +
            "        ,ppol.supp_idnt\n" +
            "        ,ppol.supp_name\n" +
            "        ,ppol.barcode_id\n" +
            "        ,ppol.item_idnt\n" +
            "        ,ppol.item_name\n" +
            "        ,ppol.brand_idnt\n" +
            "        ,ppol.brand_name\n" +
            "        ,ppol.file_type\n" +
            "        ,ppol.file_name\n" +
            "        ,ppol.connection\n" +
            "        ,ppol.upload_date\n" +
            "        ,ppol.fr_date\n" +
            "        ,ppol.end_date\n" +
            "        ,ppol.file_path\n" +
            "        ,ppol.version_num\n" +
            "        ,ppol.creation_date\n" +
            "        ,ppol.created_by\n" +
            "        ,ppol.last_update_date\n" +
            "        ,ppol.last_updated_by\n" +
            "        ,ppol.last_update_login\n" +
            "    FROM plm_product_ob_license ppol\n" +
            "    where 1=1 ";


    //使用ObId查询数据
    public static final String SqlByQa = "     select ppsi.supplier_code suppidnt\n" +
            "          ,ppsi.supplier_name suppname\n" +
            "          ,ppqd.barcode barcodeid\n" +
            "          ,pph.plm_code itemidnt\n" +
            "          ,pph.product_name itemname\n" +
            "          ,pph.brandname_en brandidnt\n" +
            "          ,pph.brandname_cn brandname\n" +
            "          ,ppqd.file_type filetype\n" +
            "          ,ppqd.file_name filename\n" +
            "          ,ppqd.upload_date uploaddate\n" +
            "          ,ppqd.signature_time enddate\n" +
            "          ,ppqd.file_address filepath\n" +
            "          ,ppqd.plm_development_qa_detail_id keyid\n" +
            "          ,pdi.product_category productcategory\n" +
            "          ,'PLM_DEVELOPMENT_QA_DETAIL' fromTable\n" +
            "          ,substr(ppqs.file_alter_type ,4) filealtertype\n" +
            "      from plm_product_head pph\n" +
            "     inner join plm_development_info pdi\n" +
            "        on pph.ob_id = pdi.ob_id\n" +
            "     inner join plm_product_supplier_info ppsi\n" +
            "        on ppsi.product_head_id = pph.product_head_id\n" +
            "      join plm_development_qa_detail ppqd\n" +
            "        on ppqd.plm_development_info_id = pdi.plm_development_info_id\n" +
            "      join plm_development_qa_summary ppqs\n" +
            "        on ppqs.plm_development_qa_summary_id =\n" +
            "           ppqd.plm_development_qa_summary_id\n" +
            "     where 1 = 1\n" +
            "       and pph.ob_id is not null \n" +
            "       and pph.rms_Code is not null\n" +
            "  and nvl(ppqd.is_spa,'0')='0'  \n";
//        +"     AND pdi.ob_id = 'OB2000061' "


    //使用ObId查询批次数据
    public static final String SqlByBatch = " select ppsi.supplier_code suppidnt\n" +
            "          ,ppsi.supplier_name suppname\n" +
            "          ,pdi.barcode barcodeid\n" +
            "          ,pph.plm_code itemidnt\n" +
            "          ,pph.product_name itemname\n" +
            "          ,pph.brandname_en brandidnt\n" +
            "          ,pph.brandname_cn brandname\n" +
            "          ,pdbi.file_type filetype\n" +
            "          ,pdbi.file_name filename\n" +
            "          ,pdbi.upload_date uploaddate\n" +
            "          ,pdbi.upload_date enddate\n" +
            "          ,pdbi.file_name filepath\n" +
            "          ,pdbi.plm_development_batch_info_id  keyid\n" +
            "          ,pdi.product_category productcategory\n" +
            "          ,'plm_development_batch_info' fromTable\n" +
            "      from plm_product_head pph\n" +
            "     inner join plm_development_info pdi\n" +
            "        on pph.ob_id = pdi.ob_id\n" +
            "     inner join plm_product_supplier_info ppsi\n" +
            "        on ppsi.product_head_id = pph.product_head_id\n" +
            "      join plm_development_batch_info pdbi\n" +
            "      ON pdbi.plm_development_info_id = pdi.plm_development_info_id\n" +
            "     where 1 = 1 \n" +
            "    and pph.rms_Code is not null \n" +
            "    and nvl(pdbi.is_spa,'0')='0' \n";
//        +"     AND pdi.ob_id = 'OB2000061' "




    //	查询证书下载  plm_supplier_qa_brand
    public static final String SqlByBrand = " SELECT ppsi.supplier_code suppidnt\n" +
            "      ,ppsi.supplier_name suppname\n" +
            "      ,ppbt.barcode       barcodeid\n" +
            "      ,pph.plm_code       itemidnt\n" +
            "      ,pph.other_info  otherInfo\n" +
            "      ,pph.ob_id       obId\n" +
            "      ,pph.product_name   itemname\n" +
            "      ,psqb.file_name     fileName\n" +
            "      ,psqb.file_address  filePath\n" +
            "      ,psqb.brand_qa_type   fileType\n" +
            "      ,'PLM_SUPPLIER_QA_BRAND'  fromTable \n" +
            "      ,psqb.plm_supplier_qa_non_ob_info_id  plmSupplierQaNonObInfoId \n" +
            "      ,psqb.PLM_SUPPLIER_QA_BRAND_ID  keyId \n" +
            "  FROM plm_api_log pal\n" +
            "  JOIN plm_product_head pph\n" +
            "    ON pph.plm_code = pal.item\n" +
            "  JOIN plm_product_supplier_info ppsi\n" +
            "    ON ppsi.product_head_id = pph.product_head_id\n" +
            "  JOIN plm_supplier_qa_non_ob_info psqh\n" +
            "    ON psqh.supplier_code = ppsi.supplier_code\n" +
            "  LEFT JOIN plm_product_barcode_table ppbt\n" +
            "    ON ppbt.product_head_id = pph.product_head_id\n" +
            "  inner JOIN plm_supplier_qa_brand psqb\n" +
            "    ON psqb.plm_supplier_qa_non_ob_info_id  =\n" +
            "       psqh.plm_supplier_qa_non_ob_info_id\n" +
            " WHERE 1 = 1  "+
            "     and nvl(psqb.is_spa,'0')='0' \n";
    //插入数据
    public static final String SqlByRequest = "    SELECT ppsi.supplier_code  suppIdnt\n" +
            "    ,ppsi.supplier_name  suppName\n" +
            "    ,ppbt.barcode  barcodeId\n" +
            "    ,pph.plm_code  itemIdnt\n" +
            "    ,pph.other_info  otherInfo\n" +
            "    ,pph.ob_id       obId\n" +
            "    ,pph.product_name  itemName\n" +
            "    ,pph.brandname_en  brandIdnt\n" +
            "    ,pph.brandname_cn  brandName\n" +
            "    ,ppqf.qa_filetype  fileType\n" +
            "    ,ppqf.qa_file_name fileName\n" +
            "    ,ppqf.datecurent  uploadDate\n" +
            "    ,ppqf.datecurent  endDate\n" +
            "    ,ppqf.qa_url  filePath" +
            "    ,'PLM_PRODUCT_QA_FILE'  fromTable" +
            "      ,ppqf.QA_ID  keyId \n" +
            "      FROM plm_api_log pal\n" +
            "      join plm_product_head pph on pph.plm_code= pal.item\n" +
            "     -- Join plm_product_store pps on pps.product_head_id =pph.product_head_id\n" +
            "      Join plm_product_supplier_info ppsi \n" +
            "      on ppsi.product_head_id =pph.product_head_id\n" +
            "     --  and pps.supplier_id = ppsi.supplier_code\n" +
            "     -- and ppsi.is_mainsupplier='Y'\n" +
            "      left join PLM_PRODUCT_BARCODE_TABLE ppbt on ppbt.product_head_id = pph.product_head_id\n" +
            "      inner join PLM_PRODUCT_QA_FILE ppqf on ppqf.product_head_id = pph.product_head_id\n" +
            "     WHERE 1 = 1 \n " +
            "     and nvl(ppqf.is_spa,'0')='0' \n";
    //			+"       AND pal.request_id = '2020032916' ";

    //	查询证书下载 plm_supplier_qa_dealer
    public static final String SqlByDealer = " SELECT ppsi.supplier_code suppIdnt\n" +
            "      ,ppsi.supplier_name suppName\n" +
            "      ,ppbt.barcode       barcodeId\n" +
            "      ,pph.plm_code       itemIdnt\n" +
            "      ,pph.product_name   itemName\n" +
            "      ,pph.other_info  otherInfo\n" +
            "      ,pph.ob_id       obId\n" +
            "      ,psqd.file_address filePath\n" +
            "      ,psqd.file_name  fileName\n" +
            "      ,psqd.person_qa_type  fileType \n" +
            "      ,'PLM_SUPPLIER_QA_DEALER'  fromTable \n" +
            "      ,psqd.person_type   personType \n" +
            "      ,psqd.plm_supplier_qa_non_ob_info_id  plmSupplierQaNonObInfoId \n" +
            "      ,psqd.PLM_SUPPLIER_QA_DEALER_ID  keyId \n" +
            "  FROM plm_api_log pal\n" +
            "  JOIN plm_product_head pph\n" +
            "    ON pph.plm_code = pal.item\n" +
            "  JOIN plm_product_supplier_info ppsi\n" +
            "    ON ppsi.product_head_id = pph.product_head_id\n" +
            "  JOIN plm_supplier_qa_non_ob_info psqh\n" +
            "    ON psqh.supplier_code= ppsi.supplier_code\n" +
            "  LEFT JOIN plm_product_barcode_table ppbt\n" +
            "    ON ppbt.product_head_id = pph.product_head_id\n" +
            "  inner JOIN plm_supplier_qa_dealer psqd\n" +
            "    ON psqd.plm_supplier_qa_non_ob_info_id =\n" +
            "       psqh.plm_supplier_qa_non_ob_info_id\n" +
            " WHERE 1 = 1 "+
            "     and nvl(psqd.is_spa,'0')='0' \n";

    public static void main(String[] args) {
        System.out.println(" 资质Detail文件：SQL1  :::" + SqlByQa);
        System.out.println("品牌文件：SQL2  :::" + SqlByBrand);
        System.out.println("经销商和生成商 文件：SQL3  :::" + SqlByDealer);
        System.out.println("OB批次文件Batch 文件：SQL5  :::" + SqlByBatch);
        System.out.println("OB批次文件Batch 文件：SQL6  :::" + SqlByRequest);
    }


    private String otherInfo;
    private String obId;
    private String source;
    private String personType;

    private String productCategory;
    private String fileAlterType;
    private Integer keyId;

    public String getProductCategory() {        return productCategory;    }

    public void setProductCategory(String productCategory) {        this.productCategory = productCategory;    }

    public String getFileAlterType() {        return fileAlterType;    }

    public void setFileAlterType(String fileAlterType) {        this.fileAlterType = fileAlterType;    }

    public Integer getKeyId() {        return keyId;    }

    public void setKeyId(Integer keyId) {        this.keyId = keyId;    }

    public String getPersonType() {        return personType;    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getObId() {
        return obId;
    }

    public void setObId(String obId) {
        this.obId = obId;
    }
}
