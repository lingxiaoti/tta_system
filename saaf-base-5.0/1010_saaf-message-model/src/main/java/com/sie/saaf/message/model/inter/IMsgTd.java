package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgTdEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgTdEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgTd  extends IBaseCommon<MsgTdEntity_HI> {

	List<MsgTdEntity_HI> findMsgTdInfo(JSONObject queryParamJSON);

	Object saveMsgTdInfo(JSONObject queryParamJSON);

    String getSmsExEx();

    Pagination<MsgTdEntity_HI_RO> findMsgTdList(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgTd(JSONObject queryParamJSON, int userId);
}
