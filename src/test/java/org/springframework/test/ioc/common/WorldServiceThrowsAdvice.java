package org.springframework.test.ioc.common;

import org.springframework.aop.MethodThrowsAdvice;

public class WorldServiceThrowsAdvice implements MethodThrowsAdvice {
    @Override
    public void throwsException(Exception exception) throws Throwable {
        System.out.println("WorldServiceThrowsAdvice throws exception: " + exception.getCause().toString());
    }
}
