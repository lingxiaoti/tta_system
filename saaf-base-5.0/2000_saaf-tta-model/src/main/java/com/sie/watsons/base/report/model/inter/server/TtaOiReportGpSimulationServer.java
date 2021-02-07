package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.*;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiReportGpSimulationEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaOiReportGpSimulation;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("ttaOiReportGpSimulationServer")
public class TtaOiReportGpSimulationServer extends BaseCommonServer<TtaOiReportGpSimulationEntity_HI> implements ITtaOiReportGpSimulation{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiReportGpSimulationServer.class);

	@Autowired
	private GenerateCodeService codeService;

	@Autowired
	private BaseCommonDAO_HI<TtaOiReportGpSimulationEntity_HI> ttaOiReportGpSimulationDAO_HI;

	@Autowired
	private ViewObject<TtaOiReportHeaderEntity_HI> ttaOiReportHeaderDAO_HI;


	@Autowired
	private BaseViewObject<TtaOiReportGpSimulationEntity_HI_RO> ttaOiReportGpSimulationDAO_HI_RO;

	public TtaOiReportGpSimulationServer() {
		super();
	}

	@Override
	public Pagination<TtaOiReportGpSimulationEntity_HI_RO> findGpSimulationList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Integer userId = queryParamJSON.getInteger("userId");
		String userType =queryParamJSON.getString("userType");
		String batchCode = queryParamJSON.getString("batchCode");
		StringBuffer sql = new StringBuffer();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		sql.append(TtaOiReportGpSimulationEntity_HI_RO.getGpSimulationSql(userType, userId,batchCode));
		SaafToolUtils.parperParam(queryParamJSON, "tr.batch_code", "batchCode", sql, queryMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "tr.order_nbr", "orderNbr", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.vendor_nbr", "vendorNbr", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.vendor_name", "vendorName", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.group_name", "groupName", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.dept_desc", "deptDesc", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.brand_en", "brandEn", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.brand_cn", "brandCn", sql, queryMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tr.major_dept_desc", "majorDeptDesc", sql, queryMap, "fulllike");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tr.order_nbr desc", false);
		Pagination<TtaOiReportGpSimulationEntity_HI_RO> resultList =ttaOiReportGpSimulationDAO_HI_RO.findPagination(sql,queryMap,pageIndex,pageRows);
		return resultList;
	}

	/**
	 * 根据日期范围创建单据。
	 */
	@Override
	public JSONObject saveGpSimulation(JSONObject jsonObject, Integer userId) {
		JSONObject resultJson = new JSONObject();
		String endDate = jsonObject.getString("endDate").replace("-","");
		jsonObject.put("endDate", endDate);
		String batchCode = codeService.getTtaOiCw("GP");
		ttaOiReportGpSimulationDAO_HI.executeSqlUpdate(TtaOiReportGpSimulationEntity_HI_RO.getGenericSql(batchCode, userId, endDate));
		TtaOiReportHeaderEntity_HI oiReportHeaderEntity = new TtaOiReportHeaderEntity_HI();
		oiReportHeaderEntity.setOperatorUserId(userId);
		oiReportHeaderEntity.setStatus("DS01"); //制单中
		oiReportHeaderEntity.setIsPublish("N");
		oiReportHeaderEntity.setBatchCode(batchCode);
		oiReportHeaderEntity.setReportType("GP");
		ttaOiReportHeaderDAO_HI.saveOrUpdate(oiReportHeaderEntity);
		resultJson.put("report", oiReportHeaderEntity);
		return resultJson;
	}

	@Override
	public void saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception{
		JSONArray save = paramsJSON.getJSONArray("save");
		String confirmStatus = paramsJSON.getString("confirmStatus");//为确认或者取消状态时设置确认/取消时间
		List<TtaOiReportGpSimulationEntity_HI> entityList = new ArrayList<>();
		for(int i = 0 ;i< save.size();i++){
			JSONObject  json = (JSONObject)save.get(i) ;
			TtaOiReportGpSimulationEntity_HI instance = SaafToolUtils.setEntity(TtaOiReportGpSimulationEntity_HI.class, json, ttaOiReportGpSimulationDAO_HI, userId);
			if (StringUtils.isNotBlank(confirmStatus)) {
				instance.setConfirmDate(new Date());
			}
			entityList.add(instance);
		}
		ttaOiReportHeaderDAO_HI.saveOrUpdateAll(entityList);
	}

	@Override
	public Map<String, Object> findSummaryGpSimulation(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<>();
		List<List<String>> resultList = new ArrayList<>();
		LinkedHashSet<String> actionCodeSet = new LinkedHashSet<>();
		LinkedHashSet<String> groupNameSet = new LinkedHashSet<>();
		Map<String,Object> keyValMap = new HashMap<>();
		String batchCode = jsonObject.getString("batchCode");
		List<Map<String, Object>> listMap = ttaOiReportGpSimulationDAO_HI.queryNamedSQLForList(TtaOiReportGpSimulationEntity_HI_RO.getSummarySql(batchCode), new HashMap<String, Object>());
		if (listMap != null) {
			for (Map<String, Object> map : listMap) {
				String actionName = map.get("ACTION_NAME") + "";
				String groupName = map.get("GROUP_NAME") + "";
				actionCodeSet.add(actionName);
				groupNameSet.add(groupName);
				keyValMap.put(map.get("KEY") + "", map.get("VALUE"));
			}
		}
		//统计列合集数
		Map<Integer, String> colTotal = new HashMap<>(); // 计算列
		Map<Integer, String> rowTotal = new HashMap<>();//计算行

		List<String> actionCodeList = new ArrayList<>(actionCodeSet);
		List<String> groupNameList = new ArrayList<>(groupNameSet);
		List<String> colList = null;
		for (int i = 0; i < actionCodeList.size(); i ++) {
			String actionName = actionCodeList.get(i);
			colList = new ArrayList<>();
			for (int j = 0; j < groupNameList.size(); j ++) {
				String groupName = groupNameList.get(j);
				Object value = keyValMap.get(actionName + "@" + groupName);
				String strValue = "0";
				if (value != null) {
					strValue = value + "";
				}
				colList.add(strValue);
				setRowOrColTotal(colTotal, j, strValue);
				setRowOrColTotal(rowTotal, i, strValue);
			}
			colList.add(0, actionName);
			resultList.add(colList);
		}
		groupNameList.add(0, "Action Code");
		groupNameList.add("TOTAL");
		Long colLastTotal = 0L;
		for (int i = 0; i < resultList.size(); i++) {
			String value = rowTotal.get(i);
			colLastTotal += Long.parseLong(value);
			resultList.get(i).add(value);//将每行的total数填充
		}
		colTotal.put(colTotal.size(), colLastTotal + "");
		colTotal.put(-1,"TOTAL");
		//列的汇总结果设置到每列
		List<List<String>> lastRow = new ArrayList<>();
		List<String> list = new ArrayList<>();
		List<String> array = resultList.get(0);
		for (int idx = 0;  idx < array.size(); idx ++) {
			list.add(colTotal.get(idx - 1));
		}
		lastRow.add(list);
		resultList.addAll(lastRow);
		resultMap.put("headList", groupNameList);
		resultMap.put("dateList",resultList);
		return resultMap;
	}

	private void setRowOrColTotal(Map<Integer, String> rowTotal, int i, String strValue) {
		String rowVlue = rowTotal.get(i);
		if (StringUtils.isNotBlank(rowVlue)) {
			rowTotal.put(i, (Long.parseLong(rowVlue) + Long.parseLong(strValue) + ""));
		} else {
			rowTotal.put(i, (Long.parseLong(strValue) + ""));
		}
	}


	public static final String getSaleGpSql(String startDate, String endDate) {
		StringBuffer buffer = new StringBuffer();
		while (endDate.compareTo(startDate) >= 0) {
			buffer.append(" select " + startDate + " as tran_date,  item as item_nbr, sales_exclude_amt, gp, sales_qty from tta_sales_in_")
					.append(startDate).append(" where nvl(sales_exclude_amt,0) != 0 ").append("\n\tunion all\n");
			startDate = SaafDateUtils.dateDiffMonth(startDate, 1);
		}
		return buffer.substring(0, buffer.lastIndexOf("\n\tunion all\n"));
	}
}
