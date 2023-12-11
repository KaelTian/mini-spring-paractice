package org.springframework.test.ioc.aop;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.test.ioc.service.HelloService;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class PointcutExpressionTest {

    @Test
    public void testPointcutExpression() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.springframework.test.ioc.service.HelloService.*(..))");
        Class<HelloService> clazz = HelloService.class;
        Method method = clazz.getDeclaredMethod("sayHello");

        assertThat(pointcut.matches(clazz)).isTrue();
        assertThat(pointcut.matches(method, clazz)).isTrue();
    }
}