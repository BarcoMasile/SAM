package xyz.marcobasile.ui.composer.listeners;

import android.util.Log;
import android.view.View;

public class CancelButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Log.i("CancelButton", "Click cancel");
    }
}
