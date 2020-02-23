package xyz.marcobasile.ui.map;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import xyz.marcobasile.R;

public class SAMTwitterUserInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final static String VALUE_SEPARATOR = "#";

    private Context context;
    private LayoutInflater layoutInflater;
    private TextView retweets, likes;

    public SAMTwitterUserInfoWindow(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        String[] countValues = marker.getSnippet().split(VALUE_SEPARATOR);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View infoWindowView = layoutInflater.inflate(R.layout.user_window_info, null);
        infoWindowView.setLayoutParams(new RelativeLayout.LayoutParams(350,360));

        retweets = infoWindowView.findViewById(R.id.user_window_info_retweets_count);
        likes = infoWindowView.findViewById(R.id.user_window_info_likes_count);

        retweets.setText(countValues[0]);
        likes.setText(countValues[1]);


        return infoWindowView;
    }
}
