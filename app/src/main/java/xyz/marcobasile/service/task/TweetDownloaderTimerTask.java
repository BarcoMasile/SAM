package xyz.marcobasile.service.task;


import java.util.TimerTask;

import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.service.twitter.TwitterClient;

public class TweetDownloaderTimerTask extends TimerTask {

    public static final long DELAY = 10L;

    private ContentProvider provider;
    private TwitterClient twitterClient;

    public TweetDownloaderTimerTask(ContentProvider provider, TwitterClient twitterClient) {
        this.provider = provider;
        this.twitterClient = twitterClient;
    }

    @Override
    public void run() {

        twitterClient.getHomeTimelineTweets(provider.tweets(), null);
    }
}
