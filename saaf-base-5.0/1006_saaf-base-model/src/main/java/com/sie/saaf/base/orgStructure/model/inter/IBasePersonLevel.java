package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePersonLevelEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePersonLevelEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBasePersonLevel extends IBaseCommon<BasePersonLevelEntity_HI> {
    /**
     * 通过属性查询职位层级信息
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位层级列表
     */
    List<BasePersonLevelEntity_HI> findBasePersonLevelByProperty(JSONObject queryParamJSON);

    /**
     * 查询职位层级列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位层级列表（分页）
     */
    Pagination<BasePersonLevelEntity_HI_RO> findBasePersonLevelPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询职位层级列表
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @return 职位层级列表（分页）
     */
    List<BasePersonLevelEntity_HI_RO> findBasePersonLevelList(JSONObject queryParamJSON);

    /**
     * 新增&修改职位层级信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param positionLevelData 行数据
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    List<BasePersonLevelEntity_HI> saveOrUpdateBasePersonLevelInfo(JSONObject paramsJSON, JSONArray positionLevelData, Integer userId) throws Exception;

    /**
     * 删除职位层级信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @throws Exception 抛出异常
     */
    void deletePersonLevel(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 根据最后更新时间查询职位层级信息
     * @param lastUpdateDateStr
     * @return
     */
    List<BasePersonLevelEntity_HI_RO> findPersonLevelByLastUpdateDate(String lastUpdateDateStr);

    /**
     * 新增&修改Oracle数据的职位层级关系信息
     * @param personLevelSynInfo
     * @throws Exception
     */
    void saveOrUpdateOraclePersonLevelInfo(BasePersonLevelEntity_HI_RO personLevelSynInfo) throws Exception;
}
