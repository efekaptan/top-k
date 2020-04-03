package com.topk.producer;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public final class TweetStreamer {
    private String language;
    private final TweetListener listener;
    private static TweetStreamer instance = null;

    private TweetStreamer() {
        this.listener = new TweetListener(new TweetProducer());
    }

    public static TweetStreamer getInstance() {
        if (instance == null) {
            instance = new TweetStreamer();
        }

        return instance;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void stream() {
        TwitterStream twitterStream = new TwitterStreamFactory(Config.getInstance())
                .getInstance()
                .addListener(listener)
                .sample(language);
    }
}
