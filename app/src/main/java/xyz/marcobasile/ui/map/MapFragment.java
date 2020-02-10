package xyz.marcobasile.ui.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.marcobasile.Manifest;
import xyz.marcobasile.R;
import xyz.marcobasile.ui.map.util.MapSetupUtils;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.getSystemService;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapFragment extends Fragment {

    private final String TAG = this.getClass().getName();

//    private MapViewModel mapViewModel;
    private MapView mapView;
    private MapSetupUtils mapUtils;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mapUtils = new MapSetupUtils(this);

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = root.<MapView>findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        if (mapUtils.permissionsGranted()) {
            Log.i(TAG, "Permission already granted");
            mapUtils.setupMapView(mapView);
        } else {
            Log.i(TAG, "Permissions to grant");
            Toast.makeText(getContext(), R.string.missing_grants, Toast.LENGTH_SHORT).show();
            mapUtils.requestPermissions();
        }

        if (!mapUtils.permissionsGranted()) {
            Toast.makeText(getContext(), "You really need to give permission grants", Toast.LENGTH_LONG).show();
        }

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mapUtils.permissionGrantedResult(grantResults)) {
            mapUtils.setupMapView(mapView);
        } else {
            Toast toast = Toast.makeText(getContext(), R.string.really_missing_grants, Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.RED);
            toast.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}