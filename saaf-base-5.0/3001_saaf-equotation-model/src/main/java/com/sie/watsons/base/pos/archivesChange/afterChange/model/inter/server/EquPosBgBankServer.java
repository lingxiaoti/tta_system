package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgBankEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierBankEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgBankEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgBank;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgBankServer")
public class EquPosBgBankServer extends BaseCommonServer<EquPosBgBankEntity_HI> implements IEquPosBgBank{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgBankServer.class);

	@Autowired
	private ViewObject<EquPosBgBankEntity_HI> equPosBgBankDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgBankEntity_HI_RO> equPosBgBankEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierBankEntity_HI> equPosSupplierBankDAO_HI;

	public EquPosBgBankServer() {
		super();
	}

	/**
	 * 档案变更-供应商银行信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
											   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgBankEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgBankEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgBankEntity_HI_RO> pagination = equPosBgBankEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商银行信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgBankEntity_HI saveBgBankInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商银行信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgBankInfo(JSONObject jsonObject) {
		Integer bankId =jsonObject.getInteger("id");
		EquPosBgBankEntity_HI BgBankEntity =equPosBgBankDAO_HI.getById(bankId);
		if(BgBankEntity!=null){
			equPosBgBankDAO_HI.delete(BgBankEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
