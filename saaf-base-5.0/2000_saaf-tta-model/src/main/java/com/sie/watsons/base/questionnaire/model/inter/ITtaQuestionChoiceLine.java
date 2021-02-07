package com.sie.watsons.base.questionnaire.model.inter;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaTestQuestionHeaderEntity_HI_RO;

public interface ITtaQuestionChoiceLine extends IBaseCommon<TtaQuestionChoiceLineEntity_HI>{

	public void saveOrUpadateChoiceLineList(JSONObject jsonParams);
	
	public void saveOrUpdateChoiceLine(TtaQuestionChoiceLineEntity_HI entity);
	
	public List<TtaQuestionChoiceLineEntity_HI_RO> findQuestionChoiceLineById(JSONObject params);
	/**
	 *  通过父id查询下层
	 * @param params
	 * @return
	 */
	public List<TtaQuestionChoiceLineEntity_HI_RO> findQuestionChoiceLineChild(JSONObject params);


	/**
	 * 功能描述：通过规则查询题目列表信息
	 * @date 2019/8/8
	 * @param
	 * @return
	 */
	public List<TtaQuestionHeaderEntity_HI_RO> findRecQuestionHeaderList(JSONObject jsonObject);


	/**
	 * 功能描述： 实例化数据
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param  
	 * @return 
	 */
	public void saveInitQuestion (List<TtaQuestionHeaderEntity_HI_RO> recQuestionHeaderList, Integer proposalId, String proposalYear);


	/**
	 * 功能描述：
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param
	 * @return
	 */
	public List<TtaTestQuestionHeaderEntity_HI_RO> findTestQuestionList(JSONObject params);

	/**
	 * 功能描述： 保存提交的问题卷
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param
	 * @return
	 */
	public  void saveSubmitQuestion(JSONObject params, List<TtaQuestionNewMapDetailEntity_HI> newMapList) throws Exception;


	/**
	 * 功能描述：
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param
	 * @return
	 */
	public void deleteCancelQuestionTest(Integer proposalId);

	/**
	 * 功能描述： 获取问卷remark 信息
	 * @date 2019/9/18
	 * @param
	 * @return
	 */
	public String findQuestionEnglishRemark(Integer proposalId);
}
