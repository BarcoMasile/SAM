package xyz.marcobasile.ui.savedposts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import xyz.marcobasile.R;

public class SavedPostsFragment extends Fragment {

    private TextInputEditText searchTextEdit;
    private Button searchBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_saved_posts, container, false);

        searchTextEdit = root.findViewById(R.id.search_string);
        searchBtn = root.findViewById(R.id.search_btn);

        return root;
    }
}