package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor() {
    }


    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnVal = invocation.proceed();
        System.out.println("MethodAfterReturningInterceptor invoke");
        this.advice.afterReturning(returnVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return returnVal;
    }

    public void setAdvice(AfterReturningAdvice advice) {
        this.advice = advice;
    }
}
