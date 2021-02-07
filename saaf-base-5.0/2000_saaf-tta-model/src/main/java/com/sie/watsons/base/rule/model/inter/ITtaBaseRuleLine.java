package com.sie.watsons.base.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

import com.sie.watsons.base.rule.model.entities.TtaBaseRuleLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaBaseRuleLine extends IBaseCommon<TtaBaseRuleLineEntity_HI> {

    /**
     * 功能描述： 通过规则头id 查询规则行信息
     * @author xiaoga
     * @date 2019/8/5
     * @param  
     * @return 
     */
    Pagination<TtaBaseRuleLineEntity_HI_RO> findRuleLinePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


    public void saveQuestionRuleLine(List<TtaBaseRuleLineEntity_HI> list);

}
