package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.sie.saaf.common.bean.FieldAttrIgnore;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaTabLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaTplTabLineEntity_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaTabLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaTabLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("ttaSaTabLineServer")
public class TtaSaTabLineServer extends BaseCommonServer<TtaSaTabLineEntity_HI> implements ITtaSaTabLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaTabLineServer.class);

	@Autowired
	private ViewObject<TtaSaTabLineEntity_HI> ttaSaTabLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaTplTabLineEntity_HI_RO> ttaSaTplTabLineDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaSaTabLineEntity_HI_RO> ttaSaTabLineDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaSaStdProposalLineEntity_HI_RO> ttaSaStdProposalLineDAO_HI;
	@Autowired
	private BaseCommonDAO_HI<TtaSaTabLineEntity_HI> ttaSaTabLineDAOHi;

	public TtaSaTabLineServer() {
		super();
	}

	@Override
	public List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> saveFindTtaSaTplTabLineList(JSONObject queryParamsJSON, int userId) throws Exception{
		Integer saStdTplDefHeaderId = queryParamsJSON.getInteger("saStdTplDefHeaderId");
		JSONObject headerInfo = queryParamsJSON.getJSONObject("headerInfo");//saStdHeaderId头信息
		Integer saStdHeaderId = headerInfo.getInteger("saStdHeaderId");

		//如果有头信息id,那么需要删除当前单据的表格信息
		if (saStdHeaderId != null) {
			String deleteSql = "delete from tta_sa_tab_line tst where tst.sa_std_header_id = " + saStdHeaderId;
			ttaSaTabLineDAO_HI.executeSqlUpdate(deleteSql);
		}

		Map<String, Object> queryParamMap = new HashMap<>();
		StringBuffer querySql = new StringBuffer(TtaSaTplTabLineEntity_HI_RO.QUERY_LIST);
		SaafToolUtils.parperParam(queryParamsJSON, "tsttl.sa_std_tpl_def_header_id", "saStdTplDefHeaderId", querySql, queryParamMap, "=");
		if (saStdTplDefHeaderId != null) {
			querySql.append("  order by tsttl.y_point asc, tsttl.x_point asc");
		} else {//查询全部
			querySql.append("  order by tsttl.sa_std_tpl_def_header_id asc, tsttl.y_point asc, tsttl.x_point asc");
		}
		List<TtaSaTplTabLineEntity_HI_RO> list = ttaSaTplTabLineDAO_HI_RO.findList(querySql, queryParamMap);
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("saStdHeaderId",saStdHeaderId);
		JSONObject jsonParam = new JSONObject();
		this.setParamToJsonObject(paramsMap,jsonParam);

		List<ArrayList<TtaSaTplTabLineEntity_HI_RO>>  returnList = new ArrayList<ArrayList<TtaSaTplTabLineEntity_HI_RO>>();
		if (saStdTplDefHeaderId != null) {
			//构造数据--->二维数组结构
			int num = 0;
			ArrayList<TtaSaTplTabLineEntity_HI_RO> cur = new ArrayList<TtaSaTplTabLineEntity_HI_RO>();
			for (TtaSaTplTabLineEntity_HI_RO tttl : list) {
				if (tttl.getYPoint().intValue() != num) {
					num = tttl.getYPoint().intValue();
					cur = new ArrayList<TtaSaTplTabLineEntity_HI_RO>();
					returnList.add(cur);
				}
				String str = tttl.getXPoint() + tttl.getYPoint();
				tttl.setPointIdentiCode(str);//组装成A1,B1,C1等等
				String pointValue = tttl.getPointValue();
				if (StringUtils.isNotBlank(pointValue)) {
					Map<String,Object> sqlValuesParam = new HashMap<>();
					//Pattern pt = Pattern.compile("[{][{].*?[}][}]");
					Pattern pt = Pattern.compile("[\\[].*?[\\]]");
					Matcher matcher = pt.matcher(pointValue);
					while (matcher.find()) {
						String group = matcher.group();
						String sqlValues = group.substring(1, group.lastIndexOf("]"));
						this.setParams(jsonParam,sqlValuesParam,sqlValues);
						List<Map<String, Object>> mapList = ttaSaTabLineDAOHi.queryNamedSQLForList(sqlValues, sqlValuesParam);
						if (mapList != null && mapList.size() > 0) {
							Object value = mapList.get(0).get("VALUE");
							pointValue = pointValue.replace(group, value + "");
						} else {
							pointValue = pointValue.replace(group, "0");
						}
					}

					Pattern compile = Pattern.compile("[{].*?[}]");//判断每个格子的内容是否包含计算公式
					Matcher findMatch = compile.matcher(pointValue);
					if (!findMatch.find()) {
						tttl.setGridPointValue(pointValue);//设置单元格的值
					} else {//如果找到{},那么就认为是含有单元格公式的
						tttl.setPointExpression(pointValue);
					}
				}

				cur.add(tttl);
			}

		} else {//查询全部

		}
		return returnList ;
	}

	private void setParamToJsonObject(Map<String,Object> paramsMap,JSONObject jsonParam) throws IllegalAccessException {
		List<TtaSaStdProposalLineEntity_HI_RO> proposalList = ttaSaStdProposalLineDAO_HI.findList(TtaSaStdProposalLineEntity_HI_RO.QUERY_PROPOSAL_PARAMS + " and tsspl.sa_std_header_id =:saStdHeaderId", paramsMap);
		if (proposalList != null && proposalList.size() > 0) {
			TtaSaStdProposalLineEntity_HI_RO hi_ro = proposalList.get(0);//暂时取一条数据作为参数
			this.setParamToJsonObject(hi_ro,jsonParam);
		}
	}

	//设置sql执行需要的参数
	private void setParams(JSONObject jsonParam, Map<String, Object> sqlValuesParam,String sqlValues) {
		Pattern compile = Pattern.compile(":\\w{1,}");
		Matcher matcher = compile.matcher(sqlValues);
		while (matcher.find()) {
			String paramKey = matcher.group().replace(":", "");
			sqlValuesParam.put(paramKey,jsonParam.getString(paramKey));
			LOGGER.info("动态sql参数key:{},参数值:{}",paramKey,jsonParam.getString(paramKey));
		}
	}

	//设置JSONObject参数
	private void setParamToJsonObject(Object object, JSONObject jsonParam) throws IllegalAccessException {
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object o = field.get(object);
			FieldAttrIgnore annotation = field.getAnnotation(FieldAttrIgnore.class);
			if (o != null && annotation == null) {
				jsonParam.put(field.getName(),field.get(object));
				LOGGER.info("动态SQL执行需要的参数名:{},参数值:{}",field.getName(),field.get(object));
			}
		}
	}


	@Override
	public List<TtaSaTabLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId) throws Exception {
		JSONArray tableData = queryParamJSON.getJSONArray("tableData");
		Integer saStdHeaderId = queryParamJSON.getInteger("saStdHeaderId");
		Assert.notNull(saStdHeaderId,"补充协议标准头信息未保存,请先保存,再进行保存表格操作");

		List<TtaSaTabLineEntity_HI> tabLineEntity_his = new ArrayList<>();
		if (tableData != null && tableData.size() > 0) {
			for (int i = 0; i < tableData.size(); i++) {//行数组
				JSONArray jsonArray = tableData.getJSONArray(i);//列数组
				for (int j = 0; j < jsonArray.size(); j++) {//每一单元格就是一个对象
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					TtaSaTabLineEntity_HI entityHi = SaafToolUtils.setEntity(TtaSaTabLineEntity_HI.class, jsonObject, ttaSaTabLineDAO_HI, userId);
					entityHi.setSaStdHeaderId(saStdHeaderId);
					tabLineEntity_his.add(entityHi);
				}
			}
			ttaSaTabLineDAO_HI.saveOrUpdateAll(tabLineEntity_his);
		}

		return tabLineEntity_his;
	}

	@Override
	public List<List<TtaSaTabLineEntity_HI_RO>> findSupplementAgreementSandardTabLine(JSONObject queryParamJSON, int userId) throws Exception{
		Integer saStdHeaderId = queryParamJSON.getInteger("saStdHeaderId");
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sql = new StringBuffer(TtaSaTabLineEntity_HI_RO.QUERY_SA_TAB_LINE);
		SaafToolUtils.parperParam(queryParamJSON,"tstl.sa_std_header_id","saStdHeaderId",sql,paramsMap,"=");
		sql.append("  order by tstl.y_point asc, tstl.x_point asc");
		List<TtaSaTabLineEntity_HI_RO> returnList = ttaSaTabLineDAO_HI_RO.findList(sql, paramsMap);
		Map<String,Object> params = new HashMap<>();
		params.put("saStdHeaderId",saStdHeaderId);
		JSONObject jsonParam = new JSONObject();
		this.setParamToJsonObject(params,jsonParam);
		List<List<TtaSaTabLineEntity_HI_RO>>  planarList = new ArrayList<>();
		int flagNum = 0;
		List<TtaSaTabLineEntity_HI_RO> linearArray = new ArrayList<>();
		//构造二维数组数据
		for (TtaSaTabLineEntity_HI_RO lineEntityHiRo : returnList) {
			if (lineEntityHiRo.getYPoint().intValue() != flagNum){
				flagNum = lineEntityHiRo.getYPoint().intValue();
				linearArray = new ArrayList<>();
				planarList.add(linearArray);
			}
			String str = lineEntityHiRo.getXPoint() + lineEntityHiRo.getYPoint();
			lineEntityHiRo.setPointIdentiCode(str);//组装成A1,B1,C1等等
			String pointValue = lineEntityHiRo.getPointValue();
			if (StringUtils.isNotBlank(pointValue)) {
				Map<String,Object> sqlValuesParam = new HashMap<>();
				//Pattern pt = Pattern.compile("[{][{].*?[}][}]");
				Pattern pt = Pattern.compile("[\\[].*?[\\]]");
				Matcher matcher = pt.matcher(pointValue);
				while (matcher.find()) {
					String group = matcher.group();
					String sqlValues = group.substring(1, group.lastIndexOf("]"));
					this.setParams(jsonParam,sqlValuesParam,sqlValues);
					List<Map<String, Object>> mapList = ttaSaTabLineDAOHi.queryNamedSQLForList(sqlValues, sqlValuesParam);
					if (mapList != null && mapList.size() > 0) {
						Object value = mapList.get(0).get("VALUE");
						pointValue = pointValue.replace(group, value + "");
					} else {
						pointValue = pointValue.replace(group, "0");
					}
				}

				Pattern compile = Pattern.compile("[{].*?[}]");//判断每个格子的内容是否包含计算公式
				Matcher findMatch = compile.matcher(pointValue);
				if (!findMatch.find()) {
					lineEntityHiRo.setGridPointValue(pointValue);//设置单元格的值
				} else {//如果找到{},那么就认为是含有单元格公式的
					lineEntityHiRo.setPointExpression(pointValue);
				}
			}
			linearArray.add(lineEntityHiRo);
		}
		return planarList;
	}

}
