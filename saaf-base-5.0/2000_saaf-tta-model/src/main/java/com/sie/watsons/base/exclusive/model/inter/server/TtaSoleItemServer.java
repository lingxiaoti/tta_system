package com.sie.watsons.base.exclusive.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SFTPUtil;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.dao.TtaSoleItemDAO_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleItemEntity_HI;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleItem;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;

@Component("ttaSoleItemServer")
public class TtaSoleItemServer extends BaseCommonServer<TtaSoleItemEntity_HI> implements ITtaSoleItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleItemServer.class);

    @Autowired
    private BaseCommonDAO_HI<TtaSoleItemEntity_HI> ttaSoleItemDAO_HI;
    @Autowired
    private BaseViewObject<TtaItemEntity_HI_RO> ttaItemDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaSoleItemEntity_HI_RO> ttaSoleItemDAO_HI_RO;
    @Autowired
    private BaseCommonDAO_HI<TtaSoleAgrtInfoEntity_HI> ttaSoleAgrtInfoDAO_HI;

    public TtaSoleItemServer() {
        super();
    }

    /**
     * 保存item明细信息
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaSoleItemEntity_HI> saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        Integer soleAgrtHId = paramsJSON.getInteger("soleAgrtHId");
        Integer soleAgrtInfoId = paramsJSON.getInteger("soleAgrtInfoId");
        JSONArray selectRowList = paramsJSON.getJSONArray("selectRowList");
        Assert.notNull(soleAgrtInfoId, "独家协议信息主键不能为空");
        LOGGER.info("参数soleAgrtInfoId: {}",soleAgrtInfoId);
        TtaSoleAgrtInfoEntity_HI soleAgrtInfoEntity = ttaSoleAgrtInfoDAO_HI.getById(soleAgrtInfoId);
        StringBuffer sql = new StringBuffer(TtaSoleItemEntity_HI_RO.QUERY_SOLE_ITEM);
        sql.append(" and tsi.sole_agrt_info_id =:soleAgrtInfoId");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("soleAgrtInfoId",soleAgrtInfoId);
        List<TtaSoleItemEntity_HI_RO> daoHiRoList = ttaSoleItemDAO_HI_RO.findList(sql, paramMap);
        List<TtaSoleItemEntity_HI> list = new ArrayList<>();
        for (int i = 0; i < selectRowList.size(); i++) {
            JSONObject jsonObject = selectRowList.getJSONObject(i);
            TtaSoleItemEntity_HI entity = SaafToolUtils.setEntity(TtaSoleItemEntity_HI.class, jsonObject, ttaSoleItemDAO_HI, userId);
            if (SaafToolUtils.isNullOrEmpty(entity.getSoleAgrtInfoId()) || SaafToolUtils.isNullOrEmpty(soleAgrtHId)) {
                entity.setSoleAgrtInfoId(soleAgrtInfoId);
                entity.setSoleAgrtHId(soleAgrtHId);
            }
            entity.setBarCode(jsonObject.getString("upc"));
            entity.setItemCode(jsonObject.getString("itemNbr"));
            entity.setItemName(jsonObject.getString("itemDescCn"));
            entity.setGroupName(jsonObject.getString("groupDesc"));
            for (TtaSoleItemEntity_HI_RO entityHiRo : daoHiRoList) {
                //item编号已存在,提示
                if (entity.getVendorNbr().equals(entityHiRo.getVendorNbr()) && entity.getGroupCode().equals(entityHiRo.getGroupCode())
                        && entity.getDeptCode().equals(entityHiRo.getDeptCode())&& entity.getBrandCn().equals(entityHiRo.getBrandCn())
                        && entity.getBrandEn().equals(entityHiRo.getBrandEn()) && entity.getItemCode().equals(entityHiRo.getItemCode())) {
                    throw new IllegalArgumentException("已存在相同的ITEM[#],保存失败".replace("#",entity.getItemCode()));
                }
            }
            ttaSoleItemDAO_HI.saveOrUpdate(entity);
            list.add(entity);
        }
        return list;
    }

    @Override
    public void saveOrUpdteBySingle(JSONObject jsonObject, int userId) throws Exception {
        Integer soleAgrtHId = jsonObject.getInteger("soleAgrtHId");
        Integer soleAgrtInfoId = jsonObject.getInteger("soleAgrtInfoId");
        JSONObject soleItem = jsonObject.getJSONObject("soleItem");
        Assert.notNull(soleAgrtHId,"独家协议标准单据未保存,请先保存再进行操作");
        Assert.notNull(soleAgrtInfoId,"请先选择一行独家协议信息数据");
        StringBuffer sql = new StringBuffer(TtaSoleItemEntity_HI_RO.QUERY_SOLE_ITEM);
        sql.append(" and tsi.sole_agrt_info_id =:soleAgrtInfoId");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("soleAgrtInfoId",soleAgrtInfoId);
        List<TtaSoleItemEntity_HI_RO> daoHiRoList = ttaSoleItemDAO_HI_RO.findList(sql, paramMap);
        TtaSoleItemEntity_HI entityHi = SaafToolUtils.setEntity(TtaSoleItemEntity_HI.class, soleItem, ttaSoleItemDAO_HI, userId);
        entityHi.setSoleAgrtInfoId(soleAgrtInfoId);
        entityHi.setSoleAgrtHId(soleAgrtHId);
        for (TtaSoleItemEntity_HI_RO itemEntityHiRo : daoHiRoList) {
            //item编号已存在,提示
            if (entityHi.getVendorNbr().equals(itemEntityHiRo.getVendorNbr()) && entityHi.getGroupCode().equals(itemEntityHiRo.getGroupCode())
                    && entityHi.getDeptCode().equals(itemEntityHiRo.getDeptCode())&& entityHi.getBrandCn().equals(itemEntityHiRo.getBrandCn())
                    && entityHi.getBrandEn().equals(itemEntityHiRo.getBrandEn()) && entityHi.getItemCode().equals(itemEntityHiRo.getItemCode())
                    && entityHi.getBarCode().equals(itemEntityHiRo.getBarCode())) {
                throw new IllegalArgumentException("已存在相同的ITEM[#],货品条码[@],不能再次添加".replace("#",entityHi.getItemCode())
                        .replace("@",entityHi.getBarCode()));
            }
        }
        if (StringUtils.isBlank(entityHi.getItemCode())){
            entityHi.setItemCode("NEW");
        }
        ttaSoleItemDAO_HI.saveOrUpdate(entityHi);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public TtaSoleItemEntity_HI  deleteSoleItemById(Integer id) throws Exception {
        TtaSoleItemEntity_HI daoHiById = ttaSoleItemDAO_HI.getById(id);
        if (daoHiById == null){
            throw new IllegalArgumentException("你选择的数据不存在,不能删除");
        }
        ttaSoleItemDAO_HI.delete(daoHiById);
        return daoHiById;
    }

    /**
     * 查询
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return
     */
    @Override
    public Pagination<TtaSoleItemEntity_HI_RO> findSoleItem(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        StringBuffer sb = new StringBuffer(TtaSoleItemEntity_HI_RO.QUERY_SOLE_ITEM);
        queryParamMap.put("soleAgrtInfoId",queryParamJSON.getInteger("soleAgrtInfoId"));
        sb.append(" and tsi.sole_agrt_info_id =:soleAgrtInfoId");
        SaafToolUtils.changeQuerySort(queryParamJSON, sb, "tsi.sole_item_id desc", false);
        Pagination<TtaSoleItemEntity_HI_RO> findListResult = ttaSoleItemDAO_HI_RO.findPagination(sb, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }


    @Override
    public List<TtaSoleItemEntity_HI> saveBatchSoleItem(JSONObject paramJSON, Integer userId) throws Exception{
        Integer soleAgrtInfoId = paramJSON.getInteger("soleAgrtInfoId");
        Assert.notNull(soleAgrtInfoId,"独家协议信息主键缺失,保存失败");
        LOGGER.info("独家协议信息主键参数:{}",soleAgrtInfoId);
        JSONArray soleItemList = paramJSON.getJSONArray("soleItemList");
        List<TtaSoleItemEntity_HI> returnItemList = new ArrayList<>();
        for (int i = 0; i < soleItemList.size(); i++) {
            JSONObject jsonObject = soleItemList.getJSONObject(i);
            TtaSoleItemEntity_HI itemEntityHi = SaafToolUtils.setEntity(TtaSoleItemEntity_HI.class, jsonObject, ttaSoleItemDAO_HI, userId);
            itemEntityHi.setLastUpdateDate(new Date());
            itemEntityHi.setLastUpdatedBy(userId);
            itemEntityHi.setLastUpdateLogin(userId);
            itemEntityHi.setOperatorUserId(userId);
            returnItemList.add(itemEntityHi);
        }
        ttaSoleItemDAO_HI.saveOrUpdateAll(returnItemList);
        return returnItemList;
    }

    @Override
    public Pagination<TtaItemEntity_HI_RO> findItem(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        Assert.notNull(queryParamJSON.getInteger("soleAgrtInfoId"),"请先选中一行独家协议信息");
        LOGGER.info("ITEM明细新增时的soleAgrtInfoId[#]".replace("#",queryParamJSON.getInteger("soleAgrtInfoId").toString()) );
        TtaSoleAgrtInfoEntity_HI entityHi = ttaSoleAgrtInfoDAO_HI.getById(queryParamJSON.getInteger("soleAgrtInfoId"));
        //String vendorNbr = entityHi.getSupplierCode();
        Integer proposalId = entityHi.getProposalId();
        String orgCode = entityHi.getOrgCode();
        String soleBrandCn = entityHi.getSoleBrandCn();
        String[] orgCodeSplit = orgCode.split(",");
        String joinOrgCode = "'" + StringUtils.join(orgCodeSplit, "','") + "'";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaItemEntity_HI_RO.getTtaItemInfoNoExist(proposalId,soleBrandCn,joinOrgCode,entityHi.getSoleAgrtInfoId()));
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.item_Id", "itemId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.status", "status", sql, paramsMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.item_Nbr", "itemNbr", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.item_Desc_Cn", "itemDescCn", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.item_Desc_En", "itemDescEn", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.group_code", "groupCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.group_desc", "groupDesc", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.brand_cn", "brandCn", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.brand_en", "brandEn", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.dept_code", "deptCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.dept_desc", "deptDesc", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.vendor_nbr", "vendorNbr", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tiuv.upc", "upc", sql, paramsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tiuv.dept_code,tiuv.item_Nbr", false);
        Pagination<TtaItemEntity_HI_RO> findList = ttaItemDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public int findSoleItemSingal(JSONObject paramsJson, Integer userId) {
        Integer soleAgrtHId = paramsJson.getInteger("soleAgrtHId");
        List<Map<String, Object>> mapList = ttaSoleItemDAO_HI.queryNamedSQLForList("select count(1) cou from tta_sole_item t where t.sole_agrt_h_id =" + soleAgrtHId, new HashMap<>());
        if (mapList != null && mapList.size() > 0) {
            BigDecimal cou= (BigDecimal)mapList.get(0).get("COU");
            return cou.intValue();
        }
        return 0;
    }

    @Override
    public void saveAllItemData(JSONObject jsonObject, int userId) throws Exception {
        Integer soleAgrtHId = jsonObject.getInteger("soleAgrtHId");
        TtaSoleAgrtInfoEntity_HI entityHi = ttaSoleAgrtInfoDAO_HI.getById(jsonObject.getInteger("soleAgrtInfoId"));
        Integer proposalId = entityHi.getProposalId();
        String orgCode = entityHi.getOrgCode();
        String soleBrandCn = entityHi.getSoleBrandCn();
        String[] orgCodeSplit = orgCode.split(",");
        String joinOrgCode = "'" + StringUtils.join(orgCodeSplit, "','") + "'";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaItemEntity_HI_RO.getTtaItemInfoNoExist(proposalId,soleBrandCn,joinOrgCode,entityHi.getSoleAgrtInfoId()));
        SaafToolUtils.changeQuerySort(jsonObject, sql, "tiuv.dept_code,tiuv.item_Nbr", false);
        List<TtaItemEntity_HI_RO> list = ttaItemDAO_HI_RO.findList(sql, paramsMap);
        if (CollectionUtils.isNotEmpty(list)) {
            for (TtaItemEntity_HI_RO itemEntityHiRo : list) {
                TtaSoleItemEntity_HI soleItemEntityHi = new TtaSoleItemEntity_HI();
                soleItemEntityHi.setSoleAgrtHId(soleAgrtHId);
                soleItemEntityHi.setSoleAgrtInfoId(entityHi.getSoleAgrtInfoId());
                soleItemEntityHi.setVendorNbr(itemEntityHiRo.getVendorNbr().toString());
                soleItemEntityHi.setVendorName(itemEntityHiRo.getVendorName());
                soleItemEntityHi.setGroupCode(itemEntityHiRo.getGroupCode().toString());
                soleItemEntityHi.setGroupName(itemEntityHiRo.getGroupDesc());
                soleItemEntityHi.setDeptCode(itemEntityHiRo.getDeptCode());
                soleItemEntityHi.setDeptDesc(itemEntityHiRo.getDeptDesc());
                soleItemEntityHi.setBrandCn(itemEntityHiRo.getBrandCn());
                soleItemEntityHi.setBrandEn(itemEntityHiRo.getBrandEn());
                soleItemEntityHi.setItemCode(itemEntityHiRo.getItemNbr());
                soleItemEntityHi.setItemName(itemEntityHiRo.getItemDescCn());
                soleItemEntityHi.setBarCode(itemEntityHiRo.getUpc());

                soleItemEntityHi.setCreationDate(new Date());
                soleItemEntityHi.setCreatedBy(userId);
                soleItemEntityHi.setLastUpdateDate(new Date());
                soleItemEntityHi.setLastUpdatedBy(userId);
                soleItemEntityHi.setLastUpdateLogin(userId);
                soleItemEntityHi.setOperatorUserId(userId);
                ttaSoleItemDAO_HI.saveOrUpdate(soleItemEntityHi);
            }
        }
    }


    @Override
    public Pagination<TtaSoleItemEntity_HI_RO> findExclusiveItemReport(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(TtaSoleItemEntity_HI_RO.QUEEY_EXCLUSIVE_ITEM);
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(jsonObject, "t.vendor_nbr", "vendorNbr", sql, paramsMap, "like");
        SaafToolUtils.parperParam(jsonObject, "t.vendor_name", "vendorName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(jsonObject, "t.item_code", "itemCode", sql, paramsMap, "like");
        SaafToolUtils.parperParam(jsonObject, "t.item_name", "itemName", sql, paramsMap, "like");
        //SaafToolUtils.changeQuerySort(jsonObject,sql,"t.sole_agrt_code desc",false);
        Pagination<TtaSoleItemEntity_HI_RO> pagination = ttaSoleItemDAO_HI_RO.findPagination(sql,paramsMap,pageIndex,pageRows);
        return pagination;
    }
}
