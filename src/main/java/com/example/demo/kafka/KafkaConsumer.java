package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import org.apache.kafka.streams.kstream.KStream;

/**
 * KafkaConsumer
 */
@Component
@EnableBinding(KafkaConsumer.ConsumerBinding.class)
public class KafkaConsumer {
    interface ConsumerBinding {
        @Input("my-kafka-topic")
        KStream<?, ?> input();
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @StreamListener("my-kafka-topic")
    public void process(KStream<String, String> input) {
        input.foreach((k, v) -> simpMessagingTemplate.convertAndSend("my-websocket-topic", v));
    }
}
