package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.readonly.TtaAnalysisLineEntity_HI_RO;
import com.sie.watsons.base.feedept.model.entities.readonly.TtaFeeDeptHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.questionnaire.model.inter.server.TtaQuestionChoiceLineServer;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaAnalysisLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaAnalysisLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component("ttaAnalysisLineServer")
public class TtaAnalysisLineServer extends BaseCommonServer<TtaAnalysisLineEntity_HI> implements ITtaAnalysisLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisLineServer.class);

	@Autowired
	private ViewObject<TtaAnalysisLineEntity_HI> ttaAnalysisLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaAnalysisLineEntity_HI_RO> ttaAnalysisLineDAO_HI_RO;

	@Autowired
	private TtaQuestionChoiceLineServer ttaQuestionChoiceLineServer;

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private ITtaAnalysisLine ttaAnalysisLineServer;

	public TtaAnalysisLineServer() {
		super();
	}

	@Override
	public List<TtaAnalysisLineEntity_HI> saveReloadAnalysis(JSONObject paramsJSON, int userId) throws Exception {
		Assert.notNull(paramsJSON.getJSONArray("analysisData"),"Analysis初始化数据为空,加载失败");
		JSONArray jsonArray = paramsJSON.getJSONArray("analysisData");
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		Integer proposalId = jsonObject.getInteger("proposalId");
		String remark = ttaQuestionChoiceLineServer.findQuestionEnglishRemark(proposalId);
		List<TtaAnalysisLineEntity_HI> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject arrayJSONObject = jsonArray.getJSONObject(i);
			TtaAnalysisLineEntity_HI entityHi = SaafToolUtils.setEntity(TtaAnalysisLineEntity_HI.class, arrayJSONObject, ttaAnalysisLineDAO_HI, userId);
			//如果bicRemark字段为空,那么查询问卷调查的remark字段
			if (StringUtils.isEmpty(entityHi.getBicRemark()) || "null".equalsIgnoreCase(entityHi.getBicRemark())) {
				entityHi.setBicRemark(remark);
			}
			list.add(entityHi);
		}
		ttaAnalysisLineDAO_HI.save(list);

		//保存往年的Prposal相关的analysis数据
/*		TtaProposalHeaderEntity_HI byId = ttaProposalHeaderServer.getById(proposalId);
		if (byId.getLastYearProposalId() != null) {
			//通过历史ProposalId查询analysis数据
			List<TtaAnalysisLineEntity_HI> serverAnalysisData = findAnalysisLineByProposalId(byId.getLastYearProposalId());
			if (CollectionUtils.isNotEmpty(serverAnalysisData)) {
				for (TtaAnalysisLineEntity_HI serverAnalysisDatum : serverAnalysisData) {
					for (TtaAnalysisLineEntity_HI lineEntityHi : list) {
						if ("freeGood".equalsIgnoreCase(serverAnalysisDatum.getOiType()) && "freeGood".equalsIgnoreCase(lineEntityHi.getOiType())) {
							serverAnalysisDatum.setCurrentYearOntop(lineEntityHi.getFormerYearOntop());
							serverAnalysisDatum.setCurrentYearTtaOntop(serverAnalysisDatum.getCurrentYearTta().add(serverAnalysisDatum.getCurrentYearOntop()));
							serverAnalysisDatum.setTtaOntopIncreAmt(serverAnalysisDatum.getCurrentYearTtaOntop().subtract(serverAnalysisDatum.getFormerYearTtaOntop()));
							serverAnalysisDatum.setTtaOntopPercentGain(serverAnalysisDatum.getTtaOntopIncreAmt().multiply(new BigDecimal("100")).divide(serverAnalysisDatum.getFormerYearTtaOntop(),2,BigDecimal.ROUND_HALF_UP));
							serverAnalysisDatum.setTtaOntopActIncreAmt(serverAnalysisDatum.getCurrentYearTtaOntop().subtract(serverAnalysisDatum.getFormerYearActual()));
							serverAnalysisDatum.setTtaOntopActPercentGain(serverAnalysisDatum.getTtaOntopActIncreAmt().multiply(new BigDecimal("100")).divide(serverAnalysisDatum.getFormerYearActual(),2,BigDecimal.ROUND_HALF_UP));
						} else if ("ABOI".equalsIgnoreCase(serverAnalysisDatum.getOiType()) && "ABOI".equalsIgnoreCase(lineEntityHi.getOiType()) && (serverAnalysisDatum.getContractLId().intValue() == lineEntityHi.getOldContractLId().intValue())) {
							serverAnalysisDatum.setCurrentYearOntop(lineEntityHi.getFormerYearOntop());
							serverAnalysisDatum.setCurrentYearTtaOntop(serverAnalysisDatum.getCurrentYearTta().add(serverAnalysisDatum.getCurrentYearOntop()));
							serverAnalysisDatum.setTtaOntopIncreAmt(serverAnalysisDatum.getCurrentYearTtaOntop().subtract(serverAnalysisDatum.getFormerYearTtaOntop()));
							serverAnalysisDatum.setTtaOntopPercentGain(serverAnalysisDatum.getTtaOntopIncreAmt().multiply(new BigDecimal("100")).divide(serverAnalysisDatum.getFormerYearTtaOntop(),2,BigDecimal.ROUND_HALF_UP));
							serverAnalysisDatum.setTtaOntopActIncreAmt(serverAnalysisDatum.getCurrentYearTtaOntop().subtract(serverAnalysisDatum.getFormerYearActual()));
							serverAnalysisDatum.setTtaOntopActPercentGain(serverAnalysisDatum.getTtaOntopActIncreAmt().multiply(new BigDecimal("100")).divide(serverAnalysisDatum.getFormerYearActual(),2,BigDecimal.ROUND_HALF_UP));
						}
					}
				}
				ttaAnalysisLineDAO_HI.save(serverAnalysisData);
			}
		}*/

		return list;
	}

	@Override
	public List<TtaAnalysisLineEntity_HI_RO> findAnalysisData(JSONObject jsonObject, int userId) {
		Integer proposalId = jsonObject.getInteger("proposalId");
		Assert.notNull(proposalId,"缺少当前单据参数");
		Map<String,Object> queryParam = new HashMap<>();
		queryParam.put("proposalId",proposalId);
		List<TtaAnalysisLineEntity_HI_RO> analysisLineDAOHiRoList = ttaAnalysisLineDAO_HI_RO.findList(TtaAnalysisLineEntity_HI_RO.QUERY_ANALYSIS, queryParam);
		return analysisLineDAOHiRoList;
	}

	@Override
	public List<TtaAnalysisLineEntity_HI> findAnalysisLineByProposalId(Integer proposalId) {
		Assert.notNull(proposalId,"缺少当前单据参数");
		String sql = "from TtaAnalysisLineEntity_HI l where " +
				"l.proposalId = "+proposalId ;
		List<TtaAnalysisLineEntity_HI> findList = ttaAnalysisLineDAO_HI.findList(sql);
		return findList;
	}
}
