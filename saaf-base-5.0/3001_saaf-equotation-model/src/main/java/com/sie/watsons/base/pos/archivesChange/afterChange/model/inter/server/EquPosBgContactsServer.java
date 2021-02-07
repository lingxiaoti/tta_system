package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgContactsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgContactsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgContacts;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgContactsServer")
public class EquPosBgContactsServer extends BaseCommonServer<EquPosBgContactsEntity_HI> implements IEquPosBgContacts{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgContactsServer.class);

	@Autowired
	private ViewObject<EquPosBgContactsEntity_HI> equPosBgContactsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgContactsEntity_HI_RO> equPosBgContactsEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	public EquPosBgContactsServer() {
		super();
	}

	/**
	 * 档案变更-供应商联系人目录查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
												   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgContactsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgContactsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgContactsEntity_HI_RO> pagination = equPosBgContactsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商联系人信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgContactsEntity_HI saveBgSupplierContactsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商联系人信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgSupplierContactsInfo(JSONObject jsonObject) {
		Integer supplierContactId =jsonObject.getInteger("id");
		EquPosBgContactsEntity_HI BgContactEntity =equPosBgContactsDAO_HI.getById(supplierContactId);
		if(BgContactEntity!=null){
			equPosBgContactsDAO_HI.delete(BgContactEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

}
