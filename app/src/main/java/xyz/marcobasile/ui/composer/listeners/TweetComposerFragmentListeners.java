package xyz.marcobasile.ui.composer.listeners;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweetButtonListener() {
        return view -> Log.i("TweetButton", "Click tweet");
    }

    public static View.OnClickListener cancelButtonListener() {
        return view -> Log.i("CancelButton", "Click cancel");
    }

    public static TextView.OnEditorActionListener tweetBodyListener() {
        return (textView, actionId, keyEvent) -> {
            Log.i("TweetBody", "Editor action tweet body");
            return true;
        };
    }
}
