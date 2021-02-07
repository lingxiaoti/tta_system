package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaNppNewProductReportEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaNppNewProductReportEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaNppNewProductReport extends IBaseCommon<TtaNppNewProductReportEntity_HI>{

    JSONObject saveOrUpdateByNppQuery(JSONObject paramsJSON, UserSessionBean userSessionBean) throws Exception;

    Pagination<TtaNppNewProductReportEntity_HI_RO> findNppInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception;

    List<TtaNppNewProductReportEntity_HI> saveOrUpdateSplitALL(JSONArray save, int userId, JSONObject currentRow) throws Exception;

    List<TtaNppNewProductReportEntity_HI> saveOrUpdateALL(JSONObject jsonObject, int userId) throws Exception;

    List<TtaNppNewProductReportEntity_HI> saveOrUpdateTransferALL(JSONObject jsonObject, int userId) throws Exception;

    Pagination<TtaNppNewProductReportEntity_HI_RO> findProcessSummaryInfo(JSONObject jsonObject, UserSessionBean sessionBean);

    Pagination<TtaNppNewProductReportEntity_HI_RO> findProcessNppInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean);

    Pagination<TtaNppNewProductReportEntity_HI_RO> findNotExist(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaNppNewProductReportEntity_HI_RO findChangeVender(JSONObject queryParamJSON);
}
