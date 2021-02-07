package com.sie.watsons.base.redisUtil.InvalidEntityUtil;

public enum VtorProfile {

    INSERT("insert", "插入"),
    UPDATE("update", "全部更新"),
    PATCH("patch", "局部更新"),
    SELECT("select", "查询"),
    DELETE("delete", "删除");

    private final String name;

    private final String desc;

    VtorProfile(String name, String desc) {
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public String getDesc() {
        return this.desc;
    }

}
