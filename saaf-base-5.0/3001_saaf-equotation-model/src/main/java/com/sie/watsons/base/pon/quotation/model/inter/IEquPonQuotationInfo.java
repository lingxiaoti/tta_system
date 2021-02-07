package com.sie.watsons.base.pon.quotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component
public interface IEquPonQuotationInfo extends IBaseCommon<EquPonQuotationInfoEntity_HI>{

    /**
     * 分页查询报价管理详情
     */
    JSONObject findQuotationInfoPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows,Integer userId) throws Exception;

    /**
     * 查询报价管理详情
     */
    EquPonQuotationInfoEntity_HI_RO findQuotationInfo(JSONObject queryParamJSON) throws Exception;

    /**
     * 立项到报价管理确认参与
     */
    Integer confirmParticipation(String params, Integer userId) throws Exception;
    /**
     * 删除头数据
     */
    String deleteQuotationInfo(JSONObject jsonObject, int userId);

    // 保存报价管理
    EquPonQuotationInfoEntity_HI saveQuotationInfo(JSONObject jsonObject, int userId) throws Exception;

    /**
     * 立项到报价管理拒绝参与
     */
    Integer rejectParticipation(String params, Integer userId) throws Exception;

    // 待报价展示内容
    JSONObject waitQuotationInfo(String params,Integer pageIndex,Integer pageRows,Integer userId) throws Exception;

    /**
     * 生成二次议价单据
     */
    String generateAgainQuotationDoc(JSONObject jsonObject) throws Exception;

    // 查询报价信息
//    List<EquPonQuotationInfoEntity_HI_RO> findQuotationDetails(JSONObject jsonObject);

    /**
     * 立项单据更新后更新报价单
     */
    void updateQuotationForUpdateProject(EquPonProjectInfoEntity_HI projectEntity) throws Exception;

    // 针对报价中或已提交或待议价,单据过了截止时间更改单据状态
    void updateQuotationInfoForQuotationStatus();

    // 用于监控发起报价邀请的所有立项单据48小时之内是否生成报价
    void updateMonitorQuotationGenerate();

    Integer confirmParticipations(String params) throws Exception;
}
