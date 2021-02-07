package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.TtaDmCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportGpSimulationEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaOiReportGpSimulation;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaOiReportGpSimulationService")
public class TtaOiReportGpSimulationService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportGpSimulationService.class);

    @Autowired
    private ITtaOiReportGpSimulation ttaOiReportGpSimulationServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaOiReportGpSimulationServer;
    }


    /**
     * 报表5实现
     */
    @RequestMapping(method = RequestMethod.POST, value = "findGpSimulation")
    public String findGpSimulation(@RequestParam(required = false) String params,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            UserSessionBean userSessionBean = this.getUserSessionBean();
            jsonObject.put("userId", userSessionBean.getUserId());
            jsonObject.put("userType", userSessionBean.getUserType());
            Pagination<TtaOiReportGpSimulationEntity_HI_RO> gpSimulationList = ttaOiReportGpSimulationServer.findGpSimulationList(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(gpSimulationList);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("IllegalArgumentException:{}", e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("Exception:{}", e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 通过选择日期生产报表单据信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveGpSimulation")
    public String saveGpSimulation(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            String endDate = jsonObject.getString("endDate");
            Assert.notNull(endDate, "缺少必填参数");
            JSONObject resultJson = ttaOiReportGpSimulationServer.saveGpSimulation(jsonObject, getSessionUserId());
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, resultJson).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn("IllegalArgumentException:{}", e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.warn("Exception:{}", e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateAll")
    public String saveOrUpdateAll(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = parseObject(params);
            ttaOiReportGpSimulationServer.saveOrUpdateALL(jsonObject, userId );
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findSummaryGpSimulation")
    public String findNodeList(@RequestParam(required = false) String params) {
        Map<String, Object> resultMap = new HashMap<>();
        String reuslt = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            Assert.notNull(1, "参数不能为空！");
            Map<String, Object> dataList = ttaOiReportGpSimulationServer.findSummaryGpSimulation(jsonObject);
            resultMap.put("headList", dataList.get("headList"));
            resultMap.put("dataList", dataList.get("dateList"));
            reuslt = SToolUtils.convertResultJSONObj("S", "查询成功！", 0, resultMap).toString();
        } catch (Exception e) {
            reuslt = SToolUtils.convertResultJSONObj("E", "查询失败！" + e.getMessage(), 0, null).toString();
            LOGGER.error(".findNodeList reuslt:{}, exception:{}", reuslt, e);
        }
        return reuslt;
    }

}