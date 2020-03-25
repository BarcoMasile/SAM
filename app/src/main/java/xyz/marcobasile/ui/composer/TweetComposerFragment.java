package xyz.marcobasile.ui.composer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.doodling.DoodlingActivity;
import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.composer.listener.TweetComposerFragmentListeners;
import xyz.marcobasile.ui.composer.util.TweetComposerFragmentUtils;

import static android.app.Activity.RESULT_CANCELED;
import static xyz.marcobasile.doodling.DoodlingActivity.DOODLE_EXTRA;
import static xyz.marcobasile.doodling.DoodlingActivity.NEW_DRAWING_EXTRA;

public class TweetComposerFragment extends Fragment {
    private static final int IMAGE_PICKER_CODE = 1;
    private static final int DOODLING_CODE = 2;
    private final String TAG = this.getClass().getName();
    private final int CHARACTER_LIMIT = 280;

    private TextInputEditText tweetBodyView;
    private MaterialButton tweetBtn, cancelBtn;
    private Button pickImage, clearAttach, attachIcon, doodle;
    private ImageView imagePreview;
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

        cancelBtn.setVisibility(View.VISIBLE);
        tweetBtn.setVisibility(View.VISIBLE);

        if (null == imageReturnedIntent || resultCode != Activity.RESULT_OK) {

            clearAttach.setEnabled(pictureHolder.canProvide());
            setTweetBtnEnabled();

            return;
        }

        if (requestCode == IMAGE_PICKER_CODE) {

            pictureHolder.setPictureUri(imageReturnedIntent.getData());
            imagePreview.setImageURI(imageReturnedIntent.getData());
        } else if (requestCode == DOODLING_CODE) {

            byte[] bitmapBytes = imageReturnedIntent.getByteArrayExtra(DOODLE_EXTRA);
            pictureHolder.setPictureBytes(bitmapBytes);
            imagePreview.setImageBitmap(BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length));
        }

        attachIcon.setVisibility(View.VISIBLE);
        clearAttach.setEnabled(true);
        setTweetBtnEnabled();
    }

    private void setTweetBtnEnabled() {

        if (null == tweetBodyView || null == tweetBtn) {
            return;
        }
        tweetBtn.setEnabled(false);
        Editable tweetBody = tweetBodyView.getText();
        Optional.ofNullable(tweetBody)
                .map(Editable::toString)
                .ifPresent(str -> tweetBtn.setEnabled(!str.equals("")));
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
        doodle = root.findViewById(R.id.doodle_btn);

        // backdrop & progress bar
        backdrop = root.<View>findViewById(R.id.backdrop);
        bar = root.findViewById(R.id.progress_bar);
        bar.setVisibility(View.INVISIBLE);

        chipGroup = root.<ChipGroup>findViewById(R.id.chip_group);

        imagePreview = root.findViewById(R.id.tweet_picture_preview);

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
        Arrays.asList(pickImage, attachIcon, cancelBtn, clearAttach, doodle)
            .forEach(btn -> btn.setEnabled(enabled));

        clearAttach.setEnabled(enabled && pictureHolder.getPictureUri() != null);
        tweetBtn.setEnabled(enabled && (!tweetBodyView.getText().toString().equals("")));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupListeners() {

        tweetBtn.setOnClickListener(TweetComposerFragmentListeners.tweet(tweetBodyView, pictureHolder, clearAttach, attachIcon, imagePreview, this::toggleProgressBarAndBackDrop));
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
            imagePreview.setImageURI(null);
            imagePreview.setImageBitmap(null);
        });

        doodle.setOnClickListener(view -> {

            boolean newDrawing = !pictureHolder.hasBitmap();

            Intent intent = new Intent(DoodlingActivity.DOODLING_INTENT);
            intent.putExtra(NEW_DRAWING_EXTRA, newDrawing);

            startActivityForResult(intent, DOODLING_CODE);
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
