package com.sie.watsons.base.product.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductBpmUserEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmUserEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmProductBpmUser extends
		IBaseCommon<PlmProductBpmUserEntity_HI> {
	/**
	 * 根据节点编码process_node_code和提单人owner_user_id查询一条或多条数据
	 * 
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<PlmProductBpmUserEntity_HI_RO> findBpmUserByCondition(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 根据process_node_code、owner_user_id、user_id删除一条或多条数据
	 * 
	 * @param queryParamJSON
	 * @return
	 */
	String deletedByCondition(JSONObject queryParamJSON);

	/**
	 * ：指定process_node_code和提单人owner_user_id，新增一条或多条数据
	 */
	String saveProductBpmUser(JSONObject queryParamJSON);

	/**
	 * 返回List
	 * 
	 * @param queryParamJSON
	 * @return
	 */
	List<PlmProductBpmUserEntity_HI_RO> plmProductBpmUserServer(
			JSONObject queryParamJSON);
}
