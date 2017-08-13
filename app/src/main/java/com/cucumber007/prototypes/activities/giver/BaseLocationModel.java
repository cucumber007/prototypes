package com.cucumber007.prototypes.activities.giver;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cucumber007.prototypes.reusables.ContextApplication;
import com.cucumber007.prototypes.reusables.logging.LogUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import rx.Subscription;

public class BaseLocationModel implements LocationListener {

    private static BaseLocationModel instance;

    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest = new LocationRequest();

    public static final int UPDATE_INTERVAL = 60000;
    public static final int LOWEST_UPDATE_INTERVAL = 30000;
    public static final float OFFER_RADIUS = 500;
    public static final double EQUATOR = 40075.7;

    public static final int REQUEST_CHECK_SETTINGS = 555;
    private Subscription subscription;

    public BaseLocationModel() {
        //todo uncomment
        /*final LocationModel inst = this;
        connectToGoogleApiClient(new GoogleApiClientCallback() {
            @Override
            public void onConnected() {
                requestLocationUpdates(inst);
            }

            @Override
            public void onFailed(ConnectionResult connectionResult) {}
        });*/
    }

    protected void connectToGoogleApiClient(GoogleApiClientCallback clientCallback) {
        googleApiClient = new GoogleApiClient.Builder(ContextApplication.getContext())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        try {
                            location = LocationServices.FusedLocationApi.getLastLocation(
                                    googleApiClient);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            LogUtil.makeToast("No permission");
                        }
                        clientCallback.onConnected();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        LogUtil.logDebug("Connection suspended");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //LogUtil.logToFabric(connectionResult.getErrorMessage());
                        if(connectionResult.getErrorCode() != ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                            Toast.makeText(ContextApplication.getContext(), "Google API error", Toast.LENGTH_SHORT).show();
                        }
                        clientCallback.onFailed(connectionResult);
                    }
                })
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private static BaseLocationModel getInstance() {
        if (instance == null) {
            instance = new BaseLocationModel();
            instance.getGoogleApiClient().connect();
        }
        return instance;
    }


    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    public Location getLocationIfPossible() {
        if (location == null) {
            try {
                location = ((LocationManager) ContextApplication.getContext().getSystemService(Context.LOCATION_SERVICE))
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (SecurityException e) {
                LogUtil.makeToast("No permission");
                e.printStackTrace();
                return null;
            }
            //todo gps provider
        }
        return location;
    }


    public void requestLocationUpdates(LocationListener listener) throws IllegalStateException {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, listener);
        } catch (SecurityException e) {
            LogUtil.makeToast("No permission");
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    /*public static void startMapActivity(Context context, Offer offer) {

        String yandexkey = "yandexmaps://maps.yandex.ru/?rtext="+offer.getLatitude()+","+offer.getLongitude()+"&rrt=pd&z=16";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri pathUri = Uri.parse("google.navigation:q="+offer.getLatitude()+","+offer.getLongitude());
        Uri pointUri = Uri.parse("geo:0,0?q="+offer.getLatitude()+","+(offer.getLongitude())+"("+offer.getPartnerName()+")");
        intent.setData(pathUri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            Uri yandexUri = Uri.parse(yandexkey);
            intent.setData(yandexUri);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(pointUri);
                if (intent.resolveActivity(context.getPackageManager()) != null)
                    context.startActivity(intent);
                else Toast.makeText(context, context.getResources().getString(R.string.no_maps_error), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public Location getCurrentLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Misc
    ///////////////////////////////////////////////////////////////////////////

    /*public boolean checkIfInRadius(@NonNull Offer offer) {
        return offer.getLocation().distanceTo(location) < offer.getRadius();
    }*/

    public static double distance(Location a, Location b) {
        //in meters
        return a.distanceTo(b);
    }

    /*public double distanceToMe(Offer offer) {
        //todo locationnullexception
        if (location == null) return 0;
        return location.distanceTo(offer.getLocation());
    }*/

    public static Location locationFromLatLng(double latitude, double longitude) {
        Location b = new Location(LocationManager.GPS_PROVIDER);
        b.setLatitude(latitude);
        b.setLongitude(longitude);
        return b;
    }

    public static Location locationFromLatLng(LatLng latLng) {
        Location b = new Location(LocationManager.GPS_PROVIDER);
        b.setLatitude(latLng.latitude);
        b.setLongitude(latLng.longitude);
        return b;
    }

    public static LatLng latLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    /*public static String wrapDistance(float distance) {
        if(distance < 1000) return Math.round(distance) + ContextApplication.getContext()
                .getResources().getString(R.string.distance_meter);
        else return Math.round(distance*10/1000)/10+ ContextApplication.getContext()
                .getResources().getString(R.string.distance_kilometer);
    }*/

    public static double calcRadius(double zoom) {
        return 1/Math.pow(2, zoom+1)*EQUATOR*1000; // /2 *1.7 ~ 1
    }

    public interface SettingsCallback {
        void onSuccess();
        void onResolutionRequired(Status status);
        void onUnavailable();
    }

    public interface GoogleApiClientCallback {
        void onConnected();
        void onFailed(ConnectionResult connectionResult);
    }

}
