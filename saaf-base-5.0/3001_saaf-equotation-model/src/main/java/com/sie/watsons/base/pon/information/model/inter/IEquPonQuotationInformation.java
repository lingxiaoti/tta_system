package com.sie.watsons.base.pon.information.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationRejectionEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationInformationEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;

public interface IEquPonQuotationInformation extends IBaseCommon<EquPonQuotationInformationEntity_HI>{

    Pagination<EquPonQuotationInformationEntity_HI_RO> findPonInformationInfo(String params, Integer pageIndex, Integer pageRows) throws Exception;
    Pagination<EquPonQuotationInformationEntity_HI_RO> findPonInformationInfoByDeptCode(String params, Integer pageIndex, Integer pageRows) throws Exception;

    Pagination<EquPonQuotationInformationEntity_HI_RO> findPonItInformationInfo(String params, Integer pageIndex, Integer pageRows) throws Exception;


    JSONObject findInformationIdDatail(String params);

    JSONObject savePonInvitation(String params,int userId,String userNumber) throws  Exception;

    JSONObject findBidSupplierList(String params);

    void deleteInformation(JSONObject jsonObject, int userId);

    JSONObject saveWitnessTeamData(String params, int userId, String userNumber);

    JSONObject updateSupplierQuotation(String params, int userId, String userNumber);
    JSONObject updateSupplierQuotationIt(String params, int userId, String userNumber);

    Pagination<EquPonQuotationInformationEntity_HI_RO> findMaxProjectInfo(String params, Integer pageIndex, Integer pageRows);

    Pagination<EquPonQuotationInformationEntity_HI_RO> findMaxItProjectInfo(String params, Integer pageIndex, Integer pageRows);

    void saveProjectSupplierQuit(JSONObject jsonObject,Integer userId);
    void saveItProjectSupplierQuit(JSONObject jsonObject,Integer userId);

    JSONObject saveRejectPonInvitation(String params, int userId, String userNumber) throws  Exception;
    JSONObject saveItRejectPonInvitation(String params, int userId, String userNumber) throws  Exception;

    Pagination<EquPonInformationRejectionEntity_HI> findRejectionReason(String params);


    JSONObject savePonItInvitation(JSONObject jsonParams,String params, int userId, String userNumber) throws  Exception;

    JSONObject findItInformationIdDatail(String params);

    JSONObject saveItWitnessTeamData(String params, int userId, String userNumber);

    JSONObject findItBidSupplierList(String params);

    void saveQuotationScoreCalculate(JSONObject params) throws Exception;
}
