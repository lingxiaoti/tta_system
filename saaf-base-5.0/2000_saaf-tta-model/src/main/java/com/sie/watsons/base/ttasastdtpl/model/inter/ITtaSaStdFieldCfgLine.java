package com.sie.watsons.base.ttasastdtpl.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdFieldCfgLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdFieldCfgLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdFieldCfgLine extends IBaseCommon<TtaSaStdFieldCfgLineEntity_HI>{

    Pagination<TtaSaStdFieldCfgLineEntity_HI_RO> findTtaSaStdFieldCfgLinePagination(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    List<TtaSaStdFieldCfgLineEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
            throws Exception;

    void delete(Integer pkId);
}
