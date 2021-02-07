package com.sie.watsons.base.contract.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractSpecialHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractSpecialHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaContractSpecialHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaContractSpecialHeaderServer")
public class TtaContractSpecialHeaderServer extends BaseCommonServer<TtaContractSpecialHeaderEntity_HI> implements ITtaContractSpecialHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaContractSpecialHeaderServer.class);

	@Autowired
	private ViewObject<TtaContractSpecialHeaderEntity_HI> ttaContractSpecialHeaderDAO_HI;

	@Autowired
	private BaseViewObject<TtaContractSpecialHeaderEntity_HI_RO> ttaContractSpecialHeaderDAO_HI_RO;

	@Autowired
	private GenerateCodeService codeService;

	public TtaContractSpecialHeaderServer() {
		super();
	}

	@Override
	public Pagination<TtaContractSpecialHeaderEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractSpecialHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		SaafToolUtils.parperParam(queryParamJSON, "tcs.order_no", "orderNo", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tcs.vendor_nbr", "vendorNbr", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tcs.vendor_name", "vendorName", sql, paramsMap, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "bu.user_full_name", "createdByName", sql, paramsMap, "fulllike");
		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate1"))) {
			sql.append(" and to_char(tcs.creation_Date,'YYYY-MM-DD') >= :creationDate1");
			paramsMap.put("creationDate1",queryParamJSON.getString("creationDate1"));
		}
		if  (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("creationDate2"))) {
			sql.append(" and to_char(tcs.creation_Date,'YYYY-MM-DD') <= :creationDate2");
			paramsMap.put("creationDate2",queryParamJSON.getString("creationDate2"));
		}

		//SaafToolUtils.parperParam(queryParamJSON, "to_char(tcs.creation_Date,'YYYY-MM-DD')", "creationDate1", sql, paramsMap, ">=");
		//SaafToolUtils.parperParam(queryParamJSON, "to_char(tcs.creation_Date,'YYYY-MM-DD')", "creationDate2", sql, paramsMap, "<=");
		SaafToolUtils.parperParam(queryParamJSON, "tcs.status", "status", sql, paramsMap, "=");

		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tcs.tta_contract_special_header_id desc", false);
		Pagination<TtaContractSpecialHeaderEntity_HI_RO> findList = ttaContractSpecialHeaderDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public void updateStatus(Integer pkId,Integer userId) throws Exception{
		if (pkId == null) {
			return;
		}
		TtaContractSpecialHeaderEntity_HI instance = ttaContractSpecialHeaderDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		if ( !("A".equals(instance.getStatus())) ) {
			throw new IllegalArgumentException("单据状态不是 制单中 不可作废");
		}
		instance.setStatus("E");
		instance.setOperatorUserId(userId);
		ttaContractSpecialHeaderDAO_HI.update(instance);
	}

	@Override
	public TtaContractSpecialHeaderEntity_HI_RO findByRoId(JSONObject queryParamJSON) {
		SaafToolUtils.validateJsonParms(queryParamJSON,"ttaContractSpecialHeaderId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaContractSpecialHeaderEntity_HI_RO.TTA_ITEM_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "tcs.tta_contract_special_header_id", "ttaContractSpecialHeaderId", sql, paramsMap, "=");
		TtaContractSpecialHeaderEntity_HI_RO proposalHeadEntity = (TtaContractSpecialHeaderEntity_HI_RO)ttaContractSpecialHeaderDAO_HI_RO.get(sql,paramsMap);
		return proposalHeadEntity;
	}

	@Override
	public TtaContractSpecialHeaderEntity_HI saveOrUpdate(JSONObject paramsJSON, UserSessionBean userSessionBean, int userId) throws Exception {
		if (null != paramsJSON.getInteger("ttaContractSpecialHeaderId")) {
			TtaContractSpecialHeaderEntity_HI_RO byRoId = findByRoId(paramsJSON);

			if (!"A".equals(byRoId.getStatus())) {
				throw new IllegalArgumentException("单据状态不是 制单中 不可保存");
			}
		}
		TtaContractSpecialHeaderEntity_HI instance = SaafToolUtils.setEntity(TtaContractSpecialHeaderEntity_HI.class, paramsJSON, ttaContractSpecialHeaderDAO_HI, userId);

		if(SaafToolUtils.isNullOrEmpty(instance.getTtaContractSpecialHeaderId())) {
			instance.setStatus("A");
			instance.setOrgCode(userSessionBean.getDeptCode());
			instance.setOrgName(userSessionBean.getDeptName());
			instance.setDeptCode(userSessionBean.getGroupCode());
			instance.setDeptName(userSessionBean.getGroupName());
			instance.setOrderNo(codeService.getTtaContractSpecialHeaderCode());

		}
		if ( (!SaafToolUtils.isNullOrEmpty(paramsJSON.getString("flag"))) && "2".equals(paramsJSON.getString("flag"))) {
			instance.setSubmitBy(userId);
		}
		instance.setOperatorUserId(userId);
		ttaContractSpecialHeaderDAO_HI.saveOrUpdate(instance);
		return instance;
	}

	@Override
	public JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception {

		Integer ttaContractSpecialHeaderId = paramsJSON.getIntValue("id");//单据Id
		JSONObject headerObject = new JSONObject();
		headerObject.put("ttaContractSpecialHeaderId", ttaContractSpecialHeaderId);
		JSONArray result=new JSONArray();
		//查询表单工具
		List<TtaContractSpecialHeaderEntity_HI> Entity = ttaContractSpecialHeaderDAO_HI.findByProperty("ttaContractSpecialHeaderId",headerObject.get("ttaContractSpecialHeaderId"));
		String orderStatus = null;//状态
		switch (paramsJSON.getString("status")) {
			case "REFUSAL":
				orderStatus = "A";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaContractSpecialHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "ALLOW":
				orderStatus = "C";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaContractSpecialHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "DRAFT":
				orderStatus = "A";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaContractSpecialHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "APPROVAL":
				orderStatus = "B";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaContractSpecialHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;
			case "CLOSED":
				orderStatus = "G";
				Entity.get(0).setStatus(orderStatus);
				Entity.get(0).setOperatorUserId(userId);
				ttaContractSpecialHeaderDAO_HI.saveOrUpdate(Entity.get(0));
				break;

		}
		return result;
	}

}
