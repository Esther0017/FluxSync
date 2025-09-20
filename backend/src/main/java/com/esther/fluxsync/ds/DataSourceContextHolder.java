package com.esther.fluxsync.ds;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void set(String key) { CONTEXT.set(key); }
    public static String get() { return CONTEXT.get(); }
    public static void clear() { CONTEXT.remove(); }

}
