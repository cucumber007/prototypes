package com.cucumber007.prototypes.activities._libraries.reactive_location;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;
import com.google.android.gms.location.LocationRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReactiveLocationActivity extends AppCompatActivity {

    @Bind(R.id.textView8) TextView textView8;
    @Bind(R.id.textView9) TextView textView9;

    private Context context = this;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive_location);
        ButterKnife.bind(this);


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
        subscription.unsubscribe();
        super.onStop();
    }
}
