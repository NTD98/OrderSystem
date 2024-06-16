package com.example.orderprocessingconsumer.producer;


public interface KafkaProducer {
    void sendMessage(String topic,String message);
}
