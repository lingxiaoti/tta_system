package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItInvitationRuleEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItInvitationRule extends IBaseCommon<EquPonItInvitationRuleEntity_HI>{
    //IT报价管理-邀请细则查询，分页查询
    JSONObject findItInvitationRule(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-邀请细则删除
    void deleteItInvitationRule(JSONObject params) throws Exception;
}
