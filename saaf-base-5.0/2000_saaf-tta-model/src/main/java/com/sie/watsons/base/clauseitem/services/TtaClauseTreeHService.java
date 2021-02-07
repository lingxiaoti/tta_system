package com.sie.watsons.base.clauseitem.services;
import com.sie.saaf.common.util.SaafToolUtils;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaClauseTreeHEntity_HI_RO;
import com.sie.watsons.base.clauseitem.model.inter.ITtaClauseTreeH;
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
@RequestMapping("/ttaClauseTreeHService")
public class TtaClauseTreeHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaClauseTreeHService.class);

	@Autowired
	private ITtaClauseTreeH ttaClauseTreeHServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaClauseTreeHServer;
	}

	/**
	 * 查询条款名目树
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findClausehTree")
	public String findClausehTree(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		queryParamJSON.put("teamFrameworkId",queryParamJSON.get("teamFrameworkId"));
		SaafToolUtils.validateJsonParms(queryParamJSON, "teamFrameworkId");
		List<TtaClauseTreeHEntity_HI_RO> clauseTreeList = ttaClauseTreeHServer.findClausehTree(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", clauseTreeList.size(), clauseTreeList).toString();
	}

	/**
	 * 查询条款名目详细信息
	 * @param params
	 * {
	 *     teamFrameworkId:框架ID
	 * }
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findClausehInfo")
	public String findClausehInfo(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		JSONObject clauseTreeInfo = ttaClauseTreeHServer.findClausehInfo(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", clauseTreeInfo.size(), clauseTreeInfo).toString();
	}

	/**
	 * 删除条款名目信息
	 * @param params 对象属性的JSON格式
	 * {
	 *     clauseId:条款名目treeID,
	 * }
	 * @return 删除结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteClausehTree")
	public String deleteClausehTree(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "clauseId");
			ttaClauseTreeHServer.deleteClausehTree(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("删除条款名目信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("删除条款名目信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

}