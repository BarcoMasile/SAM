package xyz.marcobasile.service;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.cache.ImageBitmapCache;
import xyz.marcobasile.service.task.BitmapDownloader;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class ContentProvider {

    private final static String TAG = ContentProvider.class.getName();
    private final static ContentProvider instance = new ContentProvider();

    private final ImageBitmapCache cache = new ImageBitmapCache();
    private List<SAMTweet> tweets = new ArrayList<SAMTweet>();
    private BitmapDownloader bitmapDownloader;

    public ContentProvider() {

        this.bitmapDownloader = new BitmapDownloader(this);
    }


    public List<SAMTweet> tweets() {
        return tweets;
    }


    public void tweets(List<SAMTweet> tweets, GenericProcedure callback) {

        Log.i(TAG, "About to add " + tweets.size());

        Set<String> urlStrings = tweets.stream()
                .flatMap(tweet -> Stream.<String>of(tweet.getMediaURL(), tweet.getUser().getProfileImageUrl()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (callback != null) {
            bitmapDownloader.setOnetimeCallback(callback);
        }

        bitmapDownloader.execute(urlStrings);

        this.tweets.addAll(tweets);
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

    public void setOnDataReceived(BitmapDownloader.OnDataReceived callback) {
        bitmapDownloader.setCallback(callback);
    }

}
