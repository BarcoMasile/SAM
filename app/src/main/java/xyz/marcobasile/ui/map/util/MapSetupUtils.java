package xyz.marcobasile.ui.map.util;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.checkSelfPermission;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapSetupUtils {
    private static final String[] permessi = new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
    private static final long LOCATION_REFRESH_TIME = 300;
    private static final float LOCATION_REFRESH_DISTANCE = 0;
    private static final String TAG = MapSetupUtils.class.getName();

    private Fragment fragment;
    private MapView mapView;
    private LatLng latLng = new LatLng(43.1, -87.9);
    private LocationManager locationManager;
    private CameraUpdate cameraUpdate;
    private LocationListener listener = new MapLocationListener(this::setupCameraPosition);

    @SuppressLint("MissingPermission")
    public MapSetupUtils(Fragment fragment) {
        this.fragment = fragment;
        locationManager = (LocationManager) fragment.getContext().getSystemService(LOCATION_SERVICE);
    }

    public void setupMapView(MapView mapView) {
        this.mapView = mapView;
        // registerLocationManager();
        mapView.getMapAsync(this::mapViewSetup);
    }

    public boolean permissionsGranted() {
        return !(checkSelfPermission(fragment.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(fragment.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermissions() {
        fragment.requestPermissions(permessi, 0);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public int mapViewSetup(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(getLatLng(), 8.f);
        map.animateCamera(cameraUpdate);

        return MapsInitializer.initialize(fragment.getActivity());
    }

    @SuppressLint("MissingPermission")
    public void registerLocationManager() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, listener, Looper.myLooper());
    }

    public void unregisterLocationManagerUpdates() {
        locationManager.removeUpdates(listener);
    }


    private void setupCameraPosition(LatLng latLng) {
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8.f);
        mapView.getMapAsync(map -> map.animateCamera(cameraUpdate));
    }
}
