package com.sie.saaf.base.redisdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.redisdata.model.inter.ITransactionCompensation;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.readonly.MessageTransactionManualEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IBaseCommonMessageConfirm;
import com.sie.saaf.common.model.inter.IMessageTransactionManual;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import com.yhg.transaction.compensation.core.TransactionConsistencyCore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MQ事务补偿服务，针对MQ在Redis中的消息，如果报错，则进行补偿，直接从Redis中取出数据再推送到MQ中
 *
 * @author ZhangJun
 * @createTime 2018-03-12 10:13
 * @description
 */
@RestController
@RequestMapping("/transactionCompensationService")
public class TransactionCompensationService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionCompensationService.class);
    private static final int beforeCreationDate = -7; //7天之前的创建时间

    @Autowired
    private ITransactionCompensation transactionCompensationServer;
    @Autowired
    private TransactionConsistencyCore transactionConsistencyCore;
    @Autowired
    private IBaseCommonMessageConfirm baseCommonMessageConfirmServer;
    @Autowired
    private IMessageTransactionManual messageTransactionManualServer;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return transactionCompensationServer;
    }

    public String exectScript(String script, String key, String value) {
        Object eval = this.jedisCluster.eval(script, 1, new String[]{key, value});
        return SToolUtils.object2String(eval);
    }

    /**
     * 获取需要补偿的MQ队列列表
     *
     * @author ZhangJun
     * @createTime 2018/3/12
     * @description 获取事务列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findMQMessageList")
    public String findMQMessageList(@RequestParam(required = false) String params,
                                    @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            Pagination<RedisMessageContentBean> findList = this.transactionCompensationServer.findMQMessageList(pageIndex, pageRows);
            JSONObject result = (JSONObject) JSON.toJSON(findList);
            result.fluentPut(STATUS, SUCCESS_STATUS)
                    .fluentPut(MSG, SUCCESS_MSG);
            return result.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败", 0, null).toString();
        }
    }

    /**
     * 删除数据
     *
     * @param params 参数id
     *               {
     *               id:需要删除的数据Id，如果需要删除多个，则用;分隔
     *               }
     * @return
     * @author huangminglin
     * @creteTime 2017/12/15
     */
    @RequestMapping(method = RequestMethod.POST, value = "delete")
    @Override
    public String delete(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (queryParamJSON == null || !queryParamJSON.containsKey("messageIndex")) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 messageIndex ", 0, null).toString();
            }
            List<String> messageIndexs = new ArrayList<>();
            String[] messageIndexArr = queryParamJSON.getString("messageIndex").split(",");
            for (String s : messageIndexArr) {
                messageIndexs.add(s);
            }
            JSONObject resultMessage = this.transactionCompensationServer.deleteList(messageIndexs);
            resultMessage.fluentPut("status", "S")
                    .fluentPut("msg", "操作成功");
            return resultMessage.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "删除失败", 0, null).toString();
        }
    }

    /**
     * 重新进行事务补偿，从base_manual_supplement表中取出数据推送到
     *
     * @param params {
     *               queueName:队列名,
     *               messageIdIndex:索引
     *               }
     * @author ZhangJun
     * @createTime 2018/3/12
     * @description
     */
    @RequestMapping(method = RequestMethod.POST, value = "retryCompensation")
    public String retryCompensation(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            JSONObject json = this.transactionCompensationServer.retryCompensation(queryParamJSON);
            return SToolUtils.convertResultJSONObj(json.getString("status"), json.getString("msg"), json.getInteger("count"), null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * TiDB调度程序：自动进行事务补偿
     * 为了避免调度定时设置不合理，在上一请求未处理完，不允许重复处理
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "transactionCompensation")
    public String transactionCompensation(@RequestParam(required = false) String params) {
        String LOCK_STR = "LockTiDBTransComp";
        boolean releaseLock = true;
        try {
            String lockStatus = jedisCluster.get(LOCK_STR);
            if (StringUtils.isNotBlank(lockStatus)) {
                releaseLock = false;
                logger.info("上一请求" + lockStatus + "未处理完，等待下一轮处理");
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "上一请求" + lockStatus + "未处理完，等待下一轮处理", 0, null).toString();
            }
            jedisCluster.setex(LOCK_STR, 3600, SaafDateUtils.convertDateToString(new Date()));

            //1、关联查询 base_manual_supplement 和 base_common_txn_confirm，消息已经被消费,直接删除
            messageTransactionManualServer.deleteBothExistTransactionAndManual();

            //2、查询存在 base_manual_supplement 表，不存在 base_common_txn_confirm 表的数据，进行补偿
            List<MessageTransactionManualEntity_HI_RO> existManualNotExistTransList = messageTransactionManualServer.findExistManualNotExistTransaction();
            for (MessageTransactionManualEntity_HI_RO messageTransManual : existManualNotExistTransList) {
                try {
                    Integer messageIndex = messageTransManual.getMessageIndex();
                    byte[] messageBodyByte = messageTransManual.getMessageBody();
                    String messageBody = new String(messageBodyByte);
                    String requestUrl = messageTransManual.getRequestUrl();
                    String queueName = messageTransManual.getQueueName();
                    if (queueName != null && "aaTransTestQueue".equals(queueName)) {
                        JSONObject messageBodyJSON = JSON.parseObject(messageBody);
                        messageBodyJSON.put("testFlag", "N");
                        messageBody = JSON.toJSONString(messageBodyJSON);
                    }
                    messageTransactionManualServer.saveCompensateStepOne(messageTransManual, true);
                    messageTransactionManualServer.saveSend2RedisAndSend2Mq(messageIndex, messageBody, requestUrl, queueName);
                } catch (Exception e) {
                    logger.error("TiDB清除补偿表、确认表[{}]，重新推送至redis，重新推送至MQ补偿异常:{}", JSON.toJSONString(messageTransManual), e);
                }
            }

            //3、扫描7天前的消息确认表，关联查询 base_common_message_confirm 和 base_common_txn_confirm
            //存在，则删除消息确认表，消费确认表则推送至MQ(延迟删除)
            String creationDate = SaafDateUtils.convertDateToString(SaafDateUtils.getNextDay(new Date(), beforeCreationDate), "yyyy-MM-dd HH:mm:ss");
            List<MessageTransactionManualEntity_HI_RO> messConfirmAndTransList = messageTransactionManualServer.findBothExistMessConfirmTransaction(creationDate);
            for (MessageTransactionManualEntity_HI_RO messConfirmAndTrans : messConfirmAndTransList) {
                try {
                    messageTransactionManualServer.deleteMessConfirmAndDelaySendMessage(messConfirmAndTrans);
                } catch (Exception e) {
                    logger.error("TiDB删除消息确认表[{}]，消费确认表则推送至MQ异常:{}", JSON.toJSONString(messConfirmAndTrans), e);
                }
            }

            //4、扫描7天前的消息确认表，存在 base_common_message_confirm 不存 base_common_txn_confirm
            //将messageIndex集合作为参数，MQ异步处理（数据源oracle）
            List<MessageTransactionManualEntity_HI_RO> messConfirmAndNotExistTransList = messageTransactionManualServer.findExistMessConfirmAndNotExistTransaction(creationDate);
            if (messConfirmAndNotExistTransList.size() > 0) {
                String mqParams = JSON.toJSONString(messConfirmAndNotExistTransList);
                transactionConsistencyCore.sendMessage("oracleMessageConfirmQueue", mqParams);
            }

//            //5、分批清除redis相关数据
//            transactionConsistencyCore.scanClearRedisData();

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
        } catch (Exception e) {
            logger.error("TiDB自动进行事务补偿异常:{}", e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 0, null).toString();
        } finally {
            if (releaseLock) {
                jedisCluster.del(LOCK_STR);
            }
        }
    }

    /**
     * 分批清理事务补偿相关redisKey
     * 为了避免调度定时设置不合理，在上一请求未处理完，不允许重复处理
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "scanClearRedisData")
    public String scanClearRedisData(@RequestParam(required = false) String params) {
        String LOCK_STR = "LockScanClearRedisData";
        boolean releaseLock = true;
        try {
            String lockStatus = jedisCluster.get(LOCK_STR);
            if (StringUtils.isNotBlank(lockStatus)) {
                releaseLock = false;
                logger.info("上一请求" + lockStatus + "未处理完，等待下一轮处理");
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "上一请求" + lockStatus + "未处理完，等待下一轮处理", 0, null).toString();
            }
            jedisCluster.setex(LOCK_STR, 3600, SaafDateUtils.convertDateToString(new Date()));

            //5、分批清除redis相关数据
            transactionConsistencyCore.scanClearRedisData();

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
        } catch (Exception e) {
            logger.error("TiDB自动进行事务补偿异常:{}", e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 0, null).toString();
        } finally {
            if (releaseLock) {
                jedisCluster.del(LOCK_STR);
            }
        }
    }

    /**
     * 根据messageIndex删除消息确认表相关数据
     * 由Oracle端的消费者触发
     *
     * @param params json格式字符串
     * @return 删除结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteMessConfirm")
    public String deleteMessConfirm(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamsJSON = parseObject(params);
            SaafToolUtils.validateJsonParms(queryParamsJSON, "messageIndex");
            List<BaseCommonMessageConfirmEntity_HI> messageConfirmList = baseCommonMessageConfirmServer.findByProperty("messageIndex", queryParamsJSON.getInteger("messageIndex"));
            baseCommonMessageConfirmServer.deleteAll(messageConfirmList);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "删除成功", messageConfirmList.size(), messageConfirmList).toString();
        } catch (IllegalArgumentException e) {
            logger.error("参数:{}，失败原因{}-{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "操作失败", 0, null).toString();
        }
    }
}
