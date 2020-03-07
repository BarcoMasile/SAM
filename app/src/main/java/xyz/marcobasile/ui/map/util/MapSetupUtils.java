package xyz.marcobasile.ui.map.util;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;

import xyz.marcobasile.R;
import xyz.marcobasile.model.SAMTwitterUser;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.ui.map.SAMTwitterUserInfoWindow;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.ContextCompat.checkSelfPermission;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapSetupUtils {
    private static final String TAG = MapSetupUtils.class.getName();
    private static final String[] permessi = new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
    private static final float INITIAL_ZOOM = 14.5f;
    private BitmapDescriptor DEFAULT_BITMAP_DESCRIPTOR;

    private Fragment fragment;
    private MapView mapView;

    private CameraUpdate cameraUpdate;
    private FusedLocationProviderClient fusedLocationClient;
    private ContentProvider provider;


    public MapSetupUtils(Fragment fragment, ContentProvider provider) {

        this.fragment = fragment;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.getContext());
        this.provider = provider;
    }

    public void setupMapView(MapView mapView) {
        this.mapView = mapView;
        mapView.getMapAsync(this::mapViewSetup);
    }

    public boolean permissionsGranted() {
        return !(checkSelfPermission(fragment.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(fragment.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    public boolean permissionGrantedResult(int[] grants) {
        int mustBeZero = 0;
        for(int grantResult : grants) {
            mustBeZero += grantResult;
        }

        return 0 == mustBeZero;
    }

    public void requestPermissions() {
        fragment.requestPermissions(permessi, 0);
    }


    private void mapViewSetup(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);

        map.setOnMarkerClickListener(markerClickListener());
        map.setOnInfoWindowClickListener(Marker::hideInfoWindow);
        map.setInfoWindowAdapter(new SAMTwitterUserInfoWindow(fragment.getContext()));

        MapsInitializer.initialize(fragment.getActivity());
        DEFAULT_BITMAP_DESCRIPTOR = BitmapDescriptorFactory.fromResource(R.drawable.tw__composer_logo_blue);
        provider.users()
                .stream()
                .filter(user -> user.getLatLng() != null)
                .forEach(user -> {
                    map.addMarker(makeMarkerOptions(user));
                });

        provider.users().stream().findFirst()
                .map(SAMTwitterUser::getLatLng)
                .ifPresent(this::setupCameraPosition);
    }

    public void setCameraOnUser(SAMTwitterUser user) {

        Optional.ofNullable(user.getLatLng())
                .ifPresent(this::setupCameraPosition);
    }

    private void setupCameraPosition(LatLng latLng) {
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM);
        mapView.getMapAsync(map -> {

            map.animateCamera(cameraUpdate);
        });
    }

    private MarkerOptions makeMarkerOptions(SAMTwitterUser user) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.flat(true);
        markerOptions.title(user.getScreenName());
        markerOptions.snippet(userInfoWindowSnippet(user));

        BitmapDescriptor bitmapDescriptor;
        Bitmap iconBitmap = provider.getImage(user.getProfileImageUrl());

        if (null == iconBitmap) {
            bitmapDescriptor = DEFAULT_BITMAP_DESCRIPTOR;
        } else {
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap);
        }

        markerOptions.icon(bitmapDescriptor);

        return markerOptions;
    }

    private String userInfoWindowSnippet(SAMTwitterUser user) {
        return String.format(Locale.getDefault(),"%d#%d", user.getFollowersCount(), user.getFriendsCount());
    }


    private GoogleMap.OnMarkerClickListener markerClickListener() {
        return marker -> {
            marker.showInfoWindow();
            return true;
        };
    }


}
