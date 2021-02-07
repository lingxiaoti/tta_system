package com.sie.watsons.base.pon.information.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationApprovalEntity_HI_RO;
import com.sie.watsons.base.pon.information.model.inter.IEquPonQuotationApproval;
import com.sie.watsons.base.pon.itquotation.model.inter.server.EquPonQuotationInfoItServer;
import com.sie.watsons.base.pon.quotation.model.inter.server.EquPonQuotationInfoServer;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.activemq.framework.queue.impl.ProducerService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/equPonQuotationApprovalService")
public class EquPonQuotationApprovalService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationApprovalService.class);

	@Autowired
	private IEquPonQuotationApproval equPonQuotationApprovalServer;
    @Autowired
    EquPonQuotationInfoServer equPonQuotationInfoServer;
    @Autowired
    EquPonQuotationInfoItServer equPonQuotationInfoItServer;
	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonQuotationApprovalServer;
	}

    @Autowired
    private ProducerService producerService;
//	findPonQuotationApproval

    /**
     * 查询报价结果审批（分页）
     * @param params
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPonQuotationApproval")
    public String findPonQuotationApproval(@RequestParam(required = false) String params,
                                       @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        LOGGER.info(params);
        try {
            //权限校验begin
//            JSONObject checkParamsJONS =parseObject(params);
//            CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"BJSGSPIT");
            //权限校验end
            Pagination<EquPonQuotationApprovalEntity_HI_RO> result = equPonQuotationApprovalServer.findPonQuotationApproval(params, pageIndex, pageRows);

            JSONObject jsonObject = (JSONObject) JSON.toJSON(result);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("supplierStatus");
            efferentParam.add("supplierDeptStatusName");
            typeParam.add("EQU_SUPPLIER_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues2( data , incomingParam, efferentParam, typeParam);
            jsonObject.put("data",data);

            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @return
     * @description
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierLovNotDept")
    public String findSupplierLovNotDept(@RequestParam(required = false) String params,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {

            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<EquPonQuotationApprovalEntity_HI_RO> result = equPonQuotationApprovalServer.findSupplierLovNotDept(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("supplierStatus");
            efferentParam.add("supplierDeptStatusName");
            typeParam.add("EQU_SUPPLIER_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
            jsonObject.put("data",data);

            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询供应商投标次数报表详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierBidStatusReportDetal")
    public String findSupplierBidStatusReportDetal(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            JSONObject returnJson =  equPonQuotationApprovalServer.findSupplierBidStatusReportDetal(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询供应商投标次数报表详情(Non-trade)
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierBidStatusItReportDetal")
    public String findSupplierBidStatusItReportDetal(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            JSONObject returnJson =  equPonQuotationApprovalServer.findSupplierBidStatusItReportDetal(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询供应商投标情况报表详情
     * @param params
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierBidStatusReport")
    public String findSupplierBidStatusReport(@RequestParam(required = false) String params,
                                           @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        LOGGER.info(params);
        try {
            Pagination<EquPonQuotationApprovalEntity_HI_RO> infoList = equPonQuotationApprovalServer.findSupplierBidStatusReport(params, pageIndex, pageRows);
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) JSON.toJSON(infoList);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("projectCategory");
            efferentParam.add("projectCategoryName");
            typeParam.add("EQU_PROJECT_TYPE");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询供应商投标情况报表详情(Non-trade)
     * @param params
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierBidStatusItReport")
    public String findSupplierBidStatusItReport(@RequestParam(required = false) String params,
                                              @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                              @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        LOGGER.info(params);
        try {
            Pagination<EquPonQuotationApprovalEntity_HI_RO> infoList = equPonQuotationApprovalServer.findSupplierBidStatusItReport(params, pageIndex, pageRows);
            JSONObject jsonObject = new JSONObject();
            jsonObject = (JSONObject) JSON.toJSON(infoList);
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("projectCategory");
            efferentParam.add("projectCategoryName");
            typeParam.add("EQU_PROJECT_TYPE");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 产品模版导出
     */
    @RequestMapping(method = RequestMethod.POST, value = "findExportSupplierBid")
    public String findExportSupplierBid(@RequestParam("params") String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        String accessPath = null;
        try {
            ResultFileEntity result = equPonQuotationApprovalServer.findExportSupplierBid(jsonObject);
            if(result == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "").toString();
            }
            accessPath = result.getAccessPath();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, accessPath).toString();
        } catch (Exception e) {
            LOGGER.info("产品明细导出失败"+e.getMessage());
        }
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "导出失败.请联系管理员").toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "findItExportSupplierBid")
    public String findItExportSupplierBid(@RequestParam("params") String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        String accessPath = null;
        try {
            ResultFileEntity result = equPonQuotationApprovalServer.findItExportSupplierBid(jsonObject);
            if(result == null){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "").toString();
            }
            accessPath = result.getAccessPath();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, accessPath).toString();
        } catch (Exception e) {
            LOGGER.info("产品明细导出失败"+e.getMessage());
        }
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "导出失败.请联系管理员").toString();
    }

    /**
     * 定时程序 修改状态
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateQuotationInformationSta")
    public String updateQuotationInformationStatus() {
        try {
            equPonQuotationApprovalServer.updateQuotationInformationStatus();
            return SToolUtils.convertResultJSONObj("S",null, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 定时程序 修改状态
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateQuotationInformationStaIt")
    public String updateQuotationInformationStatusIt() {
        try {
            equPonQuotationApprovalServer.updateQuotationInformationStatusIt();
            return SToolUtils.convertResultJSONObj("S",null, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询评分标准详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPonQuotationApprovalDatail")
    public String findPonQuotationApprovalDatail(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {

            JSONObject returnJson =  equPonQuotationApprovalServer.findPonQuotationApprovalDatail(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询评分标准详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSecondResult")
    public String findSecondResult(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {

            JSONObject returnJson =  equPonQuotationApprovalServer.findSecondResult(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 查询二次报价对比详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPonDoubleData")
    public String findPonDoubleData(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {

            JSONObject returnJson =  equPonQuotationApprovalServer.findPonDoubleData(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询二次报价对比详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findItPonDoubleData")
    public String findItPonDoubleData(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {

            JSONObject returnJson =  equPonQuotationApprovalServer.findItPonDoubleData(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询二次议价结果
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPonDouQouData")
    public String findPonDouQouData(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {

            JSONObject returnJson =  equPonQuotationApprovalServer.findPonDouQouData(params);
            return   SToolUtils.convertResultJSONObj("S", "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 查询是否有未二次报价的供应商
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDoubleTotalAble")
    public String findDoubleTotalAble(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            JSONObject returnJson =  equPonQuotationApprovalServer.findDoubleTotalAble(params);
            return   SToolUtils.convertResultJSONObj(returnJson.getString("status"), "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询是否有未二次报价的供应商
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findItDoubleTotalAble")
    public String findItDoubleTotalAble(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            JSONObject returnJson =  equPonQuotationApprovalServer.findItDoubleTotalAble(params);
            return   SToolUtils.convertResultJSONObj(returnJson.getString("status"), "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("S", e.getMessage(), 0, null).toString();
        }
    }


    /**
     *  计算二次报价结果
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveDoubleTotalData")
    public String saveDoubleTotalData(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            String userNumber = geSessiontUserName();
            JSONObject returnJson =  equPonQuotationApprovalServer.saveDoubleTotalData(params,userId,userNumber);
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     *  计算二次报价结果
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveItDoubleTotalData")
    public String saveItDoubleTotalData(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            String userNumber = geSessiontUserName();
            JSONObject returnJson =  equPonQuotationApprovalServer.saveItDoubleTotalData(params,userId,userNumber);
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson.get("data")).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     *  发送中标
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findApprovalResults")
    public String findApprovalResults(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            JSONObject jsonParam = JSONObject.parseObject(params);
            String userNumber = geSessiontUserName();
            equPonQuotationApprovalServer.findApprovalResults(params,userId,userNumber);
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     *  发送中标
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findItApprovalResults")
    public String findItApprovalResults(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            JSONObject jsonParam = JSONObject.parseObject(params);
            String userNumber = geSessiontUserName();
            equPonQuotationApprovalServer.findItApprovalResults(params,userId,userNumber);
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, null).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     *  保存提交
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveEquPonQuoApprove")
    public String saveEquPonQuoApprove(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            JSONObject jsonParam = JSONObject.parseObject(params);
            String userNumber = geSessiontUserName();
            String deptCode = jsonParam.getString("deptCode");
            JSONObject returnJson =  equPonQuotationApprovalServer.saveEquPonQuoApprove(params,userId,userNumber);
            if("second".equals(jsonParam.getString("action"))){
                JSONObject  jsonObject = returnJson.getJSONObject("secondJson");
                if("0E".equals(deptCode)){
                    equPonQuotationInfoServer.generateAgainQuotationDoc(jsonObject);
                }else{
                    equPonQuotationInfoItServer.generateAgainQuotationDocIt(jsonObject);
                }
                equPonQuotationApprovalServer.saveSecondJson(params,userId,userNumber);
                returnJson.put("approvalStatus","60");
            }
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, returnJson).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
     *  计算二次报价结果
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteQuotationApproval")
    public String deleteQuotationApproval(@RequestParam(required = false) String params) {
        LOGGER.info(params);
        try {
            int userId = getSessionUserId();
            String userNumber = geSessiontUserName();
            Integer listId =  equPonQuotationApprovalServer.deleteQuotationApproval(params,userId,userNumber);
            return   SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "", 0, listId).toString(); //JSON.toJSONString(returnJson);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 审批回调接口
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateQuotationApprovalCallback")
    public String updateQuotationApprovalCallback(@RequestParam(required = false) String params) {
        try {
            System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
            JSONObject paramsObject = parseObject(params);
            int userId = paramsObject.getIntValue("userId");
            EquPosSupplierWarehousingEntity_HI entity = equPonQuotationApprovalServer.updateWarehousingCallback(paramsObject,userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, "测试一下").toString();
//            return null;
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}