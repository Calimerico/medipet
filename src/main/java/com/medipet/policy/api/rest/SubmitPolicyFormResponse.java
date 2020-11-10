package com.medipet.policy.api.rest;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SubmitPolicyFormResponse extends RepresentationModel<PolicyResource> {
}
