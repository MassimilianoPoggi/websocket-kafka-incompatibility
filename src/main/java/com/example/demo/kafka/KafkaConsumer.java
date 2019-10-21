package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import org.apache.kafka.streams.kstream.KStream;

/**
 * KafkaConsumer
 */
@Component
@EnableBinding(KafkaStreamsProcessor.class)
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @StreamListener("my-kafka-topic")
    public void processMessage(KStream<String, String> input) {
        input.foreach((k, v) -> simpMessagingTemplate.convertAndSend("my-websocket-topic", v));
    }
}
