package xyz.marcobasile.ui.adapter.savedposts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.service.ContentProvider;


public class SavedPostsAdapter extends CursorAdapter {

    private final static String TAG = SavedPostsAdapter.class.getName();

    private static final boolean AUTO_REQUERY = true;
    private final int SAVED_ICON_RES = R.drawable.ic_star_24px;

    private Drawable savedIcon;

    private ImageView profileImageView, mediaImageView;
    private MaterialButton likes, retweets, saveBtn;
    private TextView usernameView, tweetBodyView;
    private View itemView;

    private int originalMinHeight;
    private TweetRepository repo;
    private ContentProvider provider = ContentProvider.getInstance();


    public SavedPostsAdapter(Context context, TweetRepository repo) {
        super(context, repo.findAllCursor(), AUTO_REQUERY);
        this.repo = repo;
        savedIcon = context.getResources().getDrawable(SAVED_ICON_RES, null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sam_tweet_item_layout, parent, false);

        return itemView;
    }

    @Override
    public void bindView(View mainView, Context context, Cursor cursor) {

        setupViews(mainView);
        populateViews(cursor);
    }

    private void populateViews(Cursor c) {

        SAMTweet tweet = repo.tweetFromCursor(c);

        String profileImageUrl = tweet.getUser().getProfileImageUrl();
        Bitmap profileImage = provider.getImage(profileImageUrl);

        if (null == profileImage) {

            profileImageView.setImageBitmap(null); // TODO: vediamo se risolve
            provider.enqueueImageDownload(profileImageUrl, () -> {

                Bitmap b = provider.getImage(profileImageUrl); // se il download e' andato bene ora ci sara' la bitmap
                profileImageView.setImageBitmap(b);
                notifyDataSetChanged();
            });
        } else {

            profileImageView.setImageBitmap(profileImage);
        }


        Bitmap mediaBitmap = Optional.<String>ofNullable(tweet.getMediaURL())
                .map(mediaURL -> provider.getImage(mediaURL))
                .orElse(null);

        mediaImageView.setImageBitmap(mediaBitmap);

        usernameView.setText(tweet.getUser().getScreenName());
        tweetBodyView.setText(tweet.getText());

        retweets.setText(Integer.toString(tweet.getRetweetCount()));
        likes.setText(Integer.toString(tweet.getFavoriteCount()));

        saveBtn.setIcon(savedIcon);

        setupSavedButton(tweet);
    }

    private void setupSavedButton(SAMTweet tweet) {

        saveBtn.setOnClickListener(view -> {
            repo.delete(tweet.getId());
            changeCursor(repo.findAllCursor());
        });
    }

    private void setupViews(View mainView) {

        profileImageView = mainView.findViewById(R.id.tweet_profile_img);
        mediaImageView = mainView.findViewById(R.id.media_image);
        usernameView = mainView.findViewById(R.id.tweet_user_name);
        tweetBodyView = mainView.findViewById(R.id.tweet_body_text);

        retweets = mainView.findViewById(R.id.retweet_count);
        likes = mainView.findViewById(R.id.likes_count);
        saveBtn = mainView.findViewById(R.id.save_btn);

        originalMinHeight = mainView.getMinimumHeight();
    }
}
