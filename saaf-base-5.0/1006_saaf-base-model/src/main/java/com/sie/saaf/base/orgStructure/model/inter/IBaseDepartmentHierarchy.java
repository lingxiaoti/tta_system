package com.sie.saaf.base.orgStructure.model.inter;

import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentHierarchyEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentHierarchyEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseDepartmentHierarchy extends IBaseCommon<BaseDepartmentHierarchyEntity_HI> {
	/**
	 * 获取部门表的所有OU_ID
	 * @return 部门表的所有OU_ID
	 */
	List<BaseDepartmentHierarchyEntity_HI_RO> findAllOuId();

	/**
	 * 获取部门表OU下的所有部门层级
	 * @param ouId 事业部ID
	 * @return 部门表的所有OU_ID
	 */
	List<BaseDepartmentHierarchyEntity_HI_RO> findAllDeptLevelByOuId(Integer ouId);

	/**
	 * 查询当前事业部下的所有层级关系
	 * @param ouId 事业部ID
	 * @return
	 */
	List<BaseDepartmentHierarchyEntity_HI>  findByProperty(Integer ouId);

	/**
	 * 删除当前OU下的层级差异数据，
	 * @param deleteList 待删除的层级差异数据
	 */
	void deleteBaseDepartmentHierarchy(List<BaseDepartmentHierarchyEntity_HI> deleteList);

	/**
	 * 插入新的层级差异数据
	 * @param ouId 事业部ID
	 * @param departmentLevel 部门层级
	 */
	void saveBaseDepartmentHierarchy(Integer ouId, Integer departmentLevel);
}
