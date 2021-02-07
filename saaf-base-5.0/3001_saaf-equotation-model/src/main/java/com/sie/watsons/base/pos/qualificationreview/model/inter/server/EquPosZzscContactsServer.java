package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscContactsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscContactsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscContacts;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscContactsServer")
public class EquPosZzscContactsServer extends BaseCommonServer<EquPosZzscContactsEntity_HI> implements IEquPosZzscContacts{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscContactsServer.class);

	@Autowired
	private ViewObject<EquPosZzscContactsEntity_HI> equPosZzscContactsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscContactsEntity_HI_RO> equPosZzscContactsEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	public EquPosZzscContactsServer() {
		super();
	}

	/**
	 * 资质审查-供应商联系人目录查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
											   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscContactsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscContactsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscContactsEntity_HI_RO> pagination = equPosZzscContactsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商联系人信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscContactsEntity_HI saveZzscSupplierContactsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商联系人信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscSupplierContactsInfo(JSONObject jsonObject) {
		Integer supplierContactId =jsonObject.getInteger("id");
		EquPosZzscContactsEntity_HI zzscContactEntity =equPosZzscContactsDAO_HI.getById(supplierContactId);
		if(zzscContactEntity!=null){
			equPosZzscContactsDAO_HI.delete(zzscContactEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
