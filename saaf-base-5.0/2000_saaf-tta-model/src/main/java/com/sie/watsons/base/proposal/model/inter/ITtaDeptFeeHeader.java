package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaDeptFeeHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.proposal.model.entities.TtaDeptFeeHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaDeptFeeHeader extends IBaseCommon<TtaDeptFeeHeaderEntity_HI>{
    Pagination<TtaDeptFeeHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaDeptFeeHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaDeptFeeHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    //确认
    TtaDeptFeeHeaderEntity_HI updateconfirm(JSONObject paramsJSON, int userId) throws Exception;

    //取消确认
    TtaDeptFeeHeaderEntity_HI updatecancelConfirm(JSONObject paramsJSON, int userId) throws Exception;

    //计算
    Map<String, Object> callCount(JSONObject paramsJSON, int userId) throws Exception;

}
