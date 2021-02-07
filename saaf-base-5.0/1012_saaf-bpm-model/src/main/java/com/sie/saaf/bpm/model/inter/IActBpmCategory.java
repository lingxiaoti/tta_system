package com.sie.saaf.bpm.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.bpm.model.entities.ActBpmCategoryEntity_HI;
import com.sie.saaf.bpm.model.entities.readonly.ActBpmCategoryEntity_HI_RO;

import java.util.List;

public interface IActBpmCategory{

	/**
	 * 查询流程分类
	 * @author laoqunzhao 2018-04-27
	 * categoryId 分类ID
	 * @return 流程分类
	 */
	ActBpmCategoryEntity_HI get(Integer categoryId);

	/**
	 * 根据流程key查询流程分类
	 * @author laoqunzhao 2018-09-27
	 * processKey 流程key
	 * @return 流程分类
	 */
	ActBpmCategoryEntity_HI getByProcessKey(String processKey);
	
	/**
	 * 查询流程分类列表
	 * @author laoqunzhao 2018-04-27
	 * @param queryParamJSON
	 * {
	 * parentId 父节点ID
	 * deleteFlag 是否删除   0.否  1.是
	 * categoryName 分类名称
	 * systemCode 系统编码
	 * ouIds 事业部ID[]
	 * }
	 * @return 流程分类列表
	 */
	List<ActBpmCategoryEntity_HI_RO> findCategories(JSONObject queryParamJSON);
	
	/**
	 * 保存流程分类
	 * @author laoqunzhao 2018-04-27
	 * @param paramsJSON
	 * {
     * categoryId 节点ID
     * categoryName　分类名称
     * parentId　父节点ID
     * }
     * @param userId 操作人ID
	 * @throws Exception 
	 */
	ActBpmCategoryEntity_HI save(JSONObject paramsJSON, Integer userId) throws Exception;
	
	/**
	 * 标记删除流程分类
	 * @author laoqunzhao 2018-04-27
	 * @param paramJSON JSONObject
     * categoryIds JSONArray 分类ID
     * @param userId 操作人ID
	 */
	void delete(JSONObject paramJSON, Integer userId);
	
	/**
     * 物理删除流程分类
     * @author laoqunzhao 2018-04-27
     * @param paramJSON JSONObject
     * categoryIds JSONArray分类ID
     */
	void destory(JSONObject paramJSON);

	/**
	 * 判断节点是否存在
	 * @author laoqunzhao 2018-04-27
	 * @param categoryId 节点ID
	 * @param parentId 父节点ID
	 * @param categoryName 分类名称
	 * @return
	 */
    boolean categoryExist(Integer categoryId, Integer parentId, String categoryName);

    /**
	 * 根据节点ID获取所有子节点（包括节点本身）
	 * @author laoqunzhao 2018-04-27
	 * @param categoryId 节点ID
	 * @return
	 */
    List<Integer> getCategoryIds(Integer categoryId);

	

}
