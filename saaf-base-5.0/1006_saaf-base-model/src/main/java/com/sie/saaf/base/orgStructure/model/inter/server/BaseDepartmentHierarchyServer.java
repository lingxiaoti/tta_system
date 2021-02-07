package com.sie.saaf.base.orgStructure.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentHierarchyEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentHierarchyEntity_HI_RO;
import com.sie.saaf.base.orgStructure.model.inter.IBaseDepartmentHierarchy;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseDepartmentHierarchyServer")
public class BaseDepartmentHierarchyServer extends BaseCommonServer<BaseDepartmentHierarchyEntity_HI> implements IBaseDepartmentHierarchy {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDepartmentHierarchyServer.class);
	@Autowired
	private ViewObject<BaseDepartmentHierarchyEntity_HI> baseDepartmentHierarchyDAO_HI;
	@Autowired
	private BaseViewObject<BaseDepartmentHierarchyEntity_HI_RO> baseDepartmentHierarchyDAO_HI_RO;

	public BaseDepartmentHierarchyServer() {
		super();
	}

	/**
	 * 获取部门表的所有OU_ID
	 * @return 部门表的所有OU_ID
	 */
	@Override
	public List<BaseDepartmentHierarchyEntity_HI_RO> findAllOuId() {
		return baseDepartmentHierarchyDAO_HI_RO.findList(BaseDepartmentHierarchyEntity_HI_RO.QUERY_OU_GROUP_BY_OU_ID_SQL);
	}

	/**
	 * 获取部门表OU下的所有部门层级
	 * @param ouId 事业部ID
	 * @return 部门表的所有OU_ID
	 */
	@Override
	public List<BaseDepartmentHierarchyEntity_HI_RO> findAllDeptLevelByOuId(Integer ouId) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("ouId", ouId);
		return baseDepartmentHierarchyDAO_HI_RO.findList(BaseDepartmentHierarchyEntity_HI_RO.QUERY_LEVEL_GROUP_BY_LEVEL_SQL, queryMap);
	}

	/**
	 * 查询OU下的每个部门自身与自身的关系
	 * @param ouId 事业部ID
	 * @return OU下的每个部门自身与自身的关系
	 */
	public List<BaseDepartmentHierarchyEntity_HI_RO> findDeptInfoByOuId(Integer ouId) {
		StringBuffer querySQL = new StringBuffer(BaseDepartmentHierarchyEntity_HI_RO.QUERY_DEPT_INFO_BY_OU_SQL);
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("ouId", ouId);
		return baseDepartmentHierarchyDAO_HI_RO.findList(querySQL, queryMap);
	}

	/**
	 * 查询OU下的每个部门自身与自身的关系
	 * @param ouId 事业部ID
	 * @return OU下的每个部门自身与自身的关系
	 */
	public List<BaseDepartmentHierarchyEntity_HI_RO> findDeptInfoByOuIdAndLevel(Integer ouId, Integer departmentLevel) {
		if (departmentLevel == 0) {
			//排除层级是0的数据
			return new ArrayList<>();
		}
		StringBuffer querySQL = new StringBuffer(BaseDepartmentHierarchyEntity_HI_RO.QUERY_DEPT_INFO_BY_OU_AND_LEVEL);
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("ouId", ouId);
		queryMap.put("departmentLevel", departmentLevel);
		return baseDepartmentHierarchyDAO_HI_RO.findList(querySQL, queryMap);
	}

	/**
	 * 查询当前事业部下的所有层级关系
	 * @param ouId 事业部ID
	 * @return
	 */
	@Override
	public List<BaseDepartmentHierarchyEntity_HI>  findByProperty(Integer ouId){
		return baseDepartmentHierarchyDAO_HI.findByProperty("ouId", ouId);
	}

	/**
	 * 删除当前OU下的层级差异数据，
	 * @param deleteList 待删除的层级差异数据
	 */
	@Override
	public void deleteBaseDepartmentHierarchy(List<BaseDepartmentHierarchyEntity_HI> deleteList) {
		baseDepartmentHierarchyDAO_HI.delete(deleteList);
	}

	/**
	 * 插入新的层级差异数据
	 * @param ouId 事业部ID
	 * @param departmentLevel 部门层级
	 */
	@Override
	public void saveBaseDepartmentHierarchy(Integer ouId, Integer departmentLevel) {
		List<BaseDepartmentHierarchyEntity_HI_RO> roList = new ArrayList<>();
		if (departmentLevel == 0) {
			roList = findDeptInfoByOuId(ouId);
		}else{
			roList = findDeptInfoByOuIdAndLevel(ouId, departmentLevel);
		}
		List<BaseDepartmentHierarchyEntity_HI> saveList = JSON.parseArray(JSON.toJSONString(roList), BaseDepartmentHierarchyEntity_HI.class);
		baseDepartmentHierarchyDAO_HI.save(saveList);
	}


}
