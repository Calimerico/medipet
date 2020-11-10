package com.medipet.policy.application.bpmn.taskhandlers;

import com.medipet.util.bpmn.CamundaTaskHandlerService;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
class Form1HandlerService extends CamundaTaskHandlerService {

    protected ExternalTaskHandler handle() {
        return (externalTask, externalTaskService) -> {

            System.out.println("Hello");

//            // retrieve a variable from the Workflow Engine
//            int defaultScore = externalTask.getVariable("defaultScore");
//
//            List<Integer> creditScores = new ArrayList<>(Arrays.asList(defaultScore, 9, 1, 4, 10));
//
//            // create an object typed variable
//            ObjectValue creditScoresObject = Variables
//                    .objectValue(creditScores)
//                    .create();
//
//            // complete the external task
            externalTaskService.complete(externalTask,new HashMap<>());
//
//            System.out.println("The External Task " + externalTask.getId() + " has been completed!");

        };
    }

    protected String getTopicName() {
        return "hello";
    }

}
