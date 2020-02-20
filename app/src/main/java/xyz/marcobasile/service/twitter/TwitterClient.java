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
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.twitter.callback.MediaCallback;
import xyz.marcobasile.service.twitter.callback.TimelineCallback;
import xyz.marcobasile.service.twitter.util.TwitterClientUtils;


public class TwitterClient {

    private static String TAG = TwitterClient.class.getName();
    private static final Integer TWEET_COUNT = 100;

    private Long sinceId = null;
    private Long maxId = null;
    private final Boolean contributeDetails = true;
    private final Boolean includeEntities = true;

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


    public void getHomeTimelineTweets(List<SAMTweet> tweets) {

        Call<List<Tweet>> listCall = apiClient.getStatusesService()
                .homeTimeline(TWEET_COUNT, sinceId, maxId,
                        false, true,
                        contributeDetails, includeEntities);

        listCall.enqueue(TimelineCallback.makeCallback(tweets, this));
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

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Long getSinceId() {
        return sinceId;
    }
}