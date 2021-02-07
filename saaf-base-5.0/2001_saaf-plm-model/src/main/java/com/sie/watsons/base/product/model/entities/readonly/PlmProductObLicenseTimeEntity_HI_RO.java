package com.sie.watsons.base.product.model.entities.readonly;

public class PlmProductObLicenseTimeEntity_HI_RO {


    //插入数据
    public static final String SqlByQa = "   SELECT ppsi.supplier_code suppidnt\n" +
            "       ,ppsi.supplier_name suppname\n" +
            "       ,ppbt.barcode barcodeid\n" +
            "       ,pph.plm_code itemidnt\n" +
            "       ,pph.other_info otherinfo\n" +
            "       ,pph.ob_id obid\n" +
            "       ,pph.product_name itemname\n" +
            "       ,pph.brandname_en brandidnt\n" +
            "       ,pph.brandname_cn brandname\n" +
            "       ,ppqf.qa_filetype filetype\n" +
            "       ,ppqf.qa_file_name filename\n" +
            "       ,ppqf.datecurent uploaddate\n" +
            "       ,ppqf.datecurent enddate\n" +
            "       ,ppqf.qa_url filepath\n" +
            "       ,'PLM_PRODUCT_QA_FILE' fromtable\n" +
            "       ,ppqf.qa_id keyid\n" +
            "   FROM  plm_product_head pph\n" +
            " -- Join plm_product_store pps on pps.product_head_id =pph.product_head_id\n" +
            "   JOIN plm_product_supplier_info ppsi\n" +
            "     ON ppsi.product_head_id = pph.product_head_id\n" +
            " --  and pps.supplier_id = ppsi.supplier_code\n" +
            " -- and ppsi.is_mainsupplier='Y'\n" +
            "   LEFT JOIN plm_product_barcode_table ppbt\n" +
            "     ON ppbt.product_head_id = pph.product_head_id\n" +
            "   inner JOIN plm_product_qa_file ppqf\n" +
            "     ON ppqf.product_head_id = pph.product_head_id\n" +
            "  WHERE 1 = 1\n" +
            "    AND nvl(ppqf.is_spa,'0') = '0'\n" +
            "    and pph.order_status = '6' \n" +
            "    and nvl(pph.rms_status,'N') != 'Y' \n";

    //	查询证书下载 plm_supplier_qa_dealer
    public static final String SqlByDealer = "  SELECT ppsi.supplier_code suppidnt\n" +
            "       ,ppsi.supplier_name suppname\n" +
            "       ,ppbt.barcode barcodeid\n" +
            "       ,pph.plm_code itemidnt\n" +
            "       ,pph.product_name itemname\n" +
            "       ,pph.other_info otherinfo\n" +
            "       ,pph.ob_id obid\n" +
            "       ,psqd.file_address filepath\n" +
            "       ,psqd.file_name filename\n" +
            "       ,psqd.person_qa_type filetype\n" +
            "       ,'PLM_SUPPLIER_QA_DEALER' fromtable\n" +
            "       ,psqd.person_type persontype\n" +
            "       ,psqd.plm_supplier_qa_non_ob_info_id plmsupplierqanonobinfoid\n" +
            "       ,psqd.plm_supplier_qa_dealer_id keyid\n" +
            "   FROM plm_product_head pph\n" +
            "   JOIN plm_product_supplier_info ppsi\n" +
            "     ON ppsi.product_head_id = pph.product_head_id\n" +
            "   JOIN plm_supplier_qa_non_ob_info psqh\n" +
            "     ON psqh.supplier_code = ppsi.supplier_code\n" +
            "   LEFT JOIN plm_product_barcode_table ppbt\n" +
            "     ON ppbt.product_head_id = pph.product_head_id\n" +
            "   LEFT JOIN plm_supplier_qa_dealer psqd\n" +
            "     ON psqd.plm_supplier_qa_non_ob_info_id =\n" +
            "        psqh.plm_supplier_qa_non_ob_info_id\n" +
            "  WHERE 1 = 1\n" +
            "    AND nvl(psqd.is_spa,'0') = '0'\n" +
            "    and pph.order_status = '6' \n" +
            "    and nvl(pph.rms_status,'N') != 'Y' \n";

