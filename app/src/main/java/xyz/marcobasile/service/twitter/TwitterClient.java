package xyz.marcobasile.service.twitter;

import android.content.Context;
import android.net.Uri;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Media;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import xyz.marcobasile.service.twitter.callback.MediaCallback;
import xyz.marcobasile.service.twitter.util.TwitterClientUtils;


public class TwitterClient {

    private static String TAG = TwitterClient.class.getName();

    private static TwitterClient instance;

    private TwitterApiClient apiClient;

    public static void createTwitterClient() {

        if (instance != null) {
            return;
        }

        instance = new TwitterClient();
        instance.setApiClient(client());
        TwitterClientUtils.services(instance.getApiClient().getStatusesService(),
                                    instance.getApiClient().getMediaService());
    }


    public List<String> getHomeTimelineTweets() {
        return null;
    }

    public void postTweet(String tweet, Callback<Tweet> callback) {

        Call<Tweet> update = TwitterClientUtils.tweet(tweet);
        update.enqueue(callback);
    }

    public void postTweetWithPicture(String tweet, Uri imageUri, Callback<Tweet> callback, Context ctx) {

        Call<Media> upload = TwitterClientUtils.picUpload(imageUri, ctx);
        upload.enqueue(new MediaCallback(tweet, callback));
    }


    private static TwitterApiClient client() {

        return TwitterCore.getInstance()
                .getApiClient(
                        TwitterCore.getInstance()
                                .getSessionManager()
                                .getActiveSession()
                );
    }

    public TwitterApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(TwitterApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public static TwitterClient getInstance() {
        return instance;
    }
}