package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import xyz.marcobasile.R;
import xyz.marcobasile.ui.composer.util.AlertDialogUtils;

public class CancelButtonListener implements View.OnClickListener {

    private final TextView textView;
    private final Button clearAttach;

    public CancelButtonListener(TextView textView, Button clearAttach) {
        this.textView = textView;
        this.clearAttach = clearAttach;
    }

    @Override
    public void onClick(View v) {
        Log.i("CancelButton", "Click cancel");
        AlertDialogUtils.cancelButtonAlertDialog(textView, clearAttach);
    }

}
