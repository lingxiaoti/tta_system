package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionHeaderEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaQuestionHeader extends IBaseCommon<TtaQuestionHeaderEntity_HI>{
	
	/**
	 *  保存问卷头部信息
	 * @param jsonParams
	 * @return
	 */
	public TtaQuestionHeaderEntity_HI saveSaafQuestionHeader(JSONObject jsonParams);

	public Pagination<TtaQuestionHeaderEntity_HI_RO> findPaginationQuestionHeader(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	public JSONObject findQuestionByHeaderId(Integer headerId);

	/**
	 * 功能描述： 保存头行明细
	 * @author xiaoga
	 * @date 2019/7/28
	 * @param
	 * @return
	 */
	public TtaQuestionHeaderEntity_HI saveOrUpdateALL(JSONObject jsonParams) throws  Exception ;
	
	/**
	 * 功能描述： 删除头或行信息，如果删除头信息级联删除行信息
	 * @author xiaoga
	 * @date 2019/7/28
	 * @param  
	 * @return 
	 */
	public void deleteQuestionHeaderOrLine(JSONObject jsonParams);
}
