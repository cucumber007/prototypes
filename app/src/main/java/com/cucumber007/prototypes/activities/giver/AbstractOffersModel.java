package com.cucumber007.prototypes.activities.giver;

import android.location.Location;

import com.cucumber007.prototypes.activities.giver.objects.Checkin;
import com.cucumber007.prototypes.activities.giver.objects.wrappers.CheckinWrapper;
import com.cucumber007.prototypes.activities.giver.objects.wrappers.OffersWrapper;

import rx.Observable;

public interface AbstractOffersModel {
    Observable<OffersWrapper> getOffersObservable(Location location, int size, double radius);
    Observable<OffersWrapper> getOffersForMapObservable(Location location, double radius);
    Observable<OffersWrapper> getSearchOffersObservable(String query, int fetch, int offset);
    Observable<Checkin> getFinishCheckinObservable(int offerId, int checkinId, String message, byte[] image, Boolean addLocation);
    Observable<CheckinWrapper> getStartCheckinObservable(int offerId);
}
