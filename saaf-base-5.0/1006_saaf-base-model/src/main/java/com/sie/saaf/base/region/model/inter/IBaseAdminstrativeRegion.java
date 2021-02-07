package com.sie.saaf.base.region.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.region.model.entities.BaseAdminstrativeRegionEntity_HI;
import com.sie.saaf.base.region.model.entities.readonly.BaseAdminstrativeRegion_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseAdminstrativeRegion extends IBaseCommon<BaseAdminstrativeRegionEntity_HI> {

	Pagination<BaseAdminstrativeRegion_HI_RO> findRegionPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<BaseAdminstrativeRegion_HI_RO> findRegionList(JSONObject queryParamJSON);

	List<BaseAdminstrativeRegion_HI_RO> findCountry(JSONObject queryParamJSON);

	List<BaseAdminstrativeRegion_HI_RO> findProvinces(JSONObject queryParamJSON);
}
