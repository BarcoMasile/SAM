package xyz.marcobasile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import lombok.extern.slf4j.Slf4j;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private BottomNavigationView navView;
    private TwitterCore twitterCore;
    private TweetComposer tweetComposer;
    private TwitterLoginButton loginBtn;
    private String currentUserUsername;
    private AuthToken currentUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);

        twitterCore = TwitterCore.getInstance();
        tweetComposer = TweetComposer.getInstance();

        setupMainView();

        if (!isUserAuthenticated()) {
            setupLoginView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginBtn.onActivityResult(requestCode, resultCode, data);
    }

    private void setupMainView() {
        setContentView(R.layout.activity_main_relative_layout);
        navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_saved_posts)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void setupLoginView() {
        loginBtn = findViewById(R.id.login_button);
        Callback<TwitterSession> twitterSessionCallback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.i(TAG, "Twitter Login successful");

                currentUserUsername = result.data.getUserName();
                currentUserToken = result.data.getAuthToken();
                Log.d(TAG, "Twitter auth result, username: " + currentUserUsername + ", userId: " + currentUserUsername + ", authToken: " + currentUserToken);

                loginBtn.setVisibility(View.INVISIBLE);
                navView.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w(TAG, "Twitter Login failure");
                currentUserUsername = null;
                currentUserToken = null;
                Log.d(TAG, "Message: " + exception.getLocalizedMessage());
            }
        };

        loginBtn.setCallback(twitterSessionCallback);

        loginBtn.setVisibility(View.VISIBLE);
        navView.setVisibility(View.INVISIBLE);
    }

    private boolean isUserAuthenticated() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return null == session;
    }
}
