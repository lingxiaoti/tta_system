package com.sie.watsons.base.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.rule.model.entities.TempParamRuleMidleEntity_HI;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleHeaderEntity_HI;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleLineEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleHeaderEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleLineEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.ITtaBaseRuleHeader;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.rule.model.inter.ITtaBaseRuleLine;
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

import java.util.List;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@SuppressWarnings("all")
@RestController
@RequestMapping("/ttaBaseRuleHeaderService")
public class TtaBaseRuleHeaderService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleHeaderService.class);

    @Autowired
    private ITtaBaseRuleHeader ttaBaseRuleHeaderServer;

    @Autowired
    private ITtaBaseRuleLine ttaBaseRuleLine;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaBaseRuleHeaderServer;
    }

    /**
     * 功能描述： 查询规则头部信息
     * @date 2019/8/5
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRuleHeaderPagination")
    public String findRuleHeaderPagination(@RequestParam String params,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaBaseRuleHeaderEntity_HI_RO> pagination = ttaBaseRuleHeaderServer.findRuleHeaderPagination(jsonObject, pageIndex, pageRows);
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pagination);
        } catch (Exception e) {
            LOGGER.error("findCheckRulePagination error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".findCheckRulePagination 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }

    /**
     * 功能描述： 查询规则选项
     *
     * @param
     * @return
     * @date 2019/8/5
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCheckRulePagination")
    public String findCheckRulePagination(@RequestParam String params,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaBaseRuleHeaderEntity_HI_RO> pagination = ttaBaseRuleHeaderServer.findCheckRulePagination(jsonObject, pageIndex, pageRows);
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pagination);
        } catch (Exception e) {
            LOGGER.error("findCheckRulePagination error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".findCheckRulePagination 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }




    /**
     * 功能描述： 保存或更新规则信息
     * @param
     * @return
     * @date 2019/8/5
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveCheckRuleList")
    public String saveCheckRuleList(@RequestParam String params) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            JSONArray checkList = jsonObject.getJSONArray("checkList");
            if (checkList != null && !checkList.isEmpty()) {
                List<TtaBaseRuleHeaderEntity_HI> entity_his = JSON.parseArray(SaafToolUtils.toJson(checkList), TtaBaseRuleHeaderEntity_HI.class);
                for (TtaBaseRuleHeaderEntity_HI entity_hi : entity_his) {
                    entity_hi.setOperatorUserId(this.getSessionUserId());
                }
                ttaBaseRuleHeaderServer.saveCheckRuleList(entity_his);
            }
            result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
        } catch (Exception e) {
            LOGGER.error("saveCheckRuleList error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".saveCheckRuleList 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }

    /**
     * 功能描述： 保存或更新规则信息
     * @param
     * @return
     * @date 2019/8/5
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRuleLinePagination")
    public String findRuleLinePagination(@RequestParam String params,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer ruleId = jsonObject.getInteger("ruleId");
            if (ruleId == null) {
                return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
            }
            Pagination<TtaBaseRuleLineEntity_HI_RO> ruleLinePagination = ttaBaseRuleLine.findRuleLinePagination(jsonObject, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(ruleLinePagination));
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, ruleLinePagination);
        } catch (Exception e) {
            LOGGER.error("findRuleLinePagination error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".findRuleLinePagination 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "saveRuleHeader")
    public String saveRuleHeader(@RequestParam String params) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            LOGGER.info(".saveRuleHeader请求的参数信息是:{}", jsonObject);
            Integer ruleId = jsonObject.getInteger("ruleId");
            String resultValue = jsonObject.getString("resultValue");
            String isEnable = jsonObject.getString("isEnable");
            Assert.notNull(new Object[]{ruleId, resultValue, isEnable}, "参数不能为空!");
            TtaBaseRuleHeaderEntity_HI entity = ttaBaseRuleHeaderServer.getById(ruleId);
            entity.setOperatorUserId(this.getSessionUserId());
            entity.setResultValue(resultValue);
            entity.setIsEnable(isEnable);
            ttaBaseRuleHeaderServer.saveOrUpdate(entity);
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, entity);
        } catch (Exception e) {
            LOGGER.error("saveRuleHeader error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, e.getMessage(), null).toString();
        }
        LOGGER.info(".saveRuleHeader 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }


    /**
     * 功能描述： 下级规则选项
     * @date 2019/8/7
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findCheckChildRulePagination")
    public String findCheckChildRulePagination(@RequestParam String params,
                                               @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                               @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            Pagination<TtaBaseRuleHeaderEntity_HI_RO> pagination = ttaBaseRuleHeaderServer.findCheckChildRulePagination(jsonObject, pageIndex, pageRows);
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pagination);
        } catch (Exception e) {
            LOGGER.error("findCheckChildRulePagination error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".findCheckChildRulePagination 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }


    /*
     * 功能描述:保存行表信息
     * @date 2019/8/7
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveChildRule")
    public String saveChildRule(@RequestParam String params) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            JSONArray checkList = jsonObject.getJSONArray("checkList");
            List<TtaBaseRuleLineEntity_HI> entityList = null;
            if (checkList != null && !checkList.isEmpty()) {
                entityList = JSON.parseArray(SaafToolUtils.toJson(checkList), TtaBaseRuleLineEntity_HI.class);
                for (TtaBaseRuleLineEntity_HI entity_hi : entityList) {
                    entity_hi.setOperatorUserId(this.getSessionUserId());
                }
                ttaBaseRuleLine.saveQuestionRuleLine(entityList);
            }
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
        } catch (Exception e) {
            LOGGER.error(".saveChildRule error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".saveChildRule 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }


    /**
     * 功能描述： 更新状态
     * @author xiaoga
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateChildRuleStatus")
    public String updateChildRuleStatus(@RequestParam String params) {
        String result = "";
        try {
            JSONObject jsonObject = this.parseObject(params);
            TtaBaseRuleLineEntity_HI entityHi = JSON.parseObject(jsonObject.toJSONString(), TtaBaseRuleLineEntity_HI.class);
            entityHi = ttaBaseRuleLine.getById(entityHi.getRuleLineId());
            entityHi.setIsEnable(jsonObject.getString("isEnable"));
            ttaBaseRuleLine.saveOrUpdate(entityHi);
            return convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
        } catch (Exception e) {
            LOGGER.error(".updateChildRuleStatus error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".updateChildRuleStatus 入参:{}, 出参:{}", new Object[]{params, result});
        return result;
    }

}