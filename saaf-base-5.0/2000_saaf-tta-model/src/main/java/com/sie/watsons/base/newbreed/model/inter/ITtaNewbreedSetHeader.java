package com.sie.watsons.base.newbreed.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetHeaderEntity_HI;
import com.sie.watsons.base.newbreed.model.entities.readonly.TtaNewbreedSetHeaderEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaNewbreedSetHeader extends IBaseCommon<TtaNewbreedSetHeaderEntity_HI>{

    /**
     * 查询新品种列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return Pagination<BaseJobEntity_HI_RO>
     */

     Pagination<TtaNewbreedSetHeaderEntity_HI_RO> findTtaNewbreedSetHeaderPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


    /**
     * 新增&修改新品种信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */
     JSONObject saveOrUpdateTtaNewbreedSetALLInfo(JSONObject paramsJSON, Integer userId) throws Exception ;


    /**
     * 查询新品种详情页
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return Pagination<BaseJobEntity_HI_RO>
     */

     JSONObject findTtaNewbreedOne(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    JSONObject saveOrUpdateCopy(JSONObject paramsJSON, Integer userId) throws Exception ;

}
