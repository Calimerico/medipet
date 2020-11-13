package com.medipet.util;

import com.medipet.policy.api.events.SendEmail;
import com.medipet.util.kafka.KafkaEventProducer;
import com.medipet.util.kafka.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaExample implements CommandLineRunner {

    private final KafkaEventProducer kafkaEventProducer;

    @Override
    public void run(String... args) throws Exception {
//        StaticBean.getBean(KafkaEventProducer.class).send();
        kafkaEventProducer.send(
                SendEmail
                        .builder()
                        .message("Hello Martin and Spasoje, you are awesome guys!")
                        .build(), Topic.EMAIL_TOPIC
        );
    }
}
