package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITtaProposalHeader extends IBaseCommon<TtaProposalHeaderEntity_HI>{

    Pagination<TtaProposalHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean userSessionBean);

    TtaProposalHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId,HttpServletRequest request,HttpServletResponse response) throws Exception;

    void delete(Integer articleId);

    TtaProposalHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON);

    public List<TtaProposalHeaderEntity_HI> findByTerm(String proposalId,String year);

    public List<TtaProposalHeaderEntity_HI> findByTermOld(String year,String vendorCode);

    public Map<String, Object> callCopyOrder(JSONObject paramsJSON, int userId);


    public Map<String, Object> callChangeOrder(JSONObject paramsJSON, int userId);

    void updateApprove(JSONObject queryParamJSON, int userId) throws Exception;

    TtaProposalHeaderEntity_HI updateStatus(JSONObject queryParamJSON, int userId) throws Exception;

    /**
     *  proposal生成页面中变更单据状态,只有是审核通过才能更新状态
     * @param paramsJSON json参数
     * @param userId 用户id
     * @return
     */
    public Map<String, Object> callChangProposalBillStatus(JSONObject paramsJSON, int userId);

    /**
     * proposal生成管理:切换上一版本
     * @param jsonObject
     * @param userId
     * @return
     */
    Map<String, Object> cutProposalBillVersion(JSONObject jsonObject, int userId);

    void findApproveCheck(JSONObject paramsJSON, int userId) throws Exception;

    /**
     * 查找供应商的销售方式
     * @param jsonObject
     * @return
     * @throws Exception
     */
    TtaProposalHeaderEntity_HI_RO findProposalSaleType(JSONObject jsonObject) throws Exception;

    /**
     *
     * 根据制作年度和供应商查询proposal信息
     * @param year1
     */
    List<TtaProposalHeaderEntity_HI_RO> findProposalInfoBySupplierAndYear(int year1, int year2, Set<String> midList) throws Exception;

    /**
     * 功能描述： 更新是否跳过GM审批状态
     * @author xiaoga
     * @date 2019/8/16
     * @param
     * @return
     */
    TtaProposalHeaderEntity_HI updateSkipStatus(JSONObject paramsJSON, Integer userId) throws Exception;


    /**
     * 功能描述：查询流程节点状态， 返回true则需要控制，否则不控制
     */
    Map<String, Object> findProccessNodeStauts(JSONObject paramsJSON) throws Exception;


    /**
     * 查找往年的proposal单据
     * @param lastProposalId
     * @return
     */
    public List<TtaProposalHeaderEntity_HI> findLastProposal(Integer lastProposalId);

    Pagination<TtaProposalHeaderEntity_HI_RO> findProposalCode(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    TtaProposalHeaderEntity_HI_RO getProposalHearder(String proposalCode, BigDecimal versionCode);

    void updateVendorName(JSONObject jsonObject, int userId) throws Exception;

    Pagination<TtaProposalHeaderEntity_HI_RO> findVendor(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
