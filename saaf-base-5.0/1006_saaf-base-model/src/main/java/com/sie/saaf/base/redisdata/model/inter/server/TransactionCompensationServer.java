package com.sie.saaf.base.redisdata.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.redisdata.model.inter.ITransactionCompensation;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import com.yhg.transaction.compensation.core.TransactionCompensationIdempotentCore;
import com.yhg.transaction.compensation.core.TransactionConsistencyCore;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ZhangJun
 * @createTime 2018-03-12 10:14
 * @description
 */
@Component("transactionCompensationServer")
public class TransactionCompensationServer extends BaseCommonServer<BaseManualSupplementEntity_HI> implements ITransactionCompensation {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCompensationServer.class);

    public static final String MQ_MESSAGE = "MQ_Message";
	public static final String TRANS_CORE_KEYS = "TRANS_CORE_KEYS";
	public static final String REDIS_KEY_NAME = "MQ_Trans_Cons_Core_";

	
//    @Autowired
//    private ViewObject<BaseManualSupplementEntity_HI> baseManualSupplementDAO_HI;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ViewObject<BaseCommonMessageConfirmEntity_HI> baseCommonMessageConfirmDAO_HI;
    
    private TransactionConsistencyCore transactionConsistencyCore;

    private TransactionCompensationIdempotentCore saveMessage2Redis;

    @Resource(name="saveMessage2Redis")
    public void setSaveMessage2Redis(TransactionCompensationIdempotentCore saveMessage2Redis) {
        this.saveMessage2Redis = saveMessage2Redis;
    }

    @Resource(name="transactionConsistencyCore")
	public void setTransactionConsistencyCore(TransactionConsistencyCore transactionConsistencyCore) {
		this.transactionConsistencyCore = transactionConsistencyCore;
	}

	/**
     * 获取MQ中需要补偿的队列列表
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 获取MQ中Redis队列列表
     */
    @Override
    public Pagination<RedisMessageContentBean> findMQMessageList(int pageIndex, int pageSize){
        Pagination<RedisMessageContentBean> redisbeanList = saveMessage2Redis.queryAllRedisTransactionDatas(pageIndex,pageSize,true);

        return redisbeanList;
    }

    /**
     * 获取MQ中需要补偿的所有队列列表
     * @return
     */
    @Override
    public List<RedisMessageContentBean> findMQMessageList(){
        return saveMessage2Redis.queryAllRedisTransactionDatas();
    }

    @Override
    public boolean automaticCompensation(JSONObject queryParamJSON){
        SaafToolUtils.validateJsonParms(queryParamJSON,"messageIndex");
        Integer messageIndex = queryParamJSON.getInteger("messageIndex");
        return this.compensationMessage(messageIndex);
    }

	/*@Override
	public JSONObject retryCompensation(JSONObject queryParamJSON){
		String queueName = queryParamJSON.getString("queueName");
		Long messageIdIndex = queryParamJSON.getLong("messageIdIndex");

		boolean result = saveMessage2Redis.compensationMessage(queueName,messageIdIndex);

		JSONObject resultJSON = new JSONObject();
		if(result) {
			resultJSON.put("status","S");
			resultJSON.put("msg","执行成功");
		}else{
			resultJSON.put("status","E");
			resultJSON.put("msg","该记录已被移除，可能已经被MQ消费者执行成功");
		}
		return resultJSON;
	}*/


    /**
     * 重新进行事务补偿，从Redis中取出数据推送到
     * @param queryParamJSON {
     *     queueName:队列名,
     *     messageIdIndex:索引
     * }
     * @author ZhangJun
     * @createTime 2018/3/15
     * @description 重新进行事务补偿，从Redis中取出数据推送到
     */
    @Override
    public JSONObject retryCompensation(JSONObject queryParamJSON){
        JSONObject resultJSON = new JSONObject();
        SaafToolUtils.validateJsonParms(queryParamJSON,"messageIndex");
		Integer messageIndex = queryParamJSON.getInteger("messageIndex");

        boolean result = this.compensationMessage(messageIndex);

        if (result) {
            resultJSON.put("status", "S");
            resultJSON.put("msg", "执行成功");
            resultJSON.put("count",1);
        } else {
            resultJSON.put("status", "E");
            resultJSON.put("msg", "该记录在消息确认表中不存在");
            resultJSON.put("count",0);
        }
        return resultJSON;
    }

    /**
     * huangminglin
     * 删除redis中的Mq队列消息
     * @param
     * @return
     */
    @Override
    public JSONObject deleteList(List<String> messageIndexs) {
        JSONObject result       = new JSONObject();
        for(int i = 0 ; i < messageIndexs.size() ; i++) {
            String messageIndex = messageIndexs.get(i);
            if (messageIndex != null) {
                String key = REDIS_KEY_NAME + messageIndex;
                jedisCluster.srem(TRANS_CORE_KEYS, key);
                jedisCluster.del(key);
            }
        }
        result.fluentPut("count",messageIndexs.size());
        return result;
    }


    private boolean compensationMessage(Integer indexValue) {

        StringBuilder      key                = new StringBuilder("MQ_Trans_Cons_Core_").append(indexValue);
        boolean            compensationResult = false;
        Map<String,String> map                = jedisCluster.hgetAll(key.toString());
        List<BaseCommonMessageConfirmEntity_HI> confirmEntity = baseCommonMessageConfirmDAO_HI.findByProperty("messageIndex", indexValue);
        if (confirmEntity != null && confirmEntity.size() > 0) {
            if(map != null && map.size() > 0) {
                RedisMessageContentBean baseTransactionCompensationBean   = getTransactionCompensationBean(map,RedisMessageContentBean.class);
                transactionConsistencyCore.sendRedisMessage2MQ(baseTransactionCompensationBean.getMessageIndex(), true);
            }
            compensationResult = true;
        }

        //判断执行的结果是否正确，如果错误则返回false.
        return compensationResult;
    }

    private static  <T>  T getTransactionCompensationBean(Map<String,String> map, Class<T> clzz){
        if (map==null)
            return null;
        try {
            T       obj    = clzz.newInstance();
            Field[] fields = clzz.getDeclaredFields();
            for (Field f:fields){
                String fieldName=f.getName();
                String str=map.get(fieldName);
                if (StringUtils.isBlank(str))
                    continue;
                String type=f.getGenericType().getTypeName();
                Object val=null;
                if (type.equals("java.lang.Long") || "long".equals(type)){
                    val=Long.valueOf(str);
                }else if ("java.lang.Integer".equals(type) || "int".equals(type)){
                    val=Integer.valueOf(str);
                }else if ("java.util.Date".equals(type)){
                    val= SaafDateUtils.string2UtilDate(str);
                }else if ("java.lang.String".equals(type)){
                    val=str;
                }
                if (val==null)
                    continue;
                BeanUtils.setProperty(obj,fieldName,val);
            }
            return obj;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 分批
     * @param redisCursor 游标
     * @return
     */
    @Override
    public JSONObject redisTransactionDatasByBatch(String redisCursor){
        return saveMessage2Redis.redisTransactionDatasByBatch(redisCursor);
    }
}
