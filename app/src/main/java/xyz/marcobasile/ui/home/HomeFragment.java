package xyz.marcobasile.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import xyz.marcobasile.R;
import xyz.marcobasile.service.twitter.TwitterClient;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private List<Tweet> tweets = new ArrayList<Tweet>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setupView(root);

        populateScrollView();

        Log.i(TAG, "End onCreateView");

        return root;
    }

    private void setupView(View root) {
        //

    }

    private void populateScrollView() {

        Twitter.initialize(getContext());
        TwitterClient twitterClient = TwitterClient.getInstance();

        twitterClient.getHomeTimelineTweets(tweets);

        Log.i(TAG, "Ottenuti " + tweets.size() + " tweets.");
    }
}