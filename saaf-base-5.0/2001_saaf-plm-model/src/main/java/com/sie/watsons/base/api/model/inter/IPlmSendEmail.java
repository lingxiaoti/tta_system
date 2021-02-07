package com.sie.watsons.base.api.model.inter;

import java.util.Map;

public interface IPlmSendEmail {

    void timingSendEmail(Map<String, String> map) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException;
}
