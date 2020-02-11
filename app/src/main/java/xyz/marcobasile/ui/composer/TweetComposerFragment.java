package xyz.marcobasile.ui.composer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import xyz.marcobasile.R;
import xyz.marcobasile.ui.composer.listeners.TweetComposerFragmentListeners;
import xyz.marcobasile.ui.composer.util.TweetComposerFragmentUtils;

public class TweetComposerFragment extends Fragment {
    private final String TAG = this.getClass().getName();
    private final int characterLimit = 280;

    private TextInputEditText tweetBodyView;
    private MaterialButton tweetBtn, cancelBtn;
    private ChipGroup chipGroup;
    private BadgeDrawable charCounter;

    private FrameLayout touchLayer;

    public TweetComposerFragment() {
        Log.i(TAG, "TweetComposerFragment init");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tweet_composer, container, false);

        setupView(root);

        setupFragmentUtils();

        setupListeners();
        setupBadgeCharCounter();

        return root;
    }



    private void setupView(View root) {
        tweetBodyView = root.<TextInputEditText>findViewById(R.id.tweet_body);
        tweetBtn = root.<MaterialButton>findViewById(R.id.tweet_button);
        cancelBtn = root.<MaterialButton>findViewById(R.id.cancel_button);
        chipGroup = root.<ChipGroup>findViewById(R.id.chip_group);

        touchLayer = root.<FrameLayout>findViewById(R.id.touch_layer);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupListeners() {
        tweetBtn.setOnClickListener(TweetComposerFragmentListeners.tweetButtonListener());
        cancelBtn.setOnClickListener(TweetComposerFragmentListeners.cancelButtonListener());
        chipGroup.setOnCheckedChangeListener(TweetComposerFragmentListeners.chipGroupCheckChangeListener());
        touchLayer.setOnTouchListener(TweetComposerFragmentListeners.touchLayerOnTouchListener(tweetBodyView));

        tweetBodyView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(characterLimit)});
        tweetBodyView.addTextChangedListener(TweetComposerFragmentListeners.tweetBodyTextWatcher());


    }

    private void setupBadgeCharCounter() {
        charCounter = BadgeDrawable.create(getContext());
        charCounter.setBadgeGravity(BadgeDrawable.BOTTOM_END);
        charCounter.setNumber(characterLimit);
        BadgeUtils.attachBadgeDrawable(charCounter, tweetBodyView, null);
    }

    private void setupFragmentUtils() {
        TweetComposerFragmentUtils.setCtx(getContext());
        TweetComposerFragmentUtils.setGroup(chipGroup);
    }
}
