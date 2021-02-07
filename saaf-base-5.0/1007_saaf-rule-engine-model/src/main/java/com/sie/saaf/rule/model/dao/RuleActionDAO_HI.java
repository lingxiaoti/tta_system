package com.sie.saaf.rule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.rule.model.entities.RuleActionEntity_HI;
import org.springframework.stereotype.Component;

/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的服务定义，非实例
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionDAO_HI")
public class RuleActionDAO_HI extends BaseCommonDAO_HI<RuleActionEntity_HI> {
    public RuleActionDAO_HI() {
        super();
    }

    @Override
    public Object save(RuleActionEntity_HI entity) {
        return super.save(entity);
    }
}
