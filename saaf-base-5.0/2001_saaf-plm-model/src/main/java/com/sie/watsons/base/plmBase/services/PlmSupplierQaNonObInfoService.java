package com.sie.watsons.base.plmBase.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaNonObInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaNonObInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierQaNonObInfo;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmSupplierQaNonObInfoService")
public class PlmSupplierQaNonObInfoService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmSupplierQaNonObInfoService.class);
    @Autowired
    private IPlmSupplierQaNonObInfo plmSupplierQaNonObInfoServer;

    public PlmSupplierQaNonObInfoService() {
        super();
    }

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.plmSupplierQaNonObInfoServer;
    }

    /**
     * 查询
     *
     * @param params    { teamFrameworkId:框架ID }
     * @param pageIndex 当前页码
     * @param pageRows  页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmSupplier")
    public String findPlmSupplier(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);

            Pagination<TtaSupplierEntity_HI_RO> dataList = plmSupplierQaNonObInfoServer
                    .findPlmSupplier(queryParamJSON, pageIndex, pageRows);
            ResultUtils.getLookUpValue("PLM_SUPPLIER_QA_STATUS");
            queryParamJSON = (JSONObject) JSON.toJSON(dataList);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils
                    .convertResultJSONObj("M", e.getMessage(), 0, null)
                    .toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E",
                    "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询
     *
     * @param params    { teamFrameworkId:框架ID }
     * @param pageIndex 当前页码
     * @param pageRows  页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmSupplierQaNonObInfoInfo")
    public String findPlmSupplierQaNonObInfoInfo(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
            if (vendcodes.size() >= 1) {
//                String vendcode = vendcodes.get(0);
//                queryParamJSON.put("vendcode", vendcode);
                queryParamJSON.put("vendorCodeList", vendcodes);
            }

            Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> dataList = plmSupplierQaNonObInfoServer
                    .findPlmSupplierQaNonObInfoInfo(queryParamJSON, pageIndex,
                            pageRows);
            ResultUtils.getLookUpValue("PLM_SUPPLIER_QA_STATUS");
            queryParamJSON = (JSONObject) JSON.toJSON(dataList);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
            // return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,
            // SUCCESS_MSG,
            // 1, queryParamJSON).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils
                    .convertResultJSONObj("M", e.getMessage(), 0, null)
                    .toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E",
                    "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @description 保存
     **/
    @RequestMapping(method = RequestMethod.POST, value = "savePlmSupplierQaNonObInfoInfo")
    public String savePlmSupplierQaNonObInfoInfo(
            @RequestParam(required = true) String params) {
        try {
            JSONObject parseObject = parseObject(params);
            // vendorcode设置成供应商编号
            List<String> vendcodes = this.getUserSessionBean().getVendorCodes();
            if (vendcodes.size() >= 1) {
                String vendcode = vendcodes.get(0);
                parseObject.put("supplierCode", vendcode);
            }
            parseObject.put("curentName", this.getUserSessionBean()
                    .getUserFullName());
            JSONObject jsonObject = (JSONObject) JSONObject
                    .toJSON(plmSupplierQaNonObInfoServer
                            .savePlmSupplierQaNonObInfoInfo(parseObject));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,
                    jsonObject).toString();

        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils
                    .convertResultJSONObj("M", e.getMessage(), 0, null)
                    .toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E",
                    "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * @param @param  params
     * @param @param  pageIndex
     * @param @param  pageRows
     * @param @return 参数
     * @return json字符串
     * @throws
     * @Title:
     * @Description: TODO(根据id查询头信息与行信息)
     * @author:caojin
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupplierQaById")
    public String findSupplierQaById(
            @RequestParam(required = false) String params) {
        try {
            JSONObject param = parseObject(params);
            JSONObject Detail = plmSupplierQaNonObInfoServer
                    .findSupplierDetail(param);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
                    1, Detail).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveNextPerson")
    public String saveNextPerson(@RequestParam(required = false) String params) {
        try {

            JSONObject param = parseObject(params);
            // 找到上级
            String headid = param.getString("id");

            String status = param.getString("status"); // 状态

            PlmSupplierQaNonObInfoEntity_HI obj = plmSupplierQaNonObInfoServer
                    .getById(new Integer(headid.split("_")[0]));
            if (status.equals("RESOLVED") || status.equals("REFUSAL")) { // 驳回
                obj.setBillStatus("REFU");
                obj.setBillStatusName("已驳回");
                obj.setCurentId(headid);
            } else if (status.equals("REVOKE")) { // 已撤回
                obj.setBillStatus("REVOKE");
                obj.setBillStatusName("已撤回");
                obj.setCurentId(headid);
            } else {
                if (obj.getBillStatus().equals("PURCHASE_TODO")) {
                    obj.setBillStatus("QA_TODO");
                    obj.setBillStatusName("QA待审");
                } else {
                    obj.setBillStatus("APPROVAL");
                    obj.setBillStatusName("已审批");
                }
            }
            plmSupplierQaNonObInfoServer.update(obj);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
                    null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "savesubmit")
    public String savesubmit(@RequestParam(required = false) String params) {
        try {
            LOGGER.info("资质组更新状态参数========="+params);
            JSONObject param = parseObject(params);
            // 找到上级
            String status = param.getString("status");
            String qaGroupCode = param.getString("billNo");
            String INCIDENT = param.getString("INCIDENT");
            String TASKID = param.getString("TASKID");
            String VERSION = param.getString("VERSION");
            String TASKUSER = param.getString("TASKUSER");

            JSONObject qajson = new JSONObject(true);
            qajson.put("qaGroupCode", qaGroupCode);
            List<PlmSupplierQaNonObInfoEntity_HI> qaob = plmSupplierQaNonObInfoServer
                    .findList(qajson);
            PlmSupplierQaNonObInfoEntity_HI qaobj = new PlmSupplierQaNonObInfoEntity_HI();
            for (int i = 0; i < qaob.size(); i++) {
                PlmSupplierQaNonObInfoEntity_HI qcobj = qaob.get(i);
               // if (!qcobj.getBillStatus().equals("CANCEL")) {
                    qaobj = qcobj;
               // }
            }

            if (qaobj.getPlmSupplierQaNonObInfoId() != null) {

                if (status.equals("QA_TODO")) {
                    qaobj.setBillStatus("QA_TODO");
                    qaobj.setBillStatusName("QA待审");

                } else if (status.equals("PURCHASE_TODO")) {
                    qaobj.setBillStatus("PURCHASE_TODO");
                    qaobj.setBillStatusName("采购待审");
                    qaobj.setProcessUser(TASKUSER);
                    qaobj.setProcessIncident(INCIDENT);
                } else if (status.equals("REFU")) {
                    qaobj.setBillStatus("REFU");
                    qaobj.setBillStatusName("已驳回");
                    String refus = INCIDENT + "&&&" + TASKID + "&&&" + VERSION
                            + "&&&" + TASKUSER;
                    qaobj.setProcessReject(refus);

                } else if (status.equals("APPROVAL")) {
                    qaobj.setBillStatus("APPROVAL");
                    qaobj.setBillStatusName("已审批");
                } else if (status.equals("CANCEL")) {
                    qaobj.setBillStatus("CANCEL");
                    qaobj.setBillStatusName("已作废");
                } else if (status.equals("UPDATE_APROV")) {
                    qaobj.setBillStatus("UPDATE_APROV");
                    qaobj.setBillStatusName("修改审批-采购待审");
                    qaobj.setProcessUser(TASKUSER);
                    qaobj.setProcessIncident(INCIDENT);
                } else if (status.equals("UPDATE_QAAPROV")) {
                    qaobj.setBillStatus("UPDATE_QAAPROV");
                    qaobj.setBillStatusName("修改审批-QA待审");
                } else if (status.equals("UPDATE_REFU")) {
                    qaobj.setBillStatus("UPDATE_REFU");
                    qaobj.setBillStatusName("修改驳回");
                    String refus = INCIDENT + "&&&" + TASKID + "&&&" + VERSION
                            + "&&&" + TASKUSER;
                    qaobj.setProcessReject(refus);
                }

                plmSupplierQaNonObInfoServer.update(qaobj);
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
                    null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }

    }

    // 修改审批回调接口
    @RequestMapping(method = RequestMethod.POST, value = "updateNextPerson")
    public String updateNextPerson(@RequestParam(required = false) String params) {
        try {

            JSONObject param = parseObject(params);
            // 找到上级
            String headid = param.getString("id");
            String status = param.getString("status"); // 状态

            PlmSupplierQaNonObInfoEntity_HI obj = plmSupplierQaNonObInfoServer
                    .getById(new Integer(headid.split("_")[0]));
            if (status.equals("RESOLVED") || status.equals("REFUSAL")) { // 驳回
                obj.setBillStatus("UPDATE_REFU");
                obj.setBillStatusName("修改驳回");
                obj.setCurentId(headid);
            } else if (status.equals("REVOKE")) {
                obj.setBillStatus("UPDATE_REVOKE");
                obj.setBillStatusName("修改撤回");
                obj.setCurentId(headid);
            } else {

                if (obj.getBillStatus().equals("UPDATE_APROV")) {
                    obj.setBillStatus("UPDATE_QAAPROV");
                    obj.setBillStatusName("修改审批-QA待审");
                } else {
                    obj.setBillStatus("APPROVAL");
                    obj.setBillStatusName("已审批");
                }
            }
            plmSupplierQaNonObInfoServer.update(obj);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
                    null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "updatesubmit")
    public String updatesubmit(@RequestParam(required = false) String params) {
        try {

            JSONObject param = parseObject(params);
            // 找到上级
            String headid = param.getString("id");
            String status = param.getString("status");

            PlmSupplierQaNonObInfoEntity_HI obj = plmSupplierQaNonObInfoServer
                    .getById(new Integer(headid.split("_")[0]));
            if (status.equals("REVOKE")) { // 撤回
                obj.setBillStatus("UPDATE_REVOKE");
                obj.setBillStatusName("修改撤回");
                obj.setCurentId(headid);
            } else if (status.equals("REFUSAL")) {
                obj.setBillStatus("UPDATE_REFU");
                obj.setBillStatusName("修改驳回");
                obj.setCurentId(headid);
            } else {
                obj.setBillStatus("UPDATE_APROV");
                obj.setBillStatusName("修改审批-采购待审");
            }
            plmSupplierQaNonObInfoServer.update(obj);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
                    null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "getProductSupplier")
    public String getProductSupplier(
            @RequestParam(required = false) String params) {
        JSONObject param = parseObject(params);
        try {
            Integer count = plmSupplierQaNonObInfoServer
                    .getProductSupplier(param);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功!", 0,
                    count).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS,
                    e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询供应商
     *
     * @param params    查询参数
     * @param pageIndex 第几页
     * @param pageRows  查几行
     * @return 供应商列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "findVendor")
    public String findVendor(
            @RequestParam(required = false) String params,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<PlmSupplierQaNonObInfoEntity_HI_RO> resultList = plmSupplierQaNonObInfoServer.findVendor(parameters, pageIndex, pageRows);
            JSONObject resultJson = (JSONObject) JSON.toJSON(resultList);
            resultJson.put(SToolUtils.STATUS, "S");
            resultJson.put(SToolUtils.MSG, SUCCESS_MSG);
            return resultJson.toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败," + e.getMessage(), 0, null).toString();
        }


    }
}
