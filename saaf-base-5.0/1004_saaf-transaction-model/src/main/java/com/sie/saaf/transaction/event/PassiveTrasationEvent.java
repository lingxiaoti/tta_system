package com.sie.saaf.transaction.event;


import org.springframework.context.ApplicationEvent;

/**
 *  被动方应用事务事件
 *  author: duzh
 *  date: 2018-06-12
 */
public class PassiveTrasationEvent extends ApplicationEvent {

    private String name;

    public PassiveTrasationEvent(String name, Object source) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
