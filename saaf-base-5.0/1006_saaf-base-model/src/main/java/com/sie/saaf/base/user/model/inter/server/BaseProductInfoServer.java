package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.base.user.model.entities.BaseProductInfoEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoChannelOrg_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseChannelPrivilege;
import com.sie.saaf.base.user.model.inter.IBaseProductInfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseProductInfoServer")
public class BaseProductInfoServer extends BaseCommonServer<BaseProductInfoEntity_HI> implements IBaseProductInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductInfoServer.class);
//    @Autowired
//    private ViewObject<BaseProductInfoEntity_HI> baseProductInfoDAO_HI;
    @Autowired
    private BaseViewObject<BaseProductInfoEntity_HI_RO> baseProductInfoDAO_HI_RO;
    @Autowired
    private BaseViewObject<BaseProductInfoChannelOrg_HI_RO> baseProductInfoChannelOrgDAO_HI_RO;
    @Autowired
    private IBaseLookupValues baseLookupValuesServer;
//    @Autowired
//    private IBaseChannelPrivilege baseChannelPrivilegeServer;
//    @Autowired
//    private OracleTemplateServer oracleTemplateServer;
//    @Autowired
//    private JedisCluster jedisCluster;

    public BaseProductInfoServer() {
        super();
    }

    /**
     * 查询产品信息，关联渠道，组织
     * @author ZhangJun
     * @createTime 2018/2/25
     * @description 查询产品信息，关联渠道，组织
     */
    @Override
    public Pagination<BaseProductInfoChannelOrg_HI_RO> findProductInfoROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sb = new StringBuffer(BaseProductInfoChannelOrg_HI_RO.QUERY_PRODUCT_SQL);
        String itemName_like = queryParamJSON.getString("itemName_like");
        if(StringUtils.isNotEmpty(itemName_like)) {
            itemName_like = itemName_like.replaceAll(" ", "%");
            queryParamJSON.put("itemName_like",itemName_like);
        }

        Map<String,Object> queryParamMap = new HashMap<String,Object>();
