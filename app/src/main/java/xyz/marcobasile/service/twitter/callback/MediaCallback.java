package xyz.marcobasile.service.twitter.callback;

import com.twitter.sdk.android.core.models.Media;
import com.twitter.sdk.android.core.models.Tweet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.service.twitter.util.TwitterClientUtils;

public class MediaCallback implements Callback<Media> {

    private Call<Tweet> update;
    private String tweetText;
    private Callback<Tweet> callback;

    public MediaCallback(String tweetText, Callback<Tweet> callback) {
        this.tweetText = tweetText;
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<Media> call, Response<Media> response) {
        Long mediaId = response.body().mediaId;

        Call<Tweet> update = TwitterClientUtils.tweet(tweetText, mediaId);
        update.enqueue(callback);
    }

    @Override
    public void onFailure(Call<Media> call, Throwable t) {
        callback.onFailure(update, t);
    }
}
