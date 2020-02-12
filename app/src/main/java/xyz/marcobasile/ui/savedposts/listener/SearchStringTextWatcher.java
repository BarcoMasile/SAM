package xyz.marcobasile.ui.savedposts.listener;

import android.text.Editable;
import android.text.TextWatcher;

import xyz.marcobasile.service.twitter.TwitterClient;

public class SearchStringTextWatcher implements TextWatcher {

    private TwitterClient client;

    public SearchStringTextWatcher(TwitterClient twitterClient) {

        client = twitterClient;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

}
