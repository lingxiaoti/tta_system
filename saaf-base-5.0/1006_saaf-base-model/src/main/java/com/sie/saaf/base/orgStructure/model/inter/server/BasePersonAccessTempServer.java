package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAccessTempEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseAccessBasedataEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBasePersonAccessTemp;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;
import com.yhg.transaction.compensation.core.TransactionConsistencyCore;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("basePersonAccessTempServer")
public class BasePersonAccessTempServer extends BaseCommonServer<BasePersonAccessTempEntity_HI> implements IBasePersonAccessTemp {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonAccessTempServer.class);
    @Autowired
    private ViewObject<BasePersonAccessTempEntity_HI> basePersonAccessTempDAO_HI;
    @Autowired
    private TransactionConsistencyCore transactionConsistencyCore;
    @Autowired
    private BaseAccessBasedataServer baseAccessBasedataServer;

    public BasePersonAccessTempServer() {
        super();
    }

//    public List<BasePersonAccessTempEntity_HI> findBasePersonAccessTempInfo(JSONObject queryParamJSON) {
//        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
//        List<BasePersonAccessTempEntity_HI> findListResult = basePersonAccessTempDAO_HI.findList("from BasePersonAccessTempEntity_HI", queryParamMap);
//        return findListResult;
//    }

    /**
     * 批量插入临时表
     *
     * @param positionDistributionPersonList 职位对应的人员信息
     * @param person20AccessList             人员权限临时数据
     * @throws Exception 抛出异常回滚
     */
    @Override
    public void saveAllPersonAccessTempByBatchCode(List<BaseAccessBasedataEntity_HI_RO> positionDistributionPersonList, List<BaseAccessBasedataEntity_HI_RO> person20AccessList) throws Exception {
        for (int i = 0; i < positionDistributionPersonList.size(); i++) {
            //批次编号
            String batchCode = System.currentTimeMillis() + "";
            List<BasePersonAccessTempEntity_HI> accessTempListNew = new ArrayList<>();
            BasePersonAccessTempEntity_HI accessTempMySelf = new BasePersonAccessTempEntity_HI();
            accessTempMySelf.setBatchCode(batchCode);
            accessTempMySelf.setAccessType("1");
            accessTempMySelf.setOrgId(positionDistributionPersonList.get(i).getOrgId());
            accessTempMySelf.setUserId(positionDistributionPersonList.get(i).getUserId());
            accessTempMySelf.setPersonId(positionDistributionPersonList.get(i).getPersonId());
            accessTempMySelf.setPositionId(positionDistributionPersonList.get(i).getPositionId());
            accessTempMySelf.setSubordinatePersonId(positionDistributionPersonList.get(i).getPersonId());
            accessTempMySelf.setSubordinatePositionId(positionDistributionPersonList.get(i).getPositionId());
            accessTempMySelf.setChannelType(positionDistributionPersonList.get(i).getChannelType());
            accessTempMySelf.setCreationDate(new Date());
            accessTempListNew.add(accessTempMySelf);
            List<BasePersonAccessTempEntity_HI> accessTempList = JSON.parseArray(JSON.toJSONString(person20AccessList), BasePersonAccessTempEntity_HI.class);
            for (int j = 0; j < accessTempList.size(); j++) {
                if (accessTempList.get(j).getSubordinatePersonId() != null && !"".equals(accessTempList.get(j).getSubordinatePersonId())) {
                    BasePersonAccessTempEntity_HI accessTempNew = accessTempList.get(j);
                    accessTempNew.setBatchCode(batchCode);
                    accessTempNew.setAccessType("1");
                    accessTempNew.setOrgId(positionDistributionPersonList.get(i).getOrgId());
                    accessTempNew.setUserId(positionDistributionPersonList.get(i).getUserId());
                    accessTempNew.setPersonId(positionDistributionPersonList.get(i).getPersonId());
                    accessTempNew.setPositionId(positionDistributionPersonList.get(i).getPositionId());
                    accessTempNew.setChannelType(positionDistributionPersonList.get(i).getChannelType());
                    accessTempNew.setCreationDate(new Date());
                    accessTempListNew.add(accessTempNew);
                }
            }
            basePersonAccessTempDAO_HI.saveAll(accessTempListNew);
        }
    }

    @Override
    public Boolean savePersonAccessTemp(Integer indexValue, JSONObject paramsJSON) throws Exception {
        BasePersonAccessTempEntity_HI accessTemp = JSON.toJavaObject(paramsJSON, BasePersonAccessTempEntity_HI.class);
        accessTemp.setBatchCode(indexValue + "");
        accessTemp.setAccessType("101");
        accessTemp.setCreationDate(new Date());
        basePersonAccessTempDAO_HI.save(accessTemp);
        return true;
    }

    @Override
    public JSONObject callSaveTransTest(String testFlag, Integer orgId, Integer positionId, List<BaseAccessBasedataEntity_HI_RO> positionDistributionPersonList) {
        JSONObject result = new JSONObject();
        RedisMessageContentBean redisMessageContentBean = new RedisMessageContentBean();
        redisMessageContentBean.setQueueName("aaTransTestQueue");
        List<Long> messageIndexs = new ArrayList<>();
        boolean executeMQFlag = false;
        try {
            List<BaseAccessBasedataEntity_HI_RO> person20AccessList = new LinkedList<>();
            person20AccessList = baseAccessBasedataServer.findPerson20AccessDataSyn(person20AccessList, orgId, positionId);
            for (int j = 0; j < positionDistributionPersonList.size(); j++) {
                JSONObject messageJSON = new JSONObject();
                messageJSON.put("testFlag", testFlag);
                messageJSON.putAll(JSON.parseObject(JSON.toJSONString(positionDistributionPersonList.get(j))));
                for (int k = 0; k < person20AccessList.size(); k++) {
                    messageJSON.putAll(JSON.parseObject(JSON.toJSONString(person20AccessList.get(k))));
                    Long messageIndex = sendTest2Redis(redisMessageContentBean, JSON.toJSONString(messageJSON));
                    messageIndexs.add(messageIndex);
                }
            }
            result.put("messageIndex", StringUtils.join(messageIndexs, ","));
            result.put("status", "S");
            result.put("msg", "提交成功");
            executeMQFlag = true;
        } catch (Exception e) {
            result.put("status", "E");
            result.put("msg", e.getMessage());
            result.put("count", 0);
            executeMQFlag = false;
        } finally {
            if (executeMQFlag) {
                //如果业务主事务执行完成之后，将消息从Redis中推送到MQ中
                if (messageIndexs != null && !messageIndexs.isEmpty()) {
                    for (int i = 0; i < messageIndexs.size(); i++) {
                        transactionConsistencyCore.sendRedisMessage2MQ(messageIndexs.get(i), executeMQFlag);
                    }
                }
            }
        }
        return result;
    }

    private Long sendTest2Redis(RedisMessageContentBean redisMessageContentBean, String messageBody) {
        //在执行主事务之前先将数据推送到redis中
        redisMessageContentBean.setMessageBody(messageBody);
        transactionConsistencyCore.sendMessageBody2Redis(redisMessageContentBean);
        Long messageIndex = redisMessageContentBean.getMessageIndex();
        return messageIndex;
    }
}
