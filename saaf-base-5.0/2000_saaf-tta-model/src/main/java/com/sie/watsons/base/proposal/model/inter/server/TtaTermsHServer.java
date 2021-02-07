package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaTermsH;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSplitBrandDetail;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ttaTermsHServer")
public class TtaTermsHServer extends BaseCommonServer<TtaTermsHEntity_HI> implements ITtaTermsH{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsHServer.class);

	@Autowired
	private ViewObject<TtaTermsHEntity_HI> ttaTermsHDAO_HI;
	@Autowired
	private BaseViewObject<TtaTermsHEntity_HI_RO> ttaTermsHDAO_HI_RO;
	@Autowired
	BaseViewObject<JSONObject> commonDAO_HI_DY ;
	@Autowired
	private TtaTermsLServer ttaTermsLServer ;

	@Autowired
	private ViewObject<BaseLookupValuesEntity_HI> baseLookupValuesDAO_HI;

	@Autowired
	BaseCommonDAO_HI<TtaContractLineEntity_HI>  ttaContractLineEntityHiBaseCommonDAOHi;

	@Autowired
	private TtaProposalHeaderServer ttaProposalHeaderServer ;

	@Autowired
	private ITtaSplitBrandDetail ttaSplitBrandDetailServer;

	@Autowired
	private BaseViewObject<TtaTermsLEntity_HI_RO> ttaTermsLDAO_HI_RO;


	public TtaTermsHServer() {
		super();
	}



	@Override
	public Pagination<TtaTermsHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer();
		sql.append(TtaTermsHEntity_HI_RO.TTA_ITEM_OLD_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Id", "itemId", sql, paramsMap, "=");
		SaafToolUtils.parperParam(queryParamJSON, "v.status", "status", sql, paramsMap, "in");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Nbr", "itemNbr", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_Cn", "itemDescCn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.item_Desc_En", "itemDescEn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.group_desc", "groupDesc", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.brand_Cn", "brandCn", sql, paramsMap, "like");
		SaafToolUtils.parperParam(queryParamJSON, "v.dept_desc", "deptDesc", sql, paramsMap, "like");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.item_Id desc", false);
		Pagination<TtaTermsHEntity_HI_RO> findList = ttaTermsHDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
		return findList;
	}

	@Override
	public TtaTermsHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
		//保存头
		JSONObject contractParams = paramsJSON.getJSONObject("contractParams");
		JSONArray dataTableTerms = paramsJSON.getJSONArray("dataTableTerms");
		JSONArray deleteTerms = paramsJSON.getJSONArray("deleteTtaTerms");
		JSONObject jsonObject = new JSONObject();
		SaafToolUtils.validateJsonParms(contractParams,"proposalId");
		jsonObject.put("proposalId",contractParams.getInteger("proposalId"));
		TtaTermsHEntity_HI instance = SaafToolUtils.setEntity(TtaTermsHEntity_HI.class, contractParams, ttaTermsHDAO_HI, userId);
		TtaProposalHeaderEntity_HI_RO byRoId = ttaProposalHeaderServer.findByRoId(jsonObject);
		if("Y".equals(byRoId.getIsTermsConfirm())){
			throw new IllegalArgumentException("当前已经确认不能保存");
		}
		ttaTermsHDAO_HI.saveOrUpdate(instance);

		//保存行
		if(null !=dataTableTerms) {
			ttaTermsLServer.saveOrUpdateALL(dataTableTerms,deleteTerms, userId, instance.getTermsHId(), instance.getProposalId(),instance.getBeoiTax());
		}
		return instance;
	}

	@Override
	public void delete(Integer pkId) {
		if (pkId == null) {
			return;
		}
		TtaTermsHEntity_HI instance = ttaTermsHDAO_HI.getById(pkId);
		if (instance == null) {
			return;
		}
		ttaTermsHDAO_HI.delete(instance);
	}


	@Override
	public TtaTermsHEntity_HI_RO saveFindByRoId(JSONObject queryParamJSON) throws Exception{
		String resource = queryParamJSON.getString("resource");

		Integer contractHId = null;
		String venderCode = null;
		if ("vender_contract".equalsIgnoreCase(resource)) {
			JSONObject vendorContractParams = queryParamJSON.getJSONObject("vendorContractParams");
			contractHId = vendorContractParams.getInteger("contractHId");
			venderCode = vendorContractParams.getString("venderCode");
			//查询来自合同拆分
			if (StringUtils.isEmpty(venderCode) || StringUtils.isEmpty(contractHId)) {
				Assert.notNull(contractHId, "必传参数不能为空！");
			}
		}
		SaafToolUtils.validateJsonParms(queryParamJSON,"proposalId");
		StringBuffer sql = new StringBuffer();
		sql.append(TtaTermsHEntity_HI_RO.TTA_ITEM_OLD_V);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(queryParamJSON, "V.proposal_Id", "proposalId", sql, paramsMap, "=");
		TtaTermsHEntity_HI_RO ttaTermsHEntity_hi_ro = ttaTermsHDAO_HI_RO.get(sql, paramsMap);
		if( (!SaafToolUtils.isNullOrEmpty(ttaTermsHEntity_hi_ro)) &&( null == ttaTermsHEntity_hi_ro.getTermsHId() ) && null != ttaTermsHEntity_hi_ro.getOldTermsHId()){

			//hmb 2020.7.31注释
		/*	TtaTermsHEntity_HI ttaTermsHHis = ttaTermsHDAO_HI.getById(ttaTermsHEntity_hi_ro.getOldTermsHId());
			TtaSplitBrandDetailEntity_HI_RO TtaSplitBrandDetail = ttaSplitBrandDetailServer.findSplitBrandByMoney(String.valueOf(Integer.parseInt(ttaTermsHEntity_hi_ro.getProposalYear())-1),ttaTermsHEntity_hi_ro.getOldVendorNbr());
			if (!SaafToolUtils.isNullOrEmpty(TtaSplitBrandDetail)  && !SaafToolUtils.isNullOrEmpty(TtaSplitBrandDetail.getSplitBrandId())) {
				ttaTermsHHis.setIsSplit("1");
				ttaTermsHHis.setFcsSplitPurchse(TtaSplitBrandDetail.getTotalFcsPurchase().add(TtaSplitBrandDetail.getFcsSplitPurchase()));
				ttaTermsHHis.setFcsSplitSales(TtaSplitBrandDetail.getTotalFcsSales().add(TtaSplitBrandDetail.getFcsSplitSales()));
			}else {
				ttaTermsHHis.setIsSplit("0");
				ttaTermsHHis.setFcsSplitPurchse(ttaTermsHHis.getFcsPurchse());
				ttaTermsHHis.setFcsSplitSales(ttaTermsHHis.getFcsSales());
			}
			ttaTermsHDAO_HI.saveOrUpdate(ttaTermsHHis);
			ttaTermsHDAO_HI.fluch();*/
			TtaTermsHEntity_HI ttaTermsHEntity_hi = new TtaTermsHEntity_HI();
			if (SaafToolUtils.isNullOrEmpty(ttaTermsHEntity_hi_ro.getBeoiTaxFlag())) {
				ttaTermsHEntity_hi.setBeoiTax(ttaTermsHEntity_hi_ro.getOldBeoiTax());
			}
			ttaTermsHEntity_hi.setAgreementEdition(ttaTermsHEntity_hi_ro.getOldAgreementEdition());
			ttaTermsHEntity_hi.setInvoiceType(ttaTermsHEntity_hi_ro.getOldInvoiceType());
			ttaTermsHEntity_hi.setVendorDesc(ttaTermsHEntity_hi_ro.getOldVendorDesc());
			ttaTermsHEntity_hi.setVendorNbr(ttaTermsHEntity_hi_ro.getOldVendorNbr());
			ttaTermsHEntity_hi.setRedTicketScale(ttaTermsHEntity_hi_ro.getOldRedTicketScale());
			ttaTermsHEntity_hi.setSalesArea(ttaTermsHEntity_hi_ro.getOldSalesArea());
			ttaTermsHEntity_hi.setWarehouseCode(ttaTermsHEntity_hi_ro.getOldWarehouseCode());
			ttaTermsHEntity_hi.setWarehouseDesc(ttaTermsHEntity_hi_ro.getOldWarehouseDesc());
			ttaTermsHEntity_hi.setIsReturn(ttaTermsHEntity_hi_ro.getOldIsReturn());
			ttaTermsHEntity_hi.setReturnConditions(ttaTermsHEntity_hi_ro.getOldReturnConditions());
			ttaTermsHEntity_hi.setPayDays(ttaTermsHEntity_hi_ro.getOldPayDays());
			ttaTermsHEntity_hi.setConsignmentDiscount(ttaTermsHEntity_hi_ro.getOldConsignmentDiscount());
			ttaTermsHEntity_hi.setSalesUpScale(ttaTermsHEntity_hi_ro.getOldSalesUpScale());
			ttaTermsHEntity_hi.setSalesType(ttaTermsHEntity_hi_ro.getOldSalesType());


			//取当前单据的
			ttaTermsHEntity_hi.setFcsSales(ttaTermsHEntity_hi_ro.getFcsSales());
			ttaTermsHEntity_hi.setFcsPurchse(ttaTermsHEntity_hi_ro.getFcsPurchse());
			ttaTermsHEntity_hi.setGp(ttaTermsHEntity_hi_ro.getGp());
			ttaTermsHEntity_hi.setDeptCode(ttaTermsHEntity_hi_ro.getDeptCode());
			ttaTermsHEntity_hi.setDeptDesc(ttaTermsHEntity_hi_ro.getDeptDesc());
			ttaTermsHEntity_hi.setTermsClass(ttaTermsHEntity_hi_ro.getTermsClass());
			if(SaafToolUtils.isNullOrEmpty(ttaTermsHEntity_hi_ro.getBrandCn())){
				ttaTermsHEntity_hi.setBrandCn(ttaTermsHEntity_hi_ro.getOldBrandCn());
				ttaTermsHEntity_hi.setBrandEn(ttaTermsHEntity_hi_ro.getOldBrandCn());
			}else{
				ttaTermsHEntity_hi.setBrandCn(ttaTermsHEntity_hi_ro.getBrandCn());
				ttaTermsHEntity_hi.setBrandCn(ttaTermsHEntity_hi_ro.getBrandCn());
			}
			ttaTermsHEntity_hi.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
			ttaTermsHEntity_hi.setProposalId(ttaTermsHEntity_hi_ro.getProposalId());
			ttaTermsHDAO_HI.saveOrUpdate(ttaTermsHEntity_hi);
			ttaTermsHDAO_HI.fluch();
			return (TtaTermsHEntity_HI_RO) ttaTermsHDAO_HI_RO.get(sql, paramsMap);
		}else{
			if ("vender_contract".equalsIgnoreCase(resource)) {
				//查询来自合同拆分
				HashMap<String, Object> queryMap = new HashMap<>();
				queryMap.put("venderCode", venderCode);
				queryMap.put("contractHId", contractHId);
				List<Map<String, Object>> list = ttaContractLineEntityHiBaseCommonDAOHi.queryNamedSQLForList("select   a.vendor_name, a.sales_area, a.delivery_granary from tta_contract_line a inner join tta_contract_header b on a.contract_h_id = b.contract_h_id\n" +
						"where b.contract_h_id=:contractHId and a.vendor_code=:venderCode and rownum = 1 ", queryMap);
				ttaTermsHEntity_hi_ro.setVendorDesc("");
				if ((venderCode + "").toUpperCase().contains("P")) {
					venderCode = "";
				}
				ttaTermsHEntity_hi_ro.setVendorNbr(venderCode);
				ttaTermsHEntity_hi_ro.setSalesArea("");
				ttaTermsHEntity_hi_ro.setWarehouseDesc("");
				if (list != null && !list.isEmpty()) {
					Map<String, Object> map = list.get(0);
					ttaTermsHEntity_hi_ro.setVendorDesc(map.get("VENDOR_NAME") + "");
					ttaTermsHEntity_hi_ro.setSalesArea(map.get("SALES_AREA") + "");
					ttaTermsHEntity_hi_ro.setWarehouseDesc(map.get("DELIVERY_GRANARY") + "");
				}

			}
			if ("parent_vender_contract".equalsIgnoreCase(resource)) {
				String vendorNbr = ttaTermsHEntity_hi_ro.getVendorNbr();
				if ((vendorNbr + "").toUpperCase().contains("P")){
					ttaTermsHEntity_hi_ro.setVendorNbr("");
				}
			}
			return ttaTermsHEntity_hi_ro ;
		}

	}

	@Override
	public List<TtaTermsLEntity_HI_RO> saveFindTermLineDataByDeptFee(JSONObject queryParamJSON, Integer userId) throws Exception {
		SaafToolUtils.validateJsonParms(queryParamJSON,"proposalId");
		TtaTermsHEntity_HI_RO ttaTermsHEntity_hi_ro = saveFindByRoId(queryParamJSON);
		StringBuffer sb = new StringBuffer();
		Map<String, Object> paramsMapH = new HashMap<String, Object>();
		if("0".equals(ttaTermsHEntity_hi_ro.getOldYear())){
			sb.append(TtaTermsLEntity_HI_RO.TTA_ITEM);
		}else{
			sb.append(TtaTermsLEntity_HI_RO.TTA_ITEM_OLD);
			paramsMapH.put("proposalId", ttaTermsHEntity_hi_ro.getProposalId()) ;
		}
		sb.append("  order   by orderNo,isMajor desc,unit") ;
		paramsMapH.put("termsHId", ttaTermsHEntity_hi_ro.getTermsHId()) ;
		return  ttaTermsLDAO_HI_RO.findList(sb, paramsMapH);
	}
}
