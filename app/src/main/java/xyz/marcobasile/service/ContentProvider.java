package xyz.marcobasile.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;
import xyz.marcobasile.service.cache.ImageBitmapCache;
import xyz.marcobasile.service.task.BitmapDownloader;
import xyz.marcobasile.service.task.UserLocationGeocoder;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class ContentProvider {

    private final static String TAG = ContentProvider.class.getName();
    private final static ContentProvider instance = new ContentProvider();

    private final ImageBitmapCache cache = new ImageBitmapCache();

    private List<SAMTweet> tweets = new ArrayList<SAMTweet>();
    private List<SAMTwitterUser> users = new ArrayList<SAMTwitterUser>();

    private BitmapDownloader.OnDataReceived dataCallback;

    private int previousTweetCardinality = 0;
    private Context ctx;

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    public List<SAMTweet> tweets() {
        return tweets;
    }

    public List<SAMTwitterUser> users() {

        return users.stream()
                .filter(user -> user.getLatLng() != null)
                .collect(Collectors.toList());
    }


    public void tweets(List<SAMTweet> tweets, GenericProcedure callback) {

        Log.i(TAG, "About to add tweets: " + tweets.size());

        Set<String> urlStrings = tweets.stream()
                .flatMap(tweet -> Stream.<String>of(tweet.getMediaURL(), tweet.getUser().getProfileImageUrl()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        BitmapDownloader bitmapDownloader = new BitmapDownloader(this);
        bitmapDownloader.setCallback(dataCallback);

        if (callback != null) {
            bitmapDownloader.setOnetimeCallback(callback);
        }

        bitmapDownloader.execute(urlStrings);

        saveRecentTweetUsers(tweets);

        this.tweets.addAll(tweets);
    }

    public void enqueueImageDownloadsForTweets(List<SAMTweet> tweets) {

        Set<String> urlStrings = tweets.stream()
                .flatMap(tweet -> Stream.<String>of(tweet.getMediaURL(), tweet.getUser().getProfileImageUrl()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        BitmapDownloader bitmapDownloader = new BitmapDownloader(this);
        bitmapDownloader.execute(urlStrings);
    }

    public void enqueueImageDownload(String url, GenericProcedure callback) {

        BitmapDownloader bitmapDownloader = new BitmapDownloader(this);
        bitmapDownloader.setOnetimeCallback(callback);
        bitmapDownloader.execute(Collections.singleton(url));
    }

    public Bitmap getImage(String key) {

        Bitmap bitmap;
        synchronized (cache) {
            bitmap = cache.get(key);
        }

        return bitmap;
    }

    public void putImage(String key, Bitmap bitmap) {

        synchronized (cache) {
            cache.put(key, bitmap);
        }
    }

    public static ContentProvider getInstance() {
        return instance;
    }

    public void setOnDataReceived(BitmapDownloader.OnDataReceived dataCallback) {
        this.dataCallback = dataCallback;
    }

    public int getAndResetPreviousTweetCardinality() {

        int previousTweetCardinality = this.previousTweetCardinality;
        this.previousTweetCardinality = tweets.size();
        return previousTweetCardinality;
    }

    private void saveRecentTweetUsers(List<SAMTweet> tweets) {

        // UserLocationGeocoder geocoderTask = new UserLocationGeocoder(ctx);

        Set<SAMTwitterUser> recentTweetUsersSet = tweets.stream()
                .map(SAMTweet::getUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        //geocoderTask.execute(recentTweetUsersSet);
        Set<SAMTwitterUser> usersToGeocode = new HashSet<>(recentTweetUsersSet);

        recentTweetUsersSet.addAll(users);

        users.clear();
        users.addAll(recentTweetUsersSet); // e poi li conservo nella lista per facile utilizzo

        usersToGeocode.forEach(user -> {
            new UserLocationGeocoder(ctx).execute(user);
        });
    }

}
