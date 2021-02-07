package com.sie.saaf.app.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheUpdateEvent;
import com.sie.saaf.base.user.model.inter.server.BaseAccreditServer;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 权限缓存更新事件监听
 *  事务提交后触发事件
 * 异步执行
 */
@Component
@Async
public class PermissionCacheUpdateListener  {

    private static final Logger log= LoggerFactory.getLogger(PermissionCacheUpdateListener.class);

    @Autowired
    private BaseAccreditServer baseAccreditServer;

    @Autowired
    private ProducerService producerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void hanldPermissionUpdate(PermissionCacheUpdateEvent event) {
        String timestamp= StringUtils.isBlank(event.getTimeStamp())?System.currentTimeMillis()+"":event.getTimeStamp();
        log.info("带事务-权限缓存事件开始执行:{}", JSON.toJSON(event));
        Set<Integer> userIds = event.getUserIds();
        if (event.getUserIds() == null)
            userIds = new HashSet<>();

        userIds.addAll(baseAccreditServer.findRelatedUserId(event.getMenuIds(), event.getRoleIds(), event.getRespIds(), event.getResouceIds()));
        ActiveMQQueue queueDestination_ = new ActiveMQQueue("permissionUpdaetQueue");
        for (Integer id:userIds){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId",id);
            jsonObject.put("timestamp",timestamp);
            producerService.sendMessage(queueDestination_, jsonObject.toJSONString());
        }
    }

}
