package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaPromotionGuidelineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaPromotionGuidelineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * Created by Administrator on 2019/7/11/011.
 */
public interface TtaPromotionGuideline extends IBaseCommon<TtaPromotionGuidelineEntity_HI> {

    Pagination<TtaPromotionGuidelineEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    TtaPromotionGuidelineEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer osdId);

}