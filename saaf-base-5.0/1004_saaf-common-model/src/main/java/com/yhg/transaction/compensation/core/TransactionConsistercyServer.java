package com.yhg.transaction.compensation.core;


import com.sie.saaf.common.model.entities.BaseCommonTransactionConfirmEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author huangtao
 * @createTime 2018年5月21日 11:35:00
 */
public abstract class TransactionConsistercyServer {


    private ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI;

    @Resource(name = "baseCommonTransactionConfirmDAO_HI")
    public void setBaseCommonTransactionConfirmDAO_HI(ViewObject<BaseCommonTransactionConfirmEntity_HI> baseCommonTransactionConfirmDAO_HI) {
        this.baseCommonTransactionConfirmDAO_HI = baseCommonTransactionConfirmDAO_HI;
    }

    /**
     *  消息幂等性校验
     * @param messageIndex
     * @param args
     * @return
     */
    public <T> T saveTransactionConsistercyServer(Integer messageIndex, Object ... args){
        Assert.notNull(messageIndex,"无法校验消息幂等性");
        BaseCommonTransactionConfirmEntity_HI instance=new BaseCommonTransactionConfirmEntity_HI();
        instance.setOperatorUserId(1);
        instance.setMessageIndex(messageIndex);
        baseCommonTransactionConfirmDAO_HI.save(instance);
        return transactionConsistercyAction( args);
    }

    /**
     *  子类重写此方法，实现消息幂等通过后的业务逻辑
     * @param arg
     * @return
     */
    public abstract <T> T  transactionConsistercyAction(Object... arg);


}
