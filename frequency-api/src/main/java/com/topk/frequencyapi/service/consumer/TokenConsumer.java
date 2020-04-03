package com.topk.frequencyapi.service.consumer;

import com.topk.frequencyapi.service.frequency.FrequencyCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

@Service
public class TokenConsumer {
    private final static int TEN_MINUTES = 60 * 1000 * 10;

    @Autowired
    private FrequencyCounter counter;

    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "${spring.kafka.consumer.queue-topic}", containerFactory = "containerFactory")
    public void consumeQueue(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) long key, Acknowledgment ack) {
        counter.insert(message);
        produceSample(message);
        ack.acknowledge();
    }

    @KafkaListener(topics = "${spring.kafka.consumer.deque-topic}", containerFactory = "containerFactory")
    public void consumeDeque(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) long key, Acknowledgment ack) {
        Timestamp stamp = new Timestamp(key);
        long diff = new Date().getTime() - stamp.getTime();
        if (diff < TEN_MINUTES) {
            ack.nack(1000);
        } else {
            counter.remove(message);
            ack.acknowledge();
        }
    }

    private void produceSample(String message) {
        final int sampleRatio = 10;
        Random random = new Random();
        if ((random.nextInt(sampleRatio) + sampleRatio) % sampleRatio == 0) {
            template.convertAndSend("/topic", message);
        }
    }
}
