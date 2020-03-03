package xyz.marcobasile.service.task;


import java.util.TimerTask;

import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.service.twitter.TwitterClient;

public class TweetDownloaderTimerTask extends TimerTask {

    public static final long DELAY = 1000L * 20L; // Millisecondi!!!

    private ContentProvider provider;
    private TwitterClient twitterClient;

    public TweetDownloaderTimerTask(ContentProvider provider, TwitterClient twitterClient) {
        this.provider = provider;
        this.twitterClient = twitterClient;
    }

    @Override
    public void run() {

        twitterClient.getHomeTimelineTweets(provider, null);
    }
}
