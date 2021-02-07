package com.sie.watsons.base.elecsign.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractRecordHeaderEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import com.sie.watsons.base.elecsign.model.entities.TtaAttrCheckItemEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.TtaElecConHeaderEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.*;
import com.sie.watsons.base.elecsign.model.inter.ITtaElecConHeader;
import com.sie.watsons.base.elecsign.model.inter.server.TtaConAttrLineServer;
import com.sie.watsons.base.withdrawal.utils.QrcodeUtils;
import com.yhg.base.utils.BeanObjectMapUtil;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.BufferInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ttaElecConHeaderService")
public class TtaElecConHeaderService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecConHeaderService.class);

    @Autowired
    private ITtaElecConHeader ttaElecConHeaderServer;

    @Autowired
    private TtaConAttrLineServer ttaConAttrLineServer;

    @Autowired
    private IFastdfs fastdfsServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaElecConHeaderServer;
    }


    @RequestMapping(method = RequestMethod.POST, value = "createContractAttr")
    public String createContractAttr(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            JSONObject result = ttaElecConHeaderServer.saveCreateContractAttr(jsonObject);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaElecConHeaderEntity_HI_RO> result = ttaElecConHeaderServer.find(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询特批单号
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSepcial")
    public String findSepcial(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaContractSpecialHeaderEntity_HI_RO> result = ttaElecConHeaderServer.findSepcial(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 查询收取单号对应头表的合同单号
     */
    @RequestMapping(method = RequestMethod.POST, value = "findReceive")
    public String findReceive(@RequestParam(required = false) String params,
                              @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaContractRecordHeaderEntity_HI_RO> result = ttaElecConHeaderServer.findReceive(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "saveElecConHeader")
    public String saveElecConHeader(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            UserSessionBean userSessionBean = this.getUserSessionBean();
            String orgCode = StringUtils.isBlank(userSessionBean.getDeptCode()) ? null : userSessionBean.getDeptCode();
            String deptCode = StringUtils.isBlank(userSessionBean.getGroupCode()) ? null : userSessionBean.getGroupCode();
            if (StringUtils.isBlank(jsonObject.getString("elecConHeaderId"))) {
                Assert.notNull(orgCode, "该用户没有配置一级大部门，请先配置！");
                Assert.notNull(deptCode, "该用户没有配置二级部门，请先配置！");
                jsonObject.put("orgCode", orgCode);//一级大部门
                jsonObject.put("deptCode", deptCode);//二级部门
            }
            JSONObject result = ttaElecConHeaderServer.saveElecConHeader(jsonObject, this.getSessionUserId());
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, result).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E",  e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     *  合同附件信息列表查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "findContractAttrList")
    public String findContractList(@RequestParam(required = false) String params,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            Pagination<TtaConAttrLineEntity_HI_RO> result = new Pagination<TtaConAttrLineEntity_HI_RO>();
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            if (StringUtils.isNotEmpty(jsonObject.getString("elecConHeaderId"))) {
                result = ttaElecConHeaderServer.findContractList(jsonObject, pageIndex, pageRows);
            }
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询附件选中情况
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAttCheckList")
    public String findAttCheckList(@RequestParam(required = false) String params) {
        try {
            List<TtaAttrCheckItemEntity_HI> result = null;
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            Integer elecConHeaderId = jsonObject.getInteger("elecConHeaderId");
            result = ttaElecConHeaderServer.findAttCheckList(userId, elecConHeaderId);
            return SToolUtils.convertResultJSONObj("S", "操作成功" , 0, result).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常" + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * 电子签章返回信息列表查询
     */
    @RequestMapping(method = RequestMethod.POST, value = "findEeleContractHandleList")
    public String findEeleContractHandleList(@RequestParam(required = false) String params,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaElecSignResultLineEntity_HI_RO> result = new Pagination<TtaElecSignResultLineEntity_HI_RO>();
            if (StringUtils.isNotEmpty(jsonObject.getString("elecConHeaderId"))) {
                result = ttaElecConHeaderServer.findEeleContractHandleList(jsonObject, pageIndex, pageRows);
            }
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 电子盖章头信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public String findById(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer elecConHeaderId = jsonObject.getInteger("elecConHeaderId");
            if (elecConHeaderId == null) {
                elecConHeaderId =  jsonObject.getInteger("id");
            }
            JSONObject result = ttaElecConHeaderServer.findById(elecConHeaderId);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, result).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 更改是否GM审批的状态
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateSkipStatus")
    public String updateSkipStatus(@RequestParam(required = true) String params) {
        try{
            JSONObject jsonObject = this.parseObject(params);
            TtaElecConHeaderEntity_HI instance = ttaElecConHeaderServer.updateSkipStatus(jsonObject, this.getSessionUserId());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }



    /**
     * 批量保存合同详情信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveBacthContractDetail")
    public String saveBacthContractDetail(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer userId = this.getSessionUserId();
            ttaElecConHeaderServer.saveBacthContractDetail(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 批量删除合同详情信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteContractDetail")
    public String deleteContractDetail(@RequestParam(required = false) String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            ttaElecConHeaderServer.deleteContractDetail(jsonObject);
            return SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }



    /**
     * 批量新增合同信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "findAddNotExistsAttList")
    public String findAddNotExistsAttList(@RequestParam String params,
                                    @RequestParam(required = false, defaultValue = "1")  Integer pageIndex,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = null;
        try {
            Pagination<TtaConAttrLineEntity_HI_RO> paramDefPage = new Pagination<TtaConAttrLineEntity_HI_RO>();
            JSONObject paramJson = this.parseObject(params);
            if (StringUtils.isNotEmpty(paramJson.getString("elecConHeaderId"))) {
                paramDefPage = ttaElecConHeaderServer.findAddNotExistsAttList(paramJson, pageIndex, pageRows);
            }
            result = convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, paramDefPage);
        } catch (Exception e) {
            LOGGER.error("findAddNotExistsAttList error:{}", e);
            result = convertResultJSONObj(ERROR_STATUS,"查询失败",null);
        }
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "saveContractAttrList")
    public String saveContractAttrList(@RequestParam String params) {
        String result = "";
        try {
            JSONObject jsonObject = JSON.parseObject(params);
            JSONArray attArr = jsonObject.getJSONArray("attArr");
            String orderNo = jsonObject.getString("orderNo");
            List<TtaElecConAttrLineEntity_HI_RO> paramsList = JSON.parseArray(SaafToolUtils.toJson(attArr), TtaElecConAttrLineEntity_HI_RO.class);
            if (paramsList == null || paramsList.isEmpty()) {
                return this.convertResultJSONObj(ERROR_STATUS, "必传参数不能为空", null).toString();
            }
            for (TtaElecConAttrLineEntity_HI_RO dto : paramsList) {
                dto.setOperatorUserId(this.getSessionUserId());
                //通过附件表id查询附件信息，并对附件进行添加水印及二维码
                TtaConAttrLineEntity_HI conLine = ttaConAttrLineServer.getById(dto.getConAttrLineId());
                /*
                if (conLine.getFileName().toLowerCase().lastIndexOf(".pdf") > 0) {
                    String fileUrl = conLine.getFileUrl();
                    File file = QrcodeUtils.createWatermark(fileUrl, orderNo);
                    FileInputStream inputStream = new FileInputStream(file);
                    ResultFileEntity resultFile = fastdfsServer.fileUpload(inputStream, dto.getFileName());
                    inputStream.close();
                    dto.setFileId(resultFile.getFileId().intValue());
                    dto.setFileUrl(resultFile.getAccessPath());
                } else {
                    dto.setFileId(conLine.getFileId());
                    dto.setFileUrl(conLine.getFileUrl());
                }
                */
                dto.setFileId(conLine.getFileId());
                dto.setFileUrl(conLine.getFileUrl());
            }
            List<TtaElecConAttrLineEntity_HI> dotList = JSON.parseArray(SaafToolUtils.toJson(paramsList), TtaElecConAttrLineEntity_HI.class);
            ttaElecConHeaderServer.saveContractAttrList(dotList);
            result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null).toString();
        } catch (Exception e) {
            LOGGER.error("saveContractAttrList error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".saveContractAttrList 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }

    /**
     * 变更
     */
    @RequestMapping(method = RequestMethod.POST, value = "changeElecAll")
    public String changeElecAll(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaElecConHeaderEntity_HI entity = ttaElecConHeaderServer.saveChangeElecAll(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 查询数据
     */
    @RequestMapping(method = RequestMethod.POST, value = "findDicValues")
    public String findElecChangeType(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            String lookupType = jsonObject.getString("lookupType");
            if (StringUtils.isBlank(lookupType)) {
                jsonObject.put("lookupType", "TTA_CHANGE_TYPE");
            }
            List<Map<String, Object>>  list = ttaElecConHeaderServer.findDicValues(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    private void findElecChangeType(JSONObject jsonObject) {
    }


    /**
     * @param params
     * @description 审批
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateApprove")
    public String updateApprove(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            ttaElecConHeaderServer.updateApprove(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findAndSaveContractNo")
    public String findAndSaveContractNo(@RequestParam(required = false) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = this.parseObject(params);
            TtaElecConHeaderEntity_HI instance = ttaElecConHeaderServer.saveAndfindContractNo(jsonObject,userId);
            return SToolUtils.convertResultJSONObj("S", "操作成功" , 0, instance).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常" + e.getMessage(), 0, null).toString();
        }
    }

}