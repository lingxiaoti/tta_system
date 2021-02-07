package com.sie.saaf.base.feedback.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.feedback.model.entities.BaseIssueFeedbackEntity_HI;
import com.sie.saaf.base.feedback.model.entities.readonly.BaseIssueFeedbackEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBaseIssueFeedback extends IBaseCommon<BaseIssueFeedbackEntity_HI> {

	/**
	 * 查询问题反馈内容列表
	 */
	Pagination<BaseIssueFeedbackEntity_HI_RO> findBaseIssueFeedbackList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 问题反馈表新增或者更新
	 */
	BaseIssueFeedbackEntity_HI saveOrUpdate(JSONObject jsonObject, Integer userId)  throws Exception ;

	/**
	 * 获取单个问题信息详情
	 * queryParamJSON:{
	 * 		feedbackId：反馈记录id
	 * }
	 * 查询问题反馈内容列表
	 */
	String findBaseIssueFeedbackById(JSONObject queryParamJSON);

}
