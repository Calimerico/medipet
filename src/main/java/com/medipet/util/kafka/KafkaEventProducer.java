package com.medipet.util.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class KafkaEventProducer {
    private final MessageChannelFactory messageChannelFactory;

    public void send(KafkaMessage message, Topic topic) {
        MessageChannel messageChannel = this.messageChannelFactory.create(topic);
        messageChannel.send(
                MessageBuilder
                        .withPayload(message)
                        .setHeader("messageType", message.getClass().getSimpleName())
                        .setHeader("contentType", MimeTypeUtils.APPLICATION_JSON)
                        .build()
        );
    }

    public KafkaEventProducer(MessageChannelFactory messageChannelFactory) {
        this.messageChannelFactory = messageChannelFactory;
    }
}
