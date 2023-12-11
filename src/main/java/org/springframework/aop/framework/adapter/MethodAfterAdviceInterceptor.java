package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodAfterAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class MethodAfterAdviceInterceptor implements MethodInterceptor {
    private MethodAfterAdvice advice;

    public MethodAfterAdviceInterceptor() {
    }

    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    public void setAdvice(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        }
    }
}
