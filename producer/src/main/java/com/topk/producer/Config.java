package com.topk.producer;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Config {
    private static final String CONSUMER_KEY = "PRODUCER_CONSUMERKEY";
    private static final String CONSUMER_SECRET = "PRODUCER_CONSUMERSECRET";
    private static final String ACCESS_TOKEN = "PRODUCER_ACCESSTOKEN";
    private static final String ACCESS_TOKEN_SECRET = "PRODUCER_ACCESSTOKENSECRET";
    private static Configuration instance = null;

    private Config() {
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new ConfigurationBuilder()
                    .setOAuthConsumerKey(getConfig(CONSUMER_KEY))
                    .setOAuthConsumerSecret(getConfig(CONSUMER_SECRET))
                    .setOAuthAccessToken(getConfig(ACCESS_TOKEN))
                    .setOAuthAccessTokenSecret(getConfig(ACCESS_TOKEN_SECRET))
                    .build();
        }
        return instance;
    }

    private static String getConfig(String key) {
        return System.getenv(key);
    }
}
