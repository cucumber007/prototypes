package com.cucumber007.prototypes.activities.giver;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import com.cucumber007.prototypes.reusables.ContextApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class LocationModel extends BaseLocationModel {

    private static LocationModel instance;

    public static final int REQUEST_CODE_GAPI = 888;

    public LocationModel() {}

    public static LocationModel getInstance() {
        if (instance == null) {
            instance = new LocationModel();
        }
        return instance;
    }

    public void getLocation(Activity activity, LocationCallback callback) {
        if(getGoogleApiClient() == null) {
            int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
            //todo check gapi
            //SUCCESS, SERVICE_MISSING, SERVICE_UPDATING, SERVICE_VERSION_UPDATE_REQUIRED, SERVICE_DISABLED, SERVICE_INVALID...
            //showErrorDialogFragment if Succes do nothing
            //Returns
            //true if the dialog is shown, false otherwise

            if(resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                GoogleApiAvailability.getInstance().showErrorDialogFragment(activity, resultCode, REQUEST_CODE_GAPI);
                callback.onFailed();

            } else {
                connectToGoogleApiClient(new GoogleApiClientCallback() {
                    @Override
                    public void onConnected() {
                        checkIfLocationEnabled(new BaseLocationModel.SettingsCallback() {
                            @Override
                            public void onSuccess() {
                                requestLocationUpdates(new LocationListener() {
                                    boolean got = false;

                                    @Override
                                    public void onLocationChanged(Location location) {
                                        if (!got) {
                                            setLocation(location);
                                            callback.onReceived(location);
                                            got = true;
                                        }
                                        setLocation(location);
                                    }
                                });
                            }

                            @Override
                            public void onResolutionRequired(Status status) {
                                callback.onLocationDisabled(status);
                            }

                            @Override
                            public void onUnavailable() {
                                callback.onFailed();
                            }
                        });

                    }

                    @Override
                    public void onFailed(ConnectionResult connectionResult) {
                        callback.onFailed();
                        Toast.makeText(ContextApplication.getContext(), "Google API error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            checkIfLocationEnabled(new BaseLocationModel.SettingsCallback() {
                @Override
                public void onSuccess() {
                    Location locationFromModel = getLocationIfPossible();
                    if(locationFromModel != null) callback.onReceived(locationFromModel);
                    else {
                        requestLocationUpdates(new LocationListener() {
                            boolean got = false;

                            @Override
                            public void onLocationChanged(Location location) {
                                if (!got) {
                                    setLocation(location);
                                    callback.onReceived(location);
                                    got = true;
                                }
                                setLocation(location);
                            }
                        });
                    }
                }

                @Override
                public void onResolutionRequired(Status status) {
                    callback.onLocationDisabled(status);
                }

                @Override
                public void onUnavailable() {
                    callback.onFailed();
                }
            });

        }

    }

    public void checkIfLocationEnabled(SettingsCallback callback) {
        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(getLocationRequest());

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(getGoogleApiClient(),
                        settingsBuilder
                                .setAlwaysShow(true)
                                .build());

        result.setResultCallback(settingsResult -> {
            final Status status = settingsResult.getStatus();
            final LocationSettingsStates states = settingsResult.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    callback.onSuccess();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    callback.onResolutionRequired(status);
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    callback.onUnavailable();
                    break;
            }
        });
    }

    public boolean checkResultIfLocationEnabled(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            return resultCode == Activity.RESULT_OK;
        } else return false;
    }
    
    public interface LocationCallback {
        void onReceived(Location location);
        void onLocationDisabled(Status status);
        void onFailed();
    }
}
