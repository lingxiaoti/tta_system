package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaTabLineEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaTplTabLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.ArrayList;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaTabLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaTabLine extends IBaseCommon<TtaSaTabLineEntity_HI>{

    List<ArrayList<TtaSaTplTabLineEntity_HI_RO>> saveFindTtaSaTplTabLineList(JSONObject queryParamsJSON, int userId) throws Exception;

    List<TtaSaTabLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId) throws Exception;

    List<List<TtaSaTabLineEntity_HI_RO>> findSupplementAgreementSandardTabLine(JSONObject queryParamJSON, int userId) throws Exception;
}
