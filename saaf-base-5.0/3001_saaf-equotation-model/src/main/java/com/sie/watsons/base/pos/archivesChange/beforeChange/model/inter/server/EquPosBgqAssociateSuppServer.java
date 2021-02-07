package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqAssociateSuppEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqAssociateSuppEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqAssociateSupp;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqAssociateSuppServer")
public class EquPosBgqAssociateSuppServer extends BaseCommonServer<EquPosBgqAssociateSuppEntity_HI> implements IEquPosBgqAssociateSupp{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqAssociateSuppServer.class);

	@Autowired
	private ViewObject<EquPosBgqAssociateSuppEntity_HI> equPosBgqAssociateSuppDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqAssociateSuppEntity_HI_RO> equPosAssociatedSupplierEntity_HI_RO;

	public EquPosBgqAssociateSuppServer() {
		super();
	}

	/**
	 * 档案变更前-供应商关联工厂查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
											   Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqAssociateSuppEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqAssociateSuppEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.associated_id");
		Pagination<EquPosBgqAssociateSuppEntity_HI_RO> pagination = equPosAssociatedSupplierEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商关联工厂保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqAssociateSuppEntity_HI saveBgqAssociatedSupplier(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商关联工厂删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqAssociatedSupplier(JSONObject jsonObject) {
		Integer associatedSupplierId =jsonObject.getInteger("id");
		EquPosBgqAssociateSuppEntity_HI associatedSupplierEntity =equPosBgqAssociateSuppDAO_HI.getById(associatedSupplierId);
		if(associatedSupplierEntity!=null){
			equPosBgqAssociateSuppDAO_HI.delete(associatedSupplierEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
