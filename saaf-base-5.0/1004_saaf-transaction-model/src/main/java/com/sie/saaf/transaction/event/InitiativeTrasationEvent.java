package com.sie.saaf.transaction.event;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

/**
 *  主动方应用事务事件
 *  author: duzh
 *  date: 2018-06-12
 */
public class InitiativeTrasationEvent extends ApplicationEvent {
    private static final Logger log= LoggerFactory.getLogger(InitiativeTrasationEvent.class);

    private String name;

    public InitiativeTrasationEvent(String name, Object source) {
        super(source);
        this.name = name;
        log.info("主动方应用事务事件{}实例化",name);
    }

    public String getName() {
        return this.name;
    }
}

