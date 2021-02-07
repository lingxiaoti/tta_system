package com.sie.watsons.base.elecsign.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaConAttrLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaConAttrLine extends IBaseCommon<TtaConAttrLineEntity_HI>{

    Pagination<TtaConAttrLineEntity_HI_RO> findTtaProposalConAttrLinePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Integer findcount(JSONObject queryParamJSON);

    JSONObject deleteInfo(JSONObject queryParamJSON) throws Exception;

    void deleteByFileId(Long fileId) throws Exception;

    JSONObject findFileIds(JSONObject queryParamJSON) throws Exception;
}
