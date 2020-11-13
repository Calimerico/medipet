package com.medipet.forms.api;

import com.medipet.policy.api.rest.PolicyResource;
import com.medipet.policy.application.PolicyDTO;
import com.medipet.policy.application.PolicyService;
import com.medipet.policy.application.bpmn.BpmnService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/forms")
@RequiredArgsConstructor
@Api(value = "Form", tags = {"Form"})
public class FormController {

    private static final String ID_PATH = "/{formId}";
    private final BpmnService bpmnService;
    private final PolicyService policyService;

    @PostMapping
    public ResponseEntity<PolicyResource> submitForm() {
        PolicyDTO policyDTO = policyService.createPolicy();
        PolicyResource policyResource = PolicyResource.from(policyDTO);
        policyResource.add(linkTo(methodOn(com.medipet.policy.api.rest.PolicyController.class).submitPolicyForm(null, policyDTO.getNextForm(), policyDTO.getPolicyId())).withRel("nextForm"));
        policyResource.add(linkTo(methodOn(com.medipet.policy.api.rest.PolicyController.class).getById(policyResource.getPolicyId())).withRel("seePolicy"));
        return ResponseEntity.ok(policyResource);
    }

}