    //	查询证书下载  plm_supplier_qa_brand
    public static final String SqlByBrand = "     \n" +
            "    SELECT ppsi.supplier_code suppidnt\n" +
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
            "  FROM  plm_product_head pph\n" +
            "  JOIN plm_product_supplier_info ppsi\n" +
            "    ON ppsi.product_head_id = pph.product_head_id\n" +
            "  JOIN plm_supplier_qa_non_ob_info psqh\n" +
            "    ON psqh.supplier_code = ppsi.supplier_code\n" +
            "  LEFT JOIN plm_product_barcode_table ppbt\n" +
            "    ON ppbt.product_head_id = pph.product_head_id\n" +
            "  LEFT JOIN plm_supplier_qa_brand psqb\n" +
            "    ON psqb.plm_supplier_qa_non_ob_info_id  =\n" +
            "       psqh.plm_supplier_qa_non_ob_info_id\n" +
            " WHERE 1 = 1       \n" +
            " and nvl(psqb.is_spa,'0')='0' \n" +
            "    and pph.order_status = '6' \n" +
            "    and nvl(pph.rms_status,'N') != 'Y' \n";

    //使用ObId查询批次数据
    public static final String SqlByBatch = "    SELECT pdi.supplier_code  suppIdnt\n" +
            "       ,pdi.supplier_name  suppName\n" +
            "       ,pdi.barcode   barcodeId\n" +
            "       ,pdi.product_no  itemIdnt\n" +
            "       ,pdi.product_name  itemName\n" +
            "       ,pdi.product_name_en  brandIdnt\n" +
            "       ,pdi.product_name_cn  brandName\n" +
            "       ,pdbi.file_type  fileType\n" +
            "       ,pdbi.file_name  fileName\n" +
            "       ,pdbi.upload_date uploadDate\n" +
            "       ,pdbi.upload_date endDate\n" +
            "       ,pdbi.file_name filePath\n" +
            "       ,pdbi.plm_development_batch_info_id keyId\n" +
            "       ,pdi.product_category  productCategory\n" +
            "       ,'PLM_DEVELOPMENT_BATCH_INFO'  fromTable\n" +
            "  FROM plm_development_batch_info pdbi\n" +
            " INNER JOIN plm_development_info pdi\n" +
            "    ON pdbi.plm_development_info_id = pdi.plm_development_info_id\n" +
            "    where 1=1 \n" +
            "    and nvl(pdbi.is_spa,'0')='0' \n" +
            "    and pdi.development_bill_status ='COMPLETED' \n";
//        +"     AND pdi.ob_id = 'OB2000061' "

    //使用ObId查询数据
    public static final String SqlByDetail = "      SELECT pdi.supplier_code  suppIdnt\n" +
            "       ,pdi.supplier_name  suppName\n" +
            "       ,ppqd.barcode   barcodeId\n" +
            "       ,pdi.product_no  itemIdnt\n" +
            "       ,pdi.product_name  itemName\n" +
            "       ,pdi.product_name_en  brandIdnt\n" +
            "       ,pdi.product_name_cn  brandName\n" +
            "       ,ppqd.file_type  fileType\n" +
            "       ,ppqd.file_name  fileName\n" +
            "       ,ppqd.upload_date uploadDate\n" +
            "       ,ppqd.signature_time endDate\n" +
            "       ,ppqd.file_address filePath\n" +
            "       ,ppqd.plm_development_qa_detail_id keyId\n" +
            "       ,pdi.product_category  productCategory\n" +
            "       ,substr(ppqs.file_alter_type ,4) fileAlterType\n" +
            "       ,'PLM_DEVELOPMENT_QA_DETAIL'  fromTable\n" +
            "   FROM plm_development_info pdi\n" +
            "   JOIN plm_development_qa_detail ppqd\n" +
            "     ON ppqd.plm_development_info_id = pdi.plm_development_info_id\n" +
            "   JOin plm_development_qa_summary ppqs \n" +
            "     on ppqs.plm_development_qa_summary_id = ppqd.plm_development_qa_summary_id\n" +
            "  WHERE 1 = 1\n" +
            "    and nvl(ppqd.is_spa,'0')='0'  \n" +
            "    and pdi.development_bill_status ='COMPLETED'\n";
//        +"     AND pdi.ob_id = 'OB2000061' "

    public static void main(String[] args) {
        System.out.println("需要展示的SQL::" + SqlByDetail);
    }


}
