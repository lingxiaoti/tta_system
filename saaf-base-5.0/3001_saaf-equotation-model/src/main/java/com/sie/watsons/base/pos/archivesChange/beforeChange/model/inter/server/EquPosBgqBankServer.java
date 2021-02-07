package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqBankEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierBankEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqBankEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqBank;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqBankServer")
public class EquPosBgqBankServer extends BaseCommonServer<EquPosBgqBankEntity_HI> implements IEquPosBgqBank{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqBankServer.class);

	@Autowired
	private ViewObject<EquPosBgqBankEntity_HI> equPosBgqBankDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqBankEntity_HI_RO> equPosBgqBankEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierBankEntity_HI> equPosSupplierBankDAO_HI;

	public EquPosBgqBankServer() {
		super();
	}

	/**
	 * 档案变更前-供应商银行信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
											 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqBankEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqBankEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqBankEntity_HI_RO> pagination = equPosBgqBankEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商银行信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqBankEntity_HI saveBgqBankInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商银行信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqBankInfo(JSONObject jsonObject) {
		Integer bankId =jsonObject.getInteger("id");
		EquPosBgqBankEntity_HI BgBankEntity =equPosBgqBankDAO_HI.getById(bankId);
		if(BgBankEntity!=null){
			equPosBgqBankDAO_HI.delete(BgBankEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
