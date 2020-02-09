package xyz.marcobasile.service.twitter.util;

import android.content.Context;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class TwitterInitializer {

    private static TwitterCore twitterCore;
    private static TweetComposer tweetComposer;

    private TwitterInitializer(Context ctx) {}

    public static void setup(Context ctx) {
        Twitter.initialize(ctx);
        twitterCore = TwitterCore.getInstance();
        tweetComposer = TweetComposer.getInstance();
    }

//    public static TwitterCore core() {
//        return TwitterCore.getInstance();
//    }
//
//    public static TweetComposer composer() {
//        return TweetComposer.getInstance();
//    }

}
