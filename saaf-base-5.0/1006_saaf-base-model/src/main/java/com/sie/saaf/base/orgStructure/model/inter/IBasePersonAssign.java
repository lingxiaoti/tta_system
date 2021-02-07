package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonAssignEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonAssignEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IBasePersonAssign extends IBaseCommon<BasePersonAssignEntity_HI> {
    /**
     * 通过属性查询职位职务分配信息
     *
     * @param queryParamMap 对象属性的JSON格式
     * @return 职位职务分配列表
     */
    List<BasePersonAssignEntity_HI> findPersonAssignByProperty(Map<String, Object> queryParamMap);

    /**
     * 查询职位职务分配列表
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位职务分配列表（分页）
     */
    List<BasePersonAssignEntity_HI_RO> findPersonAssignList(JSONObject queryParamJSON);

    /**
     * 查询职位职务分配列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位职务分配列表（分页）
     */
    Pagination<BasePersonAssignEntity_HI_RO> findPersonAssignPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 新增&修改职位职务分配信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    List<BasePersonAssignEntity_HI> saveOrUpdatePersonAssign(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 删除职位职务分配信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @throws Exception 抛出异常
     */
    void deletePersonAssign(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 有效时间范围内职位、人员列表
     *
     * @param queryParamMap 对象属性的JSON格式
     *                      {
     *                      ouId：事业部ID，
     *                      personId：人员ID
     *                      }
     * @return 有效时间范围内职位、人员列表
     */
    List<BasePersonAssignEntity_HI_RO> findEffectivePersonAssignList(Map<String, Object> queryParamMap);

    /**
     * 查询当前登录用户
     * @param paramsJSON
     * @return
     */
    List<BasePersonAssignEntity_HI_RO> findHaveBudgetMaintain(JSONObject paramsJSON);

    /**
     * 根据最后更新时间查询人员分配信息
     * @param lastUpdateDateStr
     * @return
     */
    List<BasePersonAssignEntity_HI_RO> findPersonAssignByLastUpdateDate(String lastUpdateDateStr);

    /**
     * 新增&修改Oracle数据的部门人员关系信息
     * @param deptPersonSynInfo
     * @throws Exception
     */
    void saveOrUpdateOraclePositionInfo(BasePersonAssignEntity_HI_RO deptPersonSynInfo) throws Exception;
}
