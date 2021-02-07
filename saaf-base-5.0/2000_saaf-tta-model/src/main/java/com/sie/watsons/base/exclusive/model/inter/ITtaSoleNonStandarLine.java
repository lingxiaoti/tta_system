package com.sie.watsons.base.exclusive.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleNonStandarLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleNonStandarLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSoleNonStandarLine extends IBaseCommon<TtaSoleNonStandarLineEntity_HI>{

    List<TtaSoleNonStandarLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
            throws Exception;

    void delete(Integer pkId);

    Pagination<TtaSoleNonStandarLineEntity_HI_RO> findTtaSoleNonStandarLinePagination(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
