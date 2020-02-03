package xyz.marcobasile;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);

        TwitterCore twitterCore = TwitterCore.getInstance();
        TweetComposer tweetComposer = TweetComposer.getInstance();

        setupMainView();

        if (!isUserAuthenticated()) {
            setupLoginView();
        }
    }

    private boolean isUserAuthenticated() {
        return true;
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
        // TODO far apparire il bottone di Login
        navView.setVisibility(View.INVISIBLE);
    }
}
