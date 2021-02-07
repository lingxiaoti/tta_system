package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgReceiveSqlEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgReceiveSql  extends IBaseCommon<MsgReceiveSqlEntity_HI> {

	List<MsgReceiveSqlEntity_HI> findMsgReceiveSqlInfo(JSONObject queryParamJSON);

	Object saveMsgReceiveSqlInfo(JSONObject queryParamJSON);

    Pagination<MsgReceiveSqlEntity_HI> findReceiveSqlInfo(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgReceiveSql(JSONObject queryParamJSON, int userId);
}
