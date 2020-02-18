package xyz.marcobasile;

import android.app.Activity;
import android.os.Bundle;

import com.twitter.sdk.android.core.Twitter;

import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.util.LoginUtils;

public class PictureSharingActivity extends Activity {

    private static final String TAG = PictureSharingActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(getApplicationContext());
        setupMainView();

        if (!LoginUtils.isUserAuthenticated()) {
            // setupLoginView();
        }
        TwitterClient.createTwitterClient();
    }

    private void setupMainView() {

    }

}
