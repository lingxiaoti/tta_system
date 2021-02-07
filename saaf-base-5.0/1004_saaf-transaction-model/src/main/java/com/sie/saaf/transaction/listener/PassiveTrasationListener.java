package com.sie.saaf.transaction.listener;

import com.sie.saaf.transaction.event.PassiveTrasationEvent;
import com.sie.saaf.transaction.invoke.RemoteServiceInvoke;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.core.TransactionToolUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


/**
 *
 * 用于监听主动方应用事务事件
 *  author: duzh
 *  date: 2018-06-12
 */
@Component
public class PassiveTrasationListener extends JedisClusterCore {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PassiveTrasationListener.class);

    /**
     * 当被动方应用事务提交后清理redis消息
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void hanldPassiveTrasationCommit(PassiveTrasationEvent event) {
        logger.info("触发被动方事件，{}",event.getSource());
        String msgId = event.getSource().toString();
        StringBuilder key = new StringBuilder(RemoteServiceInvoke.REDIS_KEY_NAME + msgId);
        jedisCluster.del(key.toString());
        jedisCluster.del(TransactionToolUtils.LOCK_STR + msgId);
        jedisCluster.srem(RemoteServiceInvoke.TRANS_KEYS,key.toString());
    }



}

