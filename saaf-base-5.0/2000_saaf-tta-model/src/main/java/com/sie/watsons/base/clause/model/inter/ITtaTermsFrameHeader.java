package com.sie.watsons.base.clause.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clause.model.entities.readonly.TtaTermsFrameHeaderEntity_HI_RO;
import com.sie.watsons.base.clause.model.entities.TtaTermsFrameHeaderEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaTermsFrameHeader extends IBaseCommon<TtaTermsFrameHeaderEntity_HI>{

    /**
     * 查询条款框架列表（分页）
     *
     * @param queryParamJSON 对象属性的JSON格式
     * @param pageIndex      页码
     * @param pageRows       每页查询记录数
     * @return 职务列表（分页）
     */
    Pagination<TtaTermsFrameHeaderEntity_HI_RO> findTtaTermsFrameHeaderPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 条款框架保存数据
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONObject saveOrUpdateTraermsAll(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 条款框架复制数据
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONObject saveOrUpdateCopy(JSONObject paramsJSON, Integer userId) throws Exception ;

    /**
     * 审批时更新状态更新
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONArray updateStatus(JSONObject paramsJSON, Integer userId) throws Exception ;

    /**
     * 变更复制功能
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    public JSONObject saveChangeTraermsAll(JSONObject paramsJSON, Integer userId) throws Exception ;
}
