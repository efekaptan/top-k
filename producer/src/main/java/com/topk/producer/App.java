package com.topk.producer;

import io.prometheus.client.exporter.HTTPServer;

public class App {
    private static final int PROMETHEUS_PORT = 8081;

    public static void main(String[] args) {
        try {
            HTTPServer server = new HTTPServer(PROMETHEUS_PORT);
            System.out.println("Prometheus server started on port : " + server.getPort());

            TweetStreamer tweetStreamer = TweetStreamer.getInstance();
            tweetStreamer.setLanguage("en");
            tweetStreamer.stream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
