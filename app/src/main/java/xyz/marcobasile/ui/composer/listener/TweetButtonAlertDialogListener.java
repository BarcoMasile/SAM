package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.service.twitter.callback.PostTweetCallback;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class TweetButtonAlertDialogListener implements DialogInterface.OnClickListener {

    private TextView textView;
    private GenericProcedure toggleProgressBar;
    private TwitterClient client;
    private PostTweetCallback postTweetCallback;
    private PictureHolder pictureHolder;

    public TweetButtonAlertDialogListener(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon, ImageView imagePreview, GenericProcedure toggleProgressBar) {
        this.textView = textView;
        this.toggleProgressBar = toggleProgressBar;
        client = TwitterClient.getInstance();
        postTweetCallback = new PostTweetCallback(textView, clearAttach, pictureHolder, attachIcon, imagePreview, toggleProgressBar);
        this.pictureHolder = pictureHolder;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {
            // lanciamo il tweet
            String tweetText = textView.getText().toString();

            // deve avviare
            toggleProgressBar.doProcedure();

            if (!pictureHolder.canProvide()) {

                client.postTweet(tweetText, postTweetCallback);
            } else {

                if (pictureHolder.hasBitmap()) {
                    client.postTweetWithPicture(tweetText, pictureHolder.getPictureBytes(), postTweetCallback, textView.getContext());
                } else {
                    client.postTweetWithPicture(tweetText, pictureHolder.getPictureUri(), postTweetCallback, textView.getContext());
                }

                Uri imageUri = pictureHolder.getPictureUri();

            }

        }
    }
}
