package com.sie.watsons.base.pon.information.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationApprovalEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationApprovalEntity_HI_RO;
import com.sie.watsons.base.pos.suspend.model.entities.readonly.EquPosSupplierSuspendEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IEquPonQuotationApproval extends IBaseCommon<EquPonQuotationApprovalEntity_HI>{

    Pagination<EquPonQuotationApprovalEntity_HI_RO> findPonQuotationApproval(String params, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject findPonQuotationApprovalDatail(String params);

    JSONObject findPonDoubleData(String params);
    JSONObject findItPonDoubleData(String params);

    JSONObject findPonDouQouData(String params);

    JSONObject findDoubleTotalAble(String params);
    JSONObject findItDoubleTotalAble(String params);

    JSONObject saveDoubleTotalData(String params, int userId, String userNumber);
    JSONObject saveItDoubleTotalData(String params, int userId, String userNumber);

    JSONObject saveEquPonQuoApprove(String params, int userId, String userNumber) throws  Exception;

    Integer deleteQuotationApproval(String params, int userId, String userNumber);

    JSONObject findSecondResult(String params);

    void updateQuotationInformationStatus() throws Exception;
    void updateQuotationInformationStatusIt() throws Exception;

    EquPosSupplierWarehousingEntity_HI updateWarehousingCallback(JSONObject paramsObject, int userId);

    void saveSecondJson(String params, int userId, String userNumber);

    Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierBidStatusReport(String params, Integer pageIndex, Integer pageRows);
    Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierBidStatusItReport(String params, Integer pageIndex, Integer pageRows);

    JSONObject findApprovalResults(String params, int userId, String userNumber);
    JSONObject findItApprovalResults(String params, int userId, String userNumber);

    JSONObject findSupplierBidStatusReportDetal(String params);
    JSONObject findSupplierBidStatusItReportDetal(String params);

    Pagination<EquPonQuotationApprovalEntity_HI_RO> findSupplierLovNotDept(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    ResultFileEntity findExportSupplierBid(JSONObject parameters) throws Exception;
    ResultFileEntity findItExportSupplierBid(JSONObject parameters) throws Exception;

}
