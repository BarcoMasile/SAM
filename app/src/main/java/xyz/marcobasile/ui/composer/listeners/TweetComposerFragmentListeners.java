package xyz.marcobasile.ui.composer.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import xyz.marcobasile.ui.composer.util.TweetComposerFragmentUtils;

import static androidx.core.content.ContextCompat.getSystemService;

public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweetButtonListener() {
        return view -> {
            Log.i("TweetButton", "Click tweet");

        };
    }

    public static View.OnClickListener cancelButtonListener() {
        return view -> {
            Log.i("CancelButton", "Click cancel");

        };
    }

    public static ChipGroup.OnCheckedChangeListener chipGroupCheckChangeListener() {
        return (group, chipId) -> {

        };
    }

    public static View.OnFocusChangeListener tweetBodyFocusChangeListener(Context ctx) {
        return (view, hasFocus) -> {
            if (!hasFocus) {
                Optional.ofNullable((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .ifPresent(imm -> imm.hideSoftInputFromWindow(view.getWindowToken(), 0));
            }
        };
    }

    public static TextView.OnEditorActionListener tweetBodyListener() {
        return (textView, actionId, keyEvent) -> {
            Log.i("TweetBody", "Editor action tweet body");
            return true;
        };
    }

    public static FrameLayout.OnTouchListener touchLayerOnTouchListener(TextView tweetBody) {
        return (view, event) -> {
            view.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (tweetBody.isFocused()) {
                    Rect outRect = new Rect();
                    tweetBody.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        tweetBody.clearFocus();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
            return false;
        };
    }
}
