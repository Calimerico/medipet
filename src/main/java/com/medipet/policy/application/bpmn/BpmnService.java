package com.medipet.policy.application.bpmn;

import com.medipet.config.CamundaAPIClient;
import com.medipet.policy.api.rest.SubmitPolicyFormRequest;
import com.medipet.policy.application.PolicyDTO;
import com.medipet.util.bpmn.Variables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BpmnService {

    private final CamundaAPIClient camundaAPIClient;
    @Qualifier("camundaRestClient")
    private final RestTemplate restTemplate;
    private static final String NEXT_FORM = "nextForm";
    private static final String FORM_NAME = "formName";

    public PolicyDTO createPolicy() {
        String id = UUID.randomUUID().toString();
        camundaAPIClient.startProcessInstanceByKey(id, "Policy",null);
        return new PolicyDTO(UUID.fromString(id),(String) camundaAPIClient.getVariable(id, NEXT_FORM));
    }

    public PolicyDTO submitForm(SubmitPolicyFormRequest submitPolicyFormRequest, UUID policyId, String formName) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Map<String, AbstractMap.SimpleEntry<String,Object>> restTemplateMap = new HashMap<>();
        restTemplateMap.put("submitPolicyFormRequest", new AbstractMap.SimpleEntry<String,Object>("value",submitPolicyFormRequest));
        restTemplateMap.put("formName", new AbstractMap.SimpleEntry<String,Object>("value",formName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        restTemplate.postForEntity("/task/" + camundaAPIClient.getProcessInstanceIdByBusinessKey(policyId.toString()) + "/submit-form",new Variables(restTemplateMap), Object.class);
        return new PolicyDTO(policyId,(String) camundaAPIClient.getVariable(policyId.toString(), NEXT_FORM));
    }
}
