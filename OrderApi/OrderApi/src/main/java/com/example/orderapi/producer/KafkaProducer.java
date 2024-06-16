package com.example.orderapi.producer;


public interface KafkaProducer {
    void sendMessage(String message);
}
