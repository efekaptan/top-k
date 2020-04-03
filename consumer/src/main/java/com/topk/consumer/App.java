package com.topk.consumer;

import io.prometheus.client.exporter.HTTPServer;

public class App {
    private static final int PROMETHEUS_PORT = 8082;

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(PROMETHEUS_PORT);
            System.out.println("Prometheus server started on port : " + server.getPort());

            TweetConsumer.getInstance().consume();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
