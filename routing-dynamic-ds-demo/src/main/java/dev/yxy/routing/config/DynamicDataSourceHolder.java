package dev.yxy.routing.config;

import org.springframework.lang.Nullable;

/**
 * 动态数据源上下文持有者
 *
 * @author atom
 * @create 2022/03/10 3:51
 * @update 2022/03/10 3:51
 * @origin aop-dynamic-ds-demo
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setContext(String dsName) {
        CONTEXT.set(dsName);
    }

    @Nullable
    public static String getContext() {
        return CONTEXT.get();
    }

    public static void removeContext() {
        CONTEXT.remove();
    }
}
