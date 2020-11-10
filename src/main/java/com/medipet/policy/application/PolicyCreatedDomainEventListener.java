package com.medipet.policy.application;

import com.medipet.policy.domain.events.PolicyCreatedDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PolicyCreatedDomainEventListener {

    @EventListener
    public void handle(PolicyCreatedDomainEvent domainEvent) {

        //here we should do anything that should be done when policy is created
        //here we can ping some external service and tell them that policy is created
        //here we should update our elasticsearch
    }
}
