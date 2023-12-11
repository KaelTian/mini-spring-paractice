package org.springframework.test.ioc.bean;

public class A {
    private B b;

    public void func() {
        System.out.println("I am A's func");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
