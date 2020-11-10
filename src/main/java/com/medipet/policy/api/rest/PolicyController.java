package com.medipet.policy.api.rest;

import com.medipet.policy.application.bpmn.BpmnService;
import com.medipet.policy.application.PolicyDTO;
import com.medipet.policy.application.PolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/policy")
@RequiredArgsConstructor
@Api(value = "Policy", tags = {"Policy"})
public class PolicyController {

    private static final String ID_PATH = "/{policyId}";
    private final BpmnService bpmnService;
    private final PolicyService policyService;

    @PostMapping
    public ResponseEntity<PolicyResource> createPolicy() {
        PolicyDTO policyDTO = policyService.createPolicy();
        PolicyResource policyResource = PolicyResource.from(policyDTO);
        policyResource.add(linkTo(methodOn(PolicyController.class).submitPolicyForm(null, policyDTO.getNextForm(), policyDTO.getPolicyId())).withRel("nextForm"));
        policyResource.add(linkTo(methodOn(PolicyController.class).getById(policyResource.getPolicyId())).withRel("seePolicy"));
        return ResponseEntity.ok(policyResource);
    }

    @GetMapping(value = ID_PATH)
    public ResponseEntity<PolicyResource> getById(
            @PathVariable UUID policyId
    ) {
        PolicyResource policyResponse = new PolicyResource();
//        PolicyResource.from(policyService.getPolicyById(policyId));
        policyResponse.add(linkTo(methodOn(PolicyController.class).getById(policyId)).withSelfRel());

        return new ResponseEntity<>(policyResponse, HttpStatus.OK);
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmitPolicyFormResponse> submitPolicyForm(
            @RequestBody SubmitPolicyFormRequest submitPolicyFormRequest,
            @ApiParam(required = true)
            @RequestParam String formName,
            @PathVariable UUID policyId
    ) {
        PolicyDTO policyDTO = bpmnService.submitForm(submitPolicyFormRequest, policyId, formName);
        SubmitPolicyFormResponse greeting = new SubmitPolicyFormResponse();
        greeting.add(linkTo(methodOn(PolicyController.class).submitPolicyForm(null, policyDTO.getNextForm(), policyId)).withRel("nextForm"));
        greeting.add(linkTo(methodOn(PolicyController.class).getById(policyId)).withRel("seePolicy"));

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
