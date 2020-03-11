package xyz.marcobasile.ui.adapter.map;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import xyz.marcobasile.R;
import xyz.marcobasile.ui.map.util.MapSetupUtils;

class MapTweetUserViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = MapTweetUserViewHolder.class.getName();
    private static final int LAST_ITEM_OFFSET = 200;

    private ImageView profileImage;
    private TextView username;
    private MaterialButton followers;

    private int originalMinHeight;


    public MapTweetUserViewHolder(@NonNull View itemView) {
        super(itemView);

        setupViews(itemView);
    }

    public View getItemView() {
        return itemView;
    }


    public void profileImage(Bitmap bitmap) {
        this.profileImage.setImageBitmap(bitmap);
    }

    public void username(String name) {
        username.setText(name);
    }

    public void followers(int amount) {
        followers.setText(Integer.toString(amount));
    }

    private void setupViews(View mainView) {

        profileImage = mainView.findViewById(R.id.user_profile_img);
        username = mainView.findViewById(R.id.user_name);
        followers = mainView.findViewById(R.id.followers_count);

        originalMinHeight = mainView.getMinimumHeight();
    }

    /*public void setupMarginForLastItem() {

        itemView.setMinimumHeight(originalMinHeight + LAST_ITEM_OFFSET);
    }

    public void resetMarginForItem() {

        itemView.setMinimumHeight(originalMinHeight);
    }*/
}
