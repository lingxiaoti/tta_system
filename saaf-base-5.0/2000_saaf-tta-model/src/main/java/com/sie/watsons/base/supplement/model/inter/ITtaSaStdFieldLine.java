package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdFieldLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdFieldLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdFieldLine extends IBaseCommon<TtaSaStdFieldLineEntity_HI>{

    void saveSaStaDynamicFieldlDataList(JSONArray dynamicFieldlDataList, TtaSaStdHeaderEntity_HI entity_hi,int userId) throws Exception;

    List<TtaSaStdFieldLineEntity_HI_RO> findSupplementExpandInfo(JSONObject jsonObject, int userId);
}
