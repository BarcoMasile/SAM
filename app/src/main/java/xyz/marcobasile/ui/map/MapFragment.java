package xyz.marcobasile.ui.map;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import xyz.marcobasile.R;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.ui.adapter.map.MapTweetUserAdapter;
import xyz.marcobasile.ui.map.util.MapSetupUtils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    private MapView mapView;
    private MapSetupUtils mapUtils;

    private View root;
    private RecyclerView recyclerView;

    private MapTweetUserAdapter mapTweetUserAdapter;
    private ContentProvider provider;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        provider = ContentProvider.getInstance();

        mapUtils = new MapSetupUtils(this, provider);
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

        recyclerView = (RecyclerView) root.findViewById(R.id.map_scroll_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        mapTweetUserAdapter = new MapTweetUserAdapter(provider, mapUtils);
        recyclerView.setAdapter(mapTweetUserAdapter);
        //recyclerView.addOnItemTouchListener(null);

        mapView.onCreate(savedInstanceState);
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