package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;

import java.util.List;

/**
 * Created by husaiqiang on 2018/04/11.
 */
public interface IBaseUserSyncServer {


    BaseUsersEntity_HI packingUserEntity(JSONObject user);

    /**
     * 查询员工
     * @param queryParamJSON
     * @author husaiqiang
     * @creteTime 2018/04/11
     */
    List<BasePersonEntity_HI> findPersonList(JSONObject queryParamJSON);

    /**
     * 查询用户
     * @param queryParamJSON
     * @author husaiqiang
     * @creteTime 2018/04/11
     */
    List<BaseUsersEntity_HI> findUserList(JSONObject queryParamJSON);

    Integer findPersonIdBySourceId(Integer sourceId);

    JSONObject saveSyncCrmUser(JSONObject queryJSONObject);
}
