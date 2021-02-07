package com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.server;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly.EquPosBgCategoryEntity_HI_RO;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCategoryEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgCategory;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosBgCategoryServer")
public class EquPosBgCategoryServer extends BaseCommonServer<EquPosBgCategoryEntity_HI> implements IEquPosBgCategory{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCategoryServer.class);

	@Autowired
	private ViewObject<EquPosBgCategoryEntity_HI> equPosBgCategoryDAO_HI;

	@Autowired
	private BaseViewObject<EquPosBgCategoryEntity_HI_RO> equPosBgCategoryEntity_HI_RO;

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatDAO_HI;

	public EquPosBgCategoryServer() {
		super();
	}

	/**
	 * 档案变更-供应商品类查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public JSONObject findBgSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
													Integer pageRows) {
		StringBuffer sql = new StringBuffer(EquPosBgCategoryEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosBgCategoryEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by src.supplier_category_id");
		Pagination<EquPosBgCategoryEntity_HI_RO> pagination = equPosBgCategoryEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);
		return JSONObject.parseObject(JSONObject.toJSONString(pagination));
	}

	/**
	 * 档案变更-供应商品类保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosBgCategoryEntity_HI saveBgSupplierCategorysInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 档案变更-供应商品类删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteBgSupplierCategorysInfo(JSONObject jsonObject) {
		Integer sourceId =jsonObject.getInteger("id");
		EquPosBgCategoryEntity_HI BgCategoryEntity =equPosBgCategoryDAO_HI.getById(sourceId);
		if(BgCategoryEntity!=null){
			equPosBgCategoryDAO_HI.delete(BgCategoryEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
