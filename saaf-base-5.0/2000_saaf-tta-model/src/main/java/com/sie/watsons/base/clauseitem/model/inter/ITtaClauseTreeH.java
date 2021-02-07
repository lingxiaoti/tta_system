package com.sie.watsons.base.clauseitem.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clauseitem.model.entities.readonly.TtaClauseTreeHEntity_HI_RO;

import java.util.List;
import com.sie.watsons.base.clauseitem.model.entities.TtaClauseTreeHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaClauseTreeH extends IBaseCommon<TtaClauseTreeHEntity_HI>{

    /**
     * 条款名目树
     * @param queryParamJSON
     * @return
     */
    public List<TtaClauseTreeHEntity_HI_RO> findClausehTree(JSONObject queryParamJSON);

    /**
     * 条款名目单条查询
     * @param queryParamJSON
     * @return
     */
    public JSONObject findClausehInfo(JSONObject queryParamJSON);

    /**
     * 删除当前节点以及子节点
     * @param paramsJSON
     * @param userId
     */
    public void deleteClausehTree(JSONObject paramsJSON, Integer userId) throws Exception;
}
