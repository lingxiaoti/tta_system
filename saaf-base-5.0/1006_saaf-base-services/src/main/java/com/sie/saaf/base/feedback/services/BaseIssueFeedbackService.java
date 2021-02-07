package com.sie.saaf.base.feedback.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.feedback.model.entities.BaseIssueFeedbackEntity_HI;
import com.sie.saaf.base.feedback.model.entities.readonly.BaseIssueFeedbackEntity_HI_RO;
import com.sie.saaf.base.feedback.model.inter.IBaseIssueFeedback;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题返回服务类
 * @author peizhen
 */
@RestController
@RequestMapping("/baseIssueFeedbackService")
public class BaseIssueFeedbackService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseIssueFeedbackService.class);

	@Autowired
	private IBaseIssueFeedback baseIssueFeedbackServer;
	public BaseIssueFeedbackService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return baseIssueFeedbackServer;
	}

	/**
	 * 获取反馈列表数据
	 * @param params JSON参数 <br>
	 *     {
	 *		ouId：事业部Id
	 *		title:反馈标题
	 *		userName：创建人
	 *		userFullName：创建人名称
	 *     }
	 * @return
	 * @author peizhen
	 * @creteTime 2018-07-30
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBaseIssueFeedbackList")
	public String findBaseIssueFeedbackList(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try{
			JSONObject paramsObject = parseObject(params);
			ProFileBean ouBean = baseAccreditCacheServer.getOrg(paramsObject.getInteger("varUserId"), paramsObject.getInteger("respId"));
			if(ouBean == null){
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "未获取到用户职责对应的OU信息", 0, null).toString();
			}

			paramsObject.put("ouId", ouBean.getProfileValue());

			Pagination<BaseIssueFeedbackEntity_HI_RO> page = baseIssueFeedbackServer.findBaseIssueFeedbackList(paramsObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();

		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}

	}


	/**
	 * 登录人获取自己的反馈列表数据
	 * @param params JSON参数
	 * @return
	 * @author peizhen
	 * @creteTime 2018-07-30
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findIssueFeedbackUserList")
	public String findIssueFeedbackUserList(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try{
			JSONObject paramsObject = parseObject(params);
			paramsObject.put("createdBy",paramsObject.getInteger("varUserId"));
			Pagination<BaseIssueFeedbackEntity_HI_RO> page = baseIssueFeedbackServer.findBaseIssueFeedbackList(paramsObject, pageIndex, pageRows);

			JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();

		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 获取某一个反馈信息
	 * params:{id:问题反馈的id}
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(@RequestParam(required = false) String params) {

		try{
			JSONObject paramsObject = parseObject(params);
			return baseIssueFeedbackServer.findBaseIssueFeedbackById(paramsObject);
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "获取问题信息出现异常", 0, null).toString();
		}

	}

	/**
	 * @param params ｛
	 *               title       标题
	 *               content     内容
	 *               ouId 		 事业部id
	 *               systemCode  系统编码
	 *               status      状态
	 *               ｝
	 * @description 问题反馈的新增和更新
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {

			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			SaafToolUtils.cleanNull(jsonObject, "orgId");
			BaseIssueFeedbackEntity_HI instance = baseIssueFeedbackServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		} catch (IllegalArgumentException e) {

			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


}
