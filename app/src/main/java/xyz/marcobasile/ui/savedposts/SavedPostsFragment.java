package xyz.marcobasile.ui.savedposts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import xyz.marcobasile.R;
import xyz.marcobasile.service.twitter.TwitterClient;
import xyz.marcobasile.ui.savedposts.listener.SearchStringTextWatcher;
import xyz.marcobasile.ui.shared.listener.TouchLayerOnTouchListener;

public class SavedPostsFragment extends Fragment {

    private TextView searchTextEdit;
    private Button searchBtn;

    private FrameLayout touchLayer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_saved_posts, container, false);

        setupViews(root);
        setupListeners();

        return root;
    }

    public void setupViews(View root) {
        searchTextEdit = root.findViewById(R.id.search_string);
        searchBtn = root.findViewById(R.id.search_btn);

        touchLayer = root.findViewById(R.id.touch_layer_sp);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupListeners() {
        searchTextEdit.addTextChangedListener(new SearchStringTextWatcher(TwitterClient.getInstance()));
        touchLayer.setOnTouchListener(new TouchLayerOnTouchListener(searchTextEdit));
    }

}