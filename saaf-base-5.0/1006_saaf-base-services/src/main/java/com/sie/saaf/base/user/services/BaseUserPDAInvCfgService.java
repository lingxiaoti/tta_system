package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAInvCfgEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserPDAInvCfg;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangtao
 */
@RestController
@RequestMapping("/baseUserPDAInvCfgService")
public class BaseUserPDAInvCfgService extends CommonAbstractService {
    private static final Logger log = LoggerFactory.getLogger(BaseUserPDAInvCfgService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @Autowired
    private IBaseUserPDAInvCfg baseUserPDAInvCfgServer;


    @RequestMapping(method = RequestMethod.POST, value = "findBaseUserPDAInvCfg")
    public String findBaseUserPDAInvCfg(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            List<BaseUserPDAInvCfgEntity_HI_RO> list = baseUserPDAInvCfgServer.findBaseUserPDAInvCfg(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "findBaseUserPDAInvCfgEntities")
    public String findBaseUserPDAInvCfgEntities() {
        try {
            List<BaseUserPDAInvCfgEntity_HI_RO> list = baseUserPDAInvCfgServer.findBaseUserPDAInvCfgEntities();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findBaseUserPDAInvCfgEntitiesByUserId")
    public String findBaseUserPDAInvCfgEntitiesByUserId(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            List<BaseUserPDAInvCfgEntity_HI_RO> list = baseUserPDAInvCfgServer.findBaseUserPDAInvCfgEntitiesByUserId(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(),list ).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }




}
