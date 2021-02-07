package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgHistoryEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgHistoryEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgHistory  extends IBaseCommon<MsgHistoryEntity_HI> {

	List<MsgHistoryEntity_HI> findMsgHistoryInfo(JSONObject queryParamJSON);

	Object saveMsgHistoryInfo(JSONObject queryParamJSON);

    Pagination<MsgHistoryEntity_HI_RO> findHistoy(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgHistory(JSONObject queryParamJSON, int userId);
}
