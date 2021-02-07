package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.proposal.model.entities.TtaBrandCountCRecordEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITtaBrandCountCRecord extends IBaseCommon<TtaBrandCountCRecordEntity_HI>{

    void saveBrandplHCountRecordInfo(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void updateBrandRecordStatus(JSONObject countParams,Exception e) throws Exception;

    void saveProposalSplitRecord(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response,String msgRemark) throws Exception;

    void updateProposalSplitRecordStatus(JSONObject submitParams, Exception e) throws Exception;
}
