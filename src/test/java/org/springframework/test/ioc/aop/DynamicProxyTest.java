package org.springframework.test.ioc.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.AdvisedSupport;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.CglibAopProxy;
import org.springframework.aop.framework.JdkDynamicAopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.MethodAfterAdviceInterceptor;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodThrowsAdviceInterceptor;
import org.springframework.test.ioc.common.*;
import org.springframework.test.ioc.service.WorldService;
import org.springframework.test.ioc.service.WorldServiceImpl;

public class DynamicProxyTest {

    private AdvisedSupport advisedSupport;

    @Before
    public void setup() {
        WorldService worldService = new WorldServiceImpl();

        advisedSupport = new ProxyFactory();
        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* org.springframework.test.ioc.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        AfterReturningAdviceInterceptor methodInterceptor = new AfterReturningAdviceInterceptor(new WorldServiceAfterReturningAdvice());
        advisor.setAdvice(methodInterceptor);
        TargetSource targetSource = new TargetSource(worldService);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.addAdvisor(advisor);
    }

    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testCglibDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory() throws Exception {
        // 使用JDK动态代理
        ProxyFactory factory = (ProxyFactory) advisedSupport;
        factory.setProxyTargetClass(false);
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();

        // 使用CGLIB动态代理
        factory.setProxyTargetClass(true);
        proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }

    @Test
    public void testBeforeAdvice() throws Exception {
        //设置BeforeAdvice
        String expression = "execution(* org.springframework.test.ioc.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);
        advisedSupport.addAdvisor(advisor);
        ProxyFactory factory = (ProxyFactory) advisedSupport;
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }

    @Test
    public void testAfterAdvice() throws Exception {
        WorldServiceAfterAdvice afterAdvice = new WorldServiceAfterAdvice();
        MethodAfterAdviceInterceptor methodInterceptor = new MethodAfterAdviceInterceptor(afterAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory().getProxy();
        proxy.explode();
    }

    @Test
    public void testAfterReturningAdvice() throws Exception {
        WorldServiceAfterReturningAdvice afterReturningAdvice = new WorldServiceAfterReturningAdvice();
        AfterReturningAdviceInterceptor methodInterceptor = new AfterReturningAdviceInterceptor(afterReturningAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory().getProxy();
        proxy.explode();
    }

    @Test
    public void testThrowsAdvice() throws Exception {
        WorldServiceThrowsAdvice throwsAdvice = new WorldServiceThrowsAdvice();
        MethodThrowsAdviceInterceptor methodInterceptor = new MethodThrowsAdviceInterceptor(throwsAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory().getProxy();
        proxy.explode();
    }

    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* org.springframework.test.ioc.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodAfterAdviceInterceptor methodInterceptor = new MethodAfterAdviceInterceptor(new WorldServiceAfterAdvice());
        advisor.setAdvice(methodInterceptor);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(worldService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

            WorldService proxy = (WorldService) new ProxyFactory().getProxy();
            proxy.explode();
        }
    }
}
