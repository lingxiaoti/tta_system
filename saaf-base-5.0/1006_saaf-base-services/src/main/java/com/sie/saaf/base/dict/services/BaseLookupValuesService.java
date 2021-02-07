package com.sie.saaf.base.dict.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupTypesEntity_HI;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypeValues;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypes;
import com.sie.saaf.base.dict.model.inter.IBaseLookupValues;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Objects;

/**
 * @author huangtao
 * @description 数据字典行表controller
 * @createTime 2017年12月11日 21:07:47
 */
@RestController
@RequestMapping("/baseLookupValuesService")
public class BaseLookupValuesService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseLookupValuesService.class);


    @Autowired
    private IBaseLookupValues baseLookupValuesServer;

    @Autowired
    private IBaseLookupTypeValues baseLookupTypeValuesServer;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IBaseLookupTypes baseLookupTypesServer;


    @RequestMapping(method = RequestMethod.POST, value = "findReasonSynDic")
    public String findReasonSynDic(@RequestParam String params){
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            List<BaseLookupValuesEntity_HI_RO> list = baseLookupTypeValuesServer.findLookupValuesEntities(jsonObject);
            return SToolUtils.convertResultJSONObj("S",SUCCESS_MSG,list.size(),list).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }



    /**
     * @param params    ｛
     *                  lookupType
     *                  meaning
     *                  lookupCode
     *                  parentLookupValuesId
     *                  enabledFlag
     *                  systemCode
     *                  ｝
     * @param pageIndex 当前页
     * @param pageRows  分页大小
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false) String pageIndex,
                       @RequestParam(required = false) String pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            String result = baseLookupValuesServer.findBaseLookupValuesPagination(jsonObject, Integer.valueOf(Objects.toString(pageIndex, "-1")), Integer.valueOf(Objects.toString(pageRows, "-1")));
            jsonObject = JSON.parseObject(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    /**
     * @param params ｛
     *               lookupType
     *               buOrgId
     *               ｝
     * @return
     * @description 查询数据字典
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDic")
    public String find(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            List<BaseLookupValuesEntity_HI_RO> list = baseLookupValuesServer.findCacheDic(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * @param params ｛
     *               startDateActive      生效日期
     *               parentLookupValuesId 父节点Id
     *               versionNum           版本号
     *               description          描述
     *               lookupType           数据字典所属类型
     *               enabledFlag          是否启用
     *               creationDate         创建日期
     *               lookupCode           数据字典编码
     *               endDateActive        失效日期
     *               buOrgId              BU所属组织Id
     *               systemCode           系统编码
     *               ｝
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = -1;
            JSONObject jsonObject = JSON.parseObject(params);
            BaseLookupValuesEntity_HI instance = baseLookupValuesServer.saveCacheOrUpdateBaseLookupValue(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    /**
     * @param params {
     *               value:[{
     *               startDateActive      生效日期
     *               parentLookupValuesId 父节点Id
     *               versionNum           版本号
     *               description          描述
     *               lookupType           数据字典所属类型
     *               enabledFlag          是否启用
     *               creationDate         创建日期
     *               lookupCode           数据字典编码
     *               endDateActive        失效日期
     *               buOrgId              BU所属组织Id
     *               systemCode           系统编码
     *               }],
     *               type:{
     *               parentTypeId          父节点id
     *               lastUpdateDate        更新日期
     *               versionNum            版本号
     *               description           描述
     *               lookupTypeId          主键ID
     *               lookupType            数据字典类型
     *               systemCode            系统编码
     *               createdBy             创建人
     *               meaning               说明
     *               customizationLevel    系统级别或是用户级别
     *               }
     *               }
     * @return
     * @throws Exception
     * @description 同时修改/保存 数据字典头行表
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateALL")
    public String saveOrUpdateALL(@RequestParam(required = true) String params) {
        try {
            int userId = -1;
            JSONObject paramsJson = JSON.parseObject(params);
            JSONObject jsonObject= baseLookupValuesServer.saveCacheOrUpdateALL(paramsJson.getJSONObject("type"), paramsJson.getJSONArray("value"), userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
        }  catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    /**
     * @param params-id 主键
     * @return
     * @description 数据字典行表
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            if (StringUtils.isBlank(params)) {
                return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
            }
            JSONObject jsonObject = JSON.parseObject(params);
            String[] ids = jsonObject.getString("id").split(",");
            for (String id : ids) {
                baseLookupValuesServer.deleteCache(Integer.parseInt(id));
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }
    /**
     * @param params-lookeup-code 快码值
     * @return
     * @description 数据字典行表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findList")
    public String findList(String params){
        return super.findList(params);
    }

    /**
     *
     * @author YangXiaowei
     * @creteTime 2018/4/2
     * @description
     */

    @RequestMapping(method = RequestMethod.POST, value = "findByParent")
    public String findByParent(String params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject = JSON.parseObject(params);
            List<BaseLookupValuesEntity_HI_RO> list = baseLookupTypeValuesServer.findByParent(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }
    
    @RequestMapping(method = RequestMethod.POST,value="saveData2Redis")
    public String saveData2Redis(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);

            List<BaseLookupTypesEntity_HI> types =  baseLookupTypesServer.findList(new JSONObject());
            for(BaseLookupTypesEntity_HI type : types){
                String lookupType = type.getLookupType();
                JSONObject json = new JSONObject();
                json.put("lookupType",lookupType);
                List<BaseLookupValuesEntity_HI_RO> values = baseLookupValuesServer.findCacheDic(json);

                String redisKey = lookupType + "_" + type.getSystemCode();

                jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPTYPE,redisKey, JSON.toJSONString(values));
                for(BaseLookupValuesEntity_HI_RO value : values) {
                    String idKey = ""+value.getLookupValuesId();
                    jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_LOOKUP_DATA_KEY_BY_LOOKUPVALUEID, idKey, JSON.toJSONString(value));
                }
            }

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
        }catch(Exception e){
            log.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params    ｛
     *                  lookupType
     *                  meaning
     *                  lookupCode
     *                  parentLookupValuesId
     *                  enabledFlag
     *                  systemCode
     *                  ｝
     * @param pageIndex 当前页
     * @param pageRows  分页大小
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);

            Pagination findList = baseLookupValuesServer.findCurrentBaseLookupValuesPagination(queryParamJSON, pageIndex, pageRows);

            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
