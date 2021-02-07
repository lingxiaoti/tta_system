package com.sie.watsons.base.params.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.HtmlUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.params.model.entities.TempParamDefEntity_HI;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.params.model.inter.ITempParamDef;
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

@Component("tempParamDefServer")
public class TempParamDefServer extends BaseCommonServer<TempParamDefEntity_HI> implements ITempParamDef {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempParamDefServer.class);

	@Autowired
	private ViewObject<TempParamDefEntity_HI> tempParamDefDAO_HI;

	@Autowired
	private BaseViewObject<TempParamDefEntity_HI_RO> tempParamDefEntityDao_HI_RO;

	public TempParamDefServer() {
		super();
	}

	@Override
	public Pagination<TempParamDefEntity_HI_RO> findPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TempParamDefEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,"paramKey","isSql");
		SaafToolUtils.parperParam(queryParamJSON,"t.param_key","paramKey",sql,paramsMap,"fulllike");
		SaafToolUtils.parperParam(queryParamJSON,"t.param_content","paramContent",sql,paramsMap,"fulllike");
		SaafToolUtils.parperParam(queryParamJSON,"t.remark","remark",sql,paramsMap,"fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "t.is_sql", "isSql", sql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " t.param_key asc ", false);
		Pagination<TempParamDefEntity_HI_RO> pagination = tempParamDefEntityDao_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		//保存或更新数据后可注释改代码
		if (pagination != null) {
			for (TempParamDefEntity_HI_RO entity_hi_ros : pagination.getData()) {
				//entity_hi_ros.setParamContent(HtmlUtils.clearStyle(HtmlUtils.clearHTMLTag(entity_hi_ros.getParamContent())));//2020.4.16注释,这个方式去掉下划线,因此改用下面的方式
				entity_hi_ros.setParamContent(HtmlUtils.clearStyleExculdeSpan(HtmlUtils.clearHTMLTagExculdeSpan(entity_hi_ros.getParamContent())));
			}
		}
		return pagination;
	}

	@Override
	public void delete(Integer tempId) {
		// TODO Auto-generated method stub
		tempParamDefDAO_HI.delete(tempId);
	}

	@Override
	public TempParamDefEntity_HI_RO findById(Integer tempId) {
		return tempParamDefEntityDao_HI_RO.getById(tempId);
	}


	@Override
	public Pagination<TempParamDefEntity_HI_RO> findNotExistsList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("ruleId", queryParamJSON.getInteger("ruleId"));
		StringBuffer sql = new StringBuffer();
		sql.append(TempParamDefEntity_HI_RO.QUERY_NOT_EXISTS_PARAMS);
		queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,"paramKey");
		SaafToolUtils.parperParam(queryParamJSON,"t.param_key","paramKey",sql,paramsMap,"fulllike");
		queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,"remark");
		SaafToolUtils.parperParam(queryParamJSON,"t.remark","remark",sql,paramsMap,"fulllike");
		queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON,"param_content");
		SaafToolUtils.parperParam(queryParamJSON,"t.param_content","paramContent",sql,paramsMap,"fulllike");
		sql.append("\t order by t.param_key asc");
		Pagination<TempParamDefEntity_HI_RO> pagination = tempParamDefEntityDao_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		//保存或更新数据后可注释改代码
		if (pagination != null) {
			for (TempParamDefEntity_HI_RO entity_hi_ros : pagination.getData()) {
				entity_hi_ros.setParamContent(HtmlUtils.clearStyle(HtmlUtils.clearHTMLTag(entity_hi_ros.getParamContent())));
			}
		}
		return pagination;
	}

	@Override
	public List<TempParamDefEntity_HI_RO> findParamsByRuleId(Integer ruleId) {
		StringBuffer sql = new StringBuffer();
		sql.append(TempParamDefEntity_HI_RO.QUERY_PARAM_BY_RULE);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(new JSONObject().fluentPut("ruleId", ruleId), "b.rule_id", "ruleId", sql, paramsMap, "=");
		return tempParamDefEntityDao_HI_RO.findList(sql, paramsMap);
	}

	@Override
	public void saveOrUpdate(TempParamDefEntity_HI entity_hi) {
		tempParamDefDAO_HI.saveOrUpdate(entity_hi);
	}
}
