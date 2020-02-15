package xyz.marcobasile.ui.composer.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.marcobasile.ui.composer.util.TweetComposerFragmentUtils;


public class TweetBodyTextWatcher implements TextWatcher {

    private final Pattern pattern = Pattern.compile("#\\S+");
    private final MaterialButton btn;
    private final Context ctx;

    private List<String> chips;

    public TweetBodyTextWatcher(MaterialButton btn, Context ctx) {
        this.btn = btn;
        this.ctx = ctx;
    }

    @Override
    public void beforeTextChanged(CharSequence cs, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence cs, int start, int before, int count) {
        chips = getHashtags(cs);
        TweetComposerFragmentUtils.addChips(chips, ctx);
    }

    @Override
    public void afterTextChanged(Editable editable) {

        btn.setEnabled(!editable.toString().trim().isEmpty());
    }

    private List<String> getHashtags(CharSequence cs) {
        Matcher matcher = pattern.matcher(cs);
        List<String> hashtags = new ArrayList<>();

        while( matcher.find() ) {
            String hashtag = matcher.group();
            hashtags.add(hashtag);
        }

        return hashtags;
    }

}