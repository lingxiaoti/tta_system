package com.sie.watsons.base.questionnaire.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntityModel;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionNewMapDetailEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionNewMapDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ttaQuestionNewMapDetailService")
public class TtaQuestionNewMapDetailService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionNewMapDetailService.class);

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private ITtaQuestionNewMapDetail ttaQuestionNewMapDetailServer;
	
	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaQuestionNewMapDetailServer;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "deleteQuestionNewMapDetail")
	public String deleteQuestionNewMapDetail(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonParam = this.parseObject(params);
			ttaQuestionNewMapDetailServer.deleteById(jsonParam.getInteger("mapDetailId"));
			LOGGER.info(".deleteQuestionNewMapDetail params:{}", params);
			return this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
		} catch (Exception e) {
			LOGGER.error(".deleteQuestionNewMapDetail:{}", e);
			return this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
	}


	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpadateBatchDetail")
	public String saveOrUpadateBatchDetail(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			ttaQuestionNewMapDetailServer.saveOrUpadateBatchDetail(jsonObject);
			Pagination<TtaQuestionNewMapDetailEntity_HI_RO> entityList = ttaQuestionNewMapDetailServer.queryQuestionNewMapDetailList(jsonObject, 1, 10000);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entityList).toString();
		} catch (Exception e) {
			LOGGER.error("saveOrUpadateBatchDetail:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "queryQuestionNewMapDetailList")
	public String queryQuestionNewMapDetailList(@RequestParam(required = true) String params,
												@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
												@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaQuestionNewMapDetailEntity_HI_RO> pagination = ttaQuestionNewMapDetailServer.queryQuestionNewMapDetailList(jsonObject, 1, 10000);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toJSONString();
			//return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entityList).toString();
		} catch (Exception e) {
			LOGGER.error("queryQuestionNewMapDetailList:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "importNewProductList")
	public String importNewProductList(@RequestParam("file") MultipartFile file){
		try{
			String proposalId = request.getParameter("proposalId");
			Assert.notNull(proposalId, "proposlId 不能为空!");
			JSONObject jsonObject = parseObject("");
			TtaProposalHeaderEntity_HI proposalEntity = ttaProposalHeaderServer.getById(proposalId);
			jsonObject.put("proposalId", proposalId);
			jsonObject.put("saleType", proposalEntity.getSaleType());
			ttaQuestionNewMapDetailServer.saveImportNewProductList(jsonObject, file);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "据导入成功", 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
			//return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}


}