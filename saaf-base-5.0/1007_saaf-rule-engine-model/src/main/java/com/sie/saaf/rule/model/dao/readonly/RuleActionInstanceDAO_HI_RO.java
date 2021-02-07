package com.sie.saaf.rule.model.dao.readonly;

import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

/**
 * @Description:规则引擎执行服务实例表
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionInstanceDAO_HI_RO")
public class RuleActionInstanceDAO_HI_RO extends DynamicViewObjectImpl<RuleActionInstanceEntity_HI_RO>  {
	public RuleActionInstanceDAO_HI_RO() {
		super();
	}

}
