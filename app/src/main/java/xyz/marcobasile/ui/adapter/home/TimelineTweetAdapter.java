package xyz.marcobasile.ui.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTweet;

public class TimelineTweetAdapter extends ArrayAdapter<TimelineTweetViewHolder> {

    private final static String TAG = TimelineTweetAdapter.class.getName();

    private List<SAMTweet> tweets;

    public TimelineTweetAdapter(Context context, List<SAMTweet> tweets) {

        super(context, R.layout.sam_tweet_item);
        this.tweets = tweets;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }

}
