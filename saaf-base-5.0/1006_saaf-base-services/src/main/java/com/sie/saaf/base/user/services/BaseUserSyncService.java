package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.client.BaseUserPersonSynClient;
import com.sie.saaf.base.user.model.inter.IBaseUserSyncServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.base.utils.SToolUtils;
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

/**
 * Created by husaiqiang on 2018/4/11.
 */
@RestController
@RequestMapping("/baseUserSyncService")
public class BaseUserSyncService  extends CommonAbstractService {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserSyncService.class);

    @Autowired
    private IBaseUserSyncServer baseUserSyncServer;

//    @Autowired
//    private BaseUserPersonSynClient baseUserPersonSynClient;

    @Autowired
    private JedisCluster jedisCluster;


    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
    /**
     * --用户、员工同步
     * @return
     * @author husaiqiang
     * @创建时间 2018/04/11
     */
    @RequestMapping(method = RequestMethod.POST, value = "baseUserAndPersonSync")
    public String baseUserAndPersonSync(@RequestParam(required=false) String params) {
        boolean success=true;
        String lockKey="USER_PERSON_SYNC_LOCK";
        try{
            Long val= jedisCluster.setnx(lockKey, SaafDateUtils.convertDateToString(new Date()));
            jedisCluster.expire(lockKey,1800);
            if (val==0){
                success=false;
                logger.info("同步任务正在执行");
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "同步任务正在执行", 0, null).toString();
            }
            JSONObject jsonObject=new JSONObject();
            if (StringUtils.isNotBlank(params))
                jsonObject= JSON.parseObject(params);
//            baseUserPersonSynClient.syncBasePerson();
//            JSONObject result=baseUserPersonSynClient.saveBaseUserSync(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray()).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }finally {
            if (success)
                jedisCluster.del(lockKey);
        }

    }

    @RequestMapping(method = RequestMethod.POST,value="saveSyncCrmUser")
    public String saveCRMUser(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            JSONObject result = this.baseUserSyncServer.saveSyncCrmUser(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, result.getString("syncStatus"), result.getIntValue("savecount"), result).toString();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
