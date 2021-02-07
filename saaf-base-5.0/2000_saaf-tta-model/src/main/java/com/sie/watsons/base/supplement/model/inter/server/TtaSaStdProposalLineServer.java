package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.TtaSideAgrtLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdProposalLineEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdProposalLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.supplement.model.inter.ITtaSaStdProposalLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;

@Component("ttaSaStdProposalLineServer")
public class TtaSaStdProposalLineServer extends BaseCommonServer<TtaSaStdProposalLineEntity_HI> implements ITtaSaStdProposalLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdProposalLineServer.class);

	@Autowired
	private ViewObject<TtaSaStdProposalLineEntity_HI> ttaSaStdProposalLineDAO_HI;
	@Autowired
	private BaseViewObject<TtaSaStdProposalLineEntity_HI_RO> ttaSaStdProposalLineDAO_HI_RO;
	@Autowired
	private BaseViewObject<TtaProposalHeaderEntity_HI_RO> ttaProposalHeaderDAO_HI_RO;

	public TtaSaStdProposalLineServer() {
		super();
	}

	@Override
	public Pagination<TtaSaStdProposalLineEntity_HI_RO> findProposalVendor(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String,Object> paramsMap = new HashMap<>();
		String flag = queryParamJSON.getString("flag");
		queryParamJSON.put("effectiveStartTime", StringUtils.isBlank(queryParamJSON.getString("effectiveStartTime")) ?
				-1 : queryParamJSON.getString("effectiveStartTime").substring(0,4));
		queryParamJSON.put("effectiveEndTime",StringUtils.isBlank(queryParamJSON.getString("effectiveEndTime")) ?
				-1 : queryParamJSON.getString("effectiveEndTime").substring(0,4));
		StringBuffer sql = new StringBuffer();
		if (StringUtils.isNotEmpty(flag) && "exclusive".equals(flag)) {
			sql.append(TtaProposalHeaderEntity_HI_RO.CONTRACT_EDIT_PROPOSAL);
			sql.append(" and not exists(\n" +
					"    select 1 from tta_sole_supplier tss where tss.supplier_code = t.VENDOR_CODE and tss.sole_agrt_h_id =:soleAgrtHId\n" +
							") ");
			paramsMap.put("soleAgrtHId",Integer.valueOf(queryParamJSON.getString("soleAgrtHId")));
			String lastYear = (Integer.valueOf(queryParamJSON.getString("effectiveStartTime")) - 1) + "";
			queryParamJSON.put("effectiveStartTime",lastYear);
		} else {
			sql.append(TtaProposalHeaderEntity_HI_RO.CONTRACT_SPLIT_PROPOSAL_VENDOR);
		}
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_ORDER", "proposalOrder", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.CONDITION_VENDOR", "vendorCode", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.VENDOR_NAME", "vendorName", sql, paramsMap, "like", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "effectiveStartTime", sql, paramsMap, ">=", false);
		SaafToolUtils.parperParam(queryParamJSON, "t.PROPOSAL_YEAR", "effectiveEndTime", sql, paramsMap, "<=", false);
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " t.proposal_year asc ", false);
		Pagination<TtaSaStdProposalLineEntity_HI_RO> pagination = ttaSaStdProposalLineDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public TtaSaStdProposalLineEntity_HI saveContractProposalVendor(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception {
		JSONObject jsonObject = queryParamJSON.getJSONObject("project");
		Assert.notNull(jsonObject.getInteger("saStdHeaderId"),"头信息未保存,请先进行头信息保存再进行操作");
		Integer saStdHeaderId = jsonObject.getInteger("saStdHeaderId");
		JSONObject selectRow = queryParamJSON.getJSONObject("selectRow");
		TtaProposalHeaderEntity_HI proposalHeaderEntity = selectRow.toJavaObject(TtaProposalHeaderEntity_HI.class);
		StringBuffer sql = new StringBuffer(TtaSaStdProposalLineEntity_HI_RO.QUERY);
		Map<String,Object> paramMap = new HashMap<>();
		//SaafToolUtils.parperParam(selectRow,"t.proposal_order","proposal_order",sql,paramMap,"=");
		//SaafToolUtils.parperParam(selectRow,"t.proposal_version","proposal_version",sql,paramMap,"=");
		SaafToolUtils.parperParam(selectRow,"t.proposal_id","proposalId",sql,paramMap,"=");
		sql.append(" and t.sa_std_header_id = " + saStdHeaderId);
		List<TtaSaStdProposalLineEntity_HI_RO> proposalLineEntityHiRos = ttaSaStdProposalLineDAO_HI_RO.findList(sql, paramMap);
		if (CollectionUtils.isNotEmpty(proposalLineEntityHiRos)){
			String tipMsg = "选择的Proposal合同号为" + proposalLineEntityHiRos.get(0).getProposalOrder() + ",版本号"+proposalLineEntityHiRos.get(0).getVendorCode()+",在系统中已存在,不能再次添加";
			throw new IllegalArgumentException(tipMsg);
		}
		selectRow.put("saStdHeaderId",saStdHeaderId);
		TtaSaStdProposalLineEntity_HI entity_hi= SaafToolUtils.setEntity(TtaSaStdProposalLineEntity_HI.class, selectRow, ttaSaStdProposalLineDAO_HI, sessionBean.getUserId());
		//TtaSaStdProposalLineEntity_HI entity_hi = this.setTtaSaStdProposalLineObject(proposalHeaderEntity,saStdHeaderId,sessionBean.getUserId());
		ttaSaStdProposalLineDAO_HI.saveOrUpdate(entity_hi);
		return entity_hi;
	}

	@Override
	public Pagination<TtaSaStdProposalLineEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(TtaSaStdProposalLineEntity_HI_RO.QUERY);
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(jsonObject,"t.sa_std_header_id","saStdHeaderId",sql,paramsMap,"=");
		SaafToolUtils.parperParam(jsonObject,"t.proposal_order","proposalOrder",sql,paramsMap,"=");
		SaafToolUtils.parperParam(jsonObject,"t.proposal_version","proposalVersion",sql,paramsMap,"=");
		if (null == jsonObject.getInteger("saStdHeaderId")) {
			int saStdHeaderId = -1;
			sql.append("and t.sa_std_header_id = " + saStdHeaderId);
		}
		Pagination<TtaSaStdProposalLineEntity_HI_RO> pagination = ttaSaStdProposalLineDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public TtaSaStdProposalLineEntity_HI delete(JSONObject jsonObject) throws Exception {
		Integer id = jsonObject.getInteger("id");
		Assert.notNull(id, "参数id错误");
		LOGGER.info("参数的id: {}",id);
		TtaSaStdProposalLineEntity_HI entity = ttaSaStdProposalLineDAO_HI.getById(id);
		if (entity == null) {
			throw new IllegalArgumentException("选择的数据不存在,删除失败");
		}
		ttaSaStdProposalLineDAO_HI.delete(entity);
		return entity;
	}
}
