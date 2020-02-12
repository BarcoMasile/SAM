package xyz.marcobasile.service.twitter;

import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.List;

import xyz.marcobasile.model.Tweet;

public class TwitterClient {

    private static String TAG = TwitterClient.class.getName();

    private static TwitterClient instance = new TwitterClient();

    public static TwitterClient getInstance() {
        return instance;
    }

    public TwitterClient() {
        Log.i(TAG, "Initializing custom twitter client");
    }

    public List<Tweet> getHomeTimelineTweets() {
        return null;
    }

    public void postTweet(Tweet tweet) {
        TweetComposer composer = TweetComposer.getInstance();
    }

    public Tweet getTweet(Long id) {
        return null;
    }

    public Tweet getTweet(String id) {
        return null;
    }

}