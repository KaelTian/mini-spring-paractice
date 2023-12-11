package org.springframework.aop;

public interface MethodThrowsAdvice extends ThrowsAdvice {
    void throwsException(Exception exception) throws Throwable;
}
