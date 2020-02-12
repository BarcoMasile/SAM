package xyz.marcobasile.ui.composer.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

public class TweetButtonAlertDialogListener implements DialogInterface.OnClickListener {

    private TextView textView;
    private Activity activity;

    public TweetButtonAlertDialogListener(TextView textView, Activity activity) {
        this.textView = textView;
        this.activity = activity;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                // lanciamo il tweet
                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(activity)
                        .session(session)
                        //.image(uri)
                        .text("This is a test")
                        .hashtags("#twitter")
                        .createIntent();
                activity.startActivity(intent);

                // textView.setText("");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                // do nothing
                break;

            default:
                break;
        }
    }
}
