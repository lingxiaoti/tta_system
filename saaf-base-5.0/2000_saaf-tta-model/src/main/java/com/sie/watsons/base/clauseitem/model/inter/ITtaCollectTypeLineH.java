package com.sie.watsons.base.clauseitem.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.clauseitem.model.entities.TtaCollectTypeLineHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaCollectTypeLineH extends IBaseCommon<TtaCollectTypeLineHEntity_HI>{

    List<TtaCollectTypeLineHEntity_HI> saveOrUpdate(JSONArray paramsJSON,JSONArray paramsJSON2, Integer userId,Integer clauseTreeId,Integer hId) throws Exception;

    void saveOrUpdateStatus(JSONObject paramsJSON,Integer userId) throws Exception;
}
