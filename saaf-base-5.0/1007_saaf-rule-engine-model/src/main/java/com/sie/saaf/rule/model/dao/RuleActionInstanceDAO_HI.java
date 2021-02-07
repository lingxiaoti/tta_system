package com.sie.saaf.rule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.rule.model.entities.RuleActionInstanceEntity_HI;
import org.springframework.stereotype.Component;

/**
 * @Description:规则引擎执行服务实例表
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionInstanceDAO_HI")
public class RuleActionInstanceDAO_HI extends BaseCommonDAO_HI<RuleActionInstanceEntity_HI> {
    public RuleActionInstanceDAO_HI() {
        super();
    }

    @Override
    public Object save(RuleActionInstanceEntity_HI entity) {
        return super.save(entity);
    }
}
