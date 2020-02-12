package xyz.marcobasile.ui.composer.listener;

import android.app.Activity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import xyz.marcobasile.ui.shared.listener.TouchLayerOnTouchListener;


public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweet(TextView textView, Activity activity) {
        return new TweetButtonListener(textView, activity);
    }

    public static View.OnClickListener cancel(TextView textView) {
        return new CancelButtonListener(textView);
    }

    public static ChipGroup.OnCheckedChangeListener chipGroup() {
        return new ChipGroupCheckChangeListener();
    }

    public static TextWatcher tweetBody(MaterialButton tweetButton) {
        return new TweetBodyTextWatcher(tweetButton);
    }

    public static FrameLayout.OnTouchListener layer(TextView tweetBody) {
        return new TouchLayerOnTouchListener(tweetBody);
    }

    public static TweetButtonAlertDialogListener tweetDialog(TextView textView, Activity activity) {
        return new TweetButtonAlertDialogListener(textView, activity);
    }

    public static CancelButtonAlertDialogListener cancelDialog(TextView textView) {
        return new CancelButtonAlertDialogListener(textView);
    }
}
