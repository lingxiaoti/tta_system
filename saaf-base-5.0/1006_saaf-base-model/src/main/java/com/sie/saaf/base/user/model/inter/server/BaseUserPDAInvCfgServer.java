package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAInvCfgEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserPDAInvCfg;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaseUserPDAInvCfgServer  implements IBaseUserPDAInvCfg {
    @Autowired
    private BaseViewObject<BaseUserPDAInvCfgEntity_HI_RO> baseUserPDAInvCfgDAO_HI_RO;

    public BaseUserPDAInvCfgServer(){

    }


    @Override
    public List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfg(JSONObject queryParamJSON) {
        List<BaseUserPDAInvCfgEntity_HI_RO> baseUserPDAInvCfgEntities = baseUserPDAInvCfgDAO_HI_RO.findList(BaseUserPDAInvCfgEntity_HI_RO.QUERY_USER_PDA_INV_CFG_SQL, queryParamJSON);
        return baseUserPDAInvCfgEntities;
    }

    @Override
    public List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfgEntities() {
        List<BaseUserPDAInvCfgEntity_HI_RO> baseUserPDAInvCfgEntities = baseUserPDAInvCfgDAO_HI_RO.findList(BaseUserPDAInvCfgEntity_HI_RO.QUERY_WAREHOUSE_MAPPING_SQL, new HashMap<String, Object>());
        return baseUserPDAInvCfgEntities;
    }

    @Override
    public List<BaseUserPDAInvCfgEntity_HI_RO> findBaseUserPDAInvCfgEntitiesByUserId(JSONObject queryParamJSON) {
        Map<String, Object> paramMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<BaseUserPDAInvCfgEntity_HI_RO> baseUserPDAInvCfgEntities = baseUserPDAInvCfgDAO_HI_RO.findList(BaseUserPDAInvCfgEntity_HI_RO.QUERY_WAREHOUSE_MAPPING_SQL_2, paramMap);
        return baseUserPDAInvCfgEntities;
    }
}
