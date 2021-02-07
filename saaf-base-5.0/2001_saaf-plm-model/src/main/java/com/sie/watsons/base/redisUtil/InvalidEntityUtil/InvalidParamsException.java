package com.sie.watsons.base.redisUtil.InvalidEntityUtil;

public class InvalidParamsException extends RuntimeException {
    private static final long serialVersionUID = 2059676220299846426L;

    public InvalidParamsException(String s) {
        super(s);
    }
}