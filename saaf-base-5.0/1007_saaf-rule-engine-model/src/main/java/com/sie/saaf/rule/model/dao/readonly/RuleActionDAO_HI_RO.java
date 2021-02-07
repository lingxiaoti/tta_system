package com.sie.saaf.rule.model.dao.readonly;

import com.sie.saaf.rule.model.entities.readonly.RuleActionEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的服务定义，非实例
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionDAO_HI_RO")
public class RuleActionDAO_HI_RO extends DynamicViewObjectImpl<RuleActionEntity_HI_RO>  {
	public RuleActionDAO_HI_RO() {
		super();
	}

}
