package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItWitnessTeamEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItWitnessTeam extends IBaseCommon<EquPonItWitnessTeamEntity_HI>{

    //IT报价管理-见证小组查询，分页查询
    JSONObject findItWitnessTeam(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-见证小组删除
    void deleteItWitnessTeam(JSONObject params) throws Exception;

    void saveItSupplierInvitationRemark(JSONObject jsonObject,Integer userId);
}
