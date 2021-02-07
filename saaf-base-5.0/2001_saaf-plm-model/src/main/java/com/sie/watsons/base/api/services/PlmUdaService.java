package com.sie.watsons.base.api.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.inter.IPlmApi;
import com.sie.watsons.base.api.model.inter.IPlmUda;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/1/10/010.
 */
@RestController
@RequestMapping("/plmUdaService")
public class PlmUdaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmApiService.class);
    @Autowired
    private IPlmUda iPlmUdaServer;

    public PlmUdaService() {
        super();
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateMaster")
    public String updateMaster(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            iPlmUdaServer.updateItemMaster(paramJSON);
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
        return "SCCESS";
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateSupp")
    public String updateSupp(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            iPlmUdaServer.updateItemSupp(paramJSON);
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
        return "SCCESS";
    }

    @RequestMapping(method = RequestMethod.POST, value = "updateUda")
    public String updateUda(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            iPlmUdaServer.updateUdaInformation(paramJSON);
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
        return "SCCESS";
    }
}
