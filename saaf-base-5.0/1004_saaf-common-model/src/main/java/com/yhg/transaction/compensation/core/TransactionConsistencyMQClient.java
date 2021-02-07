package com.yhg.transaction.compensation.core;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionConsistencyMQClient extends TransactionConsistercyMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConsistencyMQClient.class);
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public boolean messageAction(RedisMessageContentBean redisMessageContentBean) {
        if (restTemplate == null)
            throw new ExceptionInInitializerError("restTemplate 初始化异常");
        if (StringUtils.isBlank(redisMessageContentBean.getRequestURL())){
            LOGGER.error("messageIndex[{}]服务请求地址为空",redisMessageContentBean.getMessageIndex());
            return false;
        }
        JSONObject params=JSONObject.parseObject(redisMessageContentBean.getMessageBody()).fluentPut("messageIndex",redisMessageContentBean.getMessageIndex());
        JSONObject resultJSON = SaafToolUtils.preaseServiceResultJSON(redisMessageContentBean.getRequestURL(), params);
        LOGGER.info("request result:{}", resultJSON);
        if (resultJSON==null){
            LOGGER.error("messageIndex[{}]服务请求失败",redisMessageContentBean.getMessageIndex());
            return false;
        }
        if ("S".equalsIgnoreCase(resultJSON.getString("status"))) {
            return true;
        }
        return false;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
