package xyz.marcobasile.service.task;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import xyz.marcobasile.model.SAMTwitterUser;

public class UserLocationGeocoder extends AsyncTask<List<SAMTwitterUser>,Void, List<SAMTwitterUser>> {

    private static final String TAG = UserLocationGeocoder.class.getName();
    private Geocoder geocoder;

    public UserLocationGeocoder(Context ctx) {

        geocoder = new Geocoder(ctx, Locale.getDefault());
    }

    @Override
    protected List<SAMTwitterUser> doInBackground(List<SAMTwitterUser>... lists) {

        if (lists.length == 0) {
            return null;
        }

        List<SAMTwitterUser> users = lists[0];

        for (SAMTwitterUser user : users) {
            setLocationIfPresent(user); // TODO controllare concurrent modification exception se presente
        }

        return users;
    }

    @Override
    protected void onPostExecute(List<SAMTwitterUser> samTwitterUsers) {

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
