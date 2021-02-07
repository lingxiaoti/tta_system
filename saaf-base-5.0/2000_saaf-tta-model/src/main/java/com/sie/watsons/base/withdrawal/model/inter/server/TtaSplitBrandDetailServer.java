package com.sie.watsons.base.withdrawal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.withdrawal.model.entities.TtaSplitBrandDetailEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSplitBrandDetail;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaSplitBrandDetailServer")
public class TtaSplitBrandDetailServer extends BaseCommonServer<TtaSplitBrandDetailEntity_HI> implements ITtaSplitBrandDetail{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSplitBrandDetailServer.class);

	@Autowired
	private ViewObject<TtaSplitBrandDetailEntity_HI> ttaSplitBrandDetailDAO_HI;

	@Autowired
	private BaseViewObject<TtaSplitBrandDetailEntity_HI_RO> ttaSplitBrandDetailDAO_HI_RO;
	public TtaSplitBrandDetailServer() {
		super();
	}

	/**
	 * @date 2019.12.31
	 * @des  查询品牌计划明细拆分的数据
	 * @param oldproposalYear
	 * @param vendorNbr
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TtaSplitBrandDetailEntity_HI_RO> findSplitBrandList(String oldproposalYear, String vendorNbr) throws Exception {
		StringBuffer sb = new StringBuffer(TtaSplitBrandDetailEntity_HI_RO.QUERY_BRAND);
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("splitStatus","1");//已拆分
		queryMap.put("supplierCode",vendorNbr);
		queryMap.put("splitSupplierCode",vendorNbr);
		queryMap.put("proposalYear",oldproposalYear);
		return ttaSplitBrandDetailDAO_HI_RO.findList(sb.toString(),queryMap);
	}

	@Override
	public List<TtaSplitBrandDetailEntity_HI_RO> findSplitBrandByNotSourceSupplier(String oldproposalYear, String vendorNbr) throws Exception {
		StringBuffer sb = new StringBuffer(TtaSplitBrandDetailEntity_HI_RO.QUERY_NOT_BRAND);
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("splitStatus","1");//已拆分
		queryMap.put("supplierCode",vendorNbr);
		queryMap.put("splitSupplierCode",vendorNbr);
		queryMap.put("proposalYear",oldproposalYear);
		return ttaSplitBrandDetailDAO_HI_RO.findList(sb.toString(),queryMap);
	}

	/**
	 * 查询拆分后的金额
	 * @param oldproposalYear
	 * @param vendorNbr
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSplitBrandDetailEntity_HI_RO findSplitBrandByMoney(String oldproposalYear, String vendorNbr) throws Exception {
		StringBuffer sb = new StringBuffer(TtaSplitBrandDetailEntity_HI_RO.QUERY_MONEY);
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("splitStatus","1");//已拆分
		queryMap.put("supplierCode",vendorNbr);
		queryMap.put("proposalYear",oldproposalYear);
		return ttaSplitBrandDetailDAO_HI_RO.get(sb.toString(),queryMap);
	}

	/**
	 * 查询拆分前的供应商的FcsPurchase,FcsSales
	 * @param oldproposalYear
	 * @param vendorNbr
	 * @return
	 * @throws Exception
	 */
	@Override
	public TtaSplitBrandDetailEntity_HI_RO findSplitBrandByFcsPurchaseAndFcsSales(String oldproposalYear, String vendorNbr) throws Exception {
		StringBuffer sb = new StringBuffer(TtaSplitBrandDetailEntity_HI_RO.QUERY_SPLIT_FCS_PURCAHSE_SALES);
		Map<String,Object> queryMap = new HashMap<>();
		queryMap.put("splitStatus","1");//已拆分
		queryMap.put("supplierCode",vendorNbr);
		queryMap.put("proposalYear",oldproposalYear);
		return ttaSplitBrandDetailDAO_HI_RO.get(sb.toString(),queryMap);
	}
}
