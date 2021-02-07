package com.sie.saaf.transaction.listener;

import com.sie.saaf.transaction.event.InitiativeTrasationEvent;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.core.TransactionToolUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;

/**
 * 用于监听主动方应用事务事件
 * author: duzh
 * date: 2018-06-12
 */
@Component
public class InitiativeTrasationListener extends JedisClusterCore {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InitiativeTrasationListener.class);

    /**
     * 当主动方应用事务提交后发送redis消息
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void hanldInitiativeCommit(InitiativeTrasationEvent event) {

        /**
         * 通过本地线程得到待发送的消息列表
         */
        Set<String> messageIds = RemoteServiceInvoke.getMessageIds();

        /**
         * 循环发送消息【调用MQ或rest服务】
         */
        for (String msgId : messageIds) {
            RemoteServiceInvoke.sendRedisMessage2MQ(Long.parseLong(msgId));
            logger.info("发送消息:{}",messageIds);
        }
        RemoteServiceInvoke.clearMessageIds();
    }


    /**
     * 当主动方应用事务回滚时清理redis消息
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void hanldInitiativeRollback(InitiativeTrasationEvent event) {
        logger.info("主动方应用事务回滚:{}", event.getName());

        /**
         * 通过本地线程得到待发送的消息列表
         */
        Set<String> messageIds = RemoteServiceInvoke.getMessageIds();

        /**
         *  删除保存在redis中的预发送消息
         */
        for (String msgId : messageIds) {
            StringBuilder key = new StringBuilder(RemoteServiceInvoke.REDIS_KEY_NAME + msgId);
            jedisCluster.del(key.toString());
            jedisCluster.del(TransactionToolUtils.LOCK_STR + msgId);
            jedisCluster.srem(RemoteServiceInvoke.TRANS_KEYS, key.toString());
        }
        RemoteServiceInvoke.clearMessageIds();
    }


}
