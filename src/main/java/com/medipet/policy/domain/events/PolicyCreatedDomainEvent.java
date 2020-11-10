package com.medipet.policy.domain.events;

import com.medipet.util.ddd.DomainEvent;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class PolicyCreatedDomainEvent implements DomainEvent {
    private UUID id;
}
