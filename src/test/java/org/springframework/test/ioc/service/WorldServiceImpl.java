package org.springframework.test.ioc.service;

import org.springframework.beans.BeansException;

public class WorldServiceImpl implements WorldService {

    private String name;

    @Override
    public void explode() {
        System.out.println("The " + name + " is going to explode");
    }

    @Override
    public void throwsException() {
        throw new BeansException("I am bean exception throws by kael tian");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
