package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgCfgEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgCfg extends IBaseCommon<MsgCfgEntity_HI> {

    List<MsgCfgEntity_HI> findMsgCfgInfo(JSONObject queryParamJSON);

    Object saveMsgCfgInfo(JSONObject queryParamJSON);

    Pagination<MsgCfgEntity_HI_RO> findSourceConfigInfo(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgCfg(JSONObject queryParamJSON, int userId);
}
