package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.UserPwdTempEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("userPwdTempDAO_HI")
public class UserPwdTempDAO_HI extends BaseCommonDAO_HI<UserPwdTempEntity_HI> {
    public UserPwdTempDAO_HI() {
        super();
    }
}
