package com.sie.watsons.base.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

import com.sie.watsons.base.rule.model.entities.TtaBaseRuleHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaBaseRuleHeader extends IBaseCommon<TtaBaseRuleHeaderEntity_HI> {

    /**
     * 功能描述： 查询规则选项信息
     * @date 2019/8/5
     * @param
     * @return
     */
    Pagination<TtaBaseRuleHeaderEntity_HI_RO> findCheckRulePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    /**
     * 功能描述： 查询规则下级选项信息
     * @date 2019/8/5
     * @param
     * @return
     */
    Pagination<TtaBaseRuleHeaderEntity_HI_RO> findCheckChildRulePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);


    /**
     * 功能描述： 添加规则选项列表
     * @date 2019/8/5
     * @param
     * @return
     */
    public void saveCheckRuleList(List<TtaBaseRuleHeaderEntity_HI> entityHis);

    /**
     * 功能描述： 查询规则头表信息
     * @date 2019/8/5
     * @param
     * @return
     */
    Pagination<TtaBaseRuleHeaderEntity_HI_RO> findRuleHeaderPagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

}
