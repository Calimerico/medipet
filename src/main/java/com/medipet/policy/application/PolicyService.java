package com.medipet.policy.application;

import com.medipet.policy.domain.events.PolicyCreatedDomainEvent;
import com.medipet.policy.infrastructure.PolicyRepository;
import com.medipet.util.ddd.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyDTO getPolicyById(UUID policyId) {
        return null;
    }

    public PolicyDTO createPolicy() {
        UUID id = UUID.randomUUID();
        eventRepo.save();//
        DomainEventPublisher.publish(PolicyCreatedDomainEvent.builder().id(id).build());
        return null;
    }


}
