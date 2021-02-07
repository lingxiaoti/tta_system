package com.sie.watsons.base.clause.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clause.model.entities.readonly.TtaCollectTypeLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.clause.model.entities.TtaCollectTypeLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaCollectTypeLine extends IBaseCommon<TtaCollectTypeLineEntity_HI>{


    List<TtaCollectTypeLineEntity_HI> saveOrUpdate(JSONArray paramsJSON,JSONArray paramsJSON2, Integer userId,Integer clauseTreeId,Integer hId) throws Exception;

    List<TtaCollectTypeLineEntity_HI_RO> findUnit(JSONObject queryParamJSON);

    void saveOrUpdateStatus(JSONObject paramsJSON,Integer userId) throws Exception ;
}
