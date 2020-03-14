package xyz.marcobasile.ui.composer.listener;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;
import xyz.marcobasile.ui.shared.listener.TouchLayerOnTouchListener;


public class TweetComposerFragmentListeners {

    public static View.OnClickListener tweet(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon, ImageView imagePreview, GenericProcedure toggleProgressBar) {
        return new TweetButtonListener(textView, pictureHolder, clearAttach, attachIcon, imagePreview, toggleProgressBar);
    }

    public static View.OnClickListener cancel(TextView textView, Button clearAttach) {
        return new CancelButtonListener(textView, clearAttach);
    }

    public static TextWatcher tweetBody(MaterialButton tweetButton, Context ctx) {
        return new TweetBodyTextWatcher(tweetButton, ctx);
    }

    public static FrameLayout.OnTouchListener layer(TextView tweetBody) {
        return new TouchLayerOnTouchListener(tweetBody);
    }

    public static TweetButtonAlertDialogListener tweetDialog(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon, ImageView imagePreview, GenericProcedure toggleProgressBar) {
        return new TweetButtonAlertDialogListener(textView, pictureHolder, clearAttach, attachIcon, imagePreview, toggleProgressBar);
    }

    public static CancelButtonAlertDialogListener cancelDialog(TextView textView, Button clearAttach) {
        return new CancelButtonAlertDialogListener(textView, clearAttach);
    }
}
