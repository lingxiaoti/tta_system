package com.sie.saaf.base.cms.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsCarouselEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsCarouselEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseCmsCarousel extends IBaseCommon<BaseCmsCarouselEntity_HI> {

    Pagination<BaseCmsCarouselEntity_HI_RO> findAllCarousel(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<BaseCmsCarouselEntity_HI_RO> findAPPCarousel(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
