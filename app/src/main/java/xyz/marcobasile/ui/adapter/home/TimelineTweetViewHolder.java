package xyz.marcobasile.ui.adapter.home;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.repository.TweetRepository;


public class TimelineTweetViewHolder extends RecyclerView.ViewHolder {

    private final static String TAG = TimelineTweetViewHolder.class.getName();
    public static final int LAST_ITEM_OFFSET = 148;
    private final int SAVED_ICON_RES = R.drawable.ic_star_24px;
    private final int UNSAVED_ICON_RES = R.drawable.ic_star_border_24px;

    private Drawable savedIcon, unsavedIcon;

    private ImageView profileImageView, mediaImageView;
    private MaterialButton likes, retweets, saveBtn;
    private TextView usernameView, tweetBodyView;
    private View itemView;

    private int originalMinHeight;

    public TimelineTweetViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        setupViews(itemView);

        savedIcon = itemView.getResources().getDrawable(SAVED_ICON_RES, null);
        unsavedIcon = itemView.getResources().getDrawable(UNSAVED_ICON_RES, null);
    }

    public void setupViews(View mainView) {

        profileImageView = mainView.findViewById(R.id.tweet_profile_img);
        mediaImageView = mainView.findViewById(R.id.media_image);
        usernameView = mainView.findViewById(R.id.tweet_user_name);
        tweetBodyView = mainView.findViewById(R.id.tweet_body_text);

        retweets = mainView.findViewById(R.id.retweet_count);
        likes = mainView.findViewById(R.id.likes_count);
        saveBtn = mainView.findViewById(R.id.save_btn);

        originalMinHeight = mainView.getMinimumHeight();
    }

    public void profileImage(Bitmap profileImage) {

        profileImageView.setImageBitmap(profileImage);
    }

    public void mediaImage(Bitmap mediaImage) {

        mediaImageView.setImageBitmap(mediaImage);
    }

    public void likes(int likesCount) {

        likes.setText(String.format(Locale.getDefault(),"%d", likesCount));
    }

    public void retweets(int retweetsCount) {

        retweets.setText(String.format(Locale.getDefault(),"%d", retweetsCount));
    }

    public void saved(boolean isSaved) {
        saveBtn.setIcon(isSaved ? savedIcon : unsavedIcon);
    }

    public void username(String username) {

        usernameView.setText(username);
    }

    public void tweetBody(String tweetBody) {

        tweetBodyView.setText(tweetBody);
    }

    public void setupSavedButton(List<SAMTweet> tweets, int position) {

        saveBtn.setTag(position);
        saveBtn.setOnClickListener(btnView -> {

            SAMTweet samTweet = tweets.get(position);

            samTweet.setSaved(!samTweet.getSaved()); // TODO: controllare che il null non crei problemi
            saved(samTweet.getSaved());

            Log.i(TAG, "SaveButton on click in position: " + position);
        });
    }

    public void setupMarginForLastItem() {

        itemView.setMinimumHeight(originalMinHeight + LAST_ITEM_OFFSET);
    }

    public void resetMarginForItem() {

        itemView.setMinimumHeight(originalMinHeight);
    }
}
