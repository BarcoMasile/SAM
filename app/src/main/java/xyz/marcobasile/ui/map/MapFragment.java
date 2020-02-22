package xyz.marcobasile.ui.map;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapView;

import xyz.marcobasile.R;
import xyz.marcobasile.ui.map.util.MapSetupUtils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    private MapView mapView;
    private MapSetupUtils mapUtils;

    private View root;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mapUtils = new MapSetupUtils(this);
        root = inflater.inflate(R.layout.fragment_map, container, false);

        setupViews(savedInstanceState);

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

    private void setupViews(Bundle savedInstanceState) {
        mapView = root.<MapView>findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        listView = root.findViewById(R.id.list_view);
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