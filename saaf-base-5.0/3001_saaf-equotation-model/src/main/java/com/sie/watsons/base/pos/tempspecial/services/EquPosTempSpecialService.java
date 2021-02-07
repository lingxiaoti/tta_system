package com.sie.watsons.base.pos.tempspecial.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.tempspecial.model.entities.EquPosTempSpecialEntity_HI;
import com.sie.watsons.base.pos.tempspecial.model.entities.readonly.EquPosTempSpecialEntity_HI_RO;
import com.sie.watsons.base.pos.tempspecial.model.inter.IEquPosTempSpecial;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/equPosTempSpecialService")
public class EquPosTempSpecialService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(EquPosTempSpecialService.class);
    private static final String UTF8 = "utf-8";
    @Autowired
    private IEquPosTempSpecial equPosTempSpecialServer;
    @Autowired
    private IFastdfs fastDfsServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPosTempSpecialServer;
    }

    @PostMapping("/findTempSpecialPagination")
    public String findTempSpecialPagination(@RequestParam(required = false) String params,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            //权限校验begin
//            JSONObject checkParamsJONS =parseObject(params);
//            CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSRKLSTP");
            //权限校验end
            JSONObject jsonObject = parseObject(params);
            Pagination<EquPosTempSpecialEntity_HI_RO> pagination = equPosTempSpecialServer.findTempSpecialPagination(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(pagination);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("businessType");
            incomingParam.add("docStatus");
            incomingParam.add("qualificationStatus");
            efferentParam.add("businessTypeMeaning");
            efferentParam.add("docStatusMeaning");
            efferentParam.add("qualificationStatusMeaning");
            typeParam.add("EQU_TEMP_SPECIAL_BUSINESS_TYPE");
            typeParam.add("EQU_TEMP_SPECIAL_DOC_STATUS");
            typeParam.add("EQU_QUALIFICATION_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
            jsonObject.put("data", data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/findTempSpecialDetail")
    public String findTempSpecialDetail(@RequestParam(required = true) String params) {
        try {
            EquPosTempSpecialEntity_HI_RO tempSpecialDetail = equPosTempSpecialServer.findTempSpecialDetail(params);

            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(tempSpecialDetail));
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("businessType");
            incomingParam.add("docStatus");
            incomingParam.add("qualificationStatus");
            efferentParam.add("businessTypeMeaning");
            efferentParam.add("docStatusMeaning");
            efferentParam.add("qualificationStatusMeaning");
            typeParam.add("EQU_TEMP_SPECIAL_BUSINESS_TYPE");
            typeParam.add("EQU_TEMP_SPECIAL_DOC_STATUS");
            typeParam.add("EQU_QUALIFICATION_STATUS");
            json = ResultUtils.getLookUpValues(json, incomingParam, efferentParam, typeParam);
//            JSONObject activityJson = ResultUtils.getActivitiesHistoric(json.getString("tempSpecialCode"));
//            if(!ObjectUtils.isEmpty(activityJson)){
//                json.put("procInstId", activityJson.getInteger("procInstId"));
//            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, json).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        }
    }

    @PostMapping("/saveTempSpecialInfo")
    public String saveTempSpecialList(@RequestParam("editParams") String params) {
        try {
            int userId = getSessionUserId();
            EquPosTempSpecialEntity_HI entityHi = equPosTempSpecialServer.saveTempSpecialInfo(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, entityHi).toString();
        } catch (IllegalArgumentException e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/deleteTempSpecial")
    public String deleteTempSpecial(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            if (jsonObject.getString("docStatus") != null && !"DRAFT".equals(jsonObject.getString("docStatus")) && !"REJECT".equals(jsonObject.getString("docStatus"))) {
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "只有单据状态为拟定时才可以删除", 0, null).toString();
            }
            return equPosTempSpecialServer.deleteTempSpecial(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/fileUploadForTempSpecial")
    public String fileUploadForTempSpecial(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = request.getSession().getServletContext();
        // 业务模块
        String functionId = "EQU_POS_TEMP_SPECIAL";
        Integer userId = getSessionUserId();
        try {
            request.setCharacterEncoding(EquPosTempSpecialService.UTF8);
            List<ResultFileEntity> list = new ArrayList<>();
            //1.将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //2.获取业务主键id
            String businessId = multiRequest.getParameter("businessId");
            Iterator iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
                //3.这里的name为fileItem的alias属性值，相当于form表单中name
                String name = (String) iterator.next();
                //4.根据name值拿取文件
                MultipartFile file1 = multiRequest.getFile(name);
                Assert.notNull(file1, "上传内容为空");
                //String fileName = file1.getOriginalFilename(); //文件名 不用这种方式获取文件名,有乱码问题
                String fileName = request.getParameter("fileName");
                try (
                        InputStream inputStream = file.getInputStream();
                ) {
                    //5.保存附件到文件系统和base_attachment中
                    ResultFileEntity resultFileEntity = fastDfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(businessId),userId);
                    String filePath = resultFileEntity.getFilePath();
                    resultFileEntity.setFilePath(filePath+"?attname="+fileName);
                    list.add(resultFileEntity);
                }
            }
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (IOException e) {
            logger.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toJSONString();
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e1.getMessage(), 0, null).toJSONString();
        }
    }

    /**
     * 临时特批审批回调接口
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "processCallback")
    public String processCallback(@RequestParam(required = false) String params) {
        try {
            System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
            JSONObject paramsObject = parseObject(params);
            int userId = paramsObject.getIntValue("userId");
            EquPosTempSpecialEntity_HI entity = equPosTempSpecialServer.processCallback(paramsObject, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }
}