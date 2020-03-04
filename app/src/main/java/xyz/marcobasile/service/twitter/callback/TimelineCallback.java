package xyz.marcobasile.service.twitter.callback;

import android.util.Log;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.service.mapper.SAMTweetMapper;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class TimelineCallback implements Callback<List<Tweet>> {

    private static final String TAG = TimelineCallback.class.getName();
    private final TwitterClient client;
    private final SAMTweetMapper mapper = new SAMTweetMapper();

    private ContentProvider provider;
    private GenericProcedure callback;

    public TimelineCallback(ContentProvider provider, TwitterClient client, GenericProcedure callback) {

        this.provider = provider;
        this.client = client;
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {

        if (!response.isSuccessful()) {
            Log.e(TAG, "Impossibile ottenere tweets, possibile raggiungigmento API rate limit.");
            return;
        }

        List<SAMTweet> timeline = mapper.toSAMTweet(response.body());
        Log.i(TAG, "Get timeline success, " + timeline.size() + " tweets");
        Log.i(TAG, "Old sinceId: " + client.getSinceId());
        // Log.i(TAG, "Old maxId: " + client.getMaxId());

        timeline.stream()
                .map(SAMTweet::getId)
                .max(Long::compareTo)
                .ifPresent(client::setSinceId);
        /*timeline.stream()
                .map(SAMTweet::getId)
                .min(Long::compareTo)
                .ifPresent(client::setMaxId);*/


        Log.i(TAG, "New sinceId: " + client.getSinceId());
        // Log.i(TAG, "New maxId: " + client.getMaxId());

        provider.tweets(timeline, callback);
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {

        Log.i(TAG, "get timeline failure");
    }
}
