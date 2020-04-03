package com.topk.consumer;

import io.prometheus.client.Counter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class TokenProducer {
    private final Producer<Long, String> producer;
    private final static String KAFKA_URL = System.getenv("KAFKA_URL");
    private final static String QUEUE_TOPIC = "queue.tokens.en";
    private final static String DEQUE_TOPIC = "deque.tokens.en";
    private static final Counter requests = Counter.build().name("tokens_total").help("Total tokens.").register();

    public TokenProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_URL);
        props.put("key.serializer", LongSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        producer = new KafkaProducer<>(props);
    }

    public void send(Long key, String value) {
        requests.inc();
        producer.send(new ProducerRecord<>(QUEUE_TOPIC, key, value));
        producer.send(new ProducerRecord<>(DEQUE_TOPIC, key, value));
    }

    public void close() {
        producer.close();
    }
}
