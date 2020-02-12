package xyz.marcobasile.ui.composer.listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import xyz.marcobasile.ui.composer.util.AlertDialogUtils;

public class TweetButtonListener implements View.OnClickListener {

    private final TextView textView;
    private final Activity activity;

    public TweetButtonListener(TextView textView, Activity activity) {
        this.textView = textView;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Log.i("TweetButton", "Click tweet");
        AlertDialogUtils.tweetButtonAlertDialog(textView, activity);
    }
}
