package com.sie.saaf.sso.model.inter;

import com.sie.saaf.common.bean.UserSessionBean;

public interface ISsoServer {
     String setCookie(UserSessionBean userSessionBean);

     void deleteCookie(UserSessionBean userSessionBean);

     boolean authCookie(String cookie);

     UserSessionBean getUserSession(String cookie);

    boolean validatePdaSign(String timestamp, String sign);
}
