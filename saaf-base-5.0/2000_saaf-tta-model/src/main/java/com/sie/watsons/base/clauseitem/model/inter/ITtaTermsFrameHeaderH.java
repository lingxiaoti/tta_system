package com.sie.watsons.base.clauseitem.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clauseitem.model.entities.TtaTermsFrameHeaderHEntity_HI;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaTermsFrameHeaderHEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaTermsFrameHeaderH extends IBaseCommon<TtaTermsFrameHeaderHEntity_HI>{

    /**
     * 查询名目
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    public Pagination<TtaTermsFrameHeaderHEntity_HI_RO> findTtaTermsFrameHeaderHPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) ;

    /**
     * 条款明目保存数据
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONObject saveOrUpdateTraermsHAll(JSONObject paramsJSON, Integer userId) throws Exception;

    /**
     * 审批时更新状态
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    public JSONArray updateStatusH(JSONObject paramsJSON, Integer userId) throws Exception;
    }
