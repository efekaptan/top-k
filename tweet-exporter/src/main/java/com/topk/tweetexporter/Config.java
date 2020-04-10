package com.topk.tweetexporter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Config {
    private static final String CONSUMER_KEY = "CONSUMERKEY";
    private static final String CONSUMER_SECRET = "CONSUMERSECRET";
    private static final String ACCESS_TOKEN = "ACCESSTOKEN";
    private static final String ACCESS_TOKEN_SECRET = "ACCESSTOKENSECRET";
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
