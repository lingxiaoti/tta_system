package com.sie.watsons.report.services;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.report.model.inter.IActBpmTaskConfig;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/processSummaryService")
public class ProcessSummaryService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessSummaryService.class);




	@Autowired
	private IActBpmTaskConfig actBpmTaskConfigServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.actBpmTaskConfigServer;
	}




	@RequestMapping(method = RequestMethod.POST, value = "findNodeList")
	public String findNodeList(@RequestParam(required = false) String params) {
		Map<String, Object> resultMap = new HashMap<>();
		String reuslt = "";
		try {
			JSONObject jsonObject = this.parseObject(params);
			String lookupType = jsonObject.getString("lookupType");
			Assert.notNull(lookupType, "参数不能为空！");
			List<Map<String, Object>> nodeHeadList = actBpmTaskConfigServer.findNodeList(lookupType);//TTA_PROPOSAL_PROCESS_NODE
			List<List<Map<String, Object>>> nodeDataList = actBpmTaskConfigServer.findPropsolProcessSummery(jsonObject);
			resultMap.put("nodeHeadList", nodeHeadList);
			resultMap.put("nodeDataList", nodeDataList);
			reuslt = SToolUtils.convertResultJSONObj("S", "获取流程节点成功！", 0, resultMap).toString();
		} catch (Exception e) {
			reuslt = SToolUtils.convertResultJSONObj("E", "获取流程节点成功异常:" + e.getMessage(), 0, null).toString();
			LOGGER.error(".findNodeList reuslt:{}, exception:{}", reuslt, e);
		}
		return reuslt;
	}


	@RequestMapping(method = RequestMethod.POST, value = "findPropsolProcessSummery")
	public String findPropsolProcessSummery(@RequestParam(required = false) String params) {
		String reuslt = "";
		try {
			JSONObject jsonObject = this.parseObject(params);
			List<List<Map<String, Object>>> propsolProcessSummery = actBpmTaskConfigServer.findPropsolProcessSummery(jsonObject);//TTA_PROPOSAL_PROCESS_NODE
			reuslt = SToolUtils.convertResultJSONObj("S", "获取流程节点成功！", 0, propsolProcessSummery).toString();
		} catch (Exception e) {
			reuslt = SToolUtils.convertResultJSONObj("E", "获取流程节点成功异常:" + e.getMessage(), 0, null).toString();
			LOGGER.error(".findPropsolProcessSummery reuslt:{}, exception:{}", reuslt, e);
		}
		return reuslt;
	}

	/******************************************流程报表start********************************************************************************************/

	@RequestMapping(method = RequestMethod.POST, value = "findContractNodeList")
	public String findContractNodeList(@RequestParam(required = false) String params) {
		Map<String, Object> resultMap = new HashMap<>();
		String reuslt = "";
		try {
			JSONObject jsonObject = this.parseObject(params);
			String lookupType = jsonObject.getString("lookupType");
			Assert.notNull(lookupType, "参数不能为空！");
			List<Map<String, Object>> nodeHeadList = actBpmTaskConfigServer.findNodeList(lookupType);//TTA_PROPOSAL_PROCESS_NODE
			List<List<Map<String, Object>>> nodeDataList = actBpmTaskConfigServer.findContratProcessSummery(jsonObject);
			resultMap.put("nodeHeadList", nodeHeadList);
			resultMap.put("nodeDataList", nodeDataList);
			reuslt = SToolUtils.convertResultJSONObj("S", "获取流程节点成功！", 0, resultMap).toString();
		} catch (Exception e) {
			reuslt = SToolUtils.convertResultJSONObj("E", "获取流程节点成功异常:" + e.getMessage(), 0, null).toString();
			LOGGER.error(".findContractNodeList reuslt:{}, exception:{}", reuslt, e);
		}
		return reuslt;
	}

	/******************************************流程报表end********************************************************************************************/


}