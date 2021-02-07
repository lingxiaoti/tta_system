package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.inter.server.PdaUserAutoCreateServer;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangminglin on 2018/3/28.
 */

@RestController
@RequestMapping("/pdaUserAutoCreateService")
public class PdaUserAutoCreateService extends CommonAbstractService {

    private static final Logger logger = LoggerFactory.getLogger(PdaUserAutoCreateService.class);
    @Autowired
    private PdaUserAutoCreateServer pdaUserAutoCreateServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return pdaUserAutoCreateServer;
    }

    /**
     * PDA用户账号自动创建
     * @return count
     * @author huangminglin
     * @createTime 2018/3/2
     */
    @RequestMapping(method= RequestMethod.POST,value = "pdaUserAutoCreate")
    public String pdaUserAutoCreate(@RequestParam(value = "pHours") String pHours){
        try {
            JSONObject queryParamJSON = new JSONObject();
            queryParamJSON.put("pHours",pHours);
            SaafToolUtils.validateJsonParms(queryParamJSON,"pHours");
            JSONObject result = pdaUserAutoCreateServer.pdaUserAutoCreate(queryParamJSON);
            return result.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


}
