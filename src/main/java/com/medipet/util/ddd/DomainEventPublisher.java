package com.medipet.util.ddd;

import com.medipet.util.StaticBean;
public class DomainEventPublisher {

    public static void publish(DomainEvent domainEvent) {
        StaticBean.getBean(DomainEventPublisherBean.class).publish(domainEvent);
    }

}
