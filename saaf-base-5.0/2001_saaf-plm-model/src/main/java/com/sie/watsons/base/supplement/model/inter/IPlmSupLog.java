package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;

public interface IPlmSupLog extends IBaseCommon<PlmSupLogEntity_HI> {

	List<PlmSupLogEntity_HI> findPlmSupLogInfo(JSONObject queryParamJSON);

	Object savePlmSupLogInfo(JSONObject queryParamJSON);

}
