package com.sie.saaf.app.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheSyncEvent;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限缓存更新事件监听
 * 在service层中，服务调用的公共的保存、删除方法时，使用
 * 异步执行
 */
@Component
@Async
public class PermissionCacheSyncListener implements ApplicationListener<PermissionCacheSyncEvent> {

    private static final Logger log = LoggerFactory.getLogger(PermissionCacheSyncListener.class);

    @Autowired
    private BaseAccreditServer baseAccreditServer;

    @Autowired
    private ProducerService producerService;

    @Override
    public void onApplicationEvent(PermissionCacheSyncEvent event) {
        String timestamp= StringUtils.isBlank(event.getTimeStamp())?System.currentTimeMillis()+"":event.getTimeStamp();
        log.info("权限缓存事件开始执行:{}", JSON.toJSON(event));
        Set<Integer> userIds = event.getUserIds();
        if (event.getUserIds() == null)
            userIds=new HashSet<>();
        ActiveMQQueue queueDestination_ = new ActiveMQQueue("permissionUpdaetQueue");
        userIds.addAll(baseAccreditServer.findRelatedUserId(event.getMenuIds(), event.getRoleIds(), event.getRespIds(), event.getResouceIds()));
        for (Integer id:userIds){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId",id);
            jsonObject.put("timestamp",timestamp);
            producerService.sendMessage(queueDestination_, jsonObject.toJSONString());
        }
    }
}
