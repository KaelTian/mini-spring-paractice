package org.springframework.test.ioc.common;

import org.springframework.aop.MethodAfterAdvice;

import java.lang.reflect.Method;

public class WorldServiceAfterAdvice implements MethodAfterAdvice {
    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterAdvice: do something after the earth explodes,method name:" + method.getName());
    }
}
