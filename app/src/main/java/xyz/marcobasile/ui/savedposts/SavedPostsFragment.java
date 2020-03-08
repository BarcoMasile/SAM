package xyz.marcobasile.ui.savedposts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.ui.adapter.savedposts.SavedPostsAdapter;

public class SavedPostsFragment extends Fragment {

    private View root;
    private SearchView searchView;
    private Button searchBtn;

    private ListView listView;
    private TweetRepository repo;
    private SavedPostsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_saved_posts, container, false);

        repo = new TweetRepository(getContext());

        setupViews(root);
        setupListeners();

        return root;
    }

    public void setupViews(View root) {

        searchView = root.findViewById(R.id.search_string);
        searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setEnabled(false);

        listView = root.findViewById(R.id.saved_posts_scroll_view);

        adapter = new SavedPostsAdapter(getContext(), repo);
        listView.setAdapter(adapter);
    }



    @SuppressLint("ClickableViewAccessibility")
    public void setupListeners() {

        searchBtn.setOnClickListener(view -> {

            String str = searchView.getQuery().toString();
            adapter.changeCursor(repo.searchByStringCursor(str));
            hideKeyboard();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.length() < 3) {
                    return false;
                }

                adapter.changeCursor(repo.searchByStringCursor(query));
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchBtn.setEnabled(s.length() > 3);

                if (s.length() == 0) {
                    adapter.changeCursor(repo.findAllCursor());
                    hideKeyboard();
                }

                return true;
            }
        });
    }

    private void hideKeyboard() {
        Optional.ofNullable((InputMethodManager) root.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .ifPresent(imm -> imm.hideSoftInputFromWindow(root.getWindowToken(), 0));
    }

}