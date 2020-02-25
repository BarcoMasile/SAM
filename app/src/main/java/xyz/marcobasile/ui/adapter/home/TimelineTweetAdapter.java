package xyz.marcobasile.ui.adapter.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                .inflate(R.layout.sam_tweet_item_layout, parent, false);

        return new TimelineTweetViewHolder(samTweetItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineTweetViewHolder holder, int position) {

        SAMTweet samTweet = tweets.get(position);
        holder.tweetBody(samTweet.getText());
        holder.retweets(samTweet.getRetweetCount());
        holder.likes(samTweet.getFavoriteCount());
        holder.saved(samTweet.isSaved());

        SAMTwitterUser user = samTweet.getUser();
        holder.username(user.getScreenName());
        holder.profileImage(user.getProfileImage());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

}
