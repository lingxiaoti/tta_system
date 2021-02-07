package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosAssociatedSupplierEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosAssociatedSupplierEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosAssociatedSupplier;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("equPosAssociatedSupplierServer")
public class EquPosAssociatedSupplierServer extends BaseCommonServer<EquPosAssociatedSupplierEntity_HI> implements IEquPosAssociatedSupplier {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosAssociatedSupplierServer.class);

	@Autowired
	private ViewObject<EquPosAssociatedSupplierEntity_HI> equPosAssociatedSupplierDAO_HI;

	@Autowired
	private BaseViewObject<EquPosAssociatedSupplierEntity_HI_RO> equPosAssociatedSupplierEntity_HI_RO;

	public EquPosAssociatedSupplierServer() {
		super();
	}

	/**
	 * 供应商关联工厂查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findAssociatedSupplier(JSONObject queryParamJSON, Integer pageIndex,
									   Integer pageRows) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosAssociatedSupplierEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosAssociatedSupplierEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by t.associated_id");
		Pagination<EquPosAssociatedSupplierEntity_HI_RO> pagination = equPosAssociatedSupplierEntity_HI_RO.findPagination(sql, map,pageIndex,pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 供应商关联工厂-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosAssociatedSupplierEntity_HI saveAssociatedSupplier(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商关联工厂-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteAssociatedSupplier(JSONObject jsonObject) {
		Integer associatedSupplierId =jsonObject.getInteger("id");
		EquPosAssociatedSupplierEntity_HI associatedSupplierEntity =equPosAssociatedSupplierDAO_HI.getById(associatedSupplierId);
		if(associatedSupplierEntity!=null){
			equPosAssociatedSupplierDAO_HI.delete(associatedSupplierEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
