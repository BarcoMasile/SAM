package xyz.marcobasile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.twitter.sdk.android.core.Twitter;

import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.util.LoginUtils;
import xyz.marcobasile.service.twitter.util.TwitterClientUtils;

public class PictureSharingActivity extends Activity {

    private static final String TAG = PictureSharingActivity.class.getName();
    private TextView tweetText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupMainView();
        Twitter.initialize(getApplicationContext());

        Intent intent = getIntent();

        Log.i(TAG, "Got intent action: " + intent.getAction() + ", data: " + intent.getData());

        if (!LoginUtils.isUserAuthenticated()) {

            intent.setData(null);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

        TwitterClient.createTwitterClient();
    }

    private void setupMainView() {
        setContentView(R.layout.picture_sharing_activity);
        tweetText = findViewById(R.id.text_view);

    }

}
