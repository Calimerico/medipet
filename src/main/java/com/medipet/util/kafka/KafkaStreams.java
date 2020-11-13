package com.medipet.util.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface KafkaStreams {
    String EMAIL_TOPIC_INPUT_CHANNEL = "emailTopicInputChannel";
    String EMAIL_TOPIC_OUTPUT_CHANNEL = "emailTopicOutputChannel";

    @Input(EMAIL_TOPIC_INPUT_CHANNEL)
    SubscribableChannel emailTopicInputChannel();

    @Output(EMAIL_TOPIC_OUTPUT_CHANNEL)
    MessageChannel emailTopicOutputChannel();

}
