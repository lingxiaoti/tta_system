package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscBankEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierBankEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscBankEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscBank;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscBankServer")
public class EquPosZzscBankServer extends BaseCommonServer<EquPosZzscBankEntity_HI> implements IEquPosZzscBank{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscBankServer.class);

	@Autowired
	private ViewObject<EquPosZzscBankEntity_HI> equPosZzscBankDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscBankEntity_HI_RO> equPosZzscBankEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierBankEntity_HI> equPosSupplierBankDAO_HI;

	public EquPosZzscBankServer() {
		super();
	}

	/**
	 * 资质审查-供应商银行信息查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierBankInfo(JSONObject queryParamJSON, Integer pageIndex,
										   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscBankEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscBankEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscBankEntity_HI_RO> pagination = equPosZzscBankEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商银行信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscBankEntity_HI saveZzscBankInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商银行信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscBankInfo(JSONObject jsonObject) {
		Integer bankId =jsonObject.getInteger("id");
		EquPosZzscBankEntity_HI zzscBankEntity =equPosZzscBankDAO_HI.getById(bankId);
		if(zzscBankEntity!=null){
			equPosZzscBankDAO_HI.delete(zzscBankEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
