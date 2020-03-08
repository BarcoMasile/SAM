package xyz.marcobasile.service.task;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.marcobasile.model.SAMTwitterUser;

public class UserLocationGeocoder extends AsyncTask<Set<SAMTwitterUser>,Void, Set<SAMTwitterUser>> {

    private static final String TAG = UserLocationGeocoder.class.getName();
    private Geocoder geocoder;

    public UserLocationGeocoder(Context ctx) {

        geocoder = new Geocoder(ctx, Locale.getDefault());
    }

    @Override
    protected Set<SAMTwitterUser> doInBackground(Set<SAMTwitterUser>... lists) {
        int i = 0;

        if (lists.length == 0) {
            return null;
        }

        ArrayList<SAMTwitterUser> users = new ArrayList<>(lists[0]);

        for (i = 0 ; i < users.size(); i++) {

            setLocationIfPresent(users.get(i)); // questo necessario per evitare concurrent modification exception
        }

        return new HashSet<>(users);
    }

    @Override
    protected void onPostExecute(Set<SAMTwitterUser> samTwitterUsers) {

        Log.i(TAG, "geocoded " + samTwitterUsers.size() + " users locations");
    }

    private SAMTwitterUser setLocationIfPresent(SAMTwitterUser user) {

        if (null == user || null == user.getLocation() || "".equals(user.getLocation())) {
            return  user;
        }

        try {

            List<Address> fromLocationName = geocoder.getFromLocationName( user.getLocation(), 1);
            fromLocationName.stream().findFirst()
                    .map(address -> new LatLng(address.getLatitude(), address.getLongitude()))
                    .ifPresent(user::setLatLng);

        } catch (IOException e) {
            user.setLocation(null);
        }

        return user;
    }
}
