package com.sie.saaf.base.dict.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.dao.BaseLookupTypesDAO_HI;
import com.sie.saaf.base.dict.model.entities.BaseLookupTypesEntity_HI;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypes;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.common.constant.CommonConstants;
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

import java.util.*;

/**
 * @author huangtao
 * @description 数据字典行表server
 * @createTime 2017年12月11日 19:55:36
 */
@Component("baseLookupValuesServer")
public class BaseLookupValuesServer extends BaseCommonServer<BaseLookupValuesEntity_HI> implements IBaseLookupValues {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseLookupValuesServer.class);
    @Autowired
    private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;
    @Autowired
    private BaseViewObject<BaseLookupValuesEntity_HI_RO> baseLookupValuesDAO_HI_RO;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IBaseLookupTypes baseLookupTypesServer;
//    @Autowired
//    private BaseLookupTypesDAO_HI baseLookupTypesDAO_HI;


    /**
     * @param parameter ｛
     *                  startDateActive      生效日期
     *                  parentLookupValuesId 父节点Id
     *                  versionNum           版本号
     *                  description          描述
     *                  lookupType           数据字典所属类型
     *                  enabledFlag          是否启用
     *                  creationDate         创建日期
     *                  lookupCode           数据字典编码
     *                  endDateActive        失效日期
     *                  buOrgId              BU所属组织Id
     *                  systemCode           系统编码
     *                  ｝
     * @param userId
     * @return BaseLookupValuesEntity_HI
     * @throws Exception
     */
    @Override
    public BaseLookupValuesEntity_HI saveCacheOrUpdateBaseLookupValue(JSONObject parameter, int userId) throws Exception {

        if (StringUtils.isNotBlank(parameter.getString("lookupValuesId")) && StringUtils.isBlank(parameter.getString("versionNum")))
            throw new IllegalArgumentException("缺少参数 versionNum");


        BaseLookupValuesEntity_HI instance = SaafToolUtils.setEntity(BaseLookupValuesEntity_HI.class, parameter, baseLookupValuesDAO_HI, userId);

        if (instance.getLookupValuesId() == null) {
            if (StringUtils.isBlank(instance.getLookupType()) || StringUtils.isBlank(instance.getLookupCode())) {
                throw new IllegalArgumentException("缺失参数 lookupType/lookupCode");
            }
            if (StringUtils.isBlank(instance.getSystemCode())) {
                new IllegalArgumentException("缺少参数 systemCode");
            }
            if (instance.getStartDateActive() == null) {
                instance.setStartDateActive(new Date());
            }
            if (CommonConstants.ENABLED_TRUE.equals(instance.getEnabledFlag()) == false) {
                instance.setEnabledFlag(CommonConstants.ENABLED_FALSE);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("lookupType", instance.getLookupType());
            map.put("lookupCode", instance.getLookupCode());
            map.put("systemCode", instance.getSystemCode());
            map.put("deleteFlag", CommonConstants.DELETE_FALSE);
            if (StringUtils.isNotBlank(instance.getBuOrgId()))
                map.put("buOrgId",instance.getBuOrgId());
            if (baseLookupValuesDAO_HI.findByProperty(map).size() > 0)
                throw new IllegalArgumentException("lookupCode 值已存在");
            instance.setDeleteFlag(CommonConstants.DELETE_FALSE);
        }
        if("".equals(parameter.getString("endDateActive"))){
            instance.setEndDateActive(null);
        }
        if("".equals(parameter.getString("startDateActive"))){
            instance.setStartDateActive(null);
        }
        baseLookupValuesDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    /**
     * @param lookupTypeParameter  ｛
     *                             parentTypeId          父节点id
     *                             lastUpdateDate        更新日期
     *                             versionNum            版本号
     *                             description           描述
     *                             lookupTypeId          主键ID
     *                             lookupType            数据字典类型
     *                             systemCode            系统编码
     *                             createdBy             创建人
     *                             meaning               说明
     *                             customizationLevel    系统级别或是用户级别
     *                             ｝
     * @param lookupValueParameter ｛
     *                             startDateActive      生效日期
     *                             parentLookupValuesId 父节点Id
     *                             versionNum           版本号
     *                             description          描述
     *                             lookupType           数据字典所属类型
     *                             enabledFlag          是否启用
     *                             creationDate         创建日期
     *                             lookupCode           数据字典编码
     *                             endDateActive        失效日期
     *                             buOrgId              BU所属组织Id
     *                             systemCode           系统编码
     *                             ｝
     * @param userId
     * @throws Exception
     * @description 同时修改/保存 数据字典头行表
     */
    @Override
    public JSONObject saveCacheOrUpdateALL(JSONObject lookupTypeParameter, JSONArray lookupValueParameter, int userId) throws Exception {
        JSONObject result=new JSONObject();
        String lookupType = "",systemCode = "";
        if (lookupTypeParameter != null){
            BaseLookupTypesEntity_HI instance= baseLookupTypesServer.saveOrUpdateBaseLookupType(lookupTypeParameter, userId);

            result.put("header",instance);
            lookupType = instance.getLookupType();
            systemCode = instance.getSystemCode();
        }
        //清除全量数据字典缓存
        jedisCluster.set(BaseLookupValuesEntity_HI_RO.DIC_LIST_KEY, "");
        if (lookupValueParameter == null)
            return result;

        List<BaseLookupValuesEntity_HI> list=new ArrayList<>();
        for (int i = 0; i < lookupValueParameter.size(); i++) {
            BaseLookupValuesEntity_HI instance= saveCacheOrUpdateBaseLookupValue(lookupValueParameter.getJSONObject(i), userId);
            list.add(instance);

            String idKey = ""+instance.getLookupValuesId();
            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID, idKey, JSON.toJSONString(instance));
        }
        String redisKey = lookupType + "_" + systemCode;
        jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE,redisKey, JSON.toJSONString(list));
        result.put("line",list);
        return result;
    }

    /**
     * @param queryParamJSON ｛
     *                       lookupType
     *                       buOrgId
     *                       ｝
     * @return
     * @description 查询数据字典
     */
    @Override
    public List<BaseLookupValuesEntity_HI_RO> findCacheDic(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer(BaseLookupValuesEntity_HI_RO.QUERY_DIC_SQL);
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.lookup_type", "lookupType", sql, queryParamJSON, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.bu_org_id", "buOrgId", sql, queryParamJSON, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.system_code", "systemCode", sql, queryParamJSON, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.lookup_code", "lookupCode", sql, queryParamJSON, "in");
        SaafToolUtils.parperParam(queryParamJSON, "type.lookup_type_id", "lookupTypeId", sql, queryParamJSON, "=");
        SaafToolUtils.parperParam(queryParamJSON, "type.parent_lookup_type_id", "parentLookupTypeId", sql, queryParamJSON, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.parent_lookup_values_id", "parentLookupValuesId", sql, queryParamJSON, "=");

        queryParamJSON.remove("ouId");
        queryParamJSON.remove("lookupCode");
        String dicList = jedisCluster.get(BaseLookupValuesEntity_HI_RO.DIC_LIST_KEY);
        List<BaseLookupValuesEntity_HI_RO> list = null;
        if (queryParamJSON.isEmpty()) {
            String jsonList = jedisCluster.get(BaseLookupValuesEntity_HI_RO.DIC_LIST_KEY);
            if (StringUtils.isNotBlank(jsonList)) {
                list = JSON.parseArray(jsonList, BaseLookupValuesEntity_HI_RO.class);
            } else {
                list = baseLookupValuesDAO_HI_RO.findList(sql, queryParamJSON);
                jedisCluster.set(BaseLookupValuesEntity_HI_RO.DIC_LIST_KEY, SaafToolUtils.toJson(list));
            }
        } else {
            list = baseLookupValuesDAO_HI_RO.findList(sql, queryParamJSON);
        }
        return list;
    }

    /**
     * @param queryParamJSON ｛
     *                       lookupType
     *                       meaning
     *                       lookupCode
     *                       parentLookupValuesId
     *                       enabledFlag
     *                       systemCode
     *                       ｝
     * @param pageIndex      当前页
     * @param pageRows       分页大小
     * @return Pagination<BaseLookupValuesEntity_HI>
     */
    @Override
    public String findBaseLookupValuesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BaseLookupValuesEntity_HI_RO.QUERY_PARENT_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperHbmParam(BaseLookupValuesEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
        Pagination<BaseLookupValuesEntity_HI_RO> findList = baseLookupValuesDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
        return JSON.toJSONString(findList);
    }

    /**
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @Override
    public Pagination<BaseLookupValuesEntity_HI_RO> findCurrentBaseLookupValuesPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BaseLookupValuesEntity_HI_RO.QUERY_DIC_SQL_LIST);
        Map<String, Object> paramsMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.lookup_type", "lookupType", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.bu_org_id", "buOrgId", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.system_code", "systemCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.meaning", "meaning", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.lookup_code", "lookupCode", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "type.lookup_type_id", "lookupTypeId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "type.parent_lookup_type_id", "parentLookupTypeId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "baseLookupValues.parent_lookup_values_id", "parentLookupValuesId", sql, paramsMap, "=");

        queryParamJSON.remove("ouId");
        queryParamJSON.remove("lookupCode");
        return baseLookupValuesDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
    }

    /**
     * 数据字典行表 删除
     *
     * @param id 主键
     */
    @Override
    public void deleteCache(Integer id) {
        if (id == null)
            return;
        BaseLookupValuesEntity_HI instance = baseLookupValuesDAO_HI.getById(id);
        if (instance == null)
            return;
        instance.setDeleteFlag(CommonConstants.DELETE_TRUE);
        baseLookupValuesDAO_HI.update(instance);

        //删除字典时同时更新redis缓存
        //instance.getLookupType();
        jedisCluster.hdel(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID,String.valueOf(id));

        JSONObject json = new JSONObject();
        json.put("lookupType",instance.getLookupType());
        List<BaseLookupValuesEntity_HI_RO> values = this.findCacheDic(json);
        String redisKey = instance.getLookupType() + "_" + instance.getSystemCode();
        jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE,redisKey, JSON.toJSONString(values));
    }

    public List<BaseLookupValuesEntity_HI_RO> findBaseLookupValues(String lookupType,String lookupCode){
      if (StringUtils.isBlank(lookupType)){
        return new ArrayList<BaseLookupValuesEntity_HI_RO>();
      }
      String sql = "SELECT \n" +
        "LOOKUP_CODE lookupCode,MEANING meaning\n" +
        "FROM BASE_LOOKUP_VALUES\n" +
        "WHERE 1=1\n"+
        "AND DELETE_FLAG = '0'\n" +
        "AND ENABLED_FLAG = 'Y'\n" +
        "AND trunc(nvl(START_DATE_ACTIVE,sysdate)) <= trunc(sysdate) \n" +
        "AND trunc(nvl(END_DATE_ACTIVE,sysdate))>=trunc(sysdate)\n" +
        "AND LOOKUP_TYPE =?";
      return StringUtils.isBlank(lookupCode) ? baseLookupValuesDAO_HI_RO.findList(sql,lookupType)
        : baseLookupValuesDAO_HI_RO.findList(sql + " AND upper(LOOKUP_CODE) =?",lookupType,lookupCode);
    }
}
