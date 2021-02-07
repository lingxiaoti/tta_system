package com.sie.watsons.base.plmBase.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseSeriesEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseSeriesEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IPlmBaseSeries extends IBaseCommon<PlmBaseSeriesEntity_HI> {

	Pagination<PlmBaseSeriesEntity_HI_RO> findPlmseriesInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
