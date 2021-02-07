package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscAssociateSuppEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAssociateSuppEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscAssociateSupp;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscAssociateSuppServer")
public class EquPosZzscAssociateSuppServer extends BaseCommonServer<EquPosZzscAssociateSuppEntity_HI> implements IEquPosZzscAssociateSupp{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAssociateSuppServer.class);

	@Autowired
	private ViewObject<EquPosZzscAssociateSuppEntity_HI> equPosZzscAssociateSuppDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscAssociateSuppEntity_HI_RO> equPosAssociatedSupplierEntity_HI_RO;

	public EquPosZzscAssociateSuppServer() {
		super();
	}

	/**
	 * 资质审查-供应商关联工厂查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
											 Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscAssociateSuppEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscAssociateSuppEntity_HI_RO.class, queryParamJSON, sql, map);
		Pagination<EquPosZzscAssociateSuppEntity_HI_RO> pagination = equPosAssociatedSupplierEntity_HI_RO.findPagination(sql, map,1,999);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商关联工厂保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscAssociateSuppEntity_HI saveZzscAssociatedSupplier(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商关联工厂删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscAssociatedSupplier(JSONObject jsonObject) {
		Integer associatedSupplierId =jsonObject.getInteger("id");
		EquPosZzscAssociateSuppEntity_HI associatedSupplierEntity =equPosZzscAssociateSuppDAO_HI.getById(associatedSupplierId);
		if(associatedSupplierEntity!=null){
			equPosZzscAssociateSuppDAO_HI.delete(associatedSupplierEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
