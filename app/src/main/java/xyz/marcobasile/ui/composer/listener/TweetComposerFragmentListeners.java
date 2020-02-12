package xyz.marcobasile.ui.composer.listener;

import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;


public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweet() {
        return new TweetButtonListener();
    }

    public static View.OnClickListener cancel() {
        return new CancelButtonListener();
    }

    public static ChipGroup.OnCheckedChangeListener chipGroup() {
        return new ChipGroupCheckChangeListener();
    }

    public static TextWatcher tweetBody() {
        return new TweetBodyTextWatcher();
    }

    public static FrameLayout.OnTouchListener layer(TextView tweetBody) {
        return new TouchLayerOnTouchListener(tweetBody);
    }
}
