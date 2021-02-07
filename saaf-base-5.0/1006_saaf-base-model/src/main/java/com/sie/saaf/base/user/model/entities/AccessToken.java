package com.sie.saaf.base.user.model.entities;

/**
 * Created by chenzg on 2018/3/6.
 */
public class AccessToken {
    private String token;
    private Integer expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
