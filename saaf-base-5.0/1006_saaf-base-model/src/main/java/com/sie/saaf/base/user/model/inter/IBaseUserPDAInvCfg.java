package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAInvCfgEntity_HI_RO;

import java.util.List;

public interface IBaseUserPDAInvCfg  {
    List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfg(JSONObject queryParamJSON);

    List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfgEntities();
    List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfgEntitiesByUserId(JSONObject queryParamJSON);
}
