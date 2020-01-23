package xyz.marcobasile.ui.map.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.function.Consumer;

public class MapLocationListener implements LocationListener {

    private static final String TAG = MapLocationListener.class.getName();

    private LatLng latLng;
    private Consumer<LatLng> callback;

    public MapLocationListener(Consumer<LatLng> locationUpdateCallback) {
        this.callback = locationUpdateCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged " + location.getLatitude() + " :" + location.getLongitude());
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        callback.accept(latLng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
