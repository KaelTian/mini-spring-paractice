package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class PackageScanTest {
    @Test
    public void testScanPackage() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        car.setBrand("LOL");

        Car car1 = applicationContext.getBean("car", Car.class);
        assertThat(car1.getBrand()).isEqualTo("LOL");

        Person person = applicationContext.getBean("person", Person.class);
        person.setAge(18);
        //assertThat(person.getCar().getBrand()).isEqualTo("LOL");
        assertThat(person.getAge()).isEqualTo(18);
        Person person1 = applicationContext.getBean("person", Person.class);
        assertThat(person1.getAge()).isEqualTo(0);
//        Car car1 = applicationContext.getBean("car", Car.class);
//        assertThat(car1).isNull();
    }
}
