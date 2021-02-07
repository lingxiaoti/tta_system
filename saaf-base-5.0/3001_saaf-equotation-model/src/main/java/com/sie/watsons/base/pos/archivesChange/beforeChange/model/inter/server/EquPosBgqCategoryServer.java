package com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly.EquPosBgqCategoryEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCategoryEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCategory;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgqCategoryServer")
public class EquPosBgqCategoryServer extends BaseCommonServer<EquPosBgqCategoryEntity_HI> implements IEquPosBgqCategory{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCategoryServer.class);

	@Autowired
	private ViewObject<EquPosBgqCategoryEntity_HI> equPosBgqCategoryDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgqCategoryEntity_HI_RO> equPosBgqCategoryEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatDAO_HI;

	public EquPosBgqCategoryServer() {
		super();
	}

	/**
	 * 档案变更前-供应商品类查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgqSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
												  Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgqCategoryEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgqCategoryEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by src.supplier_category_id");
		Pagination<EquPosBgqCategoryEntity_HI_RO> pagination = equPosBgqCategoryEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更前-供应商品类保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgqCategoryEntity_HI saveBgqSupplierCategorysInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更前-供应商品类删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgqSupplierCategorysInfo(JSONObject jsonObject) {
		Integer supplierCategoryId =jsonObject.getInteger("id");
		EquPosBgqCategoryEntity_HI BgqCategoryEntity =equPosBgqCategoryDAO_HI.getById(supplierCategoryId);
		if(BgqCategoryEntity!=null){
			equPosBgqCategoryDAO_HI.delete(BgqCategoryEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
