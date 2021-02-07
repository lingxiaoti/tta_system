package com.sie.watsons.base.rule.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.rule.model.entities.RuleFileTemplateEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;

public interface IRuleFileTemplate extends IBaseCommon<RuleFileTemplateEntity_HI>{

    public RuleFileTemplateEntity_HI_RO findByBusinessType(String businessType);

    public void updateRuleFile(RuleFileTemplateEntity_HI entity_hi);
}
