package com.sie.watsons.base.product.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.contents.fileTypeContents;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentBatchInfoEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaBrandEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaDealerEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductObLicenseEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductQaFileEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseEntity_HI_RO;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseTimeEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductObLicense;
import com.sie.watsons.base.redisUtil.ResultConstant;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import jdk.nashorn.internal.scripts.JS;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component("plmProductObLicenseServer")
public class PlmProductObLicenseServer extends BaseCommonServer<PlmProductObLicenseEntity_HI> implements IPlmProductObLicense {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductObLicenseServer.class);

    @Autowired
    private ViewObject<PlmProductObLicenseEntity_HI> plmProductObLicenseDAO_HI;
    @Autowired
    private ViewObject<PlmDevelopmentQaDetailEntity_HI> plmDevelopmentQaDetailEntity;
    @Autowired
    private ViewObject<PlmDevelopmentBatchInfoEntity_HI> plmDevelopmentBatchInfoEntity;
    @Autowired
    private ViewObject<PlmSupplierQaBrandEntity_HI> brandEntity;
    @Autowired
    private ViewObject<PlmSupplierQaDealerEntity_HI> dealerEntity;
    @Autowired
    private ViewObject<PlmProductQaFileEntity_HI> qaFileEntity;
    @Autowired
    private BaseViewObject<PlmProductObLicenseEntity_HI_RO> plmProductObLicenseEntity_HI_RO;
    @Autowired
    @Lazy
    private IPlmApi plmApi;

    public PlmProductObLicenseServer() {
        super();
    }

    @Override
    public Pagination<PlmProductObLicenseEntity_HI_RO> findObLicenseByCondition(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmProductObLicenseEntity_HI_RO.querySql);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        String suppIdnt = queryParamJSON.getString("suppIdnt");
        String suppName = queryParamJSON.getString("suppName");
        String barcodeId = queryParamJSON.getString("barcodeId");
        String itemIdnt = queryParamJSON.getString("itemIdnt");
        String itemName = queryParamJSON.getString("itemName");
        String brandIdnt = queryParamJSON.getString("brandIdnt");
        String brandName = queryParamJSON.getString("brandName");
        String fileName = queryParamJSON.getString("fileName");
        SaafToolUtils.parperHbmParam(PlmProductObLicenseEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        if (!"".equals(suppIdnt) && suppIdnt != null) {
            sql.append(" and ppol.Supp_Idnt = '" + suppIdnt + "' ");
        }
        if (!"".equals(suppName) && suppName != null) {
            sql.append(" and ppol.supp_Name = '" + suppName + "' ");
        }
        if (!"".equals(barcodeId) && barcodeId != null) {
            sql.append(" and ppol.barcode_Id = '" + barcodeId + "' ");
        }
        if (!"".equals(itemIdnt) && itemIdnt != null) {
            sql.append(" and ppol.item_Idnt = '" + itemIdnt + "' ");
        }
        if (!"".equals(itemName) && itemName != null) {
            sql.append(" and ppol.item_Name = '" + itemName + "' ");
        }
        if (!"".equals(brandIdnt) && brandIdnt != null) {
            sql.append(" and ppol.brand_Idnt = '" + brandIdnt + "' ");
        }
        if (!"".equals(brandName) && brandName != null) {
            sql.append(" and ppol.brand_Name = '" + brandName + "' ");
        }
        if (!"".equals(fileName) && fileName != null) {
            sql.append(" and ppol.file_Name = '" + fileName + "' ");
        }
        sql.append(" order by ppol.last_update_date ");
        Pagination<PlmProductObLicenseEntity_HI_RO> findListResult = plmProductObLicenseEntity_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }

    @Override
    public String saveProductObLicense(JSONObject queryParamJSON) {
        PlmProductObLicenseEntity_HI plmProductObLicenseEntity = JSON.parseObject(queryParamJSON.toString(), PlmProductObLicenseEntity_HI.class);
        plmProductObLicenseDAO_HI.saveOrUpdate(plmProductObLicenseEntity);
//        JSONObject result = new JSONObject();
//        result.put("data",plmSupWarehouseEntity_HI);
        return JSONObject.toJSONString(plmProductObLicenseEntity);
    }

    @Override
    public String saveProductObLicenseByObId(JSONObject queryParamJSON) throws Exception {
        StringBuffer detailSql = new StringBuffer(PlmProductObLicenseEntity_HI_RO.SqlByQa);
        StringBuffer batchSql = new StringBuffer(PlmProductObLicenseEntity_HI_RO.SqlByBatch);
        String obId = queryParamJSON.getString("obId");
        if (obId != null && !"".equals(obId)) {
            detailSql.append("   AND pdi.ob_id = '" + obId + "' ");
            batchSql.append("   AND pdi.ob_id = '" + obId + "' ");
        } else {
            throw new Exception("没有obId参数");
        }
        //处理detail
        List<PlmProductObLicenseEntity_HI_RO> detailRos = plmProductObLicenseEntity_HI_RO.findList(detailSql.toString());
        if (CollectionUtils.isNotEmpty(detailRos)) {
            for (PlmProductObLicenseEntity_HI_RO ro : detailRos) {
                if (!validProductDataForOb(ro)) {
                    //更新已经同步
                    PlmDevelopmentQaDetailEntity_HI detail = plmDevelopmentQaDetailEntity.getById(ro.getKeyId());
                    detail.setIsSpa("2");
                    plmDevelopmentQaDetailEntity.saveOrUpdate(detail);
                    continue;
                }
                PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
                BeanUtils.copyProperties(ro, entity);
                entity.setLastUpdateDate(new Date());
                entity.setCreationDate(new Date());
                entity.setCreationDate(new Date());
                entity.setLastUpdatedBy(-999);
                entity.setCreatedBy(-999);
                entity.setCreatedBy(-999);
                entity.setVersionNum(0);
                entity.setFromTable("PLM_DEVELOPMENT_QA_DETAIL");
                entity.setFileFlag("1");
                plmProductObLicenseDAO_HI.saveOrUpdate(entity);
                //更新已经同步
                PlmDevelopmentQaDetailEntity_HI detail = plmDevelopmentQaDetailEntity.getById(ro.getKeyId());
                detail.setIsSpa("1");
                plmDevelopmentQaDetailEntity.saveOrUpdate(detail);

            }
        }
        //处理batch
        List<PlmProductObLicenseEntity_HI_RO> batchRos = plmProductObLicenseEntity_HI_RO.findList(batchSql.toString());
        if (CollectionUtils.isNotEmpty(batchRos)) {
            for (PlmProductObLicenseEntity_HI_RO ro : batchRos) {
                PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
                BeanUtils.copyProperties(ro, entity);
                entity.setLastUpdateDate(new Date());
                entity.setCreationDate(new Date());
                entity.setCreationDate(new Date());
                entity.setLastUpdatedBy(-999);
                entity.setCreatedBy(-999);
                entity.setCreatedBy(-999);
                entity.setVersionNum(0);
                entity.setFromTable("PLM_DEVELOPMENT_BATCH_INFO");
                entity.setFileFlag("1");
                plmProductObLicenseDAO_HI.saveOrUpdate(entity);
                //更新已经同步
                PlmDevelopmentBatchInfoEntity_HI batch = plmDevelopmentBatchInfoEntity.getById(ro.getKeyId());
                batch.setIsSpa("1");
                plmDevelopmentBatchInfoEntity.saveOrUpdate(batch);
            }
        }

        return "S";
    }

    @Override
    public String saveProductObLicenseByNewFile(JSONObject jsonObject) throws Exception {
        Integer plmSupplierQaNonObInfoId = jsonObject.getInteger("plmSupplierQaNonObInfoId");
        ResultUtils.getLookUpValue("PLM_PRODUCT_HEADQAFILETYE");
        ResultUtils.getLookUpValue("PLM_BRAND_QA_TYPE");
        List<PlmProductObLicenseEntity_HI_RO> licenseRos = plmProductObLicenseEntity_HI_RO.findList("   SELECT pol.item_idnt itemIdnt\n" +
                "         ,pol.item_name   itemName \n" +
                "         ,pol.barcode_id  barcodeId \n" +
                "         ,pph.other_info  otherInfo\n" +
                "     FROM plm_product_ob_license pol \n" +
                "     left join plm_product_head pph on pph.plm_code = pol.item_idnt \n" +
                "    WHERE 1 = 1\n" +
                "      AND pol.plm_supplier_qa_non_ob_info_id = '" + plmSupplierQaNonObInfoId + "'\n" +
                "    GROUP BY pol.item_idnt\n" +
                "            ,pol.item_name\n" +
                "            ,pol.barcode_id\n" +
                "            ,pph.other_info");

        JSONArray array = jsonObject.getJSONArray("newLines");

        if (array.size() > 0 && CollectionUtils.isNotEmpty(licenseRos)) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject line = array.getJSONObject(i);
                for (PlmProductObLicenseEntity_HI_RO sourceRo : licenseRos) {
                    PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
                    sourceRo.setFileName(line.getString("fileName"));
                    sourceRo.setFilePath(line.getString("filePath"));
                    sourceRo.setFileType(line.getString("fileType"));
                    String tableFlag = line.getString("tableFlag");
                    sourceRo.setPlmSupplierQaNonObInfoId(plmSupplierQaNonObInfoId);
                    if ("1".equals(tableFlag)) {
                        //品牌
                        sourceRo.setFromTable("PLM_SUPPLIER_QA_BRAND");
                    } else if ("2".equals(tableFlag)) {
                        //生产商 经销商
                        sourceRo.setFromTable("PLM_SUPPLIER_QA_DEALER");
                    }
                    sourceRo.setFileFlag("3");
                    //校验是否需要同步SPA
                    if (!plmApi.validProductData(sourceRo)) {
                        continue;
                    }
                    BeanUtils.copyProperties(sourceRo, entity);
                    entity.setLastUpdateDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setLastUpdatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setVersionNum(0);
                    plmProductObLicenseDAO_HI.saveOrUpdate(entity);
                    if ("1".equals(tableFlag)) {
                        //品牌
                        Integer keyId = line.getInteger("lineId");
                        PlmSupplierQaBrandEntity_HI brand = brandEntity.getById(keyId);
                        brand.setIsSpa("1");
                        brandEntity.saveOrUpdate(brand);
                    } else if ("2".equals(tableFlag)) {
                        //生产商 经销商
                        Integer keyId = line.getInteger("lineId");
                        PlmSupplierQaDealerEntity_HI dealer = dealerEntity.getById(keyId);
                        dealer.setIsSpa("1");
                        dealerEntity.saveOrUpdate(dealer);

                    }
                }
            }
        }
        return null;
    }

    @Override
    public String saveProductObLicenseByTime() throws  Exception{

        ResultUtils.getLookUpValue("PLM_PRODUCT_HEADQAFILETYE");
        ResultUtils.getLookUpValue("PLM_BRAND_QA_TYPE");

        //todo: PLM_PRODUCT_QA_FILE   文件
        commonDealFile(PlmProductObLicenseTimeEntity_HI_RO.SqlByQa, "2");
        //todo: PLM_SUPPLIER_QA_DEALER  文件
        commonDealFile(PlmProductObLicenseTimeEntity_HI_RO.SqlByDealer, "3");
        //todo: PLM_SUPPLIER_QA_BRAND  文件
        commonDealFile(PlmProductObLicenseTimeEntity_HI_RO.SqlByBrand, "3");
        //todo: plm_development_batch_info  文件
        commonDealFile(PlmProductObLicenseTimeEntity_HI_RO.SqlByBatch, "1");
        //todo: plm_development_qa_detail  文件
        commonDealFile(PlmProductObLicenseTimeEntity_HI_RO.SqlByDetail, "1");

        return null;
    }

    private void commonDealFile(String sql, String flag) {
        List<PlmProductObLicenseEntity_HI_RO> licenseRos = plmProductObLicenseEntity_HI_RO.findList(sql);
        if (CollectionUtils.isNotEmpty(licenseRos)) {
            if (!"1".equals(flag)) {
                for (PlmProductObLicenseEntity_HI_RO ro : licenseRos) {
                    //todo:校验是否需要同步SPA
                    if (!plmApi.validProductData(ro)) {
                        if ("PLM_PRODUCT_QA_FILE".equals(ro.getFromTable())) {
                            PlmProductQaFileEntity_HI qa = qaFileEntity.getById(ro.getKeyId());
                            if(ObjectUtils.isEmpty(qa)){
                                LOGGER.info("PlmProductQaFileEntity_HI查询数据为空");
                                continue;
                            }
                            qa.setIsSpa("2");
                            qaFileEntity.saveOrUpdate(qa);
                        } else if ("PLM_SUPPLIER_QA_BRAND".equals(ro.getFromTable())) {
                            PlmSupplierQaBrandEntity_HI brand = brandEntity.getById(ro.getKeyId());
                            if(ObjectUtils.isEmpty(brand)){
                                LOGGER.info("PlmSupplierQaBrandEntity_HI查询数据为空");
                                continue;
                            }
                            brand.setIsSpa("2");
                            brandEntity.saveOrUpdate(brand);
                        } else if ("PLM_SUPPLIER_QA_DEALER".equals(ro.getFromTable())) {
                            PlmSupplierQaDealerEntity_HI dealer = dealerEntity.getById(ro.getKeyId());
                            if(ObjectUtils.isEmpty(dealer)){
                                LOGGER.info("PlmSupplierQaDealerEntity_HI 查询数据为空");
                                continue;
                            }
                            dealer.setIsSpa("2");
                            dealerEntity.saveOrUpdate(dealer);
                        }
                        continue;
                    }
                    PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
                    BeanUtils.copyProperties(ro, entity);
                    entity.setLastUpdateDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setLastUpdatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setVersionNum(0);
                    entity.setFileFlag(flag);
                    plmProductObLicenseDAO_HI.saveOrUpdate(entity);
                    if ("PLM_PRODUCT_QA_FILE".equals(ro.getFromTable())) {
                        PlmProductQaFileEntity_HI qa = qaFileEntity.getById(ro.getKeyId());
                        qa.setIsSpa("1");
                        qaFileEntity.saveOrUpdate(qa);
                    } else if ("PLM_SUPPLIER_QA_BRAND".equals(ro.getFromTable())) {
                        PlmSupplierQaBrandEntity_HI brand = brandEntity.getById(ro.getKeyId());
                        brand.setIsSpa("1");
                        brandEntity.saveOrUpdate(brand);
                    } else if ("PLM_SUPPLIER_QA_DEALER".equals(ro.getFromTable())) {
                        PlmSupplierQaDealerEntity_HI dealer = dealerEntity.getById(ro.getKeyId());
                        dealer.setIsSpa("1");
                        dealerEntity.saveOrUpdate(dealer);
                    }
                }
            } else {
                //OB文件
                for (PlmProductObLicenseEntity_HI_RO ro : licenseRos) {
                    if ("PLM_DEVELOPMENT_QA_DETAIL".equals(ro.getFromTable())) {
                        if (!validProductDataForOb(ro)) {
                            //更新已经同步
                            PlmDevelopmentQaDetailEntity_HI detail = plmDevelopmentQaDetailEntity.getById(ro.getKeyId());
                            detail.setIsSpa("2");
                            plmDevelopmentQaDetailEntity.saveOrUpdate(detail);
                            continue;
                        }
                    }
                    PlmProductObLicenseEntity_HI entity = new PlmProductObLicenseEntity_HI();
                    BeanUtils.copyProperties(ro, entity);
                    entity.setLastUpdateDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setCreationDate(new Date());
                    entity.setLastUpdatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setCreatedBy(-999);
                    entity.setVersionNum(0);
                    entity.setFromTable("PLM_DEVELOPMENT_QA_DETAIL");
                    entity.setFileFlag("1");
                    plmProductObLicenseDAO_HI.saveOrUpdate(entity);
                    //更新已经同步
                    PlmDevelopmentQaDetailEntity_HI detail = plmDevelopmentQaDetailEntity.getById(ro.getKeyId());
                    detail.setIsSpa("1");
                    plmDevelopmentQaDetailEntity.saveOrUpdate(detail);

                }


            }

        }

    }

    private boolean validProductDataForOb(PlmProductObLicenseEntity_HI_RO ro) {
        //todo: 1. 判断公共属性
        if (Arrays.asList(fileTypeContents.obFileTypes_common_pass).contains(ro.getFileAlterType())) {
            return true;
        } else if (Arrays.asList(fileTypeContents.obFileTypes_common_no_pass).contains(ro.getFileAlterType())) {
            return false;
        }
        //todo :2.判断商品类型
        if ("DOMESTIC_COSMETICS".equals(ro.getProductCategory())) {
            //国产化妆品
            if (Arrays.asList(fileTypeContents.obFileTypes_domestic_cosmetics_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("DOMESTIC_FOOD".equals(ro.getProductCategory())) {
            //国产食品
            if (Arrays.asList(fileTypeContents.obFileTypes_domestic_food_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("IMPORTED_COSMETICS".equals(ro.getProductCategory())) {
            //进口化妆品
            if (Arrays.asList(fileTypeContents.obFileTypes_imported_cosmetics_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("IMPORTED_FOOD".equals(ro.getProductCategory())) {
            //进口食品
            if (Arrays.asList(fileTypeContents.obFileTypes_imported_food_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("MEDICAL_EQUIPMENT".equals(ro.getProductCategory())) {
            //医疗器械
            if (Arrays.asList(fileTypeContents.obFileTypes_medical_equipment_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("OTHERS".equals(ro.getProductCategory())) {
            //其他
            if (Arrays.asList(fileTypeContents.obFileTypes_others_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        } else if ("TOOTHPASTE".equals(ro.getProductCategory())) {
            //牙膏
            if (Arrays.asList(fileTypeContents.obFileTypes_toothpaste_pass).contains(ro.getFileAlterType())) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
