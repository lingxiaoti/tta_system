package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgLogEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgLogEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgLog  extends IBaseCommon<MsgLogEntity_HI> {

	List<MsgLogEntity_HI> findMsgLogInfo(JSONObject queryParamJSON);

	Object saveMsgLogInfo(JSONObject queryParamJSON);

    Pagination<MsgLogEntity_HI_RO> findMsgLogList(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgLog(JSONObject queryParamJSON, int userId);
}
