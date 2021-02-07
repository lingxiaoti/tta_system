package com.yhg.transaction.compensation.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.redis.framework.JedisClusterCore;
import com.yhg.transaction.compensation.beans.BaseTransactionCompensationBean;
import com.yhg.transaction.compensation.beans.IdempotentBean;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.ScanResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TransactionCompensationIdempotentCore extends JedisClusterCore {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCompensationIdempotentCore.class);
    private String idempotentKey = "idempotent_key";
    private String redisKeyName;
    private String mapIndexKeyName;
    private ProducerService producerService;
    //@Autowired
    private RestTemplate restTemplate;
    private static final String FIND_BUSINESS_KEY_FLAG_MATHOD_NAME = "findBusinessKeyFlag";
    private static final String TRANS_KEYS = "TRANS_KEYS";
    private static final String TRANS_CORE_KEYS = "TRANS_CORE_KEYS";

    @Autowired
    private ViewObject<BaseManualSupplementEntity_HI> baseManualSupplementDAO_HI;
    @Autowired
    private ViewObject<BaseCommonMessageConfirmEntity_HI> baseCommonMessageConfirmDAO_HI;

    private static Map<String, ActiveMQQueue> queueMapDescMap = new HashMap<String, ActiveMQQueue>();

    public TransactionCompensationIdempotentCore() {
        super();
    }

    /**
     * 根据队列的名字从Map中获取队列描述的对象
     * 如果map里面不存在改队列名字，重新创建一个队列描述对象保存到map中，并返回出去
     * @param queueDescName
     * @return
     */
    private static ActiveMQQueue trcFindQueueDesc(String queueDescName) {
        ActiveMQQueue queueDestination_ = null;
        if (queueMapDescMap.containsKey(queueDescName)) {
            queueDestination_ = queueMapDescMap.get(queueDescName);
        } else {
            queueDestination_ = new ActiveMQQueue(queueDescName);
            queueMapDescMap.put(queueDescName, queueDestination_);
        }
        return queueDestination_;
    }

    /**
     * 将消息推送到Redis
     * @param messageBody
     * @param messageType
     * @param serviceType
     * @param serviceURL
     * @return
     */
    public Long sendMessageBody2Redis(String messageBody, String queuenName, String messageType, String serviceType, String serviceURL) {
        Long indexValue = this.incr(mapIndexKeyName);
        BaseTransactionCompensationBean redisMessageBean = new BaseTransactionCompensationBean();
        redisMessageBean.setMessageBody(messageBody);
        redisMessageBean.setMessageCreateDate(new Date());
        redisMessageBean.setMessageIdIndex(indexValue);
        redisMessageBean.setMessageType(messageType);
        redisMessageBean.setServiceType(serviceType);
        redisMessageBean.setServiceUrl(serviceURL);
        redisMessageBean.setQueueName(queuenName);
        redisMessageBean.setStatus(0);
        StringBuilder key=new StringBuilder("ts_").append(queuenName).append("_").append(indexValue);
//        set(key.toString(), JSON.toJSONString(redisMessageBean));
        setHashMap(key.toString()+"", TransactionConsistencyCore.getTransactionCompensationMap(redisMessageBean));
        jedisCluster.sadd(TRANS_KEYS,key.toString());
        return indexValue;
    }

    /**
     * 将消息从Redis中推送到MQ
     * @return
     */
    public void sendMessageBodyFromRedis2MQ(String queuenName, Long indexValue, Boolean executeFlag){
        StringBuilder key=new StringBuilder("ts_").append(queuenName).append("_").append(indexValue);

        if(executeFlag){
            String redisMessageBeanStr =JSONObject.toJSONString(jedisCluster.hgetAll(key.toString()));
            Assert.isTrue(StringUtils.isNotBlank(redisMessageBeanStr),"hashkey[#]消息内容在redis中不存在".replace("#",key.toString()));
            ActiveMQQueue queueDestination_ = trcFindQueueDesc(queuenName);
            try {
                producerService.sendMessage(queueDestination_, redisMessageBeanStr);
                jedisCluster.hset(key.toString(),"status","1");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }else{
            del(key.toString());
            jedisCluster.srem(TRANS_KEYS,key.toString());
        }
    }

    /**
     * 从redis中获取所有再redis中准备补充的消息内容
     * @param pageIndex     当前页
     * @param pageSize      分页大小
     * @param cutMessgeBody 是否截取过长消息体
     * @return
     */
    public Pagination<RedisMessageContentBean> queryAllRedisTransactionDatas(int pageIndex, int pageSize, boolean cutMessgeBody) {
        if (pageSize<=0)
            pageSize=10;
        Set<String> keySet=jedisCluster.smembers(TRANS_CORE_KEYS);
        if (keySet==null || keySet.size()==0){
            Pagination pagination=new Pagination(pageIndex,pageSize,0);
            pagination.setData(Collections.emptyList());
            return pagination;
        }
        Pagination pagination = new Pagination(pageIndex,pageSize,keySet.size());
        List<String> keyList=new ArrayList<>(keySet);
        int start=Long.valueOf((pagination.getCurIndex()-1)*pagination.getPageSize()).intValue();
        int end=Long.valueOf((pagination.getCurIndex()*pagination.getPageSize()) - 1).intValue();
        end=end>=(keyList.size())?keyList.size()-1:end;
        List<RedisMessageContentBean> dataList=new LinkedList<>();
        for (int i = start; i <= end; i++) {
            Map<String, String> map = jedisCluster.hgetAll(keyList.get(i));
            RedisMessageContentBean redisMessageBean = getTransactionCompensationBean(map,RedisMessageContentBean.class);
            if (redisMessageBean == null || map.size() == 0) {
                jedisCluster.srem(TRANS_CORE_KEYS, keyList.get(i));
                if(end < keyList.size() - 1) {
                    end++;
                }
                continue;
            }
            Long messageIndex = redisMessageBean.getMessageIndex();
            List<BaseCommonMessageConfirmEntity_HI> confirmEntity = baseCommonMessageConfirmDAO_HI.findByProperty("messageIndex", messageIndex.intValue());
            if(confirmEntity != null && confirmEntity.size() == 0){
                Date creationDate = redisMessageBean.getCreationDate();
                if(creationDate != null) {
                    Date date = SToolUtils.addDate(6, Calendar.MINUTE, creationDate);
                    if (new Date().after(date)) {
                        jedisCluster.srem(TRANS_CORE_KEYS, keyList.get(i));
                        jedisCluster.del(keyList.get(i));
                        if (end < keyList.size() - 1) {
                            end++;
                        }
                        continue;
                    }
                }
            }

            if (cutMessgeBody && redisMessageBean.getMessageBody() != null && redisMessageBean.getMessageBody().length() > 500) {
                 redisMessageBean.setMessageBody(redisMessageBean.getMessageBody().substring(0, 499));
            }
            checkRedisMessageStatus(redisMessageBean);
            dataList.add(redisMessageBean);
        }
        pagination.setData(dataList);
        pagination.setCount(dataList.size());
        return  pagination;
    }

    /**
     * 从redis中获取所有再redis中准备补偿的消息内容
     * @return
     */
    public List<RedisMessageContentBean> queryAllRedisTransactionDatas() {
        List<RedisMessageContentBean> dataList = new LinkedList<>();
        Set<String> keySet = jedisCluster.smembers(TRANS_CORE_KEYS);
        if (keySet == null || keySet.size() == 0){
            return dataList;
        }
        List<String> keyList = new ArrayList<>(keySet);
        dataList = getRedisMessageContentList(dataList, keyList);
        return dataList;
    }

    /**
     *  检查消息的状态
     * @param redisMessageBean
     * @return
     */
    public void checkRedisMessageStatus(RedisMessageContentBean redisMessageBean){
        switch(redisMessageBean.getStatus()){
            case 0 :
                redisMessageBean.setStatusName("消息预发送状态");
                break;
            case 1 :
                redisMessageBean.setStatusName("消息已发送,自动补偿");
                break;
            case 2 :
                redisMessageBean.setStatusName("消息已发送,不自动补偿");
                break;
            case 3 :
                redisMessageBean.setStatusName("不自动补偿时，消费者消费失败");
                break;
        }
    }

    /**
     *  返回 redis中 准备事务补充 数量
     * @return
     */
    public int getRedisTransactionDatas(){
        Set<String> keySet=jedisCluster.smembers(TRANS_KEYS);
        return keySet.size();
    }


    /**
     *  返回 redis中 准备事务补充 所有key
     * @return
     */
    public Set<String> getRedisTransactionDataKeys(){
        return  jedisCluster.smembers(TRANS_KEYS);
    }


    /**
     * 返回 redis中准备补充的消息内容
     * @param key
     * @return
     */
    public BaseTransactionCompensationBean  getRedisTransactionDataByeKey(String key){
        Map<String,String> map=jedisCluster.hgetAll(key);
        if (map==null || map.size()==0){
            LOGGER.warn("redis中事务补偿数据为空!,key[{}]",key);
            jedisCluster.srem(TRANS_KEYS,key);
            return null;
        }
        BaseTransactionCompensationBean redisMessageBean = getTransactionCompensationBean(map,BaseTransactionCompensationBean.class);
        return redisMessageBean;
    }


    /**
     * 执行事务补偿
     * @param queueName
     * @param indexValue
     * @return
     */
    public boolean compensationMessage(String queueName, Long indexValue) {

        StringBuilder key=new StringBuilder("ts_").append(queueName).append("_").append(indexValue);
        boolean compensationResult = false;
        Map<String,String> map = jedisCluster.hgetAll(key.toString());
        BaseTransactionCompensationBean redisMessageBean = getTransactionCompensationBean(map,BaseTransactionCompensationBean.class);
        if (map==null || map.size()==0){
            LOGGER.warn("消息体内容不存在,key:{}",key);
            return compensationResult;
        }
        try {
            if (redisMessageBean.getMessageType() != null) {
                //如果待补偿的事务是webservice的方式则调用服务进行补偿
                if (redisMessageBean.getMessageType().equalsIgnoreCase("WEBSERVICE")) {
                    JSONObject messageBodyJSON = JSONObject.parseObject(redisMessageBean.getMessageBody());
                    JSONObject jsonResult = SaafToolUtils.restSpringCloudPost(redisMessageBean.getServiceUrl(), messageBodyJSON, null, restTemplate);
                    if (null != jsonResult && jsonResult.size() > 0) {
                        if (jsonResult.containsKey("statusCode") && jsonResult.getIntValue("statusCode") == 200) {
                            JSONObject jsonObject = jsonResult.getJSONObject(SToolUtils.DATA);
                            if (jsonObject.getString(SToolUtils.STATUS).equals("S")) {
                                removeMessageFromRedisMap(queueName, indexValue);
                                compensationResult = true;
                            }
                        }
                    }
                    //如果待补偿的事务是队列的方式则将消息重新放入队列入队
                } else if (redisMessageBean.getMessageType().equalsIgnoreCase("QUEUE")) {
                    ActiveMQQueue queueDestination_ = trcFindQueueDesc(redisMessageBean.getQueueName());
                    producerService.sendMessage(queueDestination_, JSON.toJSONString(map));
                    compensationResult = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        //判断执行的结果是否正确，如果错误则返回false.
        return compensationResult;
    }

    /**
     * 从redie中删除对应可以的消息内容
     * @param queueName
     * @param indexValue
     * @return
     * @throws Exception
     */
    public Long removeMessageFromRedisMap(String queueName, Long indexValue) {
        try {
            StringBuilder key=new StringBuilder("ts_").append(queueName).append("_").append(indexValue);
            Long deleteResult= del(key.toString());
            jedisCluster.srem(TRANS_KEYS,key.toString());
            return deleteResult;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return (long)-1;
        }
    }


    private static  <T>  T getTransactionCompensationBean(Map<String,String> map, Class<T> clzz){
        if (map==null)
            return null;
        try {
            T obj =clzz.newInstance();
            Field [] fields= clzz.getDeclaredFields();
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
     * 幂等性校
     * 根据传入的队列名字，从幂等的rediskey中获取对应队列的entity dao server的对象名
     * @param queryParamQueueName
     * @param updateFlagKey
     * @param applicationContext
     * @return
     */
    public boolean idempotentValidate(String queryParamQueueName, Long updateFlagKey, ApplicationContext applicationContext) {
        IdempotentBean idempotentBean = this.redisQueryIdempotentAllBaseInfo(queryParamQueueName);
        String daoName = idempotentBean.getDaoName();
        Object validate = null;

        //将业务的DAO转换位HIS表的对应的DAO
        try {
            if (applicationContext.containsBean(daoName)) {
                String errorInfo = "The daoName: " + daoName + " is not exist, please check...........";
                LOGGER.error(errorInfo);
                throw new RuntimeException(errorInfo);
            }
            Object objectClass = applicationContext.getBean(daoName);
            Class<?> clazz = objectClass.getClass();
            Method method = clazz.getMethod(FIND_BUSINESS_KEY_FLAG_MATHOD_NAME, JSONObject.class);
            JSONObject queryJSONObjectParam = new JSONObject();
            queryJSONObjectParam.put("updateKey", updateFlagKey);
            validate = method.invoke(objectClass, queryJSONObjectParam);
        } catch (InvocationTargetException ite) {
            LOGGER.error(ite.getMessage(), ite);
        } catch (IllegalAccessException iae) {
            LOGGER.error(iae.getMessage(), iae);
        } catch (IllegalArgumentException iae) {
            LOGGER.error(iae.getMessage(), iae);
        } catch (SecurityException se) {
            LOGGER.error(se.getMessage(), se);
        } catch (NoSuchMethodException nsme) {
            LOGGER.error(nsme.getMessage(), nsme);
        } catch (BeansException be) {
            LOGGER.error(be.getMessage(), be);
        }
        Boolean validateFlag = null;
        if(null != validate){
            validateFlag = Boolean.valueOf(validate + "");
        }
        return validateFlag;
    }

    /**
     * 从Redis中查询指定队列名字的幂等的对象信息
     * @param queryParamQueueName
     * @return
     */
    public IdempotentBean redisQueryIdempotentAllBaseInfo(String queryParamQueueName) {
        String idempotentBeanValue = this.getHashMap(idempotentKey, queryParamQueueName);
        List<IdempotentBean> idempotentBeans_ = new ArrayList<IdempotentBean>();
        idempotentBeans_ = JSONObject.parseArray(idempotentBeanValue, IdempotentBean.class);
        if(null != idempotentBeans_ && idempotentBeans_.size() > 0){
            return idempotentBeans_.get(0);
        }else{
            return null;
        }
    }

    /**
     * Redis中查询所有幂等对象的信息
     * @param queryParamListQueueName
     * @return
     */
    public List<IdempotentBean> redisQueryIdempotentAllBaseInfo(List<String> queryParamListQueueName) {
        Map<String, String> idempotentAllMap = this.jedisCluster.hgetAll(idempotentKey);
        List<IdempotentBean> idempotentBeans = new ArrayList<IdempotentBean>();
        if (null == queryParamListQueueName || queryParamListQueueName.size() < 1) {
            Set<Map.Entry<String, String>> entrySet = idempotentAllMap.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String queueName = entry.getKey();
                String idempotentBeanValue = entry.getValue();
                IdempotentBean idempotentBean = JSONObject.parseObject(idempotentBeanValue, IdempotentBean.class);
                idempotentBean.setQueueName(queueName);
                idempotentBeans.add(idempotentBean);
            }
        } else {
            for (int i = 0; i < queryParamListQueueName.size(); i++) {
                String queueName = queryParamListQueueName.get(i);
                String idempotentBeanValue = idempotentAllMap.get(queueName);
                List<IdempotentBean> idempotentBeans_ = new ArrayList<IdempotentBean>();
                idempotentBeans_ = JSONObject.parseArray(idempotentBeanValue, IdempotentBean.class);
                idempotentBeans.addAll(idempotentBeans_);
            }
        }
        return idempotentBeans;
    }
    //{tableName:tableName daoName:daoName serverName:serverName}

    /**
     * 保存幂等对象数据到redis
     * @param idempotentEntity
     */
    public void redisSaveIdempotentBaseInfo(JSONObject idempotentEntity) {
        Map<String, String> hashMapValue = new HashMap<String, String>();
        String queueName = idempotentEntity.getString("queueName");
        String idempotentValue = this.getHashMap(idempotentKey, queueName);
        JSONArray idempotentArray = null;
        if (null == idempotentValue || "".equals(idempotentValue)) {
            idempotentArray = new JSONArray();
        } else {
            idempotentArray = JSONArray.parseArray(idempotentValue);
        }
        idempotentArray.add(idempotentEntity);
        hashMapValue.put(queueName, idempotentArray.toString());
        jedisCluster.hmset(idempotentKey, hashMapValue);
    }

    /**
     * 分批
     * @param redisCursor 游标
     * @return
     */
    public JSONObject redisTransactionDatasByBatch(String redisCursor) {
        JSONObject jsonObject = new JSONObject();
        if(com.yhg.base.utils.StringUtils.isBlank(redisCursor)){
            redisCursor = "0";
        }
        ScanResult<String> scanResult = jedisCluster.sscan(TRANS_CORE_KEYS, redisCursor);
        redisCursor = scanResult.getStringCursor();
        LOGGER.info(JSON.toJSONString(scanResult.getResult()));

        List<RedisMessageContentBean> dataList = new LinkedList<>();
        if(redisCursor.equals("0") || redisCursor.isEmpty()){
            jsonObject.put("redisCursor", redisCursor);
            jsonObject.put("dataList", dataList);
            return jsonObject;
        }
        if (scanResult == null || scanResult.getResult() == null || scanResult.getResult().size() == 0){
            jsonObject.put("redisCursor", redisCursor);
            jsonObject.put("dataList", dataList);
            return jsonObject;
        }
        List<String> keyList = scanResult.getResult();
        dataList = getRedisMessageContentList(dataList, keyList);
        jsonObject.put("redisCursor", redisCursor);
        jsonObject.put("dataList", dataList);
        return jsonObject;
    }

    public List<RedisMessageContentBean> getRedisMessageContentList(List<RedisMessageContentBean> dataList, List<String> keyList){
        for (int i = 0; i < keyList.size(); i++) {
            Map<String, String> map = jedisCluster.hgetAll(keyList.get(i));
            RedisMessageContentBean redisMessageBean = getTransactionCompensationBean(map,RedisMessageContentBean.class);
            if (redisMessageBean == null || map.size() == 0) {
                jedisCluster.srem(TRANS_CORE_KEYS, keyList.get(i));
                continue;
            }
            Long messageIndex = redisMessageBean.getMessageIndex();
            List<BaseCommonMessageConfirmEntity_HI> confirmEntity = baseCommonMessageConfirmDAO_HI.findByProperty("messageIndex", messageIndex.intValue());
            if(confirmEntity != null && confirmEntity.size() == 0){
                Date creationDate = redisMessageBean.getCreationDate();
                if(creationDate != null) {
                    Date date = SToolUtils.addDate(6, Calendar.MINUTE, creationDate);
                    if (new Date().after(date)) {
                        jedisCluster.srem(TRANS_CORE_KEYS, keyList.get(i));
                        jedisCluster.del(keyList.get(i));
                        continue;
                    }
                }
            }
            checkRedisMessageStatus(redisMessageBean);
            dataList.add(redisMessageBean);
        }
        return dataList;
    }

    public void setRedisKeyName(String redisKeyName) {
        this.redisKeyName = redisKeyName;
    }

    public String getRedisKeyName() {
        return redisKeyName;
    }

    public void setMapIndexKeyName(String mapIndexKeyName) {
        this.mapIndexKeyName = mapIndexKeyName;
    }

    public String getMapIndexKeyName() {
        return mapIndexKeyName;
    }

    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public ProducerService getProducerService() {
        return producerService;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setIdempotentKey(String idempotentKey) {
        this.idempotentKey = idempotentKey;
    }

    public String getIdempotentKey() {
        return idempotentKey;
    }


}
