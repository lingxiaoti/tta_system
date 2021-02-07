package com.sie.watsons.base.exclusive.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleSupplier;
import org.springframework.util.Assert;

import java.util.*;

@Component("ttaSoleSupplierServer")
public class TtaSoleSupplierServer extends BaseCommonServer<TtaSoleSupplierEntity_HI> implements ITtaSoleSupplier{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleSupplierServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaSoleSupplierEntity_HI> ttaSoleSupplierDAO_HI;

	@Autowired
	private BaseViewObject<TtaSoleSupplierEntity_HI_RO> ttaSoleSupplierDAO_HI_RO;

	public TtaSoleSupplierServer() {
		super();
	}

	/**
	 *  查找数据
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */

	@Override
	public Pagination<TtaSoleSupplierEntity_HI_RO> findSoleSupplierPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sb = new StringBuffer(TtaSoleSupplierEntity_HI_RO.QUERY_CONTRACT__SUPPLIER);
		Map<String, Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(queryParamJSON, "tch.proposal_code", "poposalCode", sb, paramsMap, "like", false);
		changeQuerySort(queryParamJSON, sb, "tcl.contract_l_id asc", false);
		Pagination<TtaSoleSupplierEntity_HI_RO> findListResult = ttaSoleSupplierDAO_HI_RO.findPagination(sb, paramsMap, pageIndex, pageRows);
		return findListResult;
	}

	/**
	 * 检查数据是否重复
	 * @param queryParamJSON
	 */
	@Override
	public void checkOut(JSONObject queryParamJSON)throws Exception {
	//如果重复抛出异常,给调用者处理
	}

	/**
	 * 保存proposal供应商数据
	 * @param queryParamJSON
	 * @return
	 */
	@Override
	public TtaSoleSupplierEntity_HI saveProposalSupplier(JSONObject queryParamJSON) throws Exception{
		Integer soleAgrtHId = queryParamJSON.getInteger("soleAgrtHId");
		Integer varUserId = queryParamJSON.getInteger("varUserId");
		Assert.notNull(soleAgrtHId,"头信息未保存,请先保存再进行操作");
		JSONObject selectRow = queryParamJSON.getJSONObject("selectRow");
		StringBuffer sql = new StringBuffer(TtaSoleSupplierEntity_HI_RO.QUEREY_PROPOSAL_CONTRACT_VENDOR);
		Map<String,Object> paramsMap = new HashMap<>();
		SaafToolUtils.parperParam(selectRow,"tss.poposal_id","proposalId",sql,paramsMap,"=");
		sql.append(" and tss.sole_agrt_h_id = " + soleAgrtHId);
		List<TtaSoleSupplierEntity_HI_RO> list = ttaSoleSupplierDAO_HI_RO.findList(sql, paramsMap);
		if (CollectionUtils.isNotEmpty(list)){
			String tipMsg = "选择的Proposal合同号为" + list.get(0).getPoposalCode() + ",版本号"+list.get(0).getProposalVersion()+",在系统中已存在,不能再次添加";
			throw new IllegalArgumentException(tipMsg);
		}
		TtaSoleSupplierEntity_HI entityHi = SaafToolUtils.setEntity(TtaSoleSupplierEntity_HI.class, selectRow, ttaSoleSupplierDAO_HI, varUserId);
		entityHi.setPoposalCode(selectRow.getString("proposalOrder"));
		entityHi.setSupplierCode(selectRow.getString("vendorCode"));
		entityHi.setSupplierName(selectRow.getString("vendorName"));
		entityHi.setPoposalId(selectRow.getInteger("proposalId"));
		entityHi.setSoleAgrtHId(soleAgrtHId);
		ttaSoleSupplierDAO_HI.saveOrUpdate(entityHi);
		return entityHi;
	}

	@Override
	public List<TtaSoleSupplierEntity_HI> ttaProposalSupplierSaveForSplitMerge(JSONObject queryParamJSON) throws Exception {
		Assert.notNull(queryParamJSON.getJSONArray("proposalSupplierList"),"未选择Proposal供应商数据,保存失败");
		LOGGER.info("参数集合: {}",queryParamJSON.getJSONArray("proposalSupplierList").toString());


		//1.检查参数是否重复,主要检查选择的Proposal的供应商数据是否重复
		this.checkOut(queryParamJSON);
		//2.获取参数
		JSONArray proposalSupplierList = queryParamJSON.getJSONArray("proposalSupplierList");
		if (proposalSupplierList == null && proposalSupplierList.size() == 0) {
			throw new IllegalArgumentException("未选择Proposal供应商数据,保存失败");
		}
		List<TtaSoleSupplierEntity_HI> ttaSoleSupplierEntity_hiList = proposalSupplierList.toJavaList(TtaSoleSupplierEntity_HI.class);
		//3.设置参数
		for (TtaSoleSupplierEntity_HI entity_hi : ttaSoleSupplierEntity_hiList) {
			entity_hi.setCreatedBy(queryParamJSON.getInteger("varUserId"));
			entity_hi.setCreationDate(new Date());
			entity_hi.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
		}
		//4.保存
		ttaSoleSupplierDAO_HI.saveOrUpdateAll(ttaSoleSupplierEntity_hiList);
		return ttaSoleSupplierEntity_hiList;
	}


	/**
	 * 删除poposal供应商数据
	 * @param id
	 */
	@Override
	public TtaSoleSupplierEntity_HI deleteProposalSupplierById(Integer id) throws Exception{
		LOGGER.info("参数的id: {}",id);
		TtaSoleSupplierEntity_HI ttaSoleSupplierEntity_hi = ttaSoleSupplierDAO_HI.getById(id);
		if (ttaSoleSupplierEntity_hi == null) {
			throw new IllegalArgumentException("选择的数据不存在,不能删除");
		}
		ttaSoleSupplierDAO_HI.delete(ttaSoleSupplierEntity_hi);
		return ttaSoleSupplierEntity_hi;
	}


	@Override
	public Pagination<TtaSoleSupplierEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer(" select * from tta_sole_supplier tss where 1=1 ");
		SaafToolUtils.parperParam(queryParamJSON, "tss.sole_agrt_h_id", "soleAgrtHId", sb, queryParamMap, "=",false);
		if (null == queryParamJSON.getInteger("soleAgrtHId")) {
			int soleAgrtHId = -1;
			sb.append("and tss.sole_agrt_h_id = " + soleAgrtHId);
		}

		SaafToolUtils.changeQuerySort(queryParamJSON, sb, " tss.sole_supplier_id asc", false);
		Pagination<TtaSoleSupplierEntity_HI_RO> findListResult = ttaSoleSupplierDAO_HI_RO.findPagination(sb, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

}
