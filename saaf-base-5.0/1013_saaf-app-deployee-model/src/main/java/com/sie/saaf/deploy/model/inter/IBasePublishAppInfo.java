package com.sie.saaf.deploy.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.deploy.model.entities.BasePublishAppInfoEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBasePublishAppInfo  extends IBaseCommon<BasePublishAppInfoEntity_HI> {

	/**
	 * 查询应用发布列表
	 * @param queryParamJSON 对象属性的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	Pagination findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 查询应用发布信息
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	BasePublishAppInfoEntity_HI findPublishInfo(JSONObject queryParamJSON) throws Exception;

	/**
	 * 新增应用发布信息
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BasePublishAppInfoEntity_HI savePublishInfo(JSONObject queryParamJSON, Integer userId) throws Exception;

	/**
	 * 修改应用发布信息
	 * @param queryParamJSON
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	BasePublishAppInfoEntity_HI updatePublishInfo(JSONObject queryParamJSON, Integer userId) throws Exception;

}
