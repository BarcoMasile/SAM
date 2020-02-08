package xyz.marcobasile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import xyz.marcobasile.twitter.util.LoginUtils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    private BottomNavigationView navView;
    private TwitterCore twitterCore;
    private TweetComposer tweetComposer;
    private TwitterLoginButton loginBtn;
    private String currentUserUsername;
    private AuthToken currentUserToken;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);

        twitterCore = TwitterCore.getInstance();
        tweetComposer = TweetComposer.getInstance();

        setupMainView();

        if (!LoginUtils.isUserAuthenticated()) {
            setupLoginView();
        }

        Log.i(TAG, "Done creating " + TAG);
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

        loginBtn.setCallback(LoginUtils.makeCallback(this, loginBtn, navView));

        loginBtn.setVisibility(View.VISIBLE);
        navView.setVisibility(View.INVISIBLE);
    }
}
