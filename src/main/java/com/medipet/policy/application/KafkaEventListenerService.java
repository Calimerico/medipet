package com.medipet.policy.application;

import com.medipet.policy.api.events.SendEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.medipet.util.kafka.KafkaStreams.EMAIL_TOPIC_INPUT_CHANNEL;
import static com.medipet.util.kafka.KafkaStreams.EMAIL_TOPIC_OUTPUT_CHANNEL;

@Service
@Transactional
@RequiredArgsConstructor
public class KafkaEventListenerService {

    @StreamListener(condition = "headers['messageType'] == 'SendEmail' ", value = EMAIL_TOPIC_OUTPUT_CHANNEL)
    public void handle(@Payload SendEmail sendEmail) {
        System.out.println(sendEmail.getMessage());
    }
}
