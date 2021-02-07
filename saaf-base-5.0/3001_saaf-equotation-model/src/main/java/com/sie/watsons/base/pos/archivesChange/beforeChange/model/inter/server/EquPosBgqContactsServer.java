package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqContactsEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierContactsEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqContactsEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqContacts;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqContactsServer")
public class EquPosBgqContactsServer extends BaseCommonServer<EquPosBgqContactsEntity_HI> implements IEquPosBgqContacts{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqContactsServer.class);

	@Autowired
	private ViewObject<EquPosBgqContactsEntity_HI> equPosBgqContactsDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqContactsEntity_HI_RO> equPosBgqContactsEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierContactsEntity_HI> equPosSupplierContactsDAO_HI;

	public EquPosBgqContactsServer() {
		super();
	}

	/**
	 * 档案变更前-供应商联系人目录查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierContactsInfo(JSONObject queryParamJSON, Integer pageIndex,
												 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqContactsEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqContactsEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosBgqContactsEntity_HI_RO> pagination = equPosBgqContactsEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商联系人信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqContactsEntity_HI saveBgqSupplierContactsInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商联系人信息删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqSupplierContactsInfo(JSONObject jsonObject) {
		Integer supplierContactId =jsonObject.getInteger("id");
		EquPosBgqContactsEntity_HI BgqContactEntity =equPosBgqContactsDAO_HI.getById(supplierContactId);
		if(BgqContactEntity!=null){
			equPosBgqContactsDAO_HI.delete(BgqContactEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
