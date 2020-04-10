package com.topk.termexporter;

import io.prometheus.client.exporter.HTTPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);
    private static final int PROMETHEUS_PORT = 8082;

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(PROMETHEUS_PORT);
            logger.info("Prometheus server started on port : " + server.getPort());

            TweetConsumer.getInstance().consume();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
