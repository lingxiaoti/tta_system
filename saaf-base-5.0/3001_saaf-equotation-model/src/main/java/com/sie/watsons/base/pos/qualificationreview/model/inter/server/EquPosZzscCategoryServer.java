package com.sie.watsons.base.pos.qualificationreview.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscCategoryEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCategoryEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscCategory;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosZzscCategoryServer")
public class EquPosZzscCategoryServer extends BaseCommonServer<EquPosZzscCategoryEntity_HI> implements IEquPosZzscCategory{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCategoryServer.class);

	@Autowired
	private ViewObject<EquPosZzscCategoryEntity_HI> equPosZzscCategoryDAO_HI;

	@Autowired
	private BaseViewObject<EquPosZzscCategoryEntity_HI_RO> equPosZzscCategoryEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatDAO_HI;

	public EquPosZzscCategoryServer() {
		super();
	}

	/**
	 * 资质审查-供应商品类查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findZzscSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
												Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosZzscCategoryEntity_HI_RO.QUERY_SQL);
		if(queryParamJSON.get("searchType")!=null&&"WAREHOUSING".equals(queryParamJSON.getString("searchType"))){
			sql = new StringBuffer(EquPosZzscCategoryEntity_HI_RO.QUERY_WAREHOUSING_SQL);
		}
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosZzscCategoryEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by src.creation_date");
		Pagination<EquPosZzscCategoryEntity_HI_RO> pagination = equPosZzscCategoryEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 资质审查-供应商品类保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosZzscCategoryEntity_HI saveZzscSupplierCategorysInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 资质审查-供应商品类删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteZzscSupplierCategorysInfo(JSONObject jsonObject) {
		Integer supplierCategoryId =jsonObject.getInteger("id");
		EquPosZzscCategoryEntity_HI zzscCategoryEntity =equPosZzscCategoryDAO_HI.getById(supplierCategoryId);
		if(zzscCategoryEntity!=null){
			equPosZzscCategoryDAO_HI.delete(zzscCategoryEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}

	public void deleteZzscSupplierCategorysBySupplierId(Integer supplierId) {
		List<EquPosZzscCategoryEntity_HI> zzscCategoryList =equPosZzscCategoryDAO_HI.findByProperty("supplierId",supplierId);
		for(int i = 0; i < zzscCategoryList.size(); i++){
			EquPosZzscCategoryEntity_HI zzscCategoryEntity = zzscCategoryList.get(i);
			equPosZzscCategoryDAO_HI.delete(zzscCategoryEntity);
		}
	}
}
