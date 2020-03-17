package xyz.marcobasile.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Optional;
import java.util.stream.Stream;

import xyz.marcobasile.R;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.ui.adapter.map.MapTweetUserAdapter;
import xyz.marcobasile.ui.map.util.MapSetupUtils;


public class MapFragment extends Fragment {

    private final static String TAG = MapFragment.class.getName();

    final UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    final Handler handler = new Handler(Looper.getMainLooper());

    private MapView mapView;
    private MapSetupUtils mapUtils;

    private View root;
    private RecyclerView recyclerView;

    private MapTweetUserAdapter mapTweetUserAdapter;
    private ContentProvider provider;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        provider = ContentProvider.getInstance();

        if (null == mapUtils) {
            mapUtils = new MapSetupUtils(this, provider);
        }

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
        Log.e(TAG, "In procinto di chiamare la onCreate sulla mapView");
        mapView.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler(defaultHandler, mapView, handler));

        recyclerView = (RecyclerView) root.findViewById(R.id.map_scroll_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        mapTweetUserAdapter = new MapTweetUserAdapter(provider, mapUtils);
        recyclerView.setAdapter(mapTweetUserAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    private static UncaughtExceptionHandler uncaughtExceptionHandler(UncaughtExceptionHandler defaultHandler, MapView mapView, Handler handler) {

        return (thread, ex) -> {

            Log.e(TAG + "_Uncaught", "Catturata eccezione thread: " + thread.getName() + ", ex.message: " + ex.getMessage());

            if (ex instanceof NullPointerException && thread.getName().startsWith("GLThread")) {

                Log.e(TAG + "_Uncaught", "UncaughtExceptionHandler, Nullpointer e GLThread nel name");

                Stream.of(ex.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .filter(className -> className.contains("maps"))
                        .findAny()
                        .ifPresent( _st -> {

                            Log.e(TAG + "_Uncaught", "In procinto di postDelayed onCreate");
                            handler.postDelayed(() -> mapView.onCreate(null), 200L);
                        });

            } else {

                Optional.ofNullable(defaultHandler)
                        .ifPresent(dh -> dh.uncaughtException(thread, ex));
            }
        };
    }
}