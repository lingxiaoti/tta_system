package com.sie.saaf.base.orgStructure.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.BaseJobEntity_HI;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseJobEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IBaseJob extends IBaseCommon<BaseJobEntity_HI> {
    /**
     * 通过属性查询职务信息
     *
     * @param queryParamMap 对象属性的JSON格式
     * @return 职务列表
     */
    List<BaseJobEntity_HI> findBaseJobByProperty(Map<String, Object> queryParamMap);

    /**
     * 查询职务列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职务列表（分页）
     */
    Pagination<BaseJobEntity_HI_RO> findBaseJobPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 新增&修改职务信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
    BaseJobEntity_HI saveOrUpdateBaseJobInfo(JSONObject paramsJSON, Integer userId) throws Exception;
}
