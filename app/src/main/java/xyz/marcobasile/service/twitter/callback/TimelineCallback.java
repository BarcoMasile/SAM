package xyz.marcobasile.service.twitter.callback;

import android.util.Log;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.service.twitter.TwitterClient;

public class TimelineCallback implements Callback<List<Tweet>> {

    private static final String TAG = TimelineCallback.class.getName();
    private final TwitterClient client;

    private List<Tweet> tweets;

    public static TimelineCallback makeCallback(List<Tweet> tweets, TwitterClient client) {
        return new TimelineCallback(tweets, client);
    }

    private TimelineCallback(List<Tweet> tweets, TwitterClient client) {

        this.tweets = tweets;
        this.client = client;
    }

    @Override
    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {

        List<Tweet> timeline = response.body();
        Log.i(TAG, "get timeline success, " + timeline.size() + " tweets");
        Log.i(TAG, "Old sinceId: " + client.getSinceId());

        timeline.stream()
                .map(Tweet::getId)
                .max(Long::compareTo)
                .ifPresent(client::setSinceId);

        Log.i(TAG, "New sinceId: " + client.getSinceId());

        tweets.addAll(timeline);
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {

        Log.i(TAG, "get timeline failure");
        tweets.clear();
    }
}
