package xyz.marcobasile.ui.composer.listener;

import android.util.Log;
import android.view.View;

public class TweetButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Log.i("TweetButton", "Click tweet");
    }
}
