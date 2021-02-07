package com.sie.watsons.base.clause.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clause.model.entities.readonly.TtaClauseTreeEntity_HI_RO;
import com.sie.watsons.base.clause.model.entities.TtaClauseTreeEntity_HI;

import java.util.List;

import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaClauseTree extends IBaseCommon<TtaClauseTreeEntity_HI>{
    /**
     * 条款框架树
     * @param queryParamJSON
     * @return
     */
    List<TtaClauseTreeEntity_HI_RO> findClauseTree(JSONObject queryParamJSON);
    /**
     * 条款框架树单条
     * @param queryParamJSON
     * @return
     */
    JSONObject findClauseInfo(JSONObject queryParamJSON);

    /**
     *条款框架删除当前节点以及子节点
     * @param paramsJSON
     * @param userId
     */
    void   deleteClauseTree(JSONObject paramsJSON, Integer userId) throws Exception;

    TtaClauseTreeEntity_HI findById(Integer byId) ;
}
