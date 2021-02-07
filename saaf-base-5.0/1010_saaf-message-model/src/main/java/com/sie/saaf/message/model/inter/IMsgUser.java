package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgUserEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgUserEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgUser extends IBaseCommon<MsgUserEntity_HI> {

    List<MsgUserEntity_HI> findMsgUserInfo(JSONObject queryParamJSON);

    Object saveMsgUserInfo(JSONObject queryParamJSON);

    Pagination<MsgUserEntity_HI_RO> findMsgUserList(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgUser(JSONObject queryParamJSON, int userId);

    String getDesKey();
}
