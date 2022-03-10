package dev.yxy.routing.aspect;

import dev.yxy.routing.annotation.DataSource;
import dev.yxy.routing.config.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 数据源切面
 *
 * @author atom
 * @create 2022/03/10 23:30
 * @update 2022/03/10 23:30
 * @origin aop-dynamic-ds-demo
 */
@Aspect
@Component
public class DataSourceAspect {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@within(dev.yxy.routing.annotation.DataSource) || @annotation(dev.yxy.routing.annotation.DataSource)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String datasourceName = null;
            // 方法上的注解优先级最高
            Method method = Optional.of(joinPoint).map(JoinPoint::getSignature).map(MethodSignature.class::cast).map(MethodSignature::getMethod).orElse(null);
            if (method != null) {
                datasourceName = this.parseDatasourceName(AnnotationUtils.findAnnotation(method, DataSource.class), method);
            }

            // 尝试类型上的注解
            if (datasourceName == null) {
                Class<?> clazz = Optional.of(joinPoint).map(JoinPoint::getTarget).map(Object::getClass).orElse(null);
                if (clazz != null) {
                    datasourceName = this.parseDatasourceName(AnnotationUtils.findAnnotation(clazz, DataSource.class), method);
                }
            }

            // 设置数据源
            if (datasourceName != null) {
                DynamicDataSourceHolder.setContext(datasourceName);
            }

            return joinPoint.proceed();
        } finally {
            // 清除数据源
            DynamicDataSourceHolder.removeContext();
        }
    }

    /**
     * 解析数据源名称
     */
    private String parseDatasourceName(DataSource annotation, Method method) {
        String datasourceName = null;
        if (annotation != null) {
            datasourceName = annotation.value();
            if (logger.isDebugEnabled() && method != null) {
                logger.debug("[{}#{}] 指定数据源 [{}]", method.getDeclaringClass().getName(), method.getName(), datasourceName);
            }
        }
        return datasourceName;
    }
}
