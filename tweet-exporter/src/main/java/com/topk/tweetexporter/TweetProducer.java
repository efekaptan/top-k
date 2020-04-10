package com.topk.tweetexporter;

import io.prometheus.client.Counter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class TweetProducer {
    private static Logger logger = LoggerFactory.getLogger(TweetProducer.class);
    private final static String TOPIC = "queue.tweets.en";
    private final static String KAFKA_URL = System.getenv("KAFKA_URL");
    private final Producer<Long, String> producer;
    private static final Counter requests = Counter.build().name("tweets_total").help("Total tweets.").register();

    public TweetProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_URL);
        props.put("key.serializer", LongSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        producer = new KafkaProducer<>(props);
        logger.info("Connected to the kafka server : " + KAFKA_URL);
    }

    public void send(Long key, String value) {
        requests.inc();
        producer.send(new ProducerRecord<>(TOPIC, key, value));
    }

    public void close() {
        producer.close();
    }
}
