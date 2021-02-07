package com.sie.saaf.base.message.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageInstationEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageInstationEntity_HI_RO;
import com.sie.saaf.base.message.model.inter.IBaseMessageInstation;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: huqitao 2018/7/17
 */
@RestController
@RequestMapping("/baseMessageInstationService")
public class BaseMessageInstationService extends CommonAbstractService {
    private static final Logger logger = LoggerFactory.getLogger(BaseMessageInstationService.class);
    @Autowired
    private IBaseMessageInstation baseMessageInstationServer;
    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.baseMessageInstationServer;
    }

    /**
     * 1、查询私信列表
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("messReceiverId",queryParamJSON.getInteger("varUserId"));
            Pagination<BaseMessageInstationEntity_HI_RO> pagination = baseMessageInstationServer.findMessInstationPagination(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.warn("查询站内消息列表:{}", e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }



    /**
     * 1、查询私信列表(含消息源业务id的图片)
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPaginationIncludeImg")
    public String findPaginationIncludeImg(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("messReceiverId",queryParamJSON.getInteger("varUserId"));
            Pagination<BaseMessageInstationEntity_HI_RO> pagination = baseMessageInstationServer.findPaginationIncludeImg(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
            logger.warn("查询站内消息列表含消息源业务id的图片):{}", e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }
    /**
     * 2、新增站内接收消息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveMessageInstation")
    public String saveMessageInstation(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            if (StringUtils.isNotBlank(queryParamJSON.getString("sourceFlag")) && "MESSAGE_CENTER".equals(queryParamJSON.getString("sourceFlag"))) {
                if (StringUtils.isNotBlank(queryParamJSON.getString("userName"))) {
                    JSONObject queryJSON = new JSONObject();
                    queryJSON.put("userName", queryParamJSON.getString("userName"));
                    JSONObject userInfoJson = baseMessageInstationServer.findUserInfo(queryJSON);
                    if (userInfoJson != null) {
                        queryParamJSON.put("messReceiverId", userInfoJson.getInteger("userId"));
                        queryParamJSON.put("messReceiver", userInfoJson.getString("userFullName"));
                    }
                }
                if (StringUtils.isNotBlank(queryParamJSON.getString("messSenderId"))) {
                    JSONObject queryJSON = new JSONObject();
                    queryJSON.put("userId", queryParamJSON.getInteger("messSenderId"));
                    JSONObject userInfoJson = baseMessageInstationServer.findUserInfo(queryJSON);
                    if (userInfoJson != null) {
                        queryParamJSON.put("messSenderId", userInfoJson.getInteger("userId"));
                        queryParamJSON.put("messSender", userInfoJson.getString("userFullName"));
                    }
                }
            }
            BaseMessageInstationEntity_HI messageInstationEntity = baseMessageInstationServer.saveMessageInstation(queryParamJSON);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, messageInstationEntity).toString();
        } catch (IllegalArgumentException e) {
            logger.warn("新增站内接收消息参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.warn("新增站内接收消息异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 3、阅读：更新消息状态
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateMessageStatus")
    public String updateMessageStatus(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            JSONObject resultJSON = baseMessageInstationServer.updateMessageStatus(queryParamJSON);
            return JSON.toJSONString(resultJSON);
        } catch (IllegalArgumentException e) {
            logger.warn("阅读：更新消息状态参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.warn("阅读：更新消息状态异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

    /**
     * 4、批量更新消息状态
     * @param params
     * {
     *     insMessIdStr:1,2,3（批量更新指定消息的状态，不传，则更新所有）
     * }
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateMessageStatusByBatch")
    public String updateMessageStatusByBatch(@RequestParam(required = false) String params) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            queryParamJSON.put("messReceiverId", queryParamJSON.getInteger("varUserId"));
            JSONObject resultJSON = baseMessageInstationServer.updateMessageStatusByBatch(queryParamJSON);
            return JSON.toJSONString(resultJSON);
        } catch (IllegalArgumentException e) {
            logger.warn("批量更新消息状态参数异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.warn("批量更新消息状态异常:{}--{}", e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
        }
    }

//    /**
//     * 5、阅读消息
//     * @param params {
//     *               insMessId : 消息主键id
//     * }
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST, value = "findMessInstationDetail")
//    public String findMessInstationDetail(@RequestParam(required = false) String params) {
//        try {
//            JSONObject queryParamJSON = parseObject(params);
//            JSONObject results = baseMessageInstationServer.findMessInstationDetail(queryParamJSON);
//            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//            results.put(SToolUtils.MSG, "成功");
//            return results.toJSONString();
//        } catch (Exception e) {
//            logger.warn("查看消息:{}", e.getMessage());
//            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
//        }
//    }

}
