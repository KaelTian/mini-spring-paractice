package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class AutowiredAnnotationTest {
    @Test
    public void testAutowiredAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");

        Car car = applicationContext.getBean(Car.class);
        car.setBrand("Kael Tian");

        Person person = applicationContext.getBean(Person.class);
        person.setAge(18);
        assertThat(person.getCar()).isNotNull();
        assertThat(person.getCar().getBrand()).isEqualTo("Kael Tian");
        assertThat(person.getAge()).isEqualTo(18);

        car.setBrand("LOL");
        Person person1 = applicationContext.getBean(Person.class);
        assertThat(person1.getCar().getBrand()).isEqualTo("LOL");
        assertThat(person1.getAge()).isEqualTo(0);
    }
}
