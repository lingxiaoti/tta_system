package com.sie.watsons.base.pos.supplierinfo.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sie.watsons.base.utils.ResultUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierProductCatEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierProductCat;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("equPosSupplierProductCatServer")
public class EquPosSupplierProductCatServer extends BaseCommonServer<EquPosSupplierProductCatEntity_HI> implements IEquPosSupplierProductCat{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierProductCatServer.class);

	@Autowired
	private ViewObject<EquPosSupplierProductCatEntity_HI> equPosSupplierProductCatDAO_HI;

	@Autowired
	private BaseViewObject<EquPosSupplierProductCatEntity_HI_RO> equPosSupplierProductCatEntity_HI_RO;

	public EquPosSupplierProductCatServer() {
		super();
	}

	/**
	 * 供应商品类查询
	 * @param queryParamJSON 参数：密级Entity中的字段
	 * @return
	 */
	public JSONObject findSupplierCategorysInfo(JSONObject queryParamJSON, Integer pageIndex,
												Integer pageRows) {
		if(queryParamJSON.containsKey("deptCode")){
			if(!"0E".equals(queryParamJSON.getString("deptCode"))){
				queryParamJSON.remove("deptCode");
				queryParamJSON.put("deptCode_neq","0E");
			}
		}
		StringBuffer sql = new StringBuffer(EquPosSupplierProductCatEntity_HI_RO.QUERY_SQL);
		Map<String, Object> map = new HashMap<>();
		SaafToolUtils.parperHbmParam(EquPosSupplierProductCatEntity_HI_RO.class, queryParamJSON, sql, map);
		sql.append(" order by src.creation_date");
		Pagination<EquPosSupplierProductCatEntity_HI_RO> pagination = equPosSupplierProductCatEntity_HI_RO.findPagination(sql, map, pageIndex, pageRows);

		List<String> incomingParam = new ArrayList<>();
		List<String> efferentParam = new ArrayList<>();
		List<String> typeParam = new ArrayList<>();
		incomingParam.add("status");
		efferentParam.add("statusMeaning");
		typeParam.add("EQU_SUPPLIER_CATEGORY_STATUS");
		JSONObject returnJson = (JSONObject) JSON.toJSON(pagination);
		JSONArray data = returnJson.getJSONArray("data");
		data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
		returnJson.put("data",data);

		return JSONObject.parseObject(JSONObject.toJSONString(returnJson));
	}

	/**
	 * 供应商品类-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public EquPosSupplierProductCatEntity_HI saveSupplierCategorysInfo(JSONObject params) throws Exception {
		return this.saveOrUpdate(params);
	}

	/**
	 * 供应商品类-删除
	 * @param jsonObject 参数：密级Entity中的字段
	 * @return
	 */
	@Override
	public String deleteSupplierCategorysInfo(JSONObject jsonObject) {
		Integer supplierCategoryId =jsonObject.getInteger("id");
		EquPosSupplierProductCatEntity_HI categoryEntity =equPosSupplierProductCatDAO_HI.getById(supplierCategoryId);
		if(categoryEntity!=null){
			equPosSupplierProductCatDAO_HI.delete(categoryEntity);
			return  SToolUtils.convertResultJSONObj("S", "操作成功", 0, null).toString();
		}else{
			return  SToolUtils.convertResultJSONObj("E", "操作失败", 0, null).toString();
		}
	}
}
