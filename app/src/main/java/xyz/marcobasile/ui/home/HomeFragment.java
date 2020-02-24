package xyz.marcobasile.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.ui.adapter.home.TimelineTweetAdapter;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();

    private RecyclerView recyclerView;
    private TimelineTweetAdapter timelineTweetAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<SAMTweet> tweets = new ArrayList<SAMTweet>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setupView(root);

        populateScrollView();

        Log.i(TAG, "End onCreateView");

        return root;
    }

    private void setupView(View root) {

        recyclerView = (RecyclerView) root.findViewById(R.id.home_scroll_view);
        recyclerView.setHasFixedSize(true); // per ora a true, poi a false probabilmente
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        timelineTweetAdapter = new TimelineTweetAdapter(tweets);
        recyclerView.setAdapter(timelineTweetAdapter);
    }

    private void populateScrollView() {

        Twitter.initialize(getContext());
        TwitterClient twitterClient = TwitterClient.getInstance();
        twitterClient.getHomeTimelineTweets(tweets, () -> timelineTweetAdapter.notifyDataSetChanged());

        Log.i(TAG, "Ottenuti " + tweets.size() + " tweets.");
    }
}