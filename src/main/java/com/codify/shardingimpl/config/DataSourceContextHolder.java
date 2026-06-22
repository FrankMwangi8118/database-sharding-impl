package com.codify.shardingimpl.config;

public class DataSourceContextHolder {
    private static final ThreadLocal<String> CURRENT_DATASOURCE = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    public static void use(String dataSourceName) {
        CURRENT_DATASOURCE.set(dataSourceName);
    }

    public static String current() {
        return CURRENT_DATASOURCE.get();
    }

    public static void clear() {
        CURRENT_DATASOURCE.remove();
    }
}
