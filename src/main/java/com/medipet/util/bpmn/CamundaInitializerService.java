package com.medipet.util.bpmn;

import com.medipet.config.CamundaAPIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class CamundaInitializerService {

    private final List<CamundaTaskHandlerService> handlers;
    private final CamundaAPIClient camundaAPIClient;

    @EventListener(ApplicationReadyEvent.class)
    public void handle() {
        deployBPMNDiagrams();
        initializeTaskHandlers();
    }

    private void deployBPMNDiagrams() {
        //todo this should be changed a bit, path hardcoded and should not be!
        try (Stream<Path> paths = Files.walk(Paths.get("./src/main/resources/bpmn"))) {
            paths
                    .filter(path -> Files.isRegularFile(path))
                    .forEach(camundaAPIClient::deployProcess);
        } catch (IOException e) {
            //todo
        }
        System.out.println("Processes deployed successfully");
    }

    private void initializeTaskHandlers() {
        handlers.forEach(CamundaTaskHandlerService::subscribe);
        log.info("All handlers initialized successfully");
    }
}
