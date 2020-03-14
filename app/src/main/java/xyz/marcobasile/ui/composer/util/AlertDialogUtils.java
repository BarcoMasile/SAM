package xyz.marcobasile.ui.composer.util;

import android.content.DialogInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import xyz.marcobasile.R;
import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.composer.listener.CancelButtonAlertDialogListener;
import xyz.marcobasile.ui.composer.listener.TweetButtonAlertDialogListener;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

import static xyz.marcobasile.ui.composer.listener.TweetComposerFragmentListeners.cancelDialog;
import static xyz.marcobasile.ui.composer.listener.TweetComposerFragmentListeners.tweetDialog;

public class AlertDialogUtils {

    public static void cancelButtonAlertDialog(TextView textView, Button clearAttach) {

        CancelButtonAlertDialogListener listener = cancelDialog(textView, clearAttach);

        alertDialog(textView,
                    listener,
                    R.string.cancel_btn_dialog_title,
                    textView.getResources().getString(R.string.cancel_btn_dialog_message));
    }

    public static void tweetButtonAlertDialog(TextView textView, PictureHolder pictureHolder, Button clearAttach, Button attachIcon, ImageView imagePreview, GenericProcedure toggleProgressBar) {

        TweetButtonAlertDialogListener listener = tweetDialog(textView, pictureHolder, clearAttach, attachIcon, imagePreview, toggleProgressBar);

        alertDialog(textView,
                listener,
                R.string.tweet_btn_dialog_title,
                textView.getText().toString());

    }

    private static void alertDialog(TextView textView,
                                           DialogInterface.OnClickListener cancelButtonAlertDialogListener,
                                           int titleResId,
                                           String message) {

        new MaterialAlertDialogBuilder(textView.getContext())
                .setTitle(titleResId)
                .setMessage(message).setCancelable(true)
                .setPositiveButton("OK", cancelButtonAlertDialogListener)
                .setNegativeButton("Cancel", cancelButtonAlertDialogListener)
                .show();
    }
}
