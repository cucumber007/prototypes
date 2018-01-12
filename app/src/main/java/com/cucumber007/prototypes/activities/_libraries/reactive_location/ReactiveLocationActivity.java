package com.cucumber007.prototypes.activities._libraries.reactive_location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.google.android.gms.location.LocationRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReactiveLocationActivity extends AppCompatActivity {

    @BindView(R.id.textView8) TextView textView8;
    @BindView(R.id.textView9) TextView textView9;

    private Context context = this;
    private Subscription subscription;
    public static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive_location);
        ButterKnife.bind(this);

        LogUtil.logDebug("test");
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            LogUtil.logDebug("already granted");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LogUtil.logDebug("ok");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @OnClick(R.id.button14)
    public void onClick() {
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);
        locationProvider.getLastKnownLocation()
                .subscribe(location -> {
                    textView8.setText(location.toString()+"*");

                    Observable<List<Address>> reverseGeocodeObservable = locationProvider
                            .getReverseGeocodeObservable(location.getLatitude(), location.getLongitude(), 1);

                    reverseGeocodeObservable
                            .subscribeOn(Schedulers.io())               // use I/O thread to query for addresses
                            .observeOn(AndroidSchedulers.mainThread())  // return result in main android thread to manipulate UI
                            .subscribe(addresses -> {
                                textView8.setText(addresses.get(0).getAddressLine(0));
                            });

                });
    }
    @OnClick(R.id.button15)
    public void onClick1() {
        LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);

        subscription = locationProvider.getUpdatedLocation(request)
        .subscribe(location -> {
            textView9.setText(textView9.getText()+"\n"+location.toString()+"*");
        });

    }

    @Override
    protected void onStop() {
        if (subscription != null) subscription.unsubscribe();
        super.onStop();
    }
}
