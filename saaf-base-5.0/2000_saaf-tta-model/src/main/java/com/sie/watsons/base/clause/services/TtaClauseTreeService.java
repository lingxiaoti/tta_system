package com.sie.watsons.base.clause.services;

import com.sie.saaf.common.util.SaafToolUtils;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clause.model.entities.readonly.TtaClauseTreeEntity_HI_RO;
import com.sie.watsons.base.clause.model.inter.ITtaClauseTree;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaClauseTreeService")
public class TtaClauseTreeService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeService.class);
	@Autowired
	private ITtaClauseTree ttaClauseTreeServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaClauseTreeServer;
	}

	/**
	 * 查询条款框架树
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findClauseTree")
	public String findClauseTree(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		queryParamJSON.put("teamFrameworkId",queryParamJSON.get("teamFrameworkId"));
		SaafToolUtils.validateJsonParms(queryParamJSON, "teamFrameworkId");
		List<TtaClauseTreeEntity_HI_RO> clauseTreeList = ttaClauseTreeServer.findClauseTree(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", clauseTreeList.size(), clauseTreeList).toString();
	}

	/**
	 * 查询条款框架详细信息
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findClauseInfo")
	public String findClauseInfo(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		JSONObject clauseTreeInfo = ttaClauseTreeServer.findClauseInfo(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", clauseTreeInfo.size(), clauseTreeInfo).toString();
	}

	/**
	 * 删除条款框架信息
	 * @param params 对象属性的JSON格式
	 * {
	 *     clauseId:条款框架treeID,
	 * }
	 * @return 删除结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteClauseTree")
	public String deleteClauseTree(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "clauseId");
			ttaClauseTreeServer.deleteClauseTree(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("删除条款框架信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("删除条框架信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}
}