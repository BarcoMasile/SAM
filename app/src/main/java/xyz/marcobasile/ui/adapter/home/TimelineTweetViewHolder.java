package xyz.marcobasile.ui.adapter.home;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;

/*@Data
@Builder
@AllArgsConstructor*/
public class TimelineTweetViewHolder extends RecyclerView.ViewHolder {

    private final static String TAG = TimelineTweetViewHolder.class.getName();

//    private View samTweetItemView;

    private ImageView profileImageView;
    private Button likes, retweets, saveBtn;
    private TextView usernameView, tweetBodyView;

    public TimelineTweetViewHolder(@NonNull View itemView) {
        super(itemView);
//        this.samTweetItemView = itemView;
        setupViews(itemView);
    }

    private void setupViews(View mainView) {

        profileImageView = mainView.findViewById(R.id.tweet_profile_img);
        usernameView = mainView.findViewById(R.id.tweet_user_name);
        tweetBodyView = mainView.findViewById(R.id.tweet_body_text);

        retweets = mainView.findViewById(R.id.retweet_count);
        likes = mainView.findViewById(R.id.likes_count);
        saveBtn = mainView.findViewById(R.id.save_btn);
    }

    public void setProfileImageView(Bitmap profileImage) {

        profileImageView.setImageBitmap(profileImage);
    }

    public void setLikes(int likesCount) {

        likes.setText(String.format(Locale.getDefault(),"%d", likesCount));
    }

    public void setRetweets(int retweetsCount) {

        retweets.setText(String.format(Locale.getDefault(),"%d", retweetsCount));
    }

    public void setSaveBtn(Button saveBtn) {
        this.saveBtn = saveBtn;
    }

    public void setUsernameView(String username) {

        usernameView.setText(username);
    }

    public void setTweetBodyView(String tweetBody) {

        tweetBodyView.setText(tweetBody);
    }
}
