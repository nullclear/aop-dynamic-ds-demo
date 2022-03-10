package dev.yxy.routing.annotation;

import java.lang.annotation.*;

/**
 * The core Annotation to switch datasource. It can be annotate at class or method.
 *
 * @author atom
 * @create 2022/03/10 23:29
 * @update 2022/03/10 23:29
 * @origin aop-dynamic-ds-demo
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * datasource name
     *
     * @return the database you want to switch
     */
    String value();
}
