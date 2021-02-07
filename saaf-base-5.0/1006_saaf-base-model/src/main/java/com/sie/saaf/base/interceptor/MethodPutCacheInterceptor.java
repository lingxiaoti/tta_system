package com.sie.saaf.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.constant.CommonConstants;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author huangtao
 * @description 数据字典缓存切面
 * @createTime 2017年12月11日 20:41:45
 */
@Component
public class MethodPutCacheInterceptor implements MethodInterceptor, InitializingBean {
    private Logger log = LoggerFactory.getLogger(MethodPutCacheInterceptor.class);
    public static final String CACHE_KEY = "CACHE_KEY_LOOKUPTYPE";



    @Resource
    private JedisCluster jedisCluster;


    @Override
    public void afterPropertiesSet() throws Exception {

    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] arguments = methodInvocation.getArguments();

        log.info("Hold up method:{} ,args:{}", methodInvocation.getStaticPart(), arguments);
        boolean exception = false;
        //从参数中找出lookupType
        for (Object arg : arguments) {
            try {
                if ((arg instanceof JSONObject) == false)
                    continue;
                JSONObject jsonObject = JSON.parseObject(Objects.toString(arg, ""));
                if (jsonObject == null || jsonObject.isEmpty())
                    continue;

                if(jsonObject.containsKey("findWarehouseDict") && jsonObject.getBooleanValue("findWarehouseDict")){
                    //子库
                    String warehouseCode = jsonObject.getString("warehouseCode");
                    String val = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_WAREHOUSE_MAPPING_KEY,warehouseCode);

                    if(StringUtils.isBlank(val)){
                        log.info("Get warehouse result from database!");
                        exception = true;
                        Object result = methodInvocation.proceed();

                        if (result != null && result instanceof List && ((List) result).size() > 0) {
                            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_WAREHOUSE_MAPPING_KEY, warehouseCode, JSONObject.toJSONString(((List) result).get(0)));
                        }
                        return result;
                    }else{
                        log.info("Get warehouse result from cache!");
                        Class clazz = methodInvocation.getMethod().getReturnType();
                        if(List.class.getName().equals(clazz.getName())){
                            JSONArray array = new JSONArray().fluentAdd(JSONObject.parseObject(val));
                            return array.toJavaList(Object.class);
                        }
                        return JSON.parseObject(val);
                    }

                }else if(jsonObject.containsKey("findProductDict") && jsonObject.getBooleanValue("findProductDict")){
                    //产品，分为id与code查询
                    String itemCode = jsonObject.getString("itemCode");
                    String itemId = jsonObject.getString("itemId");

                    String val = "";
                    if(StringUtils.isNotBlank(itemId)){
                        val = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_ID_KEY,itemId);
                    }else if(StringUtils.isNotBlank(itemCode)){
                        val = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_CODE_KEY,itemCode);
                    }

                    if(StringUtils.isBlank(val)){
                        log.info("Get product result from database!");
                        exception = true;
                        Object result = methodInvocation.proceed();
                        if(result!=null && result instanceof List && ((List)result).size() > 0 && StringUtils.isNotBlank(JSONObject.toJSONString(((List) result).get(0)))){
                            String value = JSONObject.toJSONString(((List) result).get(0));
                            if(StringUtils.isNotBlank(itemId)) {
                                jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_ID_KEY, itemId, value);
                            } else {
                                jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_PRODUCT_INFO_BY_ITEM_CODE_KEY, itemCode, value);
                            }
                        }
                        return result;
                    }else{
                        log.info("Get product result from cache!");
                        Class clazz = methodInvocation.getMethod().getReturnType();
                        if(List.class.getName().equals(clazz.getName())){
                            JSONArray array = new JSONArray().fluentAdd(JSONObject.parseObject(val));
                            return array.toJavaList(Object.class);
                        }
                        return JSON.parseObject(val);
                    }
                }else if( jsonObject.containsKey("findOrgInvDict") && jsonObject.getBooleanValue("findOrgInvDict") && StringUtils.isNotBlank(jsonObject.getString("orgId"))){
                    //库存组织
                    String organizationId = jsonObject.getString("orgId");
                    String val = jedisCluster.hget(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_INV_VIEW_KEY,organizationId);
                    if(StringUtils.isBlank(val)){
                        log.info("Get organization inv result from database!");
                        exception = true;
                        Object result = methodInvocation.proceed();

                        if (result != null && result instanceof JSONArray && ((JSONArray) result).size() > 0) {
                            jedisCluster.hset(CommonConstants.RedisCacheKey.BASE_ORGANIZATION_INV_VIEW_KEY, organizationId, JSONObject.toJSONString(((List) result).get(0)));
                        }
                        return result;
                    }else{
                        log.info("Get organization inv result from cache!");
                        Class clazz = methodInvocation.getMethod().getReturnType();
                        if(JSONArray.class.getName().equals(clazz.getName())){
                            JSONArray array = new JSONArray().fluentAdd(JSONObject.parseObject(val));
                            return array;
                        }
                        return JSON.parseObject(val);
                    }

                }
            } catch (Exception e) {
                if (exception) {
                    throw new Exception(e);
                } else {
                    log.warn(e.getMessage());
                    return methodInvocation.proceed();
                }
            }
        }
        log.info("nothing to do in MethodPutCacheInterceptor");
        return methodInvocation.proceed();
    }

}
