package com.sie.watsons.base.cost.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.cost.model.entities.readonly.TtaActualCostRuleEntity_HI_RO;
import com.sie.watsons.base.cost.model.entities.TtaActualCostRuleEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaActualCostRule extends IBaseCommon<TtaActualCostRuleEntity_HI>{

    /**
     * 分页查询规则信息
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<TtaActualCostRuleEntity_HI_RO> findCostRulePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 功能描述： 更新或保存
     * @author xiaoga
     * @date 2019/6/4
     * @param
     * @return
     */
    public void saveOrUpdateCostRule(TtaActualCostRuleEntity_HI entityHi);

    /**
     * 功能描述：删除
     * @author xiaoga
     * @date 2019/6/4
     * @param
     * @return
     */
    public void deleteById(Integer id);
}
