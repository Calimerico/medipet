package com.medipet.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CamundaAPIClient {

    @Qualifier("camundaRestClient")
    private final RestTemplate restTemplate;

    public void startProcessInstanceByKey(String key, String businessKey, Map<String, Object> variables) {

    }

    public Object getVariable(String businessKey, String variableName) {
        return null;//todo
    }

    public String getProcessInstanceIdByBusinessKey(String businessKey) {
        return null;//todo
    }

    public void deployProcess(Path file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("upload", new FileSystemResource(file.toFile()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("/deployment/create?upload", requestEntity , Object.class);
    }
}
