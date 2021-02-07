package com.sie.saaf.base.user.model.inter.server;

import com.sie.saaf.base.user.model.entities.UserPwdTempEntity_HI;
import com.sie.saaf.base.user.model.inter.IUserPwdTemp;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.stereotype.Component;

/**
 * Created by husaiqiang on 2018/4/27.
 */
@Component("userPwdTempServer")
public class UserPwdTempServer  extends BaseCommonServer<UserPwdTempEntity_HI> implements IUserPwdTemp {
}
