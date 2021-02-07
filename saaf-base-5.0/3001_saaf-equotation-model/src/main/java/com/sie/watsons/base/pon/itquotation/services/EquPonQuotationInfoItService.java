package com.sie.watsons.base.pon.itquotation.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuotationInfoItEntity_HI_RO;
import com.sie.watsons.base.pon.itquotation.model.inter.IEquPonQuotationInfoIt;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPonQuotationInfoItService")
public class EquPonQuotationInfoItService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoItService.class);
    @Autowired
    private IEquPonQuotationInfoIt equPonQuotationInfoItServer;

    public EquPonQuotationInfoItService() {
        super();
    }

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    @PostMapping("/findQuotationInfoItPagination")
    public String findQuotationInfoITPagination(@RequestParam String params,
                                              @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                              @RequestParam(defaultValue = "10", required = false) Integer pageRows) {
        try {
            JSONObject jsonObject = parseObject(params);
            Integer userId = getUserSessionBean().getUserId();
            jsonObject = equPonQuotationInfoItServer.findQuotationInfoITPagination(jsonObject, pageIndex, pageRows,userId);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
//            incomingParam.add("relevantCatetory");
//            incomingParam.add("projectCategory");
//            incomingParam.add("orderType");
            incomingParam.add("docStatus");
//            efferentParam.add("relevantCatetoryMeaning");
//            efferentParam.add("projectCategoryMeaning");
//            efferentParam.add("orderTypeMeaning");
            efferentParam.add("docStatusMeaning");
//            typeParam.add("EQU_PROJECT_CATEGORY");
//            typeParam.add("EQU_PROJECT_TYPE");
//            typeParam.add("EQU_PON_ORDER_TYPE");
            typeParam.add("EQU_PON_QUOTATION_DOC_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/findQuotationInfoIt")
    public String findQuotationInfoIT(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            EquPonQuotationInfoItEntity_HI_RO quotationInfo = equPonQuotationInfoItServer.findQuotationInfoIt(jsonObject);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("relevantCatetory");
            incomingParam.add("projectCategory");
            incomingParam.add("orderType");
            incomingParam.add("docStatus");
            efferentParam.add("relevantCatetoryMeaning");
            efferentParam.add("projectCategoryMeaning");
            efferentParam.add("orderTypeMeaning");
            efferentParam.add("docStatusMeaning");
            typeParam.add("EQU_PROJECT_CATEGORY");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_PON_ORDER_TYPE");
            typeParam.add("EQU_PON_QUOTATION_DOC_STATUS");
            jsonObject = ResultUtils.getLookUpValues(JSON.parseObject(JSONObject.toJSONString(quotationInfo)),incomingParam,efferentParam,typeParam);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, jsonObject).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/waitQuotationInfoItPagination")
    public String waitQuotationInfoItPagination(@RequestParam("params") String params,
                                    @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                    @RequestParam(defaultValue = "10", required = false) Integer pageRows) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            JSONObject jsonObject = equPonQuotationInfoItServer.waitQuotationInfoItPagination(params, pageIndex, pageRows, userId);
            JSONArray data = jsonObject.getJSONArray("data");
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/confirmParticipationIt")
    public String confirmParticipationIt(@RequestParam("params") String params) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            Integer quotationId = equPonQuotationInfoItServer.confirmParticipationIt(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, quotationId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/rejectParticipationIt")
    public String rejectParticipation(@RequestParam("params") String params) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            Integer updateHeadId = equPonQuotationInfoItServer.rejectParticipationIt(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, updateHeadId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveQuotationInfoIt")
    public String saveQuotationInfoIt(@RequestParam(required = true) String editParams) {
        try {
            JSONObject jsonObject = parseObject(editParams);
            Integer userId = getUserSessionBean().getUserId();
            EquPonQuotationInfoItEntity_HI instance = equPonQuotationInfoItServer.saveQuotationInfoIt(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 生成二次议价单据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "generateAgainQuotationDocIt")
    public String generateAgainQuotationDocIt(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String instance = equPonQuotationInfoItServer.generateAgainQuotationDocIt(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
//
    /**
     * 查询单据状态是报价中或已提交的，如果截止时间比当前时间小，则将单据更改为已截止状态
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateQuotationInfoForQuotationStatusIt")
    public String updateQuotationInfoForQuotationStatusIt() {

        try {
            equPonQuotationInfoItServer.updateQuotationInfoForQuotationStatusIt();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 用于监控发起报价邀请的所有立项单据48小时之内是否生成报价
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateMonitorQuotationGenerateIt")
    public String updateMonitorQuotationGenerateIt() {

        try {
            equPonQuotationInfoItServer.updateMonitorQuotationGenerateIt();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}
