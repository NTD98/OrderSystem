package com.example.orderprocessingconsumer.listener;

public interface KafkaListenerService {
    void listenEvent(String eventMessage);
}
