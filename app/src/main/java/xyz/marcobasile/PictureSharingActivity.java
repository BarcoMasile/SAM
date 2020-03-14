package xyz.marcobasile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Media;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.callback.MediaCallback;
import xyz.marcobasile.service.twitter.util.LoginUtils;
import xyz.marcobasile.service.twitter.util.TwitterClientUtils;

import static xyz.marcobasile.service.twitter.util.TwitterClientUtils.getFileFromUri;

public class PictureSharingActivity extends Activity {

    private static final String TAG = PictureSharingActivity.class.getName();
    private static final String DEFAULT_TWEET = "Shared via #SAM App";

    private ImageView imageView;
    private Button cancel, tweet;

    private Uri pictureUri;
    private View backdrop;
    private ProgressBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupMainView();
        Twitter.initialize(getApplicationContext());

        Intent intent = getIntent();

        Log.i(TAG, "Got intent action: " + intent.getAction() + ", data: " + intent.getParcelableExtra(Intent.EXTRA_STREAM));

        if (!LoginUtils.isUserAuthenticated()) {

            intent.setData(null);
            Toast.makeText(getApplicationContext(), "Devi prima autenticarti nell'app!", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK, intent);
            finishAndRemoveTask();
        } else {
            TwitterClient.createTwitterClient();
        }

        getIntentContent(intent);

        setupListeners();
    }

    private void getIntentContent(Intent intent) {

        Optional.ofNullable(intent.getParcelableExtra(Intent.EXTRA_STREAM))
            .ifPresent(uri -> {
                pictureUri = (Uri) uri;
                imageView.setImageURI(pictureUri);
            });
    }

    private void setupMainView() {
        setContentView(R.layout.picture_sharing_activity);

        tweet = findViewById(R.id.ps_tweet_button);
        cancel = findViewById(R.id.ps_cancel_button);
        imageView = findViewById(R.id.image_view);

        // backdrop & progress bar
        backdrop = findViewById(R.id.backdrop2);
        bar = findViewById(R.id.progress_bar2);
        bar.setVisibility(View.INVISIBLE);

    }

    private void setupListeners() {

        cancel.setOnClickListener(view -> {
            pictureUri = null;
            setResult(Activity.RESULT_OK);
            finishAndRemoveTask();
        });

        tweet.setOnClickListener(view -> {
            toggleProgressBarAndBackDrop();
            TwitterClient.getInstance()
                    .postTweetWithPicture(DEFAULT_TWEET, pictureUri, mediaCallback(), getApplicationContext());
        });
    }

    private Callback<Tweet> mediaCallback() {

        return new Callback<Tweet>() {

            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {

                Log.i(TAG, "Tweet con immagine pubblicato con successo");
                toggleProgressBarAndBackDrop();
                setResult(Activity.RESULT_OK);

                Toast.makeText(getApplicationContext(), "Pubblicato!", Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {

                Log.i(TAG, "Tweet con immagine non pubblicato");
                toggleProgressBarAndBackDrop();
                Toast.makeText(getApplicationContext(), "Qualcosa e' andato storto", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_CANCELED);
                finishAndRemoveTask();
            }
        };
    }

    public void toggleProgressBarAndBackDrop() {
        if (bar.getVisibility() == View.VISIBLE) {

            bar.setVisibility(View.INVISIBLE);
            backdrop.setVisibility(View.INVISIBLE);

            Log.i(TAG, "Progress Bar playing");

        } else {

            bar.setVisibility(View.VISIBLE);
            backdrop.setVisibility(View.VISIBLE);

            Log.i(TAG, "Progress Bar stopped");

        }
    }

}
