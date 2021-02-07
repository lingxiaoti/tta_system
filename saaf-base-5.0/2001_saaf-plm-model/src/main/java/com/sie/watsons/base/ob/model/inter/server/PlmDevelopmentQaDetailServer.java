package com.sie.watsons.base.ob.model.inter.server;

import java.util.ArrayList;
import java.util.Date;
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
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaDetailEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentQaDetail;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDevelopmentQaDetailServer")
public class PlmDevelopmentQaDetailServer extends
		BaseCommonServer<PlmDevelopmentQaDetailEntity_HI> implements
		IPlmDevelopmentQaDetail {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmDevelopmentQaDetailServer.class);
	@Autowired
	private ViewObject<PlmDevelopmentQaDetailEntity_HI> plmDevelopmentQaDetailDAO_HI;
	@Autowired
	private BaseViewObject<PlmDevelopmentQaDetailEntity_HI_RO> plmDevelopmentQaDetailDAO_HI_RO;
	@Autowired
	private ViewObject<PlmDevelopmentQaSummaryEntity_HI> plmDevelopmentQaSummaryDAO_HI;

	public PlmDevelopmentQaDetailServer() {
		super();
	}

	@Override
	public Pagination<PlmDevelopmentQaDetailEntity_HI_RO> findPlmDevelopmentQaDetailInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmDevelopmentQaDetailEntity_HI_RO.QUERY_SQL);
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
			sql = new StringBuffer(
					PlmDevelopmentQaDetailEntity_HI_RO.REPORT_SQL);
		}
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getString("fileStatus_notLike"))) {
			sql.append(" and pdqd.file_status != 'IN_APPROVAL' ");
		}
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperHbmParam(PlmDevelopmentQaDetailEntity_HI_RO.class,
				queryParamJSON, sql, paramsMap);
		sql.append(" order by pdqd.last_update_date desc ");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,
				"count(*)");
		Pagination<PlmDevelopmentQaDetailEntity_HI_RO> pagination = plmDevelopmentQaDetailDAO_HI_RO
				.findPagination(sql, countSql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<PlmDevelopmentQaDetailEntity_HI> savePlmDevelopmentQaDetailInfo(
			JSONObject queryParamJSON) throws Exception {
		List<PlmDevelopmentQaDetailEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmDevelopmentQaDetailList")
						.toString(), PlmDevelopmentQaDetailEntity_HI.class);
		dataList = this.changeStatusByCommand(dataList,
				queryParamJSON.getString("commandStatus"),
				queryParamJSON.getInteger("varUserId"),
				queryParamJSON.getInteger("plmDevelopmentInfoId"),
				queryParamJSON.getInteger("plmProjectId"));
		plmDevelopmentQaDetailDAO_HI.saveOrUpdateAll(dataList);
		if (dataList.size() != 0) {
			this.saveCountsAndRejectReasonIntoSummary(dataList, queryParamJSON);
		}
		return dataList;
	}

	public void saveCountsAndRejectReasonIntoSummary(
			List<PlmDevelopmentQaDetailEntity_HI> dataList,
			JSONObject queryParamJSON) {
		Integer plmDevelopmentQaSummaryId = -1;
		if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getInteger("plmDevelopmentQaSummaryId"))) {
			plmDevelopmentQaSummaryId = queryParamJSON
					.getInteger("plmDevelopmentQaSummaryId");
		} else {
			plmDevelopmentQaSummaryId = dataList.get(0)
					.getPlmDevelopmentQaSummaryId();
		}