//        SaafToolUtils.parperHbmParam(BaseProductInfoChannelOrg_HI_RO.class,queryParamJSON,sb,queryParamMap);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_code","itemCode",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_code","itemCode_like",sb,queryParamMap,"like",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_name","itemName",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_name","itemName_like",sb,queryParamMap,"like",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_type","itemType",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.item_type","itemType_like",sb,queryParamMap,"like",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.inner_item_code","innerItemCode",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.inner_item_code","innerItemCode_like",sb,queryParamMap,"like",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.is_valid","isValid",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.series","series",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseProductInfo.org_id","ouOrgId",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseChannel.channel_name","channelName",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseChannel.channel_name","channelName_like",sb,queryParamMap,"like",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseChannel.organization_id","organizationId",sb,queryParamMap,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"baseOrganization.org_name","organizationName",sb,queryParamMap,"like",false);

        // 权限校验
        if(!"N".equals(queryParamJSON.getString("isCheckout"))) {
            if (!"Y".equals(queryParamJSON.getString("varIsadmin"))) {
                JSONArray orgIds = queryParamJSON.getJSONArray("operationOrgIds");
                String orgId_in = StringUtils.join(orgIds, ",");
                if (StringUtils.isBlank(orgId_in)) {
                    orgId_in = "0";
                }
                queryParamJSON.put("orgId_in", orgId_in);
                SaafToolUtils.parperParam(queryParamJSON, "baseProductInfo.org_id", "orgId_in", sb, queryParamMap, "in", false);
            }
        }

        sb.append(" GROUP BY itemId");

        StringBuffer count_sb = new StringBuffer(" SELECT COUNT(baseProductInfo.product_id) \n");
        count_sb.append("\t ,baseProductInfo.ITEM_ID as itemId \n");
        Integer from = sb.indexOf("FROM");
        count_sb.append(sb.substring(from));
        count_sb = SaafToolUtils.getSqlCountString(count_sb);
        Pagination<BaseProductInfoChannelOrg_HI_RO> findList = baseProductInfoChannelOrgDAO_HI_RO.findPagination(sb,count_sb,queryParamMap,pageIndex,pageRows);


        List<BaseProductInfoChannelOrg_HI_RO> datas = findList.getData();
        if(datas!=null && !datas.isEmpty()) {
            JSONObject paramsJSON = new JSONObject();
            paramsJSON.put("lookupType", "BASE_OU");
            List lookupValues = baseLookupValuesServer.findCacheDic(paramsJSON);
            JSONObject ouValues = new JSONObject();
            if (lookupValues != null && !lookupValues.isEmpty()) {
                for (int i = 0; i < lookupValues.size(); i++) {
                    Object obj = lookupValues.get(i);
                    BaseLookupValuesEntity_HI_RO lookupValue = new BaseLookupValuesEntity_HI_RO();
                    if(obj instanceof JSONObject){
                        lookupValue = JSONObject.toJavaObject((JSONObject)obj,BaseLookupValuesEntity_HI_RO.class);
                    }else if(obj instanceof BaseLookupValuesEntity_HI_RO){
                        lookupValue = (BaseLookupValuesEntity_HI_RO)obj;
                    }
                    ouValues.put(lookupValue.getLookupCode(), lookupValue.getMeaning());
                }
            }

            for (BaseProductInfoChannelOrg_HI_RO d : datas) {
                d.setOuOrgName(ouValues.getString(String.valueOf(d.getOrgId())));
            }
        }

        return findList;
    }



    /**
     *
     * @param queryParamJSON {productCodes:产品编码集合}
     * @return
     */
    @Override
    public List<BaseProductInfoEntity_HI_RO> findBaseProductInfoEntities(JSONObject queryParamJSON) {
        StringBuffer sb = new StringBuffer(BaseProductInfoEntity_HI_RO.QUERY_PRODUCT_INFO_SQL);
        sb.append(" AND A.ITEM_CODE IN ("+queryParamJSON.getString("productCodes")+")");
        Map<String,Object> queryParamMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON,"A.ORGANIZATION_ID","organizationId",sb,queryParamMap,"=",false);
        List<BaseProductInfoEntity_HI_RO> baseProductInfoEntities = baseProductInfoDAO_HI_RO.findList(sb, queryParamMap);
        return baseProductInfoEntities;
    }

    /**
     * @param queryParamJSON{productCodes:产品编码集合}
     * @return
     */
    @Override
    public List<BaseProductInfoEntity_HI_RO> findBaseProductInfoItemName(JSONObject queryParamJSON) {
        StringBuffer sb = new StringBuffer(BaseProductInfoEntity_HI_RO.QUERY_PRODUCT_INFO_ITEMNAME_SQL);
        sb.append(" AND A.ITEM_CODE in ("+queryParamJSON.getString("productCodes")+")");
        List<BaseProductInfoEntity_HI_RO> baseProductInfoEntities = baseProductInfoDAO_HI_RO.findList(sb, new HashMap<>());
        return baseProductInfoEntities;
    }

    /**
     * @param queryParamJSON{productCodes:产品编码集合}
     * @return
     */
    @Override
    public List<BaseProductInfoEntity_HI_RO> findBaseProductInfoItemDesc(JSONObject queryParamJSON) {
        StringBuffer sb = new StringBuffer(BaseProductInfoEntity_HI_RO.QUERY_PRODUCT_INFO_ITEMDESC_SQL);
        sb.append(" AND A.ITEM_CODE IN  ("+queryParamJSON.getString("productCodes")+")");
        List<BaseProductInfoEntity_HI_RO> baseProductInfoEntities = baseProductInfoDAO_HI_RO.findList(sb, new HashMap<>());
        return baseProductInfoEntities;
    }

    @Override
    public List findCacheList(JSONObject queryParamJSON) {
        return super.findList(queryParamJSON);
    }

    /**
     * 同步物料信息
     * @author ZhangJun
     * @createTime 2018/3/14
     * @description 同步物料信息
     */
    @Override
    public JSONObject saveSyncProductInfo(JSONObject queryParamJSON){
        final String BASE_PRODUCT_LAST_SYNC_TIME = "BASE_PRODUCT_LAST_SYNC_TIME";
        JSONObject result = new JSONObject();
//        try {
//            boolean isFirstSync = false;
//            String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,BASE_PRODUCT_LAST_SYNC_TIME);
//            if(StringUtils.isBlank(lastSyncTime)){
//                isFirstSync = true;
//                lastSyncTime = "2000-01-01 00:00:00";
//            }
//            String sql = "select A.INVENTORY_ITEM_ID      AS ITEM_ID,\n" +
//                    "       A.ORGANIZATION_ID             AS ORGANIZATION_ID,\n" +
//                    "       A.SEGMENT1                    AS ITEM_CODE,\n" +
//                    "       A.CUSTOMER_ORDER_ENABLED_FLAG AS IS_VALID,\n" +
//                    "       A.ATTRIBUTE2                  as INNER_ITEM_CODE,\n" +
//                    "       A.DESCRIPTION                 as ITEM_NAME,\n" +
//                    "       A.DESCRIPTION                 as ITEM_DESC,\n" +
//                    "       A.ATTRIBUTE10                 as ITEM_ABB,\n" +
//                    "       A.ITEM_TYPE                   AS ITEM_TYPE,\n" +
//                    "       A.ATTRIBUTE1                  AS UNIT_,\n" +
//                    "       A.ATTRIBUTE4                  AS TRACK_BARCODE,\n" +
//                    "       A.LOT_CONTROL_CODE            AS LOT_CONTROL_CODE,\n" +
//                    "       A.ATTRIBUTE13                 AS BOX_UNIT,\n" +
//                    "       A.SHELF_LIFE_DAYS             AS SHELF_LIFE_MONTH," +
//                    "       A.ATTRIBUTE12                 AS TRAY_UNIT,\n" +
//                    "       A.ATTRIBUTE8                 AS SERIES\n" +
//                    "  from INV.MTL_SYSTEM_ITEMS_B A WHERE  A.Last_Update_Date >= TO_DATE('"+lastSyncTime+"','yyyy-mm-dd HH24:mi:ss')\n" +
//                    "  UNION\n" +
//                    "SELECT DISTINCT A.INVENTORY_ITEM_ID      AS ITEM_ID,\n" +
//                    "       A.ORGANIZATION_ID             AS ORGANIZATION_ID,\n" +
//                    "       A.SEGMENT1                    AS ITEM_CODE,\n" +
//                    "       A.CUSTOMER_ORDER_ENABLED_FLAG AS IS_VALID,\n" +
//                    "       A.ATTRIBUTE2                  as INNER_ITEM_CODE,\n" +
//                    "       A.DESCRIPTION                 as ITEM_NAME,\n" +
//                    "       A.DESCRIPTION                 as ITEM_DESC,\n" +
//                    "       A.ATTRIBUTE10                 as ITEM_ABB,\n" +
//                    "       A.ITEM_TYPE                   AS ITEM_TYPE,\n" +
//                    "       A.ATTRIBUTE1                  AS UNIT_,\n" +
//                    "       A.ATTRIBUTE4                  AS TRACK_BARCODE,\n" +
//                    "       A.LOT_CONTROL_CODE            AS LOT_CONTROL_CODE,\n" +
//                    "       A.ATTRIBUTE13                 AS BOX_UNIT,\n" +
//                    "       A.SHELF_LIFE_DAYS             AS SHELF_LIFE_MONTH," +
//                    "       A.ATTRIBUTE12                 AS TRAY_UNIT,\n" +
//                    "       A.ATTRIBUTE8                 AS SERIES\n" +
//                    "  from INV.MTL_SYSTEM_ITEMS_B A, MTL_UOM_CONVERSIONS B\n" +
//                    "  WHERE A.INVENTORY_ITEM_ID=B.INVENTORY_ITEM_ID \n" +
//                    "  AND B.Last_Update_Date >=TO_DATE('"+lastSyncTime+"','yyyy-mm-dd HH24:mi:ss')  AND B.disable_date IS NULL";
//            LOGGER.info("{}",sql);
//
//            int count = 0;
//            Map<String,Object> params = new HashMap<>();
//            List<JSONObject> products = oracleTemplateServer.findList(sql,params);
//            if(products!=null && !products.isEmpty()) {
//                LOGGER.info("取得产品数据{}条，开始进行入处理...", products.size());
//
//                List<Integer> itemIds = new ArrayList<>();
//                List<String> itemCodes = new ArrayList<>();
//                for (int i = 0; i < products.size(); i++) {
//                    Integer itemId = products.get(i).getInteger("ITEM_ID");
//                    String itemCode = products.get(i).getString("ITEM_CODE");
//                    itemIds.add(itemId);
//                    itemCodes.add(itemCode);
//                }
//
//                //查询OU组织入渠道编码
//                Map<String,JSONObject> orgIdChannelTypeMap = findOrgIdAndChannelType(itemCodes);
//
//                //取出现有产品表中数据
//                LOGGER.info("根据需要更新的ITEM_ID获取已有产品信息");
//                StringBuffer sb = new StringBuffer();
//                sb.append("from BaseProductInfoEntity_HI where 1=1 ");
//                Map<String,BaseProductInfoEntity_HI> productMap = new HashMap<>();
//                Map<String,Object> paramsMap = new HashMap<>();
//                if(!isFirstSync) {
//                    sb.append(" and itemId in (:itemId)");
//                    paramsMap.put("itemId", itemIds);
//                }
//                List<BaseProductInfoEntity_HI> findList = baseProductInfoDAO_HI.findList(sb,paramsMap);
//                for (BaseProductInfoEntity_HI entity:findList) {
//                    productMap.put(entity.getItemId()+"_"+entity.getOrganizationId(),entity);
//                }
//                LOGGER.info("获取现有产品信息完成");
//
//                List<BaseProductInfoEntity_HI> savesList = new ArrayList<>();
//                for (int i = 0; i < products.size(); i++) {
//                    JSONObject product = products.get(i);
//                    Integer itemId = product.getInteger("ITEM_ID");
//                    Integer organizationId = product.getInteger("ORGANIZATION_ID");
//                    String key = itemId+"_"+organizationId;
//
//                    BaseProductInfoEntity_HI entity = null;
//                    if(productMap.containsKey(key)){
//                        //取出旧数据
//                        entity = productMap.get(key);
//                        BaseProductInfoEntity_HI newEntity = JSON.toJavaObject(product,BaseProductInfoEntity_HI.class);
//                        SaafBeantUtils.copyUnionProperties(newEntity,entity);
//                    }else{
//                        //没有旧数据，将新数据转成JAVA OBJECT对象
//                        entity = JSON.toJavaObject(product,BaseProductInfoEntity_HI.class);
//                    }
//
//                    if(entity.getOrganizationId().intValue() != 101){
//                        JSONObject obj = orgIdChannelTypeMap.get(entity.getItemCode());
//                        if(obj!=null) {
//                            entity.setOrgId(obj.getInteger("ORG_ID"));
//                            entity.setChannelCode(obj.getString("CHANNEL_TYPE"));
//                        }else{
//                            LOGGER.info("不存在OU组织及渠道,{}",JSON.toJSONString(entity));
//                        }
//                    }
//                    entity.setOperatorUserId(1);
//                    savesList.add(entity);
//                }
//
//                if(!savesList.isEmpty()){
//                    for (BaseProductInfoEntity_HI entity : savesList) {
//                        this.saveOrUpdate(entity);
//                        if(entity.getOrganizationId().intValue() == 101) {
//                            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_CODE_KEY, entity.getItemCode(), JSON.toJSONString(entity));
//                            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_ID_KEY, String.valueOf(entity.getItemId()), JSON.toJSONString(entity));
//                        }
//                        count++;
//                    }
//                }
//            }else{
//                result.put("syncStatus","success");
//                result.put("syncMsg","当前未取得产品数据");
//                LOGGER.info("产品同步结果：{}",result.toJSONString());
//            }
//
//            //同步完成，更新redis最后更新时间
//            jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,BASE_PRODUCT_LAST_SYNC_TIME,SaafDateUtils.convertDateToString(new Date()));
//
//            result.put("syncStatus","success");
//            result.put("syncMsg","产品同步完成");
//            result.put("updateCount",count);
//        } catch (Exception e) {
//            LOGGER.error("",e);
//            result.put("syncStatus","fail");
//            result.put("syncMsg","产品同步出现异常:"+e.getMessage());
//        }
        return result;
    }

