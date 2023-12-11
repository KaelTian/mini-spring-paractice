package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.bean.Car;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LazyInitTest {
    @Test
    public void testLazyInit() throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:lazy-test.xml");
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        System.out.println(currentDate + ":applicationContext-over");
        TimeUnit.SECONDS.sleep(4);
        Car c=(Car) applicationContext.getBean("car");
        c.showTime();//显示bean的创建时间
    }
}
