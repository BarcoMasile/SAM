package xyz.marcobasile.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twitter.sdk.android.core.Twitter;

import java.util.Timer;

import xyz.marcobasile.R;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.service.task.TweetDownloaderTimerTask;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.util.LoginUtils;
import xyz.marcobasile.ui.adapter.home.TimelineTweetAdapter;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();
    private static final String TWEET_DOWNLOADER_TIMER = "TweetDownloaderTimer";

    private RecyclerView recyclerView;
    private TimelineTweetAdapter timelineTweetAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton refreshBtn;

    private ContentProvider provider;
    private Timer tweetDownloaderTimer = new Timer(TWEET_DOWNLOADER_TIMER);
    private TweetDownloaderTimerTask tweetDownloaderTask;
    private TwitterClient twitterClient;
    private TweetRepository tweetRepo;

    private boolean properlyInitialized = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        provider = ContentProvider.getInstance();
        provider.setContext(getContext());

        tweetRepo = new TweetRepository(getContext());

        setupView(root);
        if (!LoginUtils.isUserAuthenticated()) {

            return root;
        }

        homeFragmentTweetInit();

        Log.i(TAG, "Done onCreateView");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private void homeFragmentTweetInit() {

        if (properlyInitialized) {
            return;
        }

        properlyInitialized = true;
        TwitterClient.createTwitterClient();
        populateScrollView();

        if (null == tweetDownloaderTask) {
            tweetDownloaderTask = new TweetDownloaderTimerTask(provider, twitterClient);
        }
    }

    private void setupView(View root) {

        recyclerView = (RecyclerView) root.findViewById(R.id.home_scroll_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        timelineTweetAdapter = new TimelineTweetAdapter(provider, tweetRepo);
        recyclerView.setAdapter(timelineTweetAdapter);

        refreshBtn = root.findViewById(R.id.refresh_button);

        refreshBtn.setVisibility(View.GONE);
        refreshBtn.setOnClickListener(view -> {

            timelineTweetAdapter.notifyDataSetChanged();
            refreshBtn.setVisibility(View.GONE);
        });

        provider.setOnDataReceived(tweets -> refreshBtn.setVisibility( tweets.size() > 0 ? View.VISIBLE : View.GONE));
    }

    private void populateScrollView() {

        Twitter.initialize(getContext());
        twitterClient = TwitterClient.getInstance();
        twitterClient.getHomeTimelineTweets(provider, () -> timelineTweetAdapter.notifyDataSetChanged());
    }

    private void startTimer() {

        if (LoginUtils.isUserAuthenticated()) {

            homeFragmentTweetInit();
            tweetDownloaderTimer.scheduleAtFixedRate(tweetDownloaderTask, TweetDownloaderTimerTask.DELAY, TweetDownloaderTimerTask.DELAY);
        }
    }

    private void stopTimer() {

        if (LoginUtils.isUserAuthenticated()) {

            tweetDownloaderTimer.cancel();
            tweetDownloaderTimer.purge();

            tweetDownloaderTimer = new Timer(TWEET_DOWNLOADER_TIMER);
            tweetDownloaderTask = new TweetDownloaderTimerTask(provider, twitterClient);
        }
    }
}