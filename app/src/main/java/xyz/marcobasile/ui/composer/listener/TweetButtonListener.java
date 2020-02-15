package xyz.marcobasile.ui.composer.listener;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.composer.util.AlertDialogUtils;

public class TweetButtonListener implements View.OnClickListener {

    private final TextView textView;
    private PictureHolder pictureHolder;
    private Button clearAttach, attachIcon;

    public TweetButtonListener(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon) {
        this.textView = textView;
        this.pictureHolder = pictureHolder;
        this.clearAttach = clearAttach;
        this.attachIcon = attachIcon;
    }

    @Override
    public void onClick(View v) {
        Log.i("TweetButton", "Click tweet");
        AlertDialogUtils.tweetButtonAlertDialog(textView, pictureHolder, clearAttach, attachIcon);
    }
}
