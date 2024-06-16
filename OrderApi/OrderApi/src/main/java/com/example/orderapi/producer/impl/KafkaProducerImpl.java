package com.example.orderapi.producer.impl;

import com.example.orderapi.configuration.AppProperties;
import com.example.orderapi.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    private final AppProperties appProperties;
    @Override
    @Async
    public void sendMessage(String message) {
        kafkaTemplate.send(appProperties.getKafka().getCustomerTopic(), message);
    }
}
