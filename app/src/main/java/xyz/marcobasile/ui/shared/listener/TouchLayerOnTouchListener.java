package xyz.marcobasile.ui.shared.listener;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Optional;

public class TouchLayerOnTouchListener implements FrameLayout.OnTouchListener {

    private TextView textView;

    public TouchLayerOnTouchListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        view.performClick();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (textView.isFocused()) {

                Rect outRect = new Rect();
                textView.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    textView.clearFocus();
                    Optional.ofNullable((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .ifPresent(imm -> imm.hideSoftInputFromWindow(view.getWindowToken(), 0));
                }
            }
        }
        return false;
    }
}
