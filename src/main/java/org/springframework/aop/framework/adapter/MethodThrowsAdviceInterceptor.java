package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodThrowsAdvice;

public class MethodThrowsAdviceInterceptor implements MethodInterceptor {

    private MethodThrowsAdvice advice;

    public MethodThrowsAdviceInterceptor(MethodThrowsAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Exception ex) {
            this.advice.throwsException(ex);
            throw ex;
        }
    }
}
