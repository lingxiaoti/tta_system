package com.sie.saaf.rule.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.model.entities.RuleActionInstanceEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleActionInstance;
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
 * @Description:规则引擎执行服务实例表
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@RestController
@RequestMapping("/ruleActionInstanceService")
public class RuleActionInstanceService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RuleActionInstanceService.class);
    @Autowired
    private IRuleActionInstance ruleActionInstanceServer;
    
    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return ruleActionInstanceServer;
    }

    //分页
    @PostMapping("findRuleActionInstancePage")
    public String findRuleActionInstancePage(
            @RequestParam(required = false) String params,
            @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        try {
            JSONObject parameters = this.parseObject(params);
            Pagination<RuleActionInstanceEntity_HI_RO> page = ruleActionInstanceServer.findRuleActionInstancePage(parameters, pageIndex, pageRows);
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
    @PostMapping("getRuleActionInstance")
    public String getRuleActionInstance(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            Integer actionInstanceId = parameters.getInteger("actionInstanceId");
            if (actionInstanceId == null) {
                return SToolUtils.convertResultJSONObj("E", "actionInstanceId为空", 0, null).toJSONString();
            }
            RuleActionInstanceEntity_HI_RO projecctType = ruleActionInstanceServer.getRuleActionInstance(actionInstanceId);
            return SToolUtils.convertResultJSONObj("S", "查询成功", 1, projecctType).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
        }
    }

    //保存
    @PostMapping("saveRuleActionInstance")
    public String saveRuleActionInstance(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            RuleActionInstanceEntity_HI result = ruleActionInstanceServer.saveRuleActionInstance(parameters);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
        }
    }

    //删除
    @PostMapping("deleteRuleActionInstance")
    public String deleteRuleActionInstance(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = this.parseObject(params);
            Integer actionInstanceId = parameters.getInteger("actionInstanceId");
            if (actionInstanceId == null) {
                return SToolUtils.convertResultJSONObj("E", "actionInstanceId为空", 0, null).toJSONString();
            }
            ruleActionInstanceServer.deleteRuleActionInstance(actionInstanceId);
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
        }
    }
}
