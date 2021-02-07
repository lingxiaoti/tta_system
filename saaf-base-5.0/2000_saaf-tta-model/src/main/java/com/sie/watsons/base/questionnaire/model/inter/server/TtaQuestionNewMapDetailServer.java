package com.sie.watsons.base.questionnaire.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.questionnaire.model.dao.readonly.TtaQuestionNewMapDetailDAO_HI_RO;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntityModel;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionNewMapDetailEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionNewMapDetail;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaQuestionNewMapDetailServer")
public class TtaQuestionNewMapDetailServer extends BaseCommonServer<TtaQuestionNewMapDetailEntity_HI> implements ITtaQuestionNewMapDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionNewMapDetailServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaQuestionNewMapDetailEntity_HI> ttaQuestionNewMapDetailDAO_HI;

	@Autowired
	private TtaQuestionNewMapDetailDAO_HI_RO ttaQuestionNewMapDetailDAO_HI_RO;

	public TtaQuestionNewMapDetailServer() {
		super();
	}


	@Override
	public void saveOrUpadateBatchDetail(JSONObject jsonParams) {
		if (jsonParams != null && jsonParams.getJSONArray("lineArr") != null) {
			JSONArray lineArr = jsonParams.getJSONArray("lineArr");
			List<TtaQuestionNewMapDetailEntity_HI> list = JSON.parseArray(lineArr.toJSONString(), TtaQuestionNewMapDetailEntity_HI.class);
			Integer varUserId = jsonParams.getInteger("varUserId");
			for (TtaQuestionNewMapDetailEntity_HI entity : list) {
				entity.setLastUpdatedBy(varUserId);
				entity.setCreatedBy(varUserId);
				entity.setOperatorUserId(varUserId);
			}
			ttaQuestionNewMapDetailDAO_HI.saveOrUpdateAll(list);
		}
	}

	@Override
	public Pagination<TtaQuestionNewMapDetailEntity_HI_RO> queryQuestionNewMapDetailList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(TtaQuestionNewMapDetailEntity_HI_RO.querySlq);
		SaafToolUtils.parperParam(queryParamJSON, "t.proposal_id", "proposalId", sbSql, paramsMap, "=");
		SaafToolUtils.changeQuerySort(queryParamJSON, sbSql, " map_detail_id asc", false);
		return ttaQuestionNewMapDetailDAO_HI_RO.findPagination(sbSql.toString(),SaafToolUtils.getSqlCountString(sbSql), paramsMap, pageIndex, pageRows);
	}

	@Override
	public void saveImportNewProductList(JSONObject jsonObject, MultipartFile file) throws  Exception {
		String proposalId = jsonObject.getString("proposalId");
		String saleType = jsonObject.getString("saleType");
		Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaQuestionNewMapDetailEntityModel.class,0);
		List<Map<String, Object>> datas = (List<Map<String, Object>>)result.get("datas");
		if (datas != null) {
			datas.forEach(item->{
				item.put("OPERATOR_USER_ID", jsonObject.get("varUserId"));
				item.put("PROPOSAL_ID", proposalId);
				String cost = item.get("COST") + "";
				String retailPrice = item.get("RETAIL_PRICE") + "";
				String promoPrice = item.get("PROMO_PRICE") + "";
				if (StringUtils.isBlank(cost) || "null".equalsIgnoreCase(cost)) {
					Assert.isTrue(false, "上传新品附件有cost为空的值，请检查");
				}
				if (StringUtils.isBlank(retailPrice) || "null".equalsIgnoreCase(retailPrice)) {
					Assert.isTrue(false, "上传新品附件有retailPrice为空的值，请检查");
				}
				if (StringUtils.isBlank(promoPrice) || "null".equalsIgnoreCase(promoPrice)) {
					Assert.isTrue(false, "上传新品附件有promoPrice为空的值，请检查");
				}

				Double normalGp = BigDecimalUtils.formatRoundUp(BigDecimalUtils.subtract(1, BigDecimalUtils.divide(Double.parseDouble(cost), Double.parseDouble(retailPrice))) * 100, 2);
				item.put("NORMAL_GP",  normalGp);

				if ("B01".equalsIgnoreCase(saleType)) {
					if (StringUtils.isNotEmpty(cost) && StringUtils.isNotEmpty(promoPrice)) {
						Double promoGp  = BigDecimalUtils.formatRoundUp(BigDecimalUtils.subtract(1, BigDecimalUtils.divide(Double.parseDouble(cost), Double.parseDouble(promoPrice))) * 100, 2);
						item.put("PROMO_GP", promoGp);
					}
				} else {
					item.put("PROMO_GP", item.get("NORMAL_GP"));
				}
			});
		}
		List<TtaQuestionNewMapDetailEntity_HI> newMapDetailList = JSON.parseArray(SaafToolUtils.toJson(datas), TtaQuestionNewMapDetailEntity_HI.class);
		ttaQuestionNewMapDetailDAO_HI.saveOrUpdateAll(newMapDetailList);
		//ttaQuestionNewMapDetailDAO_HI.saveSeqBatchJDBC("TTA_QUESTION_NEW_MAP_DETAIL",datas,"MAP_DETAIL_ID","SEQ_TTA_QUESTION_NEW_MAP_DETAIL.NEXTVAL");
		System.out.println(datas);
	}

	public static void main(String[] args) {
		Double subtract = BigDecimalUtils.formatRoundUp(BigDecimalUtils.subtract(1, BigDecimalUtils.divide(1.0, 3.0)) * 100,2);
		System.out.println(subtract);
	}
}
