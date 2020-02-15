package xyz.marcobasile.ui.composer.util;

import android.content.Context;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import xyz.marcobasile.R;

public class TweetComposerFragmentUtils {

    private static ChipGroup group;
    private static Map<Integer,Chip> map = new HashMap<>();
    private static TextView textView;


    public static void addChips(List<String> chips, Context ctx) {
        group.removeAllViews();
        chips.stream()
                .forEachOrdered(chipText -> addChipToGroup(chipText, ctx));

    }

    public static void addChipToGroup(String chipText, Context ctx) {
        Chip chip = makeChip(chipText, ctx);
        group.addView(chip);
    }

    public static void removeChipFromGroup(int chipId) {
        Optional.ofNullable(map.getOrDefault(chipId, null))
                .ifPresent(view -> {
                    group.removeView(view);
                    map.remove(chipId);
                    String newText = textView.getText()
                            .toString()
                            .replaceAll(view.getText().toString(), "");

                    textView.setText(newText);
                });
    }

    private static Chip makeChip(String text, Context ctx) {
        ChipDrawable chipDrawable = ChipDrawable.createFromResource(ctx, R.xml.single_chip);

        Chip chip = new Chip(ctx);
        chip.setId(group.getChildCount());
        chip.setHeight(54);
        chip.setCloseIconVisible(true);
        chip.setText(text);
        chip.setChipDrawable(chipDrawable);
        chip.setCheckable(false);
        chip.setOnClickListener(v -> removeChipFromGroup(v.getId()));

        map.put(chip.getId(), chip);

        return chip;
    }

    public static void setGroup(ChipGroup group) {
        TweetComposerFragmentUtils.group = group;
    }

    public static void setTextView(TextView textView) {
        TweetComposerFragmentUtils.textView = textView;
    }
}