//		if (dataList == null) {
//			dataList = new ArrayList<>();
//		}

		List<PlmDevelopmentQaSummaryEntity_HI> summaryList = plmDevelopmentQaSummaryDAO_HI
				.findByProperty("plmDevelopmentQaSummaryId",
						plmDevelopmentQaSummaryId);
		if (summaryList.size() != 0) {
			PlmDevelopmentQaSummaryEntity_HI data = summaryList.get(0);
			if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
					.getString("commandStatus"))
					&& queryParamJSON.getString("commandStatus").equals(
							"PASSED")) {
				data.setRejectReason("");
			} else if (!SaafToolUtils.isNullOrEmpty(queryParamJSON
					.getString("rejectReason"))) {
				data.setRejectReason(queryParamJSON.getString("rejectReason"));
			}
			data.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
			if (dataList == null) {
				data.setFileCount(data.getFileCount() - 1);
			} else {
				data.setFileCount(dataList.size());
			}
			plmDevelopmentQaSummaryDAO_HI.saveOrUpdate(data);
		}

	}

	public List<PlmDevelopmentQaDetailEntity_HI> changeStatusByCommand(
			List<PlmDevelopmentQaDetailEntity_HI> dataList,
			String commandStatus, Integer userId, Integer plmDevelopmentInfoId,
			Integer plmProjectId) {
		for (PlmDevelopmentQaDetailEntity_HI data : dataList) {

			data.setOperatorUserId(userId);
			if (SaafToolUtils.isNullOrEmpty(data.getCreatedBy())) {
				data.setCreatedBy(userId);
			}
			if (plmProjectId != null)
				data.setPlmProjectId(plmProjectId);
			if (plmDevelopmentInfoId != null) {
				data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
			}
			if (SaafToolUtils.isNullOrEmpty(data.getUploadDate())) {
				data.setUploadDate(new Date());
			}
			if (!SaafToolUtils.isNullOrEmpty(commandStatus)) {
				// 重新审批
				if (data.getFileStatus().equals("PASSED")
						&& commandStatus.equals("REAPPROVE")) {
					data.setFileStatus("APPROVING");
				}
				// 同意
				if (commandStatus.equals("PASSED")
						&& (data.getFileStatus().equals("APPROVING"))) {
					data.setFileStatus("PASSED");
				}
				// 驳回
				if (commandStatus.equals("REJECTED")
						&& (data.getFileStatus().equals("APPROVING"))) {
					data.setFileStatus("REJECTED");
				}
				// 提交
				if (commandStatus.equals("APPROVING")
						&& (data.getFileStatus().equals("IN_APPROVAL") || data
								.getFileStatus().equals("REJECTED"))) {
					data.setFileStatus("APPROVING");
				}
				// 附件上传无需变化

				// else if(!data.getFileStatus().equals("PASSED"))
				// data.setFileStatus(commandStatus);
			}
		}
		return dataList;
	}

	@Override
	public Integer deletePlmDevelopmentQaDetailInfo(JSONObject queryParamJSON)
			throws Exception {
		if (SaafToolUtils.isNullOrEmpty(queryParamJSON
				.getJSONArray("plmDevelopmentQaDetailList"))) {
			PlmDevelopmentQaDetailEntity_HI entity = JSON.parseObject(
					queryParamJSON.toString(),
					PlmDevelopmentQaDetailEntity_HI.class);
			plmDevelopmentQaDetailDAO_HI.delete(entity);
			plmDevelopmentQaDetailDAO_HI.fluch();
			this.saveCountsAndRejectReasonIntoSummary(null, queryParamJSON);
			return 1;
		}
		List<PlmDevelopmentQaDetailEntity_HI> dataList = JSON.parseArray(
				queryParamJSON.getJSONArray("plmDevelopmentQaDetailList")
						.toString(), PlmDevelopmentQaDetailEntity_HI.class);
		plmDevelopmentQaDetailDAO_HI.deleteAll(dataList);
		plmDevelopmentQaDetailDAO_HI.fluch();
		queryParamJSON.put("plmDevelopmentQaSummaryId", dataList.get(0)
				.getPlmDevelopmentQaSummaryId());
		this.saveCountsAndRejectReasonIntoSummary(null, queryParamJSON);

		return dataList.size();
	}

}
