package com.example.orderprocessingconsumer.producer.impl;

import com.example.orderprocessingconsumer.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    @Async
    public void sendMessage(String topic,String message) {
        kafkaTemplate.send(topic, message);
    }
}
