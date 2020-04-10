package com.topk.tweetexporter;

import io.prometheus.client.exporter.HTTPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final int PROMETHEUS_PORT = 8081;
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(PROMETHEUS_PORT);
            logger.info("Prometheus server started on port : " + server.getPort());

            TweetStreamer tweetStreamer = TweetStreamer.getInstance();
            tweetStreamer.setLanguage("en");
            tweetStreamer.stream();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
