package xyz.marcobasile.ui.composer.listener;

import android.util.Log;

import com.google.android.material.chip.ChipGroup;

public class ChipGroupCheckChangeListener implements ChipGroup.OnCheckedChangeListener {
    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        Log.i("ChipGroup", "ok");
    }
}
