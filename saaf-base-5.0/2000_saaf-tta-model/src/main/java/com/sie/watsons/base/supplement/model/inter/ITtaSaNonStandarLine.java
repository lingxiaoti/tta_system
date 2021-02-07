package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaNonStandarLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.TtaSaNonStandarLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaNonStandarLine extends IBaseCommon<TtaSaNonStandarLineEntity_HI>{

    List<TtaSaNonStandarLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
            throws Exception;

    void delete(Integer pkId);

    Pagination<TtaSaNonStandarLineEntity_HI_RO> findTtaSaNonStandarLinePagination(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
