package xyz.marcobasile.ui.map.util;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.ContextCompat.checkSelfPermission;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapSetupUtils {
    private static final String TAG = MapSetupUtils.class.getName();
    private static final String[] permessi = new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
    private static final float INITIAL_ZOOM = 14.5f;

    private Fragment fragment;
    private MapView mapView;
    private LatLng latLng = null;

    private CameraUpdate cameraUpdate;
    private LocationListener listener = new MapLocationListener(this::setupCameraPosition);
    private FusedLocationProviderClient fusedLocationClient;


    public MapSetupUtils(Fragment fragment) {
        this.fragment = fragment;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.getContext());
        setLastLocationAndMoveCamera();
    }

    public void setupMapView(MapView mapView) {
        this.mapView = mapView;
        mapView.getMapAsync(this::mapViewSetup);
    }

    public boolean permissionsGranted() {
        return !(checkSelfPermission(fragment.getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(fragment.getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermissions() {
        fragment.requestPermissions(permessi, 0);
    }


    private void mapViewSetup(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);

        MapsInitializer.initialize(fragment.getActivity());
    }


    private void setupCameraPosition(LatLng latLng) {
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, INITIAL_ZOOM);
        mapView.getMapAsync(map -> map.moveCamera(cameraUpdate));
    }

    private void setLastLocationAndMoveCamera() {
        if (null == latLng) {
            fusedLocationClient.getLastLocation().addOnCompleteListener(result -> {
                Location location = result.getResult();
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                setupCameraPosition(latLng);
            });
        }
    }
}
