package xyz.marcobasile.ui.savedposts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import xyz.marcobasile.R;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.ui.adapter.savedposts.SavedPostsAdapter;
import xyz.marcobasile.ui.savedposts.listener.SearchStringTextWatcher;
import xyz.marcobasile.ui.shared.listener.TouchLayerOnTouchListener;

public class SavedPostsFragment extends Fragment {

    private TextView searchTextEdit;
    private Button searchBtn;

    private FrameLayout touchLayer;
    private ListView listView;
    private TweetRepository repo;
    private SavedPostsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_saved_posts, container, false);

        repo = new TweetRepository(getContext());

        setupViews(root);
        setupListeners();

        return root;
    }

    public void setupViews(View root) {

        searchTextEdit = root.findViewById(R.id.search_string);
        searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setEnabled(false);

        touchLayer = root.findViewById(R.id.touch_layer_sp);

        listView = root.findViewById(R.id.saved_posts_scroll_view);

        adapter = new SavedPostsAdapter(getContext(), repo);
        listView.setAdapter(adapter);
    }



    @SuppressLint("ClickableViewAccessibility")
    public void setupListeners() {

        searchBtn.setOnClickListener(view -> {

            String str = searchTextEdit.getText().toString();
            adapter.changeCursor(repo.searchByStringCursor(str));
        });

        searchTextEdit.addTextChangedListener(new SearchStringTextWatcher(repo, adapter, searchBtn));
        touchLayer.setOnTouchListener(new TouchLayerOnTouchListener(searchTextEdit));
    }

}