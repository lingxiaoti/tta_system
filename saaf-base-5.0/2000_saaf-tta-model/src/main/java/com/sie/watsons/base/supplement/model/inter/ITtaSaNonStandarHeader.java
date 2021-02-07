package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITtaSaNonStandarHeader extends IBaseCommon<TtaSaNonStandarHeaderEntity_HI>{

    Pagination<TtaSaNonStandarHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    void updateStatus(Integer pkId,Integer userId) throws Exception;

    TtaSaNonStandarHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    TtaSaNonStandarHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception;

    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception;

    TtaSaNonStandarHeaderEntity_HI saveChangeApplyAll(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception;

    void showPdf(String businessId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    TtaSaNonStandarHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer userId) throws Exception;

    Long print(JSONObject jsonObject, UserSessionBean userSessionBean, Integer userId) throws Exception;
}
