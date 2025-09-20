package com.esther.fluxsync.ds;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UseDBAspect {

    @Around("@within(com.esther.fluxsync.ds.UseDB) || @annotation(com.esther.fluxsync.ds.UseDB)")
    public Object switchDB(ProceedingJoinPoint pjp) throws Throwable {

        UseDB useDB = findUseDB(pjp);
        String key = (useDB != null) ? useDB.value() : null;
        if (key != null && !key.isEmpty()) {
            DataSourceContextHolder.set(key);
        }
        try {
            return pjp.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }

    private UseDB findUseDB(ProceedingJoinPoint pjp) {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();

        UseDB onMethod = AnnotationUtils.findAnnotation(method, UseDB.class);
        if (onMethod != null) return onMethod;

        Class<?> declaringType = ms.getDeclaringType();
        UseDB onType = AnnotationUtils.findAnnotation(declaringType, UseDB.class);
        if (onType != null) return onType;

        Class<?> targetClass = pjp.getTarget() != null ? pjp.getTarget().getClass() : null;
        return targetClass != null ? AnnotationUtils.findAnnotation(targetClass, UseDB.class) : null;
    }
}