//    /**
//     * 查询oracle数据库中的渠道与OU组织
//     * @author ZhangJun
//     * @createTime 2018/3/14
//     * @description 查询oracle数据库中的渠道与OU组织
//     */
//    private Map<String,JSONObject> findOrgIdAndChannelType(List<String> itemCodes) throws SQLException {
//        LOGGER.info("获取渠道及OU组织信息");
//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT CP.ORG_ID,\n" +
//                "       CP.CHANNEL_TYPE,\n" +
//                "       CP.TRANSACTION_TYPE_ID INVENTORY_ITEM_ID,\n" +
//                "       CP.ITEM_CODE\n" +
//                "  FROM CUX.CUX_CDM_CHANNEL_PRIVILEGE CP\n" +
//                " WHERE CP.ACCESS_TYPE = '6'\n" +
//                "   AND VALID_FLAG = 'Y' ");
//
//        if(itemCodes!=null && !itemCodes.isEmpty()){
//            sb.append(" AND "+SaafToolUtils.buildLogicIN("CP.ITEM_CODE",itemCodes));
//            //sb.append(" AND CP.ITEM_CODE in ('"+StringUtils.join(itemCodes,"','")+"')");
//        }

//        Map<String,JSONObject> resultMap = new HashMap<>();
//        Map<String,Object> params = new HashMap<>();
//        List<JSONObject> list = oracleTemplateServer.findList(sb.toString(),params);
//        if(list!=null && !list.isEmpty()){
//            for (int i = 0; i < list.size(); i++) {
//                JSONObject obj = list.get(i);
//                resultMap.put(obj.getString("ITEM_CODE"),obj);
//            }
//        }
//        LOGGER.info("已成功获取渠道及OU组织信息{}条",resultMap.size());

