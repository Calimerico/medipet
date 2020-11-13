package com.medipet.util.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageChannelFactory {
    private final KafkaStreams kafkaStreams;

    public MessageChannel create(Topic topic) {
        switch(topic) {
            case EMAIL_TOPIC:
                return this.kafkaStreams.emailTopicOutputChannel();
            default:
                throw new IllegalArgumentException("Topic " + topic.getName() + " does not exist!");
        }
    }
}
