package com.sie.saaf.common.model.inter;


import com.sie.saaf.common.model.entities.readonly.MessageTransactionManualEntity_HI_RO;

import java.util.List;

/**
 * @auther: huqitao 2018/7/26
 */
public interface IMessageTransactionManual {
    /**
     * 查询同时存在于补偿表和消费表的数据，并删除
     */
    void deleteBothExistTransactionAndManual() throws Exception;

    /**
     * 查询存在补偿表不存在消费表的数据
     * @return 存在补偿表不存在消费表的数据
     */
    List<MessageTransactionManualEntity_HI_RO> findExistManualNotExistTransaction();

    /**
     * 清除补偿表，清除确认表，重新推送至redis，重新推送至MQ补偿
     * @param messageTransManual 存在补偿表不存在消费表的对象
     * @param isClearMessConfirm 是否清除消息确认表
     */
    void saveCompensateStepOne(MessageTransactionManualEntity_HI_RO messageTransManual, Boolean isClearMessConfirm) throws Exception;

    /**
     * 重新推送至redis，重新推送至MQ补偿
     * @param messageIndex 消息Id
     * @param messageBody 消息体
     * @param requestUrl 请求地址
     * @param queueName 队列名称
     */
    void saveSend2RedisAndSend2Mq(Integer messageIndex, String messageBody, String requestUrl, String queueName) throws Exception;

    /**
     * 查询同时存在于消息确认表和消费表的数据
     * @param creationDate 消息确认的创建时间
     * @return 同时存在于消息确认表和消费表的数据
     */
    List<MessageTransactionManualEntity_HI_RO> findBothExistMessConfirmTransaction(String creationDate);

    /**
     * 清除清除确认表，MQ延时投递（删除对应的消费确认数据）
     * @param messConfirmAndTrans 同时存在消息确认表和消费表的对象
     */
    void deleteMessConfirmAndDelaySendMessage(MessageTransactionManualEntity_HI_RO messConfirmAndTrans) throws Exception;

    /**
     * 查询存在于消息确认表不存在消费表的数据
     * @param creationDate 消息确认的创建时间
     * @return 存在于消息确认表不存在消费表的数据
     */
    List<MessageTransactionManualEntity_HI_RO> findExistMessConfirmAndNotExistTransaction(String creationDate);

    /**
     * 查询存在于在消费表的数据
     * @param messageIndexStr (1,2,3,4,5)
     * @return 存在于在消费表的数据
     */
    List<MessageTransactionManualEntity_HI_RO> findExistTransaction(String messageIndexStr);
}
