package com.medipet.policy.application;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@Value
public class PolicyDTO {
    private UUID policyId;
    private String nextForm;
}
