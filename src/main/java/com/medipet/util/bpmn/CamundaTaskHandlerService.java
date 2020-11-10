package com.medipet.util.bpmn;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.backoff.ExponentialBackoffStrategy;
import org.camunda.bpm.client.task.ExternalTaskHandler;

public abstract class CamundaTaskHandlerService {
    private static final String CAMUNDA_URL = "http://localhost:8081/engine-rest";

    protected abstract ExternalTaskHandler handle();

    void subscribe() {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(CAMUNDA_URL)
                .backoffStrategy(new ExponentialBackoffStrategy(500,2,2000))//or disable with .disableBackoffStrategy()
                .asyncResponseTimeout(3000)
                .build();
        client.subscribe(getTopicName())
//                .lockDuration(1000)
                .handler(handle()).open();
    }

    protected abstract String getTopicName();
}
