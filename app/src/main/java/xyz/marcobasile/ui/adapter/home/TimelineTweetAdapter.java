package xyz.marcobasile.ui.adapter.home;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.service.task.DBSavedStateControlTask;

public class TimelineTweetAdapter extends RecyclerView.Adapter<TimelineTweetViewHolder> {

    private final static String TAG = TimelineTweetAdapter.class.getName();

    private TweetRepository tweetRepo;

    private ContentProvider provider;

    public TimelineTweetAdapter(ContentProvider provider, TweetRepository tweetRepo) {

        this.provider = provider;
        this.tweetRepo = tweetRepo;
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

        SAMTweet samTweet = provider.tweets().get(position);

        holder.tweetBody(samTweet.getText());
        holder.retweets(samTweet.getRetweetCount());
        holder.likes(samTweet.getFavoriteCount());

        new DBSavedStateControlTask(holder, tweetRepo).execute(samTweet);

        Bitmap mediaBitmap = Optional.<String>ofNullable(samTweet.getMediaURL())
                .map(mediaURL -> provider.getImage(mediaURL))
                .orElse(null);
        holder.mediaImage(mediaBitmap); // se e' null, rimuoviamo l'immagine

        SAMTwitterUser user = samTweet.getUser();

        holder.username(user.getScreenName());
        Optional.ofNullable(provider.getImage(user.getProfileImageUrl()))
                .ifPresent(holder::profileImage);

        holder.setupSavedButton(provider.tweets(), position, tweetRepo);
    }

    @Override
    public int getItemCount() {
        return provider.tweets().size();
    }

}
