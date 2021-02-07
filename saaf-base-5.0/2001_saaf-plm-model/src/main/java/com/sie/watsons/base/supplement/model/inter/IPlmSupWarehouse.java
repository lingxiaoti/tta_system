package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupWarehouseEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.PlmSupWarehouseEntity_HI;

public interface IPlmSupWarehouse extends IBaseCommon<PlmSupWarehouseEntity_HI> {

	List<PlmSupWarehouseEntity_HI> findPlmSupWarehouseInfo(JSONObject queryParamJSON);

	Object savePlmSupWarehouseInfo(JSONObject queryParamJSON);

	JSONObject saveWarehouseByExcel(JSONObject param);

	Pagination<PlmSupWarehouseEntity_HI_RO> findPlmSupWarehouseInfo(JSONObject queryParamJSON, Integer pageIndex,
																		   Integer pageRows);
	List<PlmSupWarehouseEntity_HI> getExWarehouses(JSONObject queryParamJSON);

	JSONObject saveAndImportWarehouse(JSONObject queryParamJSON) throws Exception;

	/**
	 * 批量删除
	 * @param parameters
	 * @return
	 */
	String deletePlmSupForList(JSONObject parameters);

	PlmSupWarehouseEntity_HI_RO findPlmSupWarehouseInfoById(JSONObject queryParamJSON) throws Exception;

	/**
	 * 新增修改对象数据
	 * @param queryParamJSON
	 * @return
	 */
	String saveOrUpdatePlmSupWarehouse(JSONObject queryParamJSON);

    Object saveAndImportWarehouse2(JSONObject queryParamJSON);
}
