package com.medipet.policy.api.rest;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubmitPolicyFormRequest {
    private String policyName;
}
