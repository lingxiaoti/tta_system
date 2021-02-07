package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ITtaBrandplnH extends IBaseCommon<TtaBrandplnHEntity_HI>{
    Pagination<TtaBrandplnHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaBrandplnHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaBrandplnHEntity_HI_RO callfindByRoId(JSONObject queryParamJSON, int userId) throws Exception;

    //确认
    TtaBrandplnHEntity_HI updateconfirm(JSONObject paramsJSON, int userId) throws Exception;

    //取消确认
    TtaProposalHeaderEntity_HI updatecancelConfirm(JSONObject paramsJSON, int userId) throws Exception;

    //计算
    List<TtaBrandplnLEntity_HI> callCount(JSONObject paramsJSON, int userId) throws Exception;


   //更新品牌计划列表的汇总数据
    int brandplnHUpdate(JSONObject jsonObject, int userId);

    String checkConfirm(JSONObject jsonObject, int userId);

    JSONObject getBrandCreateResult(String createKey);


}
