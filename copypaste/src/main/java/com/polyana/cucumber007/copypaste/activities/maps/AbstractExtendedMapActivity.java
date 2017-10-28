package com.polyana.cucumber007.copypaste.activities.maps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.androidmapsextensions.ClusterOptions;
import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MapView;
import com.androidmapsextensions.Marker;
import com.cucumber007.reusables.R;
import com.cucumber007.reusables.listeners.DefaultLocationListener;
import com.cucumber007.reusables.listeners.LoadingListener;
import com.cucumber007.reusables.location.LocationModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.polyana.cucumber007.copypaste.ContextApplication;

import java.util.Map;

import static com.cucumber007.reusables.location.BaseLocationModel.REQUEST_CHECK_SETTINGS;


public abstract class AbstractExtendedMapActivity extends AppCompatActivity implements GoogleMap.OnCameraChangeListener, LoadingListener {

    private MapView mapView;
    private Context context = this;
    private final LatLng KIEV = new LatLng(50.448, 30.554);
    public static final int DEFAULT_ZOOM = 14;

    private GoogleMap googleMap;
    private boolean isLoading = false;
    private boolean locationReceived = false;

    private ArrayMap<Integer, Marker> markers = new ArrayMap<>();

    protected void initMapOnCreate(MapView mapView, Bundle savedInstanceState, GoogleMapConfigurator configurator) {
        mapView.getExtendedMapAsync(googleMap -> {
            this.googleMap = googleMap;
            configurator.configurate(googleMap);

            LocationModel.getInstance(ContextApplication.getContext()).getLocation(this, new DefaultLocationListener(this) {
                @Override
                public void onReceived(Location location) {
                    if (location == null) googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KIEV, DEFAULT_ZOOM));
                    else {
                        locationReceived = true;
                        init(location);
                    }
                }
            });

            googleMap.setOnCameraChangeListener(this);
        });
        mapView.onCreate(savedInstanceState);
    }

    protected void initMapOnCreate(MapView mapView) {
        initMapOnCreate(mapView, null, googleMap -> {
            googleMap.setClustering(new ClusteringSettings()
                    .clusterOptionsProvider(markers -> new ClusterOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_group)))
                    .addMarkersDynamically(true)
                    .clusterSize(96)
                    .enabled(true)
            );
            googleMap.setMyLocationEnabled(true);
        });
    }

    private void init(Location location) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
        initMarkers();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if(!isLoading) {
            onStartLoading();
            loadMarkers(googleMap.getCameraPosition().target, calcRadius(googleMap.getCameraPosition().zoom));
        }
    }

    abstract void loadMarkers(LatLng coords, float radius);

    abstract void onMarkerClick(Marker marker);

    abstract void onGroupMarkerClick(Marker marker);

    @Override
    public void onStartLoading() {
        isLoading = true;
    }

    @Override
    public void onStopLoading() {
        isLoading = false;
    }

    protected void initMarkers() {
        onStartLoading();
        googleMap.clear();
        markers.clear();

        googleMap.setOnMarkerClickListener(marker -> {
            if(marker.getMarkers() == null)
                onMarkerClick(marker);
            else {
                onGroupMarkerClick(marker);
            }
            return true;
        });

        loadMarkers(googleMap.getCameraPosition().target, calcRadius(DEFAULT_ZOOM));
    }

    private void addMarkers(Map<Integer, Marker> markers) {
        this.markers.putAll(markers);
    }

    public interface GoogleMapConfigurator {
        void configurate(GoogleMap googleMap);
    }

    public static float calcRadius(float zoom) {
        return ((float) (1 / Math.pow(2, zoom + 1) * LocationModel.EQUATOR * 1000)); // /2 *1.7 ~ 1

    }

    ///////////////////////////////////////////////////////////////////////////
    // Activity Lifecycle
    ///////////////////////////////////////////////////////////////////////////


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //if (googleMap != null) initMarkers();
    }


    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private LocationModel.LocationCallback locationListener = new DefaultLocationListener(this) {
        @Override
        public void onReceived(Location location) {
            //todo update location?
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK)
                if(!locationReceived) {
                    LocationModel.getInstance(ContextApplication.getContext()).getLocation(this, new DefaultLocationListener(this) {
                        @Override
                        public void onReceived(Location location) {
                            locationReceived = true;
                            init(location);
                        }
                    });
                }
        } else locationListener.onFailed();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
