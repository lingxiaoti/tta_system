package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.base.user.model.entities.BaseWarehouseMappingEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseDelaer_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseWarehouseMapping_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseWarehouseMapping;
import com.sie.saaf.common.cache.server.IRedisCacheData;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.*;

@Component("baseWarehouseMappingServer")
public class BaseWarehouseMappingServer extends BaseCommonServer<BaseWarehouseMappingEntity_HI> implements IBaseWarehouseMapping {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseWarehouseMappingServer.class);
    @Autowired
    private ViewObject<BaseWarehouseMappingEntity_HI> baseWarehouseMappingDAO_HI;
    @Autowired
    private BaseViewObject<BaseWarehouseMapping_HI_RO> baseWarehouseMappingDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseDelaer_HI_RO> baseDelaerDAO_HI_RO;
    @Autowired
    private IBaseLookupValues baseLookupValuesServer;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;
//    @Autowired
//    private JedisCluster jedisCluster;
    @Autowired
    private IRedisCacheData redisCacheDataServer;

    public BaseWarehouseMappingServer() {
        super();
    }

    @Override
    public Pagination<BaseWarehouseMapping_HI_RO> findROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        // 权限控制
        JSONArray orgIds = queryParamJSON.getJSONArray("operationOrgIds");
        String orgId_in = StringUtils.join(orgIds,",");
        if(StringUtils.isBlank(orgId_in)){
            orgId_in = "0";
        }
        queryParamJSON.put("orgId_in",orgId_in);

        Pagination<BaseWarehouseMappingEntity_HI> findList = super.findPagination(queryParamJSON, pageIndex, pageRows);

        List<BaseWarehouseMappingEntity_HI> datas = findList.getData();

        Pagination<BaseWarehouseMapping_HI_RO> pagination = new Pagination<>(pageIndex,pageRows,findList.getCount());
        if(datas != null && !datas.isEmpty()) {
            List<BaseWarehouseMapping_HI_RO> newDatas = new ArrayList<>();
            JSONObject invTypes = new JSONObject();
            JSONObject paramsJSON = new JSONObject();
            paramsJSON.put("lookupType", "BRC_INV_TYPE");
            List lookupValues = baseLookupValuesServer.findCacheDic(paramsJSON);
            if (lookupValues != null && !lookupValues.isEmpty()) {

                for (int i = 0; i < lookupValues.size(); i++) {
                    Object obj = lookupValues.get(i);
                    BaseLookupValuesEntity_HI_RO lookupValue = new BaseLookupValuesEntity_HI_RO();
                    if(obj instanceof JSONObject){
                        lookupValue = JSONObject.toJavaObject((JSONObject)obj,BaseLookupValuesEntity_HI_RO.class);
                    }else if(obj instanceof BaseLookupValuesEntity_HI_RO){
                        lookupValue = (BaseLookupValuesEntity_HI_RO)obj;
                    }
                    invTypes.put(lookupValue.getLookupCode(), lookupValue.getMeaning());
                }
            }

            for(BaseWarehouseMappingEntity_HI data : datas) {
                BaseWarehouseMapping_HI_RO entity = new BaseWarehouseMapping_HI_RO();
                BeanUtils.copyProperties(data, entity);
                entity.setWarehouseTypeName(invTypes.getString(String.valueOf(entity.getWarehouseType())));
                newDatas.add(entity);
            }
            pagination.setData(newDatas);
        }else{
            pagination.setData(new ArrayList<BaseWarehouseMapping_HI_RO>());
        }


        return pagination;
    }

    /**
     * 设置默认字段
     * @param entity BaseWarehouseMappingEntity_HI
     * @author ZhangJun
     * @createTime 2017/12/27 23:21
     * @description 设置默认字段
     */
    @Override
    protected void setEntityDefaultValue(BaseWarehouseMappingEntity_HI entity) {
        if (StringUtils.isEmpty(entity.getMainFlag())) {
            entity.setMainFlag(CommonConstants.NO);
        }
        if (entity.getStartDateActive() == null) {
            entity.setStartDateActive(new Date());
        }
    }

    @Override
    public List<BaseWarehouseMappingEntity_HI> findBaseWarehouseMappingEntityInfo(JSONObject queryParamJSON){
        JSONObject resultJSONObject = new JSONObject();
        StringBuffer querySQLSB = new StringBuffer(" from BaseWarehouseMappingEntity_HI where 1 = 1 ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "WAREHOUSE_CODE", "oprInv", querySQLSB, paramMap, "=");
        List<BaseWarehouseMappingEntity_HI> baseWarehouseMappingEntitys = baseWarehouseMappingDAO_HI.findList(querySQLSB.toString(), paramMap);
        //                       SELECT A.ORGANIZATION_ID, -- 操作子库组织
        //                               A.CHANNEL_CODE  -- 操作子库渠道
        //                        FROM base_warehouse_mapping A
        //                        WHERE A.WAREHOUSE_CODE = '操作子库';    
        return baseWarehouseMappingEntitys;
    }

    @Override
    public List<BaseWarehouseMappingEntity_HI> findChildrenWarehouseMapping(JSONObject queryParamJSON) {
        SaafToolUtils.validateJsonParms(queryParamJSON,"warehouseCode");

        return baseWarehouseMappingDAO_HI.findByProperty("parentWarehouseCode",queryParamJSON.getString("warehouseCode"));
    }

    @Override
    public List<BaseWarehouseMappingEntity_HI> findBaseWarehouseMappingEntityInfoByCode(JSONObject queryParamJSON) {

        JSONObject resultJSONObject = new JSONObject();
        StringBuffer querySQLSB = new StringBuffer(" from BaseWarehouseMappingEntity_HI where 1 = 1 ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "ACCOUNT_CODE", "accountCode", querySQLSB, paramMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "WAREHOUSE_CODE", "warehouseCode", querySQLSB, paramMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "ACCOUNT_NAME", "accountName", querySQLSB, paramMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "WAREHOUSE_NAME", "warehouseName", querySQLSB, paramMap, "like");
        List<BaseWarehouseMappingEntity_HI> baseWarehouseMappingEntitys = baseWarehouseMappingDAO_HI.findList(querySQLSB.toString(), paramMap);
        return baseWarehouseMappingEntitys;
    }

    @Override
    public List<BaseWarehouseMappingEntity_HI> findCacheWarehouse(JSONObject queryParamJSON){
        return super.findList(queryParamJSON);
    }


    @Override
    public List<BaseDelaer_HI_RO> findDelaerList(JSONObject queryParamJSON){
        StringBuffer sb = new StringBuffer();
        sb.append(BaseDelaer_HI_RO.QUERY_DELARE);
        Map<String,Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON,"customer_id","accountId",sb,paramsMap,"=");
        SaafToolUtils.parperParam(queryParamJSON,"customer_id","accountId_in",sb,paramsMap,"in");
        SaafToolUtils.parperParam(queryParamJSON,"customer_number","accountCode",sb,paramsMap,"=");
        SaafToolUtils.parperParam(queryParamJSON,"customer_number","accountCode_in",sb,paramsMap,"in");
        SaafToolUtils.parperParam(queryParamJSON,"customer_number","accountName",sb,paramsMap,"like");
        List<BaseDelaer_HI_RO> findList = baseDelaerDAO_HI_RO.findList(sb,paramsMap);
        return findList;
    }

    /**
     * 门店盘点查询主子库
     * @param paramJSON
     * @return
     */
    @Override
    public List<BaseWarehouseMapping_HI_RO> findMainInv(JSONObject paramJSON){
        List<BaseWarehouseMapping_HI_RO> list= baseWarehouseMappingDAO_HI_RO.findList(BaseWarehouseMapping_HI_RO.QUERY,paramJSON);
       return list;
    }

    /**
     * 根据orgId获取可访问的子库编码
     * @author ZhangJun
     * @createTime 2018/4/17
     * @description 根据orgId获取可访问的子库编码
     */
    @Override
    public List<String> findWarehouseCodeByOrgId(JSONObject paramJSON) {
        Map<String,Object> paramsMap = new HashMap<>();
        StringBuffer sb = new StringBuffer(BaseWarehouseMapping_HI_RO.QUERY_WAREHOUSE_CODE_SQL);
        JSONArray orgIds = paramJSON.getJSONArray("operationOrgIds");
        String orgId_in = StringUtils.join(orgIds,",");
        if(StringUtils.isBlank(orgId_in)){
            orgId_in = "0";
        }
        paramJSON.put("orgId_in",orgId_in);
        SaafToolUtils.parperParam(paramJSON,"A.ORG_ID","orgId_in",sb,paramsMap,"in");

        List<String> rets = new ArrayList<>();
        List<BaseWarehouseMapping_HI_RO> findList = baseWarehouseMappingDAO_HI_RO.findList(sb,paramsMap);
        for(BaseWarehouseMapping_HI_RO entity : findList){
            rets.add(entity.getWarehouseCode());
        }
        return rets;
    }

    /**
     * 同步子库信息
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 同步子库信息
     */
    @Override
    public JSONObject saveSyncBaseWarehouseMapping(JSONObject queryParamJSON){
        final String BASE_WAREHOUSE_MAPPING_LAST_SYNC_TIME = "BASE_WAREHOUSE_MAPPING_LAST_SYNC_TIME";
        JSONObject result = new JSONObject();
//        try {
//            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,BASE_WAREHOUSE_MAPPING_LAST_SYNC_TIME);
//            if(StringUtils.isBlank(lastSyncTime)){
//                lastSyncTime = "2000-01-01 00:00:00";
//            }
//
//            String countsql = "select count(1) from BRC.BRC_INV_V3 WHERE LAST_UPDATE_DATE >=TO_DATE('"+lastSyncTime+"','yyyy-mm-dd HH24:mi:ss') ";
//            LOGGER.info("同步子库查询总数countsql: {}",countsql);
//            int dataCount = oracleTemplateServer.findCount(countsql);
//            int pagesize = 500;
//            int page = dataCount / pagesize + 1;
//
//            int startPage = 1;
//            if(queryParamJSON.containsKey("startPage")){
//                startPage = queryParamJSON.getIntValue("startPage");
//            }
//            if(queryParamJSON.containsKey("endPage")){
//                page = queryParamJSON.getIntValue("endPage");
//            }
//
//            int count = 0;
//            if(dataCount == 0){
//                result.put("syncStatus", "success");
//                result.put("syncMsg", "当前未取得子库数据");
//                LOGGER.info("子库同步结果：{}", result.toJSONString());
//            }else {
//                String sql = "select * from BRC.BRC_INV_V3 WHERE LAST_UPDATE_DATE >=TO_DATE('" + lastSyncTime + "','yyyy-mm-dd HH24:mi:ss') ORDER BY LAST_UPDATE_DATE,INV_CODE ASC";
//                LOGGER.info("同步子库查询sql:{}", sql);
//
//                //子库信息太多，使用分页查询导入数据，每次导入1000条（第一次导入时信息比较多）
//                for (int pageIndex = startPage; pageIndex <= page; pageIndex++) {
//                    LOGGER.info("分页查询子库，第{}页，共{}页",pageIndex,page);
//                    Pagination<JSONObject> products = oracleTemplateServer.findByProperty(sql, pageIndex, pagesize);
//                    List<JSONObject> invs = products.getData();
//
//                    List<String> invCodes = new ArrayList<>();
//                    for (int i = 0; i < invs.size(); i++) {
//                        String invCode = invs.get(i).getString("INV_CODE");
//                        invCodes.add(invCode);
//                    }
//
//                    Map<String,BaseWarehouseMappingEntity_HI> warehouseMap = new HashMap<>();
//                    JSONObject params = new JSONObject();
//                    params.put("warehouseCode_in",StringUtils.join(invCodes,","));
//                    List<BaseWarehouseMappingEntity_HI> warehouseMappings = findList(params);
//                    if(warehouseMappings!=null && !warehouseMappings.isEmpty()){
//                        for(BaseWarehouseMappingEntity_HI entity : warehouseMappings) {
//                            warehouseMap.put(entity.getWarehouseCode(),entity);
//                        }
//                    }
//
//                    List<BaseWarehouseMappingEntity_HI> savesList = new ArrayList<>();
//                    for (int i = 0; i < invs.size(); i++) {
//                        JSONObject inv = invs.get(i);
//                        String invCode = inv.getString("INV_CODE");
//
//                        BaseWarehouseMappingEntity_HI entity = null;
//                        if(warehouseMap.containsKey(invCode)){
//                            entity = warehouseMap.get(invCode);
//                            BaseWarehouseMappingEntity_HI newEntity = packingWarehouseMappingEntity(inv);
//                            SaafBeantUtils.copyUnionProperties(newEntity,entity);
//                            if(newEntity.getEndDateActive() == null){
//                                entity.setEndDateActive(null);
//                            }
//                        }else{
//                            entity = packingWarehouseMappingEntity(inv);
//                        }
//
//                        entity.setOperatorUserId(1);
//                        savesList.add(entity);
//                    }
//
//                    if(!savesList.isEmpty()){
//                        for(BaseWarehouseMappingEntity_HI entity : savesList){
//                            entity.setVersionNum(0);
//                            this.saveOrUpdate(entity);
//                            count++;
//                            String key = entity.getWarehouseCode();
//                            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_WAREHOUSE_MAPPING_KEY,key, JSON.toJSONString(entity));
//                        }
//                    }
//
//                }
//            }
//
//            //同步完成，更新redis最后更新时间
//            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,BASE_WAREHOUSE_MAPPING_LAST_SYNC_TIME, SaafDateUtils.convertDateToString(new Date()));
//
//            result.put("syncStatus","success");
//            result.put("syncMsg","子库同步完成");
//            result.put("updateCount",count);
//        } catch (Exception e) {
//            LOGGER.error("",e);
//            result.put("syncStatus","fail");
//            result.put("syncMsg","子库同步出现异常:"+e.getMessage());
//        }
        return result;
    }

//    private BaseWarehouseMappingEntity_HI packingWarehouseMappingEntity(JSONObject inv) {
//        BaseWarehouseMappingEntity_HI entity = new BaseWarehouseMappingEntity_HI();
//        entity.setOrganizationId(inv.getInteger("ORGANIZATION_ID"));
//        entity.setOrganizationName(inv.getString("ORGANIZATION_NAME"));
//        entity.setChannelCode(inv.getString("CHANNEL_CODE"));
//        entity.setWarehouseCode(inv.getString("INV_CODE"));
//        entity.setWarehouseName(inv.getString("INV_NAME"));
//        entity.setWarehouseType(inv.getInteger("INV_TYPE"));
//        entity.setParentWarehouseCode(inv.getString("SUPER_INV"));
//        entity.setAccountId(inv.getInteger("ACCOUNT_ID"));
//        entity.setAccountCode(inv.getString("ACCOUNT_NUMBER"));
//        entity.setAccountName(inv.getString("ACCOUNT_NAME"));
//        entity.setMainFlag(inv.getString("IS_MASTER_INV"));
//        entity.setProvincial(inv.getString("PROVINCE"));
//        entity.setMunicipal(inv.getString("CITY"));
//        entity.setAddressDetail(inv.getString("ADDRESS"));
//        entity.setCounty(null);
//        entity.setAddr(null);
//
//        Integer organizationId = inv.getInteger("ORGANIZATION_ID");
//        JSONObject orgInv = redisCacheDataServer.findOrganizationInv(organizationId);
//        entity.setOrgId(orgInv.getInteger("orgId"));
//        entity.setDefaultFlag(null);
//        entity.setDescription(null);
//        entity.setLongitude(null);
//        entity.setLatitude(null);
//        entity.setStartDateActive(SaafDateUtils.string2UtilDate("2010-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
//        entity.setEndDateActive(inv.getDate("DISABLE_DATE"));
//        entity.setAllowNegativeOnhand("N");
//        return entity;
//    }

}