//        return resultMap;
//    }
    /**
     * 交易汇总相关查询（仓库发货确认）
     * @author yuzhenli
     * @description 通过unitTraQuantity+itemCode获取boxUnit
     */
    @Override
    public BaseProductInfoEntity_HI_RO getBoxUnit(JSONObject queryParamJSON) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("unitTraQuantity",queryParamJSON.getString("unitTraQuantity"));
        conditionMap.put("itemCode",queryParamJSON.getString("itemCode"));
        List<BaseProductInfoEntity_HI_RO> result = baseProductInfoDAO_HI_RO.findList(BaseProductInfoEntity_HI_RO.QUERY_BOX_UNIT, conditionMap);
        if (!result.isEmpty()){
            return result.get(0);
        }
        return null;
    }


    /**
     * 部分盘点查询品规信息
     *
     * @param queryParamJSON｛ orgId:          部门
     *                        channelCode:    渠道
     *                        itemCode:       品规编码
     *                        ｝
     * @return
     */
    @Override
    public Pagination<BaseProductInfoEntity_HI_RO> findItemInfo(JSONObject queryParamJSON,Integer pageIndex,Integer pageSize) {
        SaafToolUtils.validateJsonParms(queryParamJSON,"orgId","channelCode");
        StringBuffer sql=new StringBuffer(BaseProductInfoEntity_HI_RO.QUERY_PRODUCT_ITEM_INFO);
        Map<String,Object> map=new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseProductInfoEntity_HI_RO.class,queryParamJSON,sql,map);
        return baseProductInfoDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), map, pageIndex, pageSize);
    }

}
