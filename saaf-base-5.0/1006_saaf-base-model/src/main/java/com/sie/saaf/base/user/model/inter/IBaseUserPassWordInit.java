package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonEntity_HI;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;

import java.sql.SQLException;
import java.util.List;

public interface IBaseUserPassWordInit {

    /**
     * 查询用户
     * @author husaiqiang
     * @creteTime 2018/04/25
     */
    List<BaseUsersEntity_HI> findUserList(JSONObject queryParamJSON);

    /**
     * 查询员工
     * @author husaiqiang
     * @creteTime 2018/04/25
     */
    List<BasePersonEntity_HI> findPersonList(JSONObject paramJSON);

    void initPassWord(String userName) throws SQLException;

    void sendEmailBatch(String userName) throws Exception;

    void initPassWord82(String userName) throws SQLException;
}
