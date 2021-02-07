package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionalLeafletEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaPromotionalLeafletEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaPromotionalLeaflet extends IBaseCommon<TtaPromotionalLeafletEntity_HI>{

    Pagination<TtaPromotionalLeafletEntity_HI_RO> find(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    TtaPromotionalLeafletEntity_HI saveOrUpdate(JSONObject jsonObject, int userId) throws Exception;
}
