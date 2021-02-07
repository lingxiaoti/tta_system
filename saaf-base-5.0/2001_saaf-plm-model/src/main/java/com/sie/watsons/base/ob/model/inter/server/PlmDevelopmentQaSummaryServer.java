package com.sie.watsons.base.ob.model.inter.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaSummaryEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentQaSummary;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDevelopmentQaSummaryServer")
public class PlmDevelopmentQaSummaryServer extends
		BaseCommonServer<PlmDevelopmentQaSummaryEntity_HI> implements
		IPlmDevelopmentQaSummary {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDevelopmentQaSummaryServer.class);
	@Autowired
	private ViewObject<PlmDevelopmentQaSummaryEntity_HI> plmDevelopmentQaSummaryDAO_HI;
	@Autowired
	private BaseViewObject<PlmDevelopmentQaSummaryEntity_HI_RO> plmDevelopmentQaSummaryDAO_HI_RO;

	public PlmDevelopmentQaSummaryServer() {
		super();
	}

	@Override
	public Pagination<PlmDevelopmentQaSummaryEntity_HI_RO> findPlmDevelopmentQaSummaryInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmDevelopmentQaSummaryEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<>();
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
			sql = new StringBuffer(
					PlmDevelopmentQaSummaryEntity_HI_RO.REPORT_SQL);
		}
		SaafToolUtils.parperHbmParam(PlmDevelopmentQaSummaryEntity_HI_RO.class,
				queryParamJSON, sql, paramsMap);
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
			sql.append(" ORDER BY pdi.plm_development_info_id ");
		}
		else{
			sql.append(" order by  pdqs.FILE_ALTER_TYPE ");
		}
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,
				"count(*)");
		Pagination<PlmDevelopmentQaSummaryEntity_HI_RO> pagination = plmDevelopmentQaSummaryDAO_HI_RO
				.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmDevelopmentQaSummaryEntity_HI> savePlmDevelopmentQaSummaryInfo(
			JSONObject queryParamJSON) {
		List<PlmDevelopmentQaSummaryEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmDevelopmentQaSummaryList")
						.toString(), PlmDevelopmentQaSummaryEntity_HI.class);
		dataList = this.changeStatusByCommand(dataList,
				queryParamJSON.getString("commandStatus"),
				queryParamJSON.getInteger("varUserId"),
				queryParamJSON.getInteger("plmDevelopmentInfoId"),
				queryParamJSON.getInteger("plmProjectId"));
		plmDevelopmentQaSummaryDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	public List<PlmDevelopmentQaSummaryEntity_HI> changeStatusByCommand(
			List<PlmDevelopmentQaSummaryEntity_HI> dataList,
			String commandStatus, Integer userId, Integer plmDevelopmentInfoId,
			Integer plmProjectId) {
		for (PlmDevelopmentQaSummaryEntity_HI data : dataList) {

			data.setOperatorUserId(userId);
			if (plmProjectId != null)
				data.setPlmProjectId(plmProjectId);
			if (plmDevelopmentInfoId != null) {
				data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
			}
		}
		return dataList;
	}

	@Override
	public Integer deletePlmDevelopmentQaSummaryInfo(JSONObject queryParamJSON) {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmDevelopmentQaSummaryList"))) {
			PlmDevelopmentQaSummaryEntity_HI entity = JSON.parseObject(
					queryParamJSON.toString(),
					PlmDevelopmentQaSummaryEntity_HI.class);
			plmDevelopmentQaSummaryDAO_HI.delete(entity);
			return 1;
		}
		List<PlmDevelopmentQaSummaryEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmDevelopmentQaSummaryList")
						.toString(), PlmDevelopmentQaSummaryEntity_HI.class);
		plmDevelopmentQaSummaryDAO_HI.deleteAll(dataList);
		return dataList.size();
	}

	/**
	 * 根据产品品类初始化产品资质文件汇总表
	 * 
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public List<PlmDevelopmentQaSummaryEntity_HI> savePlmDevelopmentQaSummaryInfoByCategory(
			JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON, "productCategory",
				"plmProjectId", "plmDevelopmentInfoId");
		String fileTypeArray = returnStringByCategory(queryParamJSON
				.getString("productCategory"));
		List<PlmDevelopmentQaSummaryEntity_HI> dataList = createQaSummaryByCategory(
				fileTypeArray, queryParamJSON.getString("productCategory"),
				queryParamJSON.getInteger("varUserId"),
				queryParamJSON.getInteger("plmProjectId"),
				queryParamJSON.getInteger("plmDevelopmentInfoId"));
		plmDevelopmentQaSummaryDAO_HI.saveOrUpdateAll(dataList);
		return dataList;
	}

	public String returnStringByCategory(String productCategory) {
		String returnString = "";

		if (productCategory.equals("DOMESTIC_COSMETICS")) {
			returnString = "01 成分表确认," + "02 卖点支持证据," + "03 稳定性测试,"
					+ "04 包材兼容性测试," + "05 TRA," + "06 防腐剂功效测试," + "07 皮肤斑贴测试,"
					+ "08 敏感肌测试," + "09 风险物质检测," + "10 第三方检验报告,"
					+ "11 化妆品安全风险评估或毒理测试," + "12 产品功效测试," + "13 AW签署,"
					+ "14 FM签署," + "15 国产非特备案凭证或国产特妆批件," + "16 PSI样品签署,"
					+ "17 PSI," + "18 TPC," + "19 其他";
		} else if (productCategory.equals("IMPORTED_COSMETICS")) {
			returnString = "01 成分表确认," + "02 卖点支持证据," + "03 稳定性测试,"
					+ "04 包材兼容性测试," + "05 TRA," + "06 防腐剂功效测试," + "07 皮肤斑贴测试,"
					+ "08 敏感肌测试," + "09 风险物质检测," + "10 第三方检验报告,"
					+ "11 化妆品安全风险评估或毒理测试," + "12 产品功效测试," + "13 AW签署,"
					+ "14 FM签署," + "16 首批报关单," + "17 首批CIQ报告,"
					+ "18 进口非特备案凭证或进口特妆批件," + "19 PSI样品签署," + "20 PSI,"
					+ "21 TPC," + "22 其他";
		} else if (productCategory.equals("DOMESTIC_FOOD")) {
			returnString = "01 成分表确认," + "02 稳定性测试," + "03 卖点支持证据,"
					+ "04 第三方检验报告," + "05 营养成分检测," + "06 AW签署," + "07 FM签署,"
					+ "08 PSI样品签署," + "09 PSI," + "10 TPC," + "11 其他";
		} else if (productCategory.equals("IMPORTED_FOOD")) {
			returnString = "01 成分表确认," + "02 稳定性测试," + "03 卖点支持证据,"
					+ "04 第三方检验报告," + "05 营养成分检测," + "06 AW签署," + "07 FM签署,"
					+ "08 首批报关单," + "09 首批CIQ报告," + "10 PSI样品签署," + "11 PSI,"
					+ "12 TPC," + "13 其他";
		} else if (productCategory.equals("TOOTHPASTE")) {
			returnString = "01 成分表确认," + "02 卖点支持证据," + "03 稳定性测试,"
					+ "04 包材兼容性测试," + "05 TRA," + "06 防腐剂功效测试," + "07 产品功效测试,"
					+ "08 风险物质检测," + "09 第三方检验报告," + "10 AW签署," + "11 FM签署,"
					+ "12 PSI样品签署," + "13 PSI," + "14 TPC," + "15 其他";
		} else if (productCategory.equals("OTHERS")) {
			returnString = "01 卖点支持证据," + "02 风险物质检测," + "03 敏感肌测试,"
					+ "04 第三方检验报告," + "05 AW签署," + "06 FM签署," + "07 PSI样品签署,"
					+ "08 PSI," + "09 TPC," + "10 其他";
		} else if (productCategory.equals("MEDICAL_EQUIPMENT")) {
			returnString = "01 第三方检验报告," + "02 卖点支持证据," + "03 AW签署,"
					+ "04 FM签署," + "05 PSI样品签署," + "06 PSI," + "07 其他,"
					+ "08 第一类医疗器械备案凭证或二类三类医疗器械注册证," + "09 TPC";
		}

		return returnString;
	}

	public List<PlmDevelopmentQaSummaryEntity_HI> createQaSummaryByCategory(
			String fileTypeArray, String productCategory, Integer userId,
			Integer plmProjectId, Integer plmDevelopmentInfoId) {
		String[] array = fileTypeArray.split(",");
		List<PlmDevelopmentQaSummaryEntity_HI> returnList = new ArrayList<>();
		for (String singleFileType : array) {
			PlmDevelopmentQaSummaryEntity_HI data = new PlmDevelopmentQaSummaryEntity_HI();
			data.setOperatorUserId(userId);
			data.setPlmProjectId(plmProjectId);
			data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
			data.setFileAlterType(singleFileType);
			data.setFileCount(0);
			data.setInapplicableSign("N");
			data.setProductCategory(productCategory);
			returnList.add(data);
		}

		return returnList;
	}

}
