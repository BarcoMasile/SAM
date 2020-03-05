package xyz.marcobasile.ui.adapter.map;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTwitterUser;
import xyz.marcobasile.service.ContentProvider;

public class MapTweetUserAdapter extends RecyclerView.Adapter<MapTweetUserViewHolder> {

    private final static String TAG = MapTweetUserAdapter.class.getName();

    private ContentProvider provider;

    private List<SAMTwitterUser> users = Arrays.asList(SAMTwitterUser.builder().screenName("Marco Basile").statusesCount(123).followersCount(12).friendsCount(21).location("Foggia, Italy").profileImageUrl("https://pbs.twimg.com/profile_images/903288596802797568/raJx8uZk_400x400.jpg").build());

    public MapTweetUserAdapter(ContentProvider provider) {

        this.provider = provider;
    }

    @NonNull
    @Override
    public MapTweetUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "Map TweetUser view holder init");
        View tweetUserItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.map_user_list_item, parent, false);

        return new MapTweetUserViewHolder(tweetUserItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MapTweetUserViewHolder holder, int position) {

        SAMTwitterUser user = provider.users().get(position);

        holder.username(user.getScreenName());
        holder.followers(user.getFollowersCount());
        holder.profileImage(provider.getImage(user.getProfileImageUrl()));

        holder.getItemView().setOnClickListener(view -> {

        });

        holder.resetMarginForItem();
        if (position == provider.users().size() - 1) {
            // e' l'ultimo item
            holder.setupMarginForLastItem();
        }
    }

    @Override
    public int getItemCount() {

        return users.size();
    }
}
