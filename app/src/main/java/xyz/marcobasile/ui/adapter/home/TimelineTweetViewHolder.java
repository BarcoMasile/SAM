package xyz.marcobasile.ui.adapter.home;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import xyz.marcobasile.R;


public class TimelineTweetViewHolder extends RecyclerView.ViewHolder {

    private final static String TAG = TimelineTweetViewHolder.class.getName();
    private final int SAVED_ICON_RES = R.drawable.ic_star_24px;
    private final int UNSAVED_ICON_RES = R.drawable.ic_star_border_24px;

    private final View samTweetItemView;
    private Drawable savedIcon, unsavedIcon;

    private ImageView profileImageView, mediaImageView;
    private Button likes, retweets, saveBtn;
    private TextView usernameView, tweetBodyView;


    public TimelineTweetViewHolder(@NonNull View itemView) {
        super(itemView);
        this.samTweetItemView = itemView;
        setupViews(itemView);

        savedIcon = itemView.getResources().getDrawable(SAVED_ICON_RES, null);
        unsavedIcon = itemView.getResources().getDrawable(UNSAVED_ICON_RES, null);
    }

    private void setupViews(View mainView) {

        profileImageView = mainView.findViewById(R.id.tweet_profile_img);
        mediaImageView = mainView.findViewById(R.id.media_image);
        usernameView = mainView.findViewById(R.id.tweet_user_name);
        tweetBodyView = mainView.findViewById(R.id.tweet_body_text);

        retweets = mainView.findViewById(R.id.retweet_count);
        likes = mainView.findViewById(R.id.likes_count);
        saveBtn = mainView.findViewById(R.id.save_btn);
    }

    public void profileImage(Bitmap profileImage) {

        profileImageView.setImageBitmap(profileImage);
    }

    public void likes(int likesCount) {

        likes.setText(String.format(Locale.getDefault(),"%d", likesCount));
    }

    public void retweets(int retweetsCount) {

        retweets.setText(String.format(Locale.getDefault(),"%d", retweetsCount));
    }

    public void saved(boolean isSaved) {

        saveBtn.setCompoundDrawables(isSaved ? savedIcon : unsavedIcon, null, null, null);
    }

    public void username(String username) {

        usernameView.setText(username);
    }

    public void tweetBody(String tweetBody) {

        tweetBodyView.setText(tweetBody);
    }
}
