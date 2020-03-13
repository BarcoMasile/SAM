package xyz.marcobasile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import xyz.marcobasile.repository.TwitterUserRepository;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.util.LoginUtils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    private BottomNavigationView navView;

    private TwitterLoginButton loginBtn;
    private TextView welcomeTextView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(getApplicationContext());
        setupMainView();

        if (!LoginUtils.isUserAuthenticated()) {
            setupLoginView();
        } else {
            TwitterClient.createTwitterClient();
        }
        Log.i(TAG, "Done creating " + TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loginBtn.onActivityResult(requestCode, resultCode, data);
        welcomeTextView.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
    }

    private void setupMainView() {

        setContentView(R.layout.activity_main_relative_layout);
        loginBtn = findViewById(R.id.login_button);
        welcomeTextView = findViewById(R.id.welcome_text);

        navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_tweet_composer, R.id.navigation_saved_posts)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void setupLoginView() {
        loginBtn.setCallback(LoginUtils.makeCallback(this, loginBtn, navView));

        welcomeTextView.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
        navView.setVisibility(View.INVISIBLE);
    }

}
