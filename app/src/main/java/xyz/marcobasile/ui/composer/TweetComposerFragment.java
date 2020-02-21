package xyz.marcobasile.ui.composer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

import xyz.marcobasile.R;
import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.composer.listener.TweetComposerFragmentListeners;
import xyz.marcobasile.ui.composer.util.TweetComposerFragmentUtils;

public class TweetComposerFragment extends Fragment {
    private static final int IMAGE_PICKER_CODE = 1;
    private final String TAG = this.getClass().getName();
    private final int CHARACTER_LIMIT = 280;

    private TextInputEditText tweetBodyView;
    private MaterialButton tweetBtn, cancelBtn;
    private Button pickImage, clearAttach, attachIcon;
    private ChipGroup chipGroup;
    private ProgressBar bar;
    private View backdrop;

    private PictureHolder pictureHolder = new PictureHolder();

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

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        
        if (requestCode != IMAGE_PICKER_CODE || null == imageReturnedIntent ) {
            clearAttach.setEnabled(pictureHolder.getPictureUri() != null);
            return;
        }

        attachIcon.setVisibility(View.VISIBLE);
        pictureHolder.setPictureUri(imageReturnedIntent.getData());
        clearAttach.setEnabled(true);
    }


    private void setupView(View root) {

        tweetBodyView = root.<TextInputEditText>findViewById(R.id.tweet_body);
        tweetBtn = root.<MaterialButton>findViewById(R.id.tweet_button);
        tweetBtn.setEnabled(false);
        cancelBtn = root.<MaterialButton>findViewById(R.id.cancel_button);
        attachIcon = root.<Button>findViewById(R.id.attachment_icon);
        attachIcon.setVisibility(View.INVISIBLE);
        pickImage = root.<Button>findViewById(R.id.image_picker_btn);
        clearAttach = root.<Button>findViewById(R.id.delete_attach_btn);
        clearAttach.setEnabled(false);

        // backdrop & progress bar
        backdrop = root.<View>findViewById(R.id.backdrop);
        bar = root.findViewById(R.id.progress_bar);
        bar.setVisibility(View.INVISIBLE);

        chipGroup = root.<ChipGroup>findViewById(R.id.chip_group);

        touchLayer = root.<FrameLayout>findViewById(R.id.touch_layer);


    }

    public void toggleProgressBarAndBackDrop() {
        if (bar.getVisibility() == View.VISIBLE) {

            bar.setVisibility(View.INVISIBLE);
            backdrop.setVisibility(View.INVISIBLE);

            setEnableAllButtonsInView(true);
            Log.i(TAG, "Progress Bar playing");

        } else {

            bar.setVisibility(View.VISIBLE);
            backdrop.setVisibility(View.VISIBLE);

            setEnableAllButtonsInView(false);
            Log.i(TAG, "Progress Bar stopped");

        }
    }

    private void setEnableAllButtonsInView(boolean enabled) {
        Arrays.asList(pickImage, attachIcon, tweetBtn, cancelBtn)
            .forEach(btn -> btn.setEnabled(enabled));

        clearAttach.setEnabled(pictureHolder.getPictureUri() != null);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupListeners() {
        tweetBtn.setOnClickListener(TweetComposerFragmentListeners.tweet(tweetBodyView, pictureHolder, clearAttach, attachIcon, this::toggleProgressBarAndBackDrop));
        cancelBtn.setOnClickListener(TweetComposerFragmentListeners.cancel(tweetBodyView, clearAttach));

        pickImage.setOnClickListener(view -> {
            Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
            imagePickerIntent.setType("image/*");
            startActivityForResult(imagePickerIntent, IMAGE_PICKER_CODE);
        });

        clearAttach.setOnClickListener(view -> {

            pictureHolder.clear();
            attachIcon.setVisibility(View.INVISIBLE);
            view.setEnabled(false);
        });

        touchLayer.setOnTouchListener(TweetComposerFragmentListeners.layer(tweetBodyView));

        tweetBodyView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(CHARACTER_LIMIT)});
        tweetBodyView.addTextChangedListener(TweetComposerFragmentListeners.tweetBody(tweetBtn, getContext()));


    }

    private void setupFragmentUtils() {
        TweetComposerFragmentUtils.setGroup(chipGroup);
        TweetComposerFragmentUtils.setTextView(tweetBodyView);
    }
}
