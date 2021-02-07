package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgTempleCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgTempleCfgEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgTempleCfg  extends IBaseCommon<MsgTempleCfgEntity_HI> {

	List<MsgTempleCfgEntity_HI> findMsgTempleCfgInfo(JSONObject queryParamJSON);

	Object saveMsgTempleCfgInfo(JSONObject queryParamJSON);

    Pagination<MsgTempleCfgEntity_HI_RO> findTempleConfigInfo(JSONObject jsonObject, Integer integer, Integer integer1);

    String deleteMsgTempleCfg(JSONObject queryParamJSON, int userId);

    Object saveOrUpdateMsgTemple(JSONObject queryParamJSON);
}
