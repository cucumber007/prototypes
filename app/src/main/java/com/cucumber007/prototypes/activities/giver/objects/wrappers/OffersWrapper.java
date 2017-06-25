package com.cucumber007.prototypes.activities.giver.objects.wrappers;

import com.cucumber007.prototypes.activities.giver.objects.Offer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OffersWrapper {

    private List<Offer> offers;
    @SerializedName("crop_radius")
    private double radius;

    public OffersWrapper() {
        this.offers = new ArrayList<>();
        this.radius = 0;
    }

    public OffersWrapper(List<Offer> offers, double radius) {
        this.offers = offers;
        this.radius = radius;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public double getRadius() {
        return radius;
    }
}
