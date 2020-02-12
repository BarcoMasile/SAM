package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import xyz.marcobasile.R;
import xyz.marcobasile.ui.composer.util.AlertDialogUtils;

public class CancelButtonListener implements View.OnClickListener {

    private final TextView textView;

    public CancelButtonListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onClick(View v) {
        Log.i("CancelButton", "Click cancel");
        AlertDialogUtils.cancelButtonAlertDialog(textView);
    }

}
