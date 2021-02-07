package com.sie.saaf.schedule.utils;

public class DBContextHolder{
    public static final String DATA_SOURCE_FROM = "dataSourceDefault";
    public static final String DATA_SOURCE_TO = "dataSourceOut";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDBType() {
        return contextHolder.get();
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}
