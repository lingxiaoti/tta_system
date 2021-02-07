package com.sie.watsons.base.sync.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemSuppFromRmsEntity_HI;

import java.util.List;

public interface IPlmSyncItemSuppFromRms extends IBaseCommon<PlmSyncItemSuppFromRmsEntity_HI> {

	List<PlmSyncItemSuppFromRmsEntity_HI> findPlmSyncItemSuppFromRmsInfo(JSONObject queryParamJSON);

	Object savePlmSyncItemSuppFromRmsInfo(JSONObject queryParamJSON);

}
