package xyz.marcobasile.ui.composer.listeners;

import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;


public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweetButtonListener() {
        return new TweetButtonListener();
    }

    public static View.OnClickListener cancelButtonListener() {
        return new CancelButtonListener();
    }

    public static ChipGroup.OnCheckedChangeListener chipGroupCheckChangeListener() {
        return new ChipGroupCheckChangeListener();
    }

    public static TextWatcher tweetBodyTextWatcher() {
        return new TweetBodyTextWatcher();
    }

    public static FrameLayout.OnTouchListener touchLayerOnTouchListener(TextView tweetBody) {
        return new TouchLayerOnTouchListener(tweetBody);
    }
}
