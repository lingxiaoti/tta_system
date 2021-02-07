package com.sie.watsons.base.questionnaire.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupTypeEntity_HI_RO;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.utils.Util;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionHeaderEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionHeader;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaQuestionHeaderService")
public class TtaQuestionHeaderService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionHeaderService.class);

    @Autowired
    private ITtaQuestionHeader ttaQuestionHeaderServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaQuestionHeaderServer;
    }

    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

    /**
     * 保存问卷头部信息
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveSaafQuestionHeader")
    public String saveSaafQuestionHeader(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonParam = this.parseObject(params);
            TtaQuestionHeaderEntity_HI entity = ttaQuestionHeaderServer.saveSaafQuestionHeader(jsonParam);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findPaginationQuestionHeader")
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "-1") String pageIndex,
                       @RequestParam(required = false, defaultValue = "10") String pageRows) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            jsonObject = SaafToolUtils.cleanNull(jsonObject, "projectType", "projectCnTitle", "projectEnTitle");
            Pagination<TtaQuestionHeaderEntity_HI_RO> pagination = ttaQuestionHeaderServer.findPaginationQuestionHeader(jsonObject, Integer.parseInt(pageIndex), Integer.parseInt(pageRows));
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "findQuestionByHeaderId")
    public String find(@RequestParam(required = false) String params){
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer qHeaderId = jsonObject.getInteger("qHeaderId");
            Assert.notNull(qHeaderId, "问卷头部信息不能为空！");
            JSONObject jsonResult = ttaQuestionHeaderServer.findQuestionByHeaderId(qHeaderId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonResult).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateALL")
    public String saveOrUpdateALL(@RequestParam(required = true) String params) {
        try {
            JSONObject resultJson = new JSONObject();
            JSONObject paramsJson = this.parseObject(params);
            JSONObject headData = paramsJson.getJSONObject("headData");
            if (headData != null) {
                TtaQuestionHeaderEntity_HI entity = JSON.parseObject(headData.toJSONString(), TtaQuestionHeaderEntity_HI.class);
                String projectEnTitle = entity.getProjectEnTitle();
                if (Util.isIllegalSqlChar(projectEnTitle)){
                    return SToolUtils.convertResultJSONObj(ERROR_STATUS, "含有非法sql语句！", 0, null).toString();
                }
            }
            TtaQuestionHeaderEntity_HI questionHeaderEntity = ttaQuestionHeaderServer.saveOrUpdateALL(paramsJson);
            if (questionHeaderEntity != null &&  questionHeaderEntity.getQHeaderId() != null) {
                resultJson = ttaQuestionHeaderServer.findQuestionByHeaderId(questionHeaderEntity.getQHeaderId());
            }
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, resultJson).toString();
        } catch (Exception e) {
            LOGGER.error("saveOrUpdateALL:{}", e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }

    /**
     * 通过问卷头id查询问卷行信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteQuestionHeaderOrLine")
    public String deleteQuestionHeaderOrLine(@RequestParam(required = true) String params){
        try {
            JSONObject jsonParam = this.parseObject(params);
            Integer qHeaderId = jsonParam.getInteger("qHeaderId");
            Integer choiceLineId = jsonParam.getInteger("choiceLineId");
            Assert.notNull(qHeaderId == null && choiceLineId == null, "参数错误！");
            ttaQuestionHeaderServer.deleteQuestionHeaderOrLine(jsonParam);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error("deleteQuestionHeaderOrLine:{}", e);
            return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
        }
    }

}