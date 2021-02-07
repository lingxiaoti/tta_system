package com.sie.watsons.base.ttasastdtpl.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.ArrayList;
import java.util.List;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaTplTabLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaTplTabLine extends IBaseCommon<TtaSaTplTabLineEntity_HI>{

    List<TtaSaTplTabLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
            throws Exception;

    List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> findTtaSaTplTabLineList(JSONObject queryParamJSON);

    List<TtaSaTplTabLineEntity_HI> findTtaSaTplTabLineSList(JSONObject queryParamJSON);

}
