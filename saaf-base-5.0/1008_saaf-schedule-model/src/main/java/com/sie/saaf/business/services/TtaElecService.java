package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.dao.readonly.TtaElectEntity_RO;
import com.sie.saaf.business.model.dao.readonly.TtaExclusiveHeaderEntity_RO;
import com.sie.saaf.business.model.entities.readonly.TtaObjectSalePurchaseEntity_HI_RO;
import com.sie.saaf.business.model.inter.ITtaObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.sie.saaf.schedule.utils.MyLogUtils;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.sie.saaf.schedule.utils.SaafToolUtils;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
@RestController
@RequestMapping("/ttaElecService")
public class TtaElecService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaElecService.class);

	@Override
	public IBaseCommon getBaseCommonServer() {
		return  (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
	}
	
	@RequestMapping("/saveContraotDetail")
	public String saveBatchTtaShopMarket(JobExecutionContext context) {
		ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		FTPClient ftpClient = null;
		InputStream ins = null;
		BufferedReader reader = null;
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		ArrayList<String> jobErrorList = new ArrayList<>();
		try {
			//1.查询签署中的状态
			List<Map<String, Object>> maps = ttaObjectServer.queryNamedSQLForList(TtaElectEntity_RO.QUERY_ELEC, new HashMap<String, Object>());
			List<TtaElectEntity_RO> list = JSON.parseArray(SaafToolUtils.toJson(maps), TtaElectEntity_RO.class);
			for (TtaElectEntity_RO entity : list) {
				try {
					ttaObjectServer.saveElectStauts(entity);
				} catch (Exception e) {
					jobErrorList.add(com.sie.saaf.common.util.SaafToolUtils.toJson(entity));
					LOGGER.error(this.getClass() + ".调度合同详情信息异常:{}, entity:{}", e, com.sie.saaf.common.util.SaafToolUtils.toJson(entity));
				}
			}
			if (jobErrorList.isEmpty()) {
				MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "调度合同详情信息", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			} else {
				MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), "调度异常的记录有：\n" + SaafToolUtils.toJson(jobErrorList), "调度合同详情信息异常", null,MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".调度合同详情信息异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "调度合同详情信息异常", null,MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}

	/**
	 * 定时更新独家协议(标准/非标准)的Proposal信息
	 * @param context
	 * @return
	 */
	@RequestMapping("/saveExclusiveProposal")
	public String saveExclusiveProposal(JobExecutionContext context) {
		ITtaObject  ttaObjectServer = (ITtaObject) SpringBeanUtil.getBean("ttaObjectServer");
		String scheduleId = MyLogUtils.getArguments(context, "scheduleId")  + "";
		ArrayList<String> jobErrorList = new ArrayList<>();
		try {
			//1.查询审批通过的独家协议单据
			List<Map<String, Object>> maps = ttaObjectServer.queryNamedSQLForList(TtaExclusiveHeaderEntity_RO.QUERY_EXCLUSEVE_INFO, new HashMap<String, Object>());
			List<TtaExclusiveHeaderEntity_RO> list = JSON.parseArray(SaafToolUtils.toJson(maps), TtaExclusiveHeaderEntity_RO.class);
			for (TtaExclusiveHeaderEntity_RO entity : list) {
				try {
					ttaObjectServer.saveExclusiveProposalData(entity);
				} catch (Exception e) {
					jobErrorList.add(com.sie.saaf.common.util.SaafToolUtils.toJson(entity));
					LOGGER.error(this.getClass() + ".调度更新独家协议Proposal异常:{}, entity:{}", e, com.sie.saaf.common.util.SaafToolUtils.toJson(entity));
				}
			}
			if (jobErrorList.isEmpty()) {
				MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), null, "调度更新独家协议Proposal信息", null, MyLogUtils.SCHEDULE_OK_STATUSCODE);
			} else {
				MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), "调度异常的记录有：\n" + SaafToolUtils.toJson(jobErrorList), "调度更新独家协议Proposal异常", null,MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(this.getClass() + ".调度更新独家协议Proposal异常：{}", e);
			MyLogUtils.saveErrorData(Integer.parseInt(scheduleId), MyLogUtils.getErrorMsg(e), "调度更新独家协议Proposal异常", null,MyLogUtils.SCHEDULE_ERROR_STATUSCODE);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		}
	}

}