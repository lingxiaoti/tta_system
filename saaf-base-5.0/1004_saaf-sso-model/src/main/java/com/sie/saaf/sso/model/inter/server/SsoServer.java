package com.sie.saaf.sso.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.yhg.base.utils.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.Objects;
import java.util.UUID;

@Component("ssoServer")
public class SsoServer implements ISsoServer {
    private static final Logger log = LoggerFactory.getLogger(SsoServer.class);


    @Autowired
    private JedisCluster jedisCluster;

    public static final String SUPPLIER_TYPE = "60";

    /**
     * PLM：当userSessionBean.userType为供应商(SUPPLIER_TYPE)，redis添加key值用于用户登录信息校验
     * @since 2019-11-08
     * @param userSessionBean
     * @return
     * @description 获取cookie
     *
     */
    @Override
    public String setCookie(UserSessionBean userSessionBean) {
        Assert.notNull(userSessionBean, "userSessionBean is null");
        String uuid = UUID.randomUUID().toString();
        String key = DigestUtils.md5(uuid + userSessionBean.getUserName());
        key=userSessionBean.getUserId()+System.nanoTime()+key;
        key=key.substring(0,25);
        userSessionBean.setCertificate(key);
        JSONObject jsonObject= (JSONObject) JSON.toJSON(userSessionBean);
        jsonObject.remove("oprRespMap");
        jedisCluster.hset("cookie_" + key,"sessionInfo", jsonObject.toJSONString());
        if(SUPPLIER_TYPE.equals(userSessionBean.getUserType())){
            String previousCertification = jedisCluster.hget("supplier"+userSessionBean.getUserId(),"supplierCertificate");
            if(!SaafToolUtils.isNullOrEmpty(previousCertification)) {
                jedisCluster.del("cookie_" + previousCertification);
            }
            jedisCluster.hset("supplier"+userSessionBean.getUserId(),"supplierCertificate",userSessionBean.getCertificate());
        }
        if (!userSessionBean.isFromApp())
            jedisCluster.expire("cookie_" + key,CommonConstants.COOKIE_EXPIRED);
        return key;
    }

    @Override
    public void deleteCookie(UserSessionBean userSessionBean) {
        if (userSessionBean == null)
            return;
        if (StringUtils.isBlank(userSessionBean.getCertificate()))
            return;
        jedisCluster.del("cookie_" + userSessionBean.getCertificate());
    }

    /**
     * @param cookie
     * @return
     * @description 验证cookie 检查用户是否登录
     */
    @Override
    public boolean authCookie(String cookie) {
        String key = "cookie_" + cookie;
        log.info("authCookie入参key:{}", key);
        if (StringUtils.isBlank(cookie)) {
            return false;
        }
        boolean existsFlag = jedisCluster.exists(key);
        log.info("authCookie入参key:{}, 判断是否存在existsFlag:{}",key, existsFlag);
        if (!existsFlag) {
            return false;
        }
        UserSessionBean userSessionBean = getUserSession(cookie);
        String expire = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire");
        if (StringUtils.isBlank(expire) && !StringUtils.isNumeric(expire)) {
            jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire", "48");
            expire = "48";
        }
        jedisCluster.expire(key, userSessionBean.isFromApp() ? Integer.valueOf(expire) * 3600 : CommonConstants.COOKIE_EXPIRED);
        return true;
    }

    /**
     * @param cookie
     * @return
     * @description 通过cookie 获取usersession
     */
    @Override
    public UserSessionBean getUserSession(String cookie) {
        if (StringUtils.isBlank(cookie))
            return null;
        String key = "cookie_" + cookie;
        try {
            String val= jedisCluster.hget(key,"sessionInfo");
            if (StringUtils.isBlank(val))
                return null;
            UserSessionBean userSessionBean=JSON.parseObject(val,UserSessionBean.class);
            String expire = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire");
            if (StringUtils.isBlank(expire) && !StringUtils.isNumeric(expire)) {
                jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire", "48");
                expire = "48";
            }
            jedisCluster.expire(key, userSessionBean.isFromApp() ? Integer.valueOf(expire) * 3600 : CommonConstants.COOKIE_EXPIRED);
            return userSessionBean;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 校验内部请求签名
     * @param timestamp 时间戳  与服务器时间不能相差不超过10min
     * @param sign      签名
     * @return
     */
    @Override
    public  boolean validatePdaSign(String timestamp, String sign){
        if (StringUtils.isBlank(timestamp))
            return false;
        try {
            Long t = System.currentTimeMillis() - Long.valueOf(timestamp);
            t = t < 0 ? (-1 * t) : t;
            if (t > 10 * 60 * 1000)
                return false;
            Integer lastNum=Integer.valueOf(timestamp.substring(timestamp.length()-1));
            int first=lastNum%5+1;
            int count=0;
            for (int i=0;i<first;i++){
                int n= timestamp.charAt(i)- '0';
                count+=n;
            }
            int md5Times=count%4+1;
            String str=timestamp;
            while ((md5Times--)>0){
                str= DigestUtils.md5(str);
            }
            return Objects.equals(str,sign);
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return false;
    }


}
