package org.springframework.test.ioc.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.service.WorldService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AutoProxyTest {
    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");


        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }
}
