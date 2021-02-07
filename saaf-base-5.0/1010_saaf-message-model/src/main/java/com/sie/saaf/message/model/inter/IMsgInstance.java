package com.sie.saaf.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.message.model.entities.MsgCfgEntity_HI;
import com.sie.saaf.message.model.entities.MsgInstanceEntity_HI;
import com.sie.saaf.message.model.entities.MsgSourceCfgEntity_HI;
import com.sie.saaf.message.model.entities.readonly.MsgInstanceEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

public interface IMsgInstance extends IBaseCommon<MsgInstanceEntity_HI> {

    List<MsgInstanceEntity_HI> findMsgInstanceInfo(JSONObject queryParamJSON);

    Object saveMsgInstanceInfo(JSONObject queryParamJSON);

    Map<String, Object> saveInstance(JSONObject params) throws Exception;

    JSONObject retry(JSONObject queryParamJSON);

    Pagination<MsgInstanceEntity_HI_RO> findInstance(JSONObject jsonObject, Integer integer, Integer integer1);

    JSONObject sendSMSImmediately(MsgInstanceEntity_HI instance, MsgSourceCfgEntity_HI sourceCfg, MsgCfgEntity_HI msgCfg);

    String deleteMsgInstance(JSONObject queryParamJSON, int userId);

    Integer saveRequestLog(JSONObject paramJSON);

    void updateRequestLog(int logId, String returnStr);

    MsgInstanceEntity_HI_RO findMsgInstanceDetailById(JSONObject queryParamJSON);
}
