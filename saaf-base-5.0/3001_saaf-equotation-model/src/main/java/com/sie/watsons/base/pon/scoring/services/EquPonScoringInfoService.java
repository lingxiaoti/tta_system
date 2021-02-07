package com.sie.watsons.base.pon.scoring.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import com.sie.watsons.base.pon.scoring.model.inter.IEquPonScoringInfo;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPonScoringInfoService")
public class EquPonScoringInfoService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringInfoService.class);

    @Autowired
    private IEquPonScoringInfo equPonScoringInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPonScoringInfoServer;
    }

    /**
     * 报价管理-评分单据查询，分页查询
     *
     * @param params    参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findScoringInfo")
    public String findScoringInfo(@RequestParam(required = false) String params,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            //权限校验begin
//            JSONObject checkParamsJONS =parseObject(params);
//            CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"PF");
            //权限校验end
            JSONObject paramsJONS = parseObject(params);
            JSONObject result = equPonScoringInfoServer.findScoringInfo(paramsJONS, pageIndex, pageRows);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("scoringStatus");
            incomingParam.add("relevantCatetory");
            incomingParam.add("projectCategory");
            incomingParam.add("sensoryTestTypes");
            efferentParam.add("scoringStatusMeaning");
            efferentParam.add("relevantCatetoryMeaning");
            efferentParam.add("projectCategoryMeaning");
            efferentParam.add("sensoryTestTypesMeaning");
            typeParam.add("EQU_SCORING_ORDER_STATUS");
            typeParam.add("EQU_PROJECT_CATEGORY");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_SENSORY_TEST_TYPE");
            JSONArray data = result.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            result.put("data", data);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, SUCCESS_MSG);
            return result.toString();

//			result.put(SToolUtils.STATUS, "S");
//			result.put(SToolUtils.MSG, SUCCESS_MSG);
//			return result.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-评分单据查询，分页查询
     *
     * @param params    参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findScoringInfoByDeptCode")
    public String findScoringInfoByDeptCode(@RequestParam(required = false) String params,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJONS = parseObject(params);
            JSONObject result = equPonScoringInfoServer.findScoringInfoByDeptCode(paramsJONS, pageIndex, pageRows);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("scoringStatus");
            efferentParam.add("scoringStatusMeaning");
            typeParam.add("EQU_SCORING_ORDER_STATUS");
            JSONArray data = result.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            result.put("data", data);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, SUCCESS_MSG);
            return result.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 报价管理-评分单据查询，流程查询
     *
     * @param params    参数：密级Entity中的字段
     * @param pageIndex
     * @param pageRows
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findScoringInfoForFlow")
    public String findScoringInfoForFlow(@RequestParam(required = false) String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJONS = parseObject(params);
            if(paramsJONS.containsKey("id")){
                paramsJONS.put("scoringId",paramsJONS.getInteger("id"));
            }
            JSONObject result = equPonScoringInfoServer.findScoringInfoForFlow(paramsJONS, pageIndex, pageRows);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("scoringStatus");
            incomingParam.add("relevantCatetory");
            incomingParam.add("projectCategory");
            incomingParam.add("sensoryTestTypes");
            efferentParam.add("scoringStatusMeaning");
            efferentParam.add("relevantCatetoryMeaning");
            efferentParam.add("projectCategoryMeaning");
            efferentParam.add("sensoryTestTypesMeaning");
            typeParam.add("EQU_SCORING_ORDER_STATUS");
            typeParam.add("EQU_PROJECT_CATEGORY");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_SENSORY_TEST_TYPE");
            JSONArray data = result.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            result.put("data", data);
            result.put(SToolUtils.STATUS, SUCCESS_STATUS);
            result.put(SToolUtils.MSG, SUCCESS_MSG);
            return result.toString();

//			result.put(SToolUtils.STATUS, "S");
//			result.put(SToolUtils.MSG, SUCCESS_MSG);
//			return result.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-评分单据保存
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveScoringInfo")
    public String saveScoringInfo(@RequestParam(required = false) String params) {
        JSONObject jsonObject = null;
        try{
            jsonObject = parseObject(params);
        }catch(JSONException ex){
            jsonObject = parseObject("{\"scoringHeaderInfo\":"+ params.substring(1,params.length()-1) + "}");
        }


        try {
//            JSONObject jsonObject = parseObject(params);
            EquPonScoringInfoEntity_HI instance = equPonScoringInfoServer.saveScoringInfo(jsonObject);
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
     * 报价管理-评分单据保存
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveScoringInfoForFlow")
    public String saveScoringInfoForFlow(@RequestParam(required = false) String params) {
        try {
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-评分单据删除
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteScoringInfo")
    public String deleteScoringInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            Integer listId = equPonScoringInfoServer.deleteScoringInfo(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-评分单据非价格计算
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveNonPriceCalculate")
    public String saveNonPriceCalculate(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            equPonScoringInfoServer.saveNonPriceCalculate(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-报价总分计算
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveQuotationScoreCalculate")
    public String saveQuotationScoreCalculate(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            equPonScoringInfoServer.saveQuotationScoreCalculate(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询报价资料开启LOV
     *
     * @param params
     * @param pageIndex 页码
     * @param pageRows  每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPonInformationInfoLov")
    public String findPonInformationInfoLov(@RequestParam(required = false) String params,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject paramsJONS = this.parseJson(params);
            JSONObject result = equPonScoringInfoServer.findPonInformationInfoLov(paramsJONS, pageIndex, pageRows);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("informationStatus");
            incomingParam.add("projectCategory");
            incomingParam.add("sensoryTestTypes");
            efferentParam.add("informationStatusName");
            efferentParam.add("projectCategoryName");
            efferentParam.add("sensoryTestTypesName");
            typeParam.add("EQU_PON_QUOTATION_STATUS");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_SENSORY_TEST_TYPE");
            JSONObject returnJson = (JSONObject) JSON.toJSON(result);
            JSONArray data = returnJson.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            returnJson.put("data", data);
            returnJson.put("status", "S");
            return JSON.toJSONString(returnJson);

//			result.put(SToolUtils.STATUS, "S");
//			result.put(SToolUtils.MSG, SUCCESS_MSG);
//			return result.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-评分单据删除
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "checkQuotationStatus")
    public String checkQuotationStatus(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            equPonScoringInfoServer.checkQuotationStatus(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * panel test 导入模板下载
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "btnExportTemplage")
    public String btnExportTemplage(@RequestParam(required = false) String params, HttpServletResponse response) {
        try {
            JSONObject jsonObject = parseObject(params);
            ResultFileEntity result = equPonScoringInfoServer.btnExportTemplage(jsonObject, response);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 导出评分信息
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "exportScoringTemplate")
    public String exportScoringTemplate(@RequestParam(required = false) String params, HttpServletResponse response) {
        try {
            JSONObject jsonObject = parseObject(params);
            ResultFileEntity result = equPonScoringInfoServer.exportScoringTemplate(jsonObject, response);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 报价管理-终止评分单据
     *
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "terminateScoringInfo")
    public String terminateScoringInfo(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            equPonScoringInfoServer.terminateScoringInfo(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 批量导入Panel Test 分数
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updatePanelTestScoreImport")
    public String updatePanelTestScoreImport(@RequestParam(required = true) String params){
        try{
            JSONObject jsonObject = parseObject(params);
            int size = equPonScoringInfoServer.updatePanelTestScoreImport(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
        }catch (Exception e){
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, e.getMessage()).toString();
        }
    }

    /**
     * 评分单据审批回调接口
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "scoringApprovalCallback")
    public String scoringApprovalCallback(@RequestParam(required = false) String params) {
        try {
            System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
            JSONObject paramsObject = parseObject(params);
            int userId = paramsObject.getIntValue("userId");
            EquPonScoringInfoEntity_HI entity = equPonScoringInfoServer.scoringApprovalCallback(paramsObject, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 校验用户操作权限
     * @param params 参数：密级Entity中的字段
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "validateScoringUserOperator")
    public String validateScoringUserOperator(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsJONS =parseObject(params);
            String result  =equPonScoringInfoServer.validateScoringUserOperator(paramsJONS);
            JSONObject resultJson = new JSONObject();
            resultJson.put("result",result);
            resultJson.put(SToolUtils.STATUS, "S");
            resultJson.put(SToolUtils.MSG, SUCCESS_MSG);
            return resultJson.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findScoringOwner")
    public String findScoringOwner(@RequestParam(required = false) String params) {
        try {
            JSONObject paramsObject = parseObject(params);
            JSONObject result  =equPonScoringInfoServer.findScoringOwner(paramsObject);
            result.put(SToolUtils.STATUS, "S");
            result.put(SToolUtils.MSG, SUCCESS_MSG);
            return result.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}