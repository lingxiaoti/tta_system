package com.sie.saaf.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleActionEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleAction;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的服务定义，非实例
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:32:42
 */
@RestController
@RequestMapping("/ruleActionService")
public class RuleActionService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RuleActionService.class);
    @Autowired
    private IRuleAction ruleActionServer;
    
    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return ruleActionServer;
    }

    //分页
    @PostMapping("findRuleActionPage")
    public String findRuleActionPage(
            @RequestParam(required = false) String params,
            @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<RuleActionEntity_HI_RO> page = ruleActionServer.findRuleActionPage(parameters, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
        }
    }

    //详情
    @PostMapping("getRuleAction")
    public String getRuleAction(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            Integer executeActionId = parameters.getInteger("executeActionId");
            if (executeActionId == null) {
                return SToolUtils.convertResultJSONObj("E", "executeActionId为空", 0, null).toJSONString();
            }
            RuleActionEntity_HI_RO projecctType = ruleActionServer.getRuleAction(executeActionId);
            return SToolUtils.convertResultJSONObj("S", "查询成功", 1, projecctType).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
        }
    }

    //保存
    @PostMapping("saveRuleAction")
    public String saveRuleAction(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            RuleActionEntity_HI result = ruleActionServer.saveRuleAction(parameters);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
        }
    }

    //删除
    @PostMapping("deleteRuleAction")
    public String deleteRuleAction(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            Integer executeActionId = parameters.getInteger("executeActionId");
            if (executeActionId == null) {
                return SToolUtils.convertResultJSONObj("E", "executeActionId为空", 0, null).toJSONString();
            }
            ruleActionServer.deleteRuleAction(executeActionId);
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
        }
    }
}
