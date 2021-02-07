package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.proposal.utils.Util;
import com.yhg.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.proposal.model.entities.TtaBrandCountCRecordEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("ttaBrandCountCRecordServer")
public class TtaBrandCountCRecordServer extends BaseCommonServer<TtaBrandCountCRecordEntity_HI> implements ITtaBrandCountCRecord{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandCountCRecordServer.class);

	@Autowired
	private ViewObject<TtaBrandCountCRecordEntity_HI> ttaBrandCountCRecordDAO_HI;

	public TtaBrandCountCRecordServer() {
		super();
	}

	@Override
	public void saveBrandplHCountRecordInfo(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TtaBrandCountCRecordEntity_HI instance = new TtaBrandCountCRecordEntity_HI();
		if (null != jsonObject.getInteger("userId") || null != jsonObject.getInteger("varUserId")) {
			Integer userId = jsonObject.getInteger("userId");
			instance.setOperatorUserId(userId);
			instance.setCreatedBy(userId);
			instance.setLastUpdatedBy(userId);
			instance.setLastUpdateLogin(userId);
		}
		instance.setCreationDate(new Date());
		instance.setCreateKey(jsonObject.getString("createKey"));
		instance.setFunctionName("TTA_BRANDPLN");//品牌模块
		instance.setMsgCode("Processing");//执行中
		instance.setMsgRemark("正在进行品牌计算操作");
		instance.setProposalId(jsonObject.getJSONObject("project").getInteger("proposalId") != null ? jsonObject.getJSONObject("project").getInteger("proposalId").toString() : "-1");
		instance.setRequestUrl(request.getRequestURL().toString());
		instance.setRequestParams(JSONObject.toJSONString(jsonObject));
		instance.setStatus("1");//执行中
		ttaBrandCountCRecordDAO_HI.save(instance);
	}

	@Override
	public void updateBrandRecordStatus(JSONObject countParams,Exception e) throws Exception {
		if (countParams == null || StringUtils.isBlank(countParams.getString("createKey")))
			return;
		List<TtaBrandCountCRecordEntity_HI> list = ttaBrandCountCRecordDAO_HI.findByProperty("createKey",countParams.getString("createKey"));
		if (list.size()==0)
			return ;
		Integer userId = null;
		if (null != countParams.getInteger("userId")) {
			userId = countParams.getInteger("userId");
			list.get(0).setOperatorUserId(userId);
		}
		list.get(0).setCompleteDate(new Date());
		list.get(0).setLastUpdatedBy(userId);
		list.get(0).setLastUpdateDate(new Date());
		list.get(0).setLastUpdateLogin(userId);
		if (null != e) {
			list.get(0).setStatus("3");//执行失败
			list.get(0).setMsgCode("failed");
			list.get(0).setMsgRemark("参数信息:\n " + JSONObject.toJSONString(countParams) + "\n执行异常信息:\n"  + Util.getErrorMsg(e));
		} else {
			list.get(0).setStatus("2");
			list.get(0).setMsgCode("successed");
			list.get(0).setMsgRemark("品牌计算操作执行成功!\n参数信息:\n" + JSONObject.toJSONString(countParams));
		}
		ttaBrandCountCRecordDAO_HI.update(list.get(0));
		ttaBrandCountCRecordDAO_HI.fluch();
	}

	@Override
	public void saveProposalSplitRecord(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response,String msgRemark) throws Exception {
		TtaBrandCountCRecordEntity_HI instance = new TtaBrandCountCRecordEntity_HI();
		if (null != jsonObject.getInteger("userId") || null != jsonObject.getInteger("varUserId")) {
			Integer userId = jsonObject.getInteger("userId");
			instance.setOperatorUserId(userId);
			instance.setCreatedBy(userId);
			instance.setLastUpdatedBy(userId);
			instance.setLastUpdateLogin(userId);
		}
		instance.setCreationDate(new Date());
		instance.setCreateKey(jsonObject.getString("createKey"));
		instance.setFunctionName("Proposal拆分与合并");
		instance.setMsgCode("Processing");//执行中
		instance.setMsgRemark(msgRemark);
		//instance.setProposalId();
		instance.setRequestUrl(request.getRequestURL().toString());
		instance.setRequestParams(JSONObject.toJSONString(jsonObject));
		instance.setStatus("1");//执行中
		ttaBrandCountCRecordDAO_HI.save(instance);
	}

	@Override
	public void updateProposalSplitRecordStatus(JSONObject submitParams, Exception e) throws Exception {
		if (submitParams == null || StringUtils.isBlank(submitParams.getString("createKey")))
			return;
		List<TtaBrandCountCRecordEntity_HI> list = ttaBrandCountCRecordDAO_HI.findByProperty("createKey",submitParams.getString("createKey"));
		if (list.size()==0)
			return ;
		Integer userId = null;
		if (null != submitParams.getInteger("userId")) {
			userId = submitParams.getInteger("userId");
			list.get(0).setOperatorUserId(userId);
		}
		list.get(0).setCompleteDate(new Date());
		list.get(0).setLastUpdatedBy(userId);
		list.get(0).setLastUpdateDate(new Date());
		list.get(0).setLastUpdateLogin(userId);
		if (null != e) {
			list.get(0).setStatus("3");//执行失败
			list.get(0).setMsgCode("failed");
			list.get(0).setMsgRemark("Proposal拆分与合并执行失败!\n参数信息:\n " + JSONObject.toJSONString(submitParams) + "\n执行异常信息:\n"  + Util.getErrorMsg(e));
		} else {
			String type = submitParams.getString("type");
			list.get(0).setStatus("2");//执行完成
			list.get(0).setMsgCode("successed");
			if ("1".equals(type)) {
				list.get(0).setMsgRemark("Proposal拆分与合并提交成功!\n参数信息:\n" + JSONObject.toJSONString(submitParams));
			} else if ("2".equals(type)) {
				list.get(0).setMsgRemark("Proposal拆分与合并生成明细成功!\n参数信息:\n" + JSONObject.toJSONString(submitParams));
			}

		}
		ttaBrandCountCRecordDAO_HI.update(list.get(0));
		ttaBrandCountCRecordDAO_HI.fluch();
	}
}
