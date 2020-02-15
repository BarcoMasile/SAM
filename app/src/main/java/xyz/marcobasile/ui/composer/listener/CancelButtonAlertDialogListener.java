package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

public class CancelButtonAlertDialogListener implements DialogInterface.OnClickListener {

    private TextView textView;
    private Button clearAttach;

    public CancelButtonAlertDialogListener(TextView textView, Button clearAttach) {
        this.textView = textView;
        this.clearAttach = clearAttach;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                // do something
                textView.setText("");
                clearAttach.performClick();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                // do nothing
                break;

            default:
                break;
        }
    }
}
