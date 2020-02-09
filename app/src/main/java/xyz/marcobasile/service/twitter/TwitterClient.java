package xyz.marcobasile.service.twitter;

import android.util.Log;

import java.util.List;

import xyz.marcobasile.model.Tweet;

public class TwitterClient {
    private static String TAG = TwitterClient.class.getName();

    public TwitterClient() {
        Log.i(TAG, "Initializing custom twitter client");
    }

    public List<Tweet> getHomeTimelineTweets() {
        return null;
    }

    public void postTweet(Tweet tweet) {

    }

    public Tweet getTweet(Long id) {
        return null;
    }

    public Tweet getTweet(String id) {
        return null;
    }

}