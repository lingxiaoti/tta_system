package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPDAAutoCreate_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.sql.SQLException;

/**
 * Created by huangminglin on 2018/3/28.
 */
public interface IPdaUserAutoCreate extends IBaseCommon<BaseUsersEntity_HI> {

    JSONObject pdaUserAutoCreate(JSONObject queryParamJSON) throws SQLException;

    void createPdaUser(BaseUsersPDAAutoCreate_HI_RO entitie);
}
