package com.sie.watsons.base.sync.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemUdaFromRmsEntity_HI;

import java.util.List;

public interface IPlmSyncItemUdaFromRms extends IBaseCommon<PlmSyncItemUdaFromRmsEntity_HI> {

	List<PlmSyncItemUdaFromRmsEntity_HI> findPlmSyncItemUdaFromRmsInfo(JSONObject queryParamJSON);

	Object savePlmSyncItemUdaFromRmsInfo(JSONObject queryParamJSON);

}
