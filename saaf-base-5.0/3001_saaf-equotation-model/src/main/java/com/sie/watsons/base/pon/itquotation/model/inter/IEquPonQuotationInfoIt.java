package com.sie.watsons.base.pon.itquotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectInfoEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuotationInfoItEntity_HI_RO;

public interface IEquPonQuotationInfoIt {

    /**
     * 分页查询报价管理it详情
     */
    JSONObject findQuotationInfoITPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows,Integer userId) throws Exception;

    /**
     * 查询报价管理it详情
     */
    EquPonQuotationInfoItEntity_HI_RO findQuotationInfoIt(JSONObject queryParamJSON);

    /**
     * 立项到报价管理确认参与
     */
    Integer confirmParticipationIt(String params, Integer userId) throws Exception;

    /**
     * 立项到报价管理拒绝参与
     */
    Integer rejectParticipationIt(String params, Integer userId) throws Exception;

    // 待报价展示内容
    JSONObject waitQuotationInfoItPagination(String params,Integer pageIndex,Integer pageRows,Integer userId) throws Exception;

    // 保存报价管理
    EquPonQuotationInfoItEntity_HI saveQuotationInfoIt(JSONObject jsonObject, int userId) throws Exception;

    /**
     * 生成二次议价单据
     */
    String generateAgainQuotationDocIt(JSONObject jsonObject) throws Exception;

    // 查询单据状态是报价中或已提交的，如果截止时间比当前时间小，则将单据更改为已截止状态
    void updateQuotationInfoForQuotationStatusIt();

    // 用于监控发起报价邀请的所有立项单据48小时之内是否生成报价
    void updateMonitorQuotationGenerateIt();

    /**
     * 立项修改发送报价邀请后同步更新it报价单
     */
    void updateQuotationForUpdateItProject(EquPonItProjectInfoEntity_HI projectEntity) throws Exception;
}
