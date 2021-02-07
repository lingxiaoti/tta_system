package com.sie.saaf.base.orgStructure.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseAccessBasedata;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonAccessTemp;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.base.utils.SToolUtils;
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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @auther: huqitao 2018/8/6
 */
@RestController
@RequestMapping("/baseAccessBasedataService")
public class BaseAccessBasedataService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAccessBasedataService.class);
    private static final String person10SyncLock = "PERSON10_SYNC_LOCK";
    private static final String person20SyncLock = "PERSON20_SYNC_LOCK";
    private static final String person20ToDay = "person20ToDay";
    private static final String dealer10SyncLock = "dealer10SyncLock";
    private static final String dealerPerson20SyncLock = "dealerPerson20SyncLock";
    private static final String dealerUser20SyncLock = "dealerUser20SyncLock";
    private static final String dealerSub20SyncLock = "dealerSub20SyncLock";
    private static final String storeSub20SyncLock = "storeSub20SyncLock";

    @Autowired
    private IBaseAccessBasedata baseAccessBasedataServer;

    @Autowired
    private IBasePersonAccessTemp basePersonAccessTempServer;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private TransactionConsistencyCore transactionConsistencyCore;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return baseAccessBasedataServer;
    }

    /**
     * 人员20权限数据同步
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "person20AccessDataSyn")
    public String person20AccessDataSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(person20SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(person20SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            //经销商--员工用户20权限数据同步锁，必须执行完人员20权限数据同步，才允许执行经销商--员工用户20权限数据同步锁
            jedisCluster.setex(dealerPerson20SyncLock, 7200, SaafDateUtils.convertDateToString(new Date()));
            //标记今天已经执行过
            jedisCluster.set(person20ToDay, SaafDateUtils.convertDateToString(new Date(), "yyyy-MM-dd"));

            JSONObject queryParamJSON = parseObject(params);
            List<BaseAccessBasedataEntity_HI_RO> allPositionList = baseAccessBasedataServer.findAllPositionList(queryParamJSON);

            //1、插入人员权限数据临时表
            for (int i = 0; i < allPositionList.size(); i++) {
                Integer orgId = allPositionList.get(i).getOrgId();
                Integer positionId = allPositionList.get(i).getPositionId();
                //查询职位对应的人员信息
                queryParamJSON.put("orgId", orgId);
                queryParamJSON.put("positionId", positionId);
                List<BaseAccessBasedataEntity_HI_RO> positionDistributionPersonList = baseAccessBasedataServer.findPositionDistributionPersonList(queryParamJSON);
                //如果职位没有分配给任何人，跳至下一循环
                if (positionDistributionPersonList.size() == 0) {
                    continue;
                }
                List<BaseAccessBasedataEntity_HI_RO> person20AccessList = new LinkedList<>();
                person20AccessList = baseAccessBasedataServer.findPerson20AccessDataSyn(person20AccessList, orgId, positionId);
                basePersonAccessTempServer.saveAllPersonAccessTempByBatchCode(positionDistributionPersonList, person20AccessList);
            }
            List<BaseAccessBasedataEntity_HI_RO> allBatchCodeList = baseAccessBasedataServer.findAllBatchCodeList();
            if (allBatchCodeList.size() == 0) {
                LOGGER.warn("未获取到批次对应的人员信息，不同步人员权限数据");
            }
            for (int i = 0; i < allBatchCodeList.size(); i++) {
                transactionConsistencyCore.sendMessage("access20PersonQueue", JSON.toJSONString(allBatchCodeList.get(i)));
            }
            LOGGER.info("同步人员20权限数据：入队消息数量:{}", allBatchCodeList.size());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "MQ队列access20PersonQueue正在同步人员权限数据，请耐心等待", allBatchCodeList.size(), null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(person20SyncLock);
            }
        }
    }

    /**
     * 人员10权限数据同步
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "person10AccessDataSyn")
    public String person10AccessDataSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(person10SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(person10SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
//            JSONObject findDeleteResult = baseAccessBasedataServer.findDeletePerson10AccessData();
            JSONObject findSaveResult = baseAccessBasedataServer.findSavePerson10AccessData();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, new JSONArray().fluentAdd(findSaveResult)).toString();
//            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, new JSONArray().fluentAdd(findDeleteResult).fluentAdd(findSaveResult)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(person10SyncLock);
            }
        }
    }

    /**
     * 经销商全局用户权限（10权限）
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "dealer10AccessDataSyn")
    public String dealer10AccessDataSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(dealer10SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(dealer10SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
//            JSONObject findDeleteResult = baseAccessBasedataServer.findDeleteDistributor10AccessDataSyn();
            JSONObject findSaveResult = baseAccessBasedataServer.findSaveDistributor10AccessDataSyn();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, new JSONArray().fluentAdd(findSaveResult)).toString();
//            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "OK", 0, new JSONArray().fluentAdd(findDeleteResult).fluentAdd(findSaveResult)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(dealer10SyncLock);
            }
        }
    }

    /**
     * 经销商员工用户20权限数据同步
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "dealerPerson20AccessDataSyn")
    public String dealerPerson20AccessDataSyn(@RequestParam(required = false) String params) {
        try {
            String redisValue = jedisCluster.get(dealerPerson20SyncLock);
            if (StringUtils.isNotBlank(redisValue)) {
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            String toDayStr = jedisCluster.get(person20ToDay);
            if (toDayStr == null || SaafDateUtils.compare2Days(toDayStr, null, "yyyy-MM-dd") != 0) {
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "请先执行过人员20权限数据同步,再执行经销商员工用户20权限数据同步", 0, null).toString();
            }
            sendToMQ20Access("access20DealerPersonQueue", "20"); //员工用户20权限数据同步
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "MQ队列access20DealerPersonQueue正在同步经销商权限数据，请耐心等待", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 经销商用户20权限数据同步
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "dealerUser20AccessDataSyn")
    public String dealerUser20AccessDataSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(dealerUser20SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(dealerUser20SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            sendToMQ20Access("access20DealerUserQueue", "30"); //经销商用户20权限数据同步
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "MQ队列access20DealerUserQueue正在同步经销商权限数据，请耐心等待", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(dealerUser20SyncLock);
            }
        }
    }

    /**
     * 同步经销商访问子库20权限数据
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "dealerSub20AccessSyn")
    public String dealerSub20AccessSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(dealerSub20SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(dealerSub20SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            sendToMQ20Access("access20DealerSub20Queue", "30"); //同步经销商访问子库20权限数据
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "MQ队列access20DealerSub20Queue正在同步经销商权限数据，请耐心等待", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(dealerSub20SyncLock);
            }
        }
    }

    /**
     * 门店、分销商访问子库20权限数据
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "storeSub20AccessSyn")
    public String storeSub20AccessSyn(@RequestParam(required = false) String params) {
        boolean success = true;
        try {
            Long val = jedisCluster.setnx(storeSub20SyncLock, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(storeSub20SyncLock,1800);
            if (val == 0){
                success = false;
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            sendToMQ20Access("access20StoreDealerSubQueue", "40"); //同步经销商访问子库20权限数据
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "MQ队列access20StoreDealerSubQueue正在同步经销商权限数据，请耐心等待", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } finally {
            if (success) {
                jedisCluster.del(storeSub20SyncLock);
            }
        }
    }

    public void sendToMQ20Access(String queueName, String userType){
        List<BaseAccessBasedataEntity_HI_RO> allUserList = baseAccessBasedataServer.findUserInfo(userType, null);
        if (allUserList.size() == 0) {
            LOGGER.warn("未获取到[{}]对应的人员信息，不同步(经销商员工用户和经销商用户)20权限数据", userType);
        }
        for (int i = 0; i < allUserList.size(); i++) {
            transactionConsistencyCore.sendMessage(queueName, JSON.toJSONString(allUserList.get(i)));
        }
    }
}
