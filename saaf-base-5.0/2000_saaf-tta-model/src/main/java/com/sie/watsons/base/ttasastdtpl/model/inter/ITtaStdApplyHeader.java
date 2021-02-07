package com.sie.watsons.base.ttasastdtpl.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaStdApplyHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaStdApplyHeaderEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITtaStdApplyHeader extends IBaseCommon<TtaStdApplyHeaderEntity_HI>{

    Pagination<TtaStdApplyHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    void updateStatus(Integer pkId,Integer userId) throws Exception;

    TtaStdApplyHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    TtaStdApplyHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception;

    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception;

    TtaStdApplyHeaderEntity_HI saveChangeApplyAll(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception;

    void showPdf(String businessId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
