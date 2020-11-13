package com.medipet;

import com.medipet.util.kafka.KafkaStreams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(KafkaStreams.class)
public class MediPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediPetApplication.class, args);
	}

}
