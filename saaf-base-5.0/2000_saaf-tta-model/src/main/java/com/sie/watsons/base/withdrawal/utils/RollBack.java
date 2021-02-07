package com.sie.watsons.base.withdrawal.utils;

/**
 * @author hmb
 * @date 2019/10/11 22:05
 */
public class RollBack {
    private Boolean isRollBack;

    public Boolean getRollBack() {
        return isRollBack;
    }

    public void setRollBack(Boolean rollBack) {
        isRollBack = rollBack;
    }

    public RollBack(Boolean isRollBack) {
        this.isRollBack = isRollBack;
    }

}
