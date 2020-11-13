package com.medipet.policy.api.events;

import com.medipet.util.kafka.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmail implements KafkaMessage {
    private String message;
}
