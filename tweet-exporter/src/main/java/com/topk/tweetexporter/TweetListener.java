package com.topk.tweetexporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.sql.Timestamp;

public class TweetListener implements StatusListener {
    private static Logger logger = LoggerFactory.getLogger(TweetListener.class);
    private final TweetProducer producer;

    public TweetListener(TweetProducer producer) {
        this.producer = producer;
    }

    @Override
    public void onStatus(Status status) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        producer.send(timestamp.getTime(), status.getText());
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        logger.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        logger.info("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        logger.info("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        logger.info("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}
