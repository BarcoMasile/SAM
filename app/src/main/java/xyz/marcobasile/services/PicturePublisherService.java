package xyz.marcobasile.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.Objects;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.service.twitter.TwitterClient;

public class PicturePublisherService extends IntentService {

    private static final String TAG = PicturePublisherService.class.getName();
    private static final String TWEET_BODY = "Via SAM App for Android";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PicturePublisherService(String name) {
        super(name);
        Log.i(TAG, "Init " + TAG + " by thread: " + name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Optional.ofNullable(intent)
                .filter(this::isActionSend)
                .map(Intent::getData)
                .filter(Objects::nonNull)
                .ifPresent(this::postTweetPicture);

    }

    private void postTweetPicture(Uri pictureUri) {
        TwitterClient.getInstance()
                .postTweetWithPicture(TWEET_BODY, pictureUri, callback(), this);
    }

    private boolean isActionSend(Intent intent) {
        return intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEND);
    }

    private Callback<Tweet> callback() {

        return new Callback<Tweet>() {

            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                Log.i(TAG, "onResponse callback");
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Log.i(TAG, "onFailure callback");
            }
        };
    }
}
