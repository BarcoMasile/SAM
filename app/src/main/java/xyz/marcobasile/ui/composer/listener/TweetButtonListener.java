package xyz.marcobasile.ui.composer.listener;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.composer.util.AlertDialogUtils;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class TweetButtonListener implements View.OnClickListener {

    private final TextView textView;
    private PictureHolder pictureHolder;
    private Button clearAttach;
    private Button attachIcon;
    private ImageView imagePreview;
    private GenericProcedure toggleProgressBar;

    public TweetButtonListener(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon, ImageView imagePreview, GenericProcedure toggleProgressBar) {
        this.textView = textView;
        this.pictureHolder = pictureHolder;
        this.clearAttach = clearAttach;
        this.attachIcon = attachIcon;
        this.imagePreview = imagePreview;

        this.toggleProgressBar = toggleProgressBar;
    }

    @Override
    public void onClick(View v) {
        Log.i("TweetButton", "Click tweet");
        AlertDialogUtils.tweetButtonAlertDialog(textView, pictureHolder, clearAttach, attachIcon, imagePreview, toggleProgressBar);
    }
}
