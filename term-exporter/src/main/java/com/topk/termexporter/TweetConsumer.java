package com.topk.termexporter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class TweetConsumer {
    private static Logger logger = LoggerFactory.getLogger(TweetConsumer.class);
    private static TweetConsumer instance = null;
    private final KafkaConsumer<Long, String> consumer;
    private final static String KAFKA_URL = System.getenv("KAFKA_URL");
    private final static String TOPIC = "queue.tweets.en";
    private final static String GROUP = "group";
    private final TokenProducer tokenProducer;

    private TweetConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_URL);
        props.put("key.deserializer", LongDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);
        props.put("group.id", GROUP);

        consumer = new KafkaConsumer<>(props);
        tokenProducer = new TokenProducer();
    }

    public static TweetConsumer getInstance() {
        if (instance == null) {
            instance = new TweetConsumer();
        }

        return instance;
    }

    public void consume() {
        consumer.subscribe(Arrays.asList(TOPIC));
        logger.info("Consuming to the topic : " + TOPIC);

        while (true) {
            ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Long, String> record : records) {
                List<String> tokens = Tokenizer.tokenize(record.value());
                for (String token : tokens) {
                    tokenProducer.send(record.key(), token);
                }
            }
        }
    }
}
