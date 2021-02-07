package com.sie.saaf.app.event;


import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录事件
 */
public class UserLoginEvent  extends ApplicationEvent {
    private BaseLoginLogEntity_HI loginInfo;

    public UserLoginEvent(Object source, BaseLoginLogEntity_HI loginInfo) {
        super(source);
        this.loginInfo = loginInfo;
    }

    public BaseLoginLogEntity_HI getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(BaseLoginLogEntity_HI loginInfo) {
        this.loginInfo = loginInfo;
    }
}
