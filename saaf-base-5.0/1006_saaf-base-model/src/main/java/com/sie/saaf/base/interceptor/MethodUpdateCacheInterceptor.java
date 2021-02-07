package com.sie.saaf.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author huangtao
 * @description 数据字典缓存切面
 * @createTime 2017年12月11日 20:41:20
 */
@Component
public class MethodUpdateCacheInterceptor implements MethodInterceptor, InitializingBean {
    private Logger log = LoggerFactory.getLogger(MethodUpdateCacheInterceptor.class);

    @Resource
    private JedisCluster jedisCluster;




    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] arguments = methodInvocation.getArguments();
        log.info("Hold up method:{} ,args:{}", methodInvocation.getStaticPart(), arguments);
        Object result = methodInvocation.proceed();
        try {
            if (arguments == null || arguments.length == 0)
                return methodInvocation.proceed();
            for (Object arg : arguments) {
                //参数类型处理
                JSONObject jsonObject = new JSONObject();
                JSONArray array = new JSONArray();
                if (arg instanceof JSONObject) {
                    jsonObject = (JSONObject) arg;
                } else if (arg instanceof BaseLookupValuesEntity_HI) {
                    jsonObject = (JSONObject) JSON.toJSON(arg);
                } else if (arg instanceof JSONArray) {
                    array = (JSONArray) arg;
                } else if (arguments.length == 1 && arg instanceof Integer) {
                    jsonObject.put("lookupValuesId", arg);
                }
                //根据lookType 删除hash 结构redis缓存
                for (int i = 0; i < array.size(); i++) {
                    delCache(array.getJSONObject(i));
                    break;
                }
                delCache(jsonObject);
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return result;
    }


    private void delCache(JSONObject jsonObject) {
        if (StringUtils.isNotBlank(jsonObject.getString("lookupType"))) {
            jedisCluster.hdel(MethodPutCacheInterceptor.CACHE_KEY, jsonObject.getString("lookupType"));
            log.info("del cache {} with field {}", MethodPutCacheInterceptor.CACHE_KEY, jsonObject.getString("lookupType"));
            return;
        }
        if (StringUtils.isNotBlank(jsonObject.getString("lookupValuesId"))) {
            Map<String, String> map = jedisCluster.hgetAll(MethodPutCacheInterceptor.CACHE_KEY);
            Integer lookupValuesId = jsonObject.getInteger("lookupValuesId");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                String str = map.get(key);
                JSONArray array = JSON.parseArray(str);
                for (int i = 0; i < array.size(); i++) {
                    if (array.getJSONObject(i).getIntValue("lookupValuesId") == lookupValuesId) {
                        jedisCluster.hdel(MethodPutCacheInterceptor.CACHE_KEY, key);
                        log.info("del cache {} with field {}", MethodPutCacheInterceptor.CACHE_KEY, key);
                        break;
                    }
                }
            }
        }
    }
}
