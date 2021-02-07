package com.sie.saaf.message.send.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.message.model.inter.IMsgInstance;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chenzg
 * @createTime 2018-06-25 10:13
 * @description Transaction compensation
 * MQ事务补偿服务，针对实例表中待发送状态的发送MQ，则进行补偿，直接从实例表中取出数据再推送到MQ中
 */
@RestController
@RequestMapping("/transactionCompensationService")
public class TransactionCompensationService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionCompensationService.class);

    @Autowired
    private IMsgInstance msgInstanceServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * 如果传了instanID ，则只补偿该实例，否则补偿所有状态为1的
     * ps：为了避免补偿和正常流程冲突，只取12小时前的数据
     * */
    @RequestMapping(method = RequestMethod.POST,value="retry")
    public String retry(@RequestParam(required=false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            JSONObject json = msgInstanceServer.retry(queryParamJSON);
            return SToolUtils.convertResultJSONObj(json.getString("status"), json.getString("msg"), json.getInteger("count"), null).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}
