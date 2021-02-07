package com.sie.saaf.common.util;

import com.sie.saaf.common.constant.CommonConstants;
import com.yhg.redis.framework.facotry.JedisClusterFactory;

import java.lang.reflect.Method;

public class CustomRedisConnection extends JedisClusterFactory {
    /**
     * 重写父类 setPassword（）
     */
    @Override
    public synchronized void setPassword(String password) {
        String dePassword = null;
        try {
            dePassword = DesUtil.AESDncode(CommonConstants.ENCODE_RULE,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setPassword(dePassword);//解密之后调用父类的setPassword();因为父类放中调用了一个私有变量，重写的方法不能完全代替父类方法
    }
}
