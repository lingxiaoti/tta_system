package com.sie.watsons.base.sync.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.sync.model.entities.PlmSyncItemFromRmsEntity_HI;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IPlmSyncItemFromRms extends IBaseCommon<PlmSyncItemFromRmsEntity_HI> {

	List<PlmSyncItemFromRmsEntity_HI> findPlmSyncItemFromRmsInfo(JSONObject queryParamJSON);

	Object savePlmSyncItemFromRmsInfo(JSONObject queryParamJSON);

	void updateRMD(Date date) throws Exception;

    void callProc()throws Exception;

    void rabbitSendMessage();


}
