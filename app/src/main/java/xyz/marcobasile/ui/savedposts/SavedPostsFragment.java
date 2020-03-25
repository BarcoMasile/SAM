package xyz.marcobasile.ui.savedposts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.ui.adapter.savedposts.SavedPostsAdapter;
import xyz.marcobasile.ui.shared.listener.TouchLayerOnTouchListener;

public class SavedPostsFragment extends Fragment {

    private View root;
    private SearchView searchView;

    private ListView listView;
    private TweetRepository repo;
    private SavedPostsAdapter adapter;
    private ImageView searchViewCloseBtn;

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
        searchViewCloseBtn = searchView.findViewById(R.id.search_close_btn);
        listView = root.findViewById(R.id.saved_posts_scroll_view);

        adapter = new SavedPostsAdapter(getContext(), searchView, repo);
        listView.setAdapter(adapter);
    }



    @SuppressLint("ClickableViewAccessibility")
    public void setupListeners() {

        // searchViewCloseBtn.setOnClickListener(view -> searchView.clearFocus());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.length() < 1) {
                    return false;
                }

                adapter.changeCursor(repo.searchByStringCursor(query));
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                boolean searchEnabled = s.length() > 1;

                if (searchEnabled) {
                    adapter.changeCursor(repo.searchByStringCursor(s));
                }

                if (s.length() == 0) {
                    adapter.changeCursor(repo.findAllCursor());
                    new Handler(Looper.getMainLooper()).postDelayed(() -> searchView.clearFocus(), 100L);
                }

                return true;
            }
        });
    }

}