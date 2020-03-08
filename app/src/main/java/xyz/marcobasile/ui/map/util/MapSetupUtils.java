package xyz.marcobasile.ui.map.util;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private ContentProvider provider;

    private List<Marker> markers = new ArrayList<>();

    public MapSetupUtils(Fragment fragment, ContentProvider provider) {

        this.fragment = fragment;
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
        map.setOnMapClickListener(mapClickListener());

        map.setOnInfoWindowClickListener(Marker::hideInfoWindow);
        map.setInfoWindowAdapter(new SAMTwitterUserInfoWindow(fragment.getContext()));

        MapsInitializer.initialize(fragment.getActivity());
        DEFAULT_BITMAP_DESCRIPTOR = BitmapDescriptorFactory.fromResource(R.drawable.tw__composer_logo_blue);
        provider.users()
                .stream()
                .filter(user -> user.getLatLng() != null)
                .forEach(user -> {

                    Marker marker = map.addMarker(makeMarkerOptions(user));
                    marker.setTitle(user.getScreenName());
                    markers.add(marker);
                });

        // setto la posizione al primo elemento
        provider.users().stream().findFirst()
                .ifPresent(this::setCameraOnUser);
    }


    public void setCameraOnUser(SAMTwitterUser user) {

        LatLng latLng = user.getLatLng();
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM);
        mapView.getMapAsync(map -> {

            hideAllOtherMarkers(user.getScreenName());
            map.animateCamera(cameraUpdate);
        });
    }

    private void hideAllOtherMarkers(String username) {

        markers.forEach(marker -> marker.setVisible(marker.getTitle().equals(username)));
    }

    private MarkerOptions makeMarkerOptions(SAMTwitterUser user) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.flat(true);
        markerOptions.title(user.getScreenName());
        markerOptions.snippet(userInfoWindowSnippet(user));
        markerOptions.position(user.getLatLng());

        Bitmap iconBitmap = provider.getImage(user.getProfileImageUrl());

        markerOptions.icon(scaleIcon(iconBitmap));

        return markerOptions;
    }

    private BitmapDescriptor scaleIcon(Bitmap b) {

        if (null == b) {
            return DEFAULT_BITMAP_DESCRIPTOR;
        }

        Bitmap scaledBitmap = Bitmap
                .createScaledBitmap(b,b.getWidth() * 2,b.getHeight() * 2, false);

        return BitmapDescriptorFactory.fromBitmap(scaledBitmap);
    }

    private String userInfoWindowSnippet(SAMTwitterUser user) {
        return String.format(Locale.getDefault(),"%d#%d", user.getFollowersCount(), user.getFriendsCount());
    }

    private GoogleMap.OnMapClickListener mapClickListener() {

        return latLng -> {
            markers.forEach(marker -> marker.setVisible(true));
        };
    }

    private GoogleMap.OnMarkerClickListener markerClickListener() {
        return marker -> {
            marker.showInfoWindow();
            return true;
        };
    }


}
