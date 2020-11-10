package com.medipet.policy.api.rest;

import com.medipet.policy.application.PolicyDTO;
import com.medipet.policy.domain.Policy;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PolicyResource extends RepresentationModel<PolicyResource> {
    private UUID policyId;
    private String name;

    public static PolicyResource from(PolicyDTO policy) {
        return PolicyResource
                .builder()
                .policyId(policy.getPolicyId())
                .build();
    }
}
