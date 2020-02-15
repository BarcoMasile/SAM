package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.callback.PostTweetCallback;

public class TweetButtonAlertDialogListener implements DialogInterface.OnClickListener {

    private TextView textView;
    private TwitterClient client;
    private PostTweetCallback postTweetCallback;
    private PictureHolder pictureHolder;
    private Button attachIcon;

    public TweetButtonAlertDialogListener(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon) {
        this.textView = textView;
        client = TwitterClient.getInstance();
        postTweetCallback = new PostTweetCallback(textView, clearAttach, pictureHolder, attachIcon);
        this.pictureHolder = pictureHolder;
        this.attachIcon = attachIcon;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {
            // lanciamo il tweet
            String tweetText = textView.getText().toString();

            if (null == pictureHolder.getPictureUri()) {

                client.postTweet(tweetText, postTweetCallback);
            } else {

                Uri imageUri = pictureHolder.getPictureUri();
                client.postTweetWithPicture(tweetText, imageUri, postTweetCallback, textView.getContext());
            }

        }
    }
}
