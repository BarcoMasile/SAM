package xyz.marcobasile.ui.adapter.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;

public class TimelineTweetAdapter extends RecyclerView.Adapter<TimelineTweetViewHolder> {

    private final static String TAG = TimelineTweetAdapter.class.getName();

    private List<SAMTweet> tweets;

    public TimelineTweetAdapter(List<SAMTweet> tweets) {
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public TimelineTweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "Timeline Tweet view holder init");

        View samTweetItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sam_tweet_item, parent, false);

        // TODO: usare il builder
        return new TimelineTweetViewHolder(samTweetItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineTweetViewHolder holder, int position) {

        SAMTweet samTweet = tweets.get(position);
        SAMTwitterUser user = samTweet.getUser();

        holder.setUsernameView(user.getScreenName());
        holder.setTweetBodyView(samTweet.getText());
        holder.setRetweets(samTweet.getRetweetCount());
        holder.setLikes(samTweet.getFavoriteCount());
        holder.setProfileImageView(user.getProfileImage());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

}
