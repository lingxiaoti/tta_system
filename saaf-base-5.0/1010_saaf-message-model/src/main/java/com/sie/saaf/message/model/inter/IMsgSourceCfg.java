package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgSourceCfgEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IMsgSourceCfg  extends IBaseCommon<MsgSourceCfgEntity_HI> {

	List<MsgSourceCfgEntity_HI> findMsgSourceCfgInfo(JSONObject queryParamJSON);

	Object saveMsgSourceCfgInfo(JSONObject queryParamJSON);

    Pagination<MsgSourceCfgEntity_HI_RO> findSourceConfigInfo(JSONObject jsonObject, Integer integer, Integer integer1);

	String deleteMsgCfgSource(JSONObject queryParamJSON, int userId);

    Object saveOrUpdateMsgSource(JSONObject queryParamJSON);
}
