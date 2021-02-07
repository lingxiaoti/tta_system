package com.sie.watsons.base.ttasastdtpl.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplFieldCfgEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplFieldCfgEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaSaStdTplFieldCfg extends IBaseCommon<TtaSaStdTplFieldCfgEntity_HI>{

    /**
     * 查询TTA_SA_STD_TPL_FIELD_CFG 数据
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<TtaSaStdTplFieldCfgEntity_HI_RO> findTtaSaStdTplFieldCfgPagination(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 保存 TTA_SA_STD_TPL_FIELD_CFG 数据
     * @param queryParamJSON
     * @param userId
     * @return
     * @throws Exception
     */
    List<TtaSaStdTplFieldCfgEntity_HI> saveOrUpdateAll(JSONObject queryParamJSON, Integer userId)
            throws Exception;

}
