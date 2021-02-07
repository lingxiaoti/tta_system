package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaIrTerms extends IBaseCommon<TtaIrTermsEntity_HI>{

    Pagination<TtaIrTermsEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaIrTermsEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

}
