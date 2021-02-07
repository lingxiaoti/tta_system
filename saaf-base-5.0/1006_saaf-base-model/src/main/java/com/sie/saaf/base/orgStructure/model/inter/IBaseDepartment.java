package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseDepartmentEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IBaseDepartment extends IBaseCommon<BaseDepartmentEntity_HI> {
    /**
     * 部门树
     * @param queryParamJSON
     * @return
     */
    List<BaseDepartmentEntity_HI_RO> findDeptTreeList(JSONObject queryParamJSON);

    /**
     * 部门（分页）
     * @param queryParamJSON
     * @return
     */
    Pagination<BaseDepartmentEntity_HI_RO> findDeptPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 根据部门类型和上级部门查找部门
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    Pagination<BaseDepartmentEntity_HI_RO> findDeptByGroupIdAndDepartmentType(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception;

    /**
     * 新增&修改部门信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    BaseDepartmentEntity_HI saveOrUpdateBaseDepartment(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 查询是否存在相同的部门：同一事业部内部门名称+部门后缀在有效期间内是否重复；
     * @param queryParamMap
     * @return
     */
    Boolean findIsExistRepeatDept(Map<String, Object> queryParamMap);

    /**
     * 查询需要同步的部门数据
     * @param lastUpdateDateStr
     * @return
     */
    List<BaseDepartmentEntity_HI_RO> findDepartmentSynList(String lastUpdateDateStr);

    /**
     * 新增&修改Oracle数据的部门信息
     * @param deptSynInfo
     * @throws Exception
     */
    void saveOrUpdateOracleDeptInfo(BaseDepartmentEntity_HI_RO deptSynInfo) throws Exception;

    /**
     * 根据部门id找到顶级部门
     * @param deptId
     * @return
     */
    BaseDepartmentEntity_HI_RO findDeptByRecursion(Integer deptId) throws Exception;

    /**
     * 根据用户id查找部门
     * @param userId
     * @return
     */
    BaseDepartmentEntity_HI_RO findDeptList(Integer userId);
}
