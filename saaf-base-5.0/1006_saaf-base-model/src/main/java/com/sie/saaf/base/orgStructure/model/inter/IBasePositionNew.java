package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BasePositionNewEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BasePositionNewEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IBasePositionNew extends IBaseCommon<BasePositionNewEntity_HI> {
    /**
     * 通过属性查询职位信息
     *
     * @param queryParamMap 对象属性的JSON格式
     * @return 职位列表
     */
    List<BasePositionNewEntity_HI> findBasePositionNewByProperty(Map<String, Object> queryParamMap);

    /**
     * 通过最后更新时间查询职位信息（用于数据同步）
     * @param lastUpdateDateStr
     * @return
     */
    List<BasePositionNewEntity_HI_RO> findBasePositionNewList(String lastUpdateDateStr);

    /**
     * 查询职位列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职位列表（分页）
     */
    Pagination<BasePositionNewEntity_HI_RO> findBasePositionNewPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 新增&修改职位信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    BasePositionNewEntity_HI saveOrUpdateBasePositionNewInfo(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 新增&修改Oracle数据的部门信息
     * @param positionSynInfo
     * @throws Exception
     */
    void saveOrUpdateOraclePositionInfo(BasePositionNewEntity_HI_RO positionSynInfo) throws Exception;


    /**
     * 职位树
     * @param queryParamJSON
     * @return
     */
    List<BasePositionNewEntity_HI_RO> findPositionTree(JSONObject queryParamJSON);
}
