package xyz.marcobasile.service;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Setter;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.cache.ImageBitmapCache;

public class ContentProvider {

    private final static String TAG = ContentProvider.class.getName();
    // public static ContentProvider provider = new ContentProvider();

    private List<SAMTweet> tweets = new ArrayList<SAMTweet>();
    private final ImageBitmapCache cache = new ImageBitmapCache();

    @Setter
    private OnDataReceived onDataReceived;

    public List<SAMTweet> tweets() {

        return tweets;
    }


    /**
     * All'aggiunta di un insieme di tweet (circa 5-12) parte la sfilza di
     * {@link AsyncTask} per scaricare le foto (profilo e media) in cache
     * @param tweets
     */
    public void tweets(List<SAMTweet> tweets) {

        Log.i(TAG, "About to add " + tweets.size());
        if (onDataReceived != null) {
            // TODO: questo lo devo fare quando finiscono tutti i task
            onDataReceived.accept(tweets);
        }

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

    public static interface OnDataReceived extends Consumer<List<SAMTweet>> {}

}
