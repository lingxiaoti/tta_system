package com.sie.saaf.base.feedback.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.feedback.model.entities.BaseIssueFeedbackEntity_HI;
import com.sie.saaf.base.feedback.model.entities.readonly.BaseIssueFeedbackEntity_HI_RO;
import com.sie.saaf.base.feedback.model.inter.IBaseIssueFeedback;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseIssueFeedbackServer")
public class BaseIssueFeedbackServer extends BaseCommonServer<BaseIssueFeedbackEntity_HI> implements IBaseIssueFeedback {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIssueFeedbackServer.class);

	@Autowired
	private ViewObject<BaseIssueFeedbackEntity_HI> baseIssueFeedbackDAO_HI;
	@Autowired
	private BaseViewObject<BaseIssueFeedbackEntity_HI_RO> baseIssueFeedbackDAO_HI_RO;

	public BaseIssueFeedbackServer() {
		super();
	}

	/**
	 * queryParamJSON:{
	 * 		ouId：事业部Id
	 * 	    title:反馈标题
	 * 	    userName：创建人
	 * 	    userFullName：创建人名称
	 * }
	 * 查询问题反馈内容列表
	 */
	public Pagination<BaseIssueFeedbackEntity_HI_RO> findBaseIssueFeedbackList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows){
		StringBuffer queryListSql=new StringBuffer(BaseIssueFeedbackEntity_HI_RO.QUERY_FEEDBACK_LIST);
		Map<String,Object> paramsMap = new HashMap<>();
		//事业部ID过滤
		SaafToolUtils.parperParam(queryParamJSON, "bif.ou_id", "ouId", queryListSql, paramsMap, "=");
		//问题反馈过滤
		SaafToolUtils.parperParam(queryParamJSON, "bif.title", "title", queryListSql, paramsMap, "like");
		//状态过滤
		SaafToolUtils.parperParam(queryParamJSON, "bif.status", "status", queryListSql, paramsMap, "=");
		//创建人过滤
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_name", "userName", queryListSql, paramsMap, "like");
		//创建人过滤
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_full_name", "userFullName", queryListSql, paramsMap, "like");
		//开始时间筛选
		SaafToolUtils.parperParam(queryParamJSON, "bif.creation_date", "startDate", queryListSql, paramsMap, ">=");
		//结束时间筛选
		SaafToolUtils.parperParam(queryParamJSON, "bif.creation_date", "endDate", queryListSql, paramsMap, "<=");
		//获取自己的筛选
		SaafToolUtils.parperParam(queryParamJSON, "bif.created_by", "createdBy", queryListSql, paramsMap, "=");
		//系统编码筛选
		//SaafToolUtils.parperParam(queryParamJSON, "bif.system_code", "systemCode", queryListSql, paramsMap, "=");
		//来源筛选
		SaafToolUtils.parperParam(queryParamJSON, "bif.source", "source", queryListSql, paramsMap, "=");

		queryListSql.append(" order by bif.creation_date desc ");
		return baseIssueFeedbackDAO_HI_RO.findPagination(queryListSql,SaafToolUtils.getSqlCountString(queryListSql), paramsMap, pageIndex, pageRows);
	}

	/**
	 * 获取单个问题信息详情
	 * queryParamJSON:{
	 * 		feedbackId：反馈记录id
	 * }
	 * 查询问题反馈内容列表
	 */
	public String findBaseIssueFeedbackById(JSONObject queryParamJSON){
		try {
			if(null != queryParamJSON.getInteger("feedbackId") && queryParamJSON.getInteger("feedbackId") > 0){
				StringBuffer queryListSql=new StringBuffer(BaseIssueFeedbackEntity_HI_RO.QUERY_FEEDBACK_LIST);
				Map<String,Object> paramsMap = new HashMap<>();
				//ID过滤
				SaafToolUtils.parperParam(queryParamJSON, "bif.feedback_id", "feedbackId", queryListSql, paramsMap, "=");
				List<BaseIssueFeedbackEntity_HI_RO> baseIssueFeedbackEntity_hi_ros = baseIssueFeedbackDAO_HI_RO.findList(queryListSql,paramsMap);

				if(null != baseIssueFeedbackEntity_hi_ros && baseIssueFeedbackEntity_hi_ros.size() > 0 ){
					return SToolUtils.convertResultJSONObj("S","获取信息成功！", 0, baseIssueFeedbackEntity_hi_ros.get(0)).toString();
				}else{
					return SToolUtils.convertResultJSONObj("E","获取问题信息出现异常！", 0, null).toString();
				}
			}else{
				return SToolUtils.convertResultJSONObj("E","获取问题信息出现异常！", 0, null).toString();
			}


		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj("E","获取问题信息出现异常！", 0, null).toString();
		}
	}



	/**
	 * 问题反馈表新增或者更新
	 * paramsJSON:{
	 *     title       标题
	 * 	   content     内容
	 * 	   ouId 		 事业部id
	 * 	   systemCode  系统编码
	 * 	   status      状态
	 * }
	 */
	public BaseIssueFeedbackEntity_HI saveOrUpdate(JSONObject paramsJSON, Integer userId) throws Exception {

		try {

			BaseIssueFeedbackEntity_HI instance = SaafToolUtils.setEntity(BaseIssueFeedbackEntity_HI.class, paramsJSON, baseIssueFeedbackDAO_HI, userId);
			instance.setDeleteFlag(0);
			baseIssueFeedbackDAO_HI.saveOrUpdate(instance);
			return  instance;

		}catch(Exception e){
			throw new Exception("更新失败");

		}
	}

}
