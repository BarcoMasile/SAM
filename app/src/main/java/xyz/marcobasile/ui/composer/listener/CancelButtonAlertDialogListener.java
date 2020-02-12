package xyz.marcobasile.ui.composer.listener;

import android.content.DialogInterface;
import android.widget.TextView;

public class CancelButtonAlertDialogListener implements DialogInterface.OnClickListener {

    private TextView textView;

    public CancelButtonAlertDialogListener(TextView textView) {
        this.textView = textView;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                // do something
                textView.setText("");
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                // do nothing
                break;

            default:
                break;
        }
    }
}
