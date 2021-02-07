package com.sie.watsons.base.proposal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.proposal.model.entities.TtaTermsHEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITtaTermsH extends IBaseCommon<TtaTermsHEntity_HI>{
    Pagination<TtaTermsHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaTermsHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    TtaTermsHEntity_HI_RO saveFindByRoId(JSONObject queryParamJSON) throws Exception;

    List<TtaTermsLEntity_HI_RO> saveFindTermLineDataByDeptFee(JSONObject jsonObject, Integer userId) throws Exception;
}
