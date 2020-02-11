package xyz.marcobasile.ui.composer.listeners;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Optional;

public class TouchLayerOnTouchListener implements FrameLayout.OnTouchListener {

    private TextView tweetBody;

    public TouchLayerOnTouchListener(TextView tweetBody) {
        this.tweetBody = tweetBody;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        view.performClick();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (tweetBody.isFocused()) {

                Rect outRect = new Rect();
                tweetBody.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    tweetBody.clearFocus();
                    Optional.ofNullable((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .ifPresent(imm -> imm.hideSoftInputFromWindow(view.getWindowToken(), 0));
                }
            }
        }
        return false;
    }
}
