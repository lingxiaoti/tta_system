package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplement.model.inter.IPlmSupShopLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plmSupShopLogService")
public class PlmSupShopLogService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupShopLogService.class);
    @Autowired
    private IPlmSupShopLog plmSupShopLogServer;
    public PlmSupShopLogService() {
        super();
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * 保存或更新数据
     * @param params JSON参数 <br>
     *     {<br>
     *                      apihId:主键，（更新时必填）<br>
     *                      centerName:项目/中心名称<br>
     *                      centerCode:项目/中心编码<br>
     *                      versionNum:版本号（更新时必填）<br>
     *     }
     * @return
     * @author your name
     * @creteTime Tue Oct 15 13:46:14 CST 2019
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmSupShopLogInfo")
    //	/restServer/plmSupShopLogService/findPlmSupShopLogInfo
    public String findPlmSupShopLogInfo(@RequestParam(required = true) String params) {
        LOGGER.info(params);
        JSONObject paramJSON = JSON.parseObject(params);
        String resultStr = JSONObject.toJSONString(plmSupShopLogServer.findPlmSupShopLogInfo(paramJSON));
        LOGGER.info(resultStr);
        return resultStr;
    }
}
