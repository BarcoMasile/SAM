package xyz.marcobasile.ui.savedposts.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.ui.adapter.savedposts.SavedPostsAdapter;

public class SearchStringTextWatcher implements TextWatcher {

    private TweetRepository repo;
    private SavedPostsAdapter adapter;
    private Button searchBtn;

    public SearchStringTextWatcher(TweetRepository repo, SavedPostsAdapter adapter, Button searchBtn) {

        this.repo = repo;
        this.adapter = adapter;
        this.searchBtn = searchBtn;
    }

    @Override
    public void afterTextChanged(Editable s) {

        searchBtn.setEnabled(s.length() > 3);
        if (s.length() == 0) {
            adapter.changeCursor(repo.findAllCursor());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

}
