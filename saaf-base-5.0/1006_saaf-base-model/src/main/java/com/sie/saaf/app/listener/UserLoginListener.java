package com.sie.saaf.app.listener;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.app.event.UserLoginEvent;
import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.base.user.model.inter.IBaseLoginLog;
import com.yhg.redis.framework.JedisClusterCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class UserLoginListener extends JedisClusterCore implements ApplicationListener<UserLoginEvent> {
    private static final Logger log= LoggerFactory.getLogger(UserLoginListener.class);

    @Autowired
    private IBaseLoginLog baseLoginLogServer;

    @Override
    public void onApplicationEvent(UserLoginEvent event) {
        log.info("开始执行登录历史记录事件,{}", JSON.toJSON(event));
        BaseLoginLogEntity_HI loginInfo=event.getLoginInfo();
        if (loginInfo==null)
            return;
        baseLoginLogServer.save(loginInfo);
    }
}
