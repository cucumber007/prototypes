package com.cucumber007.prototypes.activities.giver;


public class OffersMapPresenter {

    /*private boolean addressMode = false;

    private Activity activity;
    private OffersMapView mapView;
    private LatLng address;
    private boolean isLoading = false;
    private Map<LatLng, Offer> offers = new HashMap<>();

    public OffersMapPresenter(Activity activity, OffersMapView mapView, LatLng address) {
        this.activity = activity;
        this.mapView = mapView;
        this.address = address;
        addressMode = true;
        //GiverApplication.getAppComponent().inject(this);
    }

    public OffersMapPresenter(Activity activity, OffersMapView mapView) {
        this.activity = activity;
        this.mapView = mapView;
        //GiverApplication.getAppComponent().inject(this);
    }

    public void onMapLoaded() {
        if(!addressMode) {
            LocationModel.getInstance().getLocation(activity, new DefaultLocationListener(activity) {
                @Override
                public void onReceived(Location location) {

                    mapView.moveToLocation(location);

                    onStartLoading();
                    offersModel.getOffersForMapObservable(location, calcRadius(MapActivity.DEFAULT_ZOOM)).subscribe(offersWrapper -> {

                        for(Offer offer : offersWrapper.getOffers()) {

                            offers.put(offer.getLatLng(), offer);
                            Glide.with(activity).load(offer.getTypeImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    mapView.addMarker(offer.getLocation(), offer, resource);
                                }
                            });
                        }
                        onStopLoading();
                    }, throwable -> RequestManager.handleError(throwable, activity));
                }
            });
        } else {
            mapView.moveToLocation(LocationModel.locationFromLatLng(address), MapActivity.ADDRESS_ZOOM);
        }
    }

    public static double calcRadius(float zoom) {
        return 1/Math.pow(2, zoom+1)*EQUATOR*1000; // /2 *1.7 ~ 1
    }

    public void onMapMoved(Location location) {
        if(!isLoading) {

            onStartLoading();
            offersModel.getOffersForMapObservable(location, calcRadius(mapView.getCurrentZoom())).subscribe(offersWrapper -> {

                for (Offer offer : offersWrapper.getOffers()) {

                    if(!offers.keySet().contains(offer.getLatLng()))
                        Glide.with(activity).load(offer.getTypeImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                mapView.addMarker(offer.getLocation(), offer, resource);
                                offers.put(offer.getLatLng(), offer);
                            }
                        });
                }
                onStopLoading();
            }, throwable -> RequestManager.handleError(throwable, activity));
        }
    }

    public boolean isAddressMode() {
        return addressMode;
    }

    @Override
    public void onStartLoading() {
        isLoading = true;
    }

    @Override
    public void onStopLoading() {
        isLoading = false;
    }

    public interface OffersMapView {
        void addMarker(Location location, Offer offer, Bitmap icon);
        void moveToLocation(Location location);
        void moveToLocation(Location location, float zoom);
        float getCurrentZoom();
    }

    public interface OffersCallback {
        void onLoad(List<Offer> offers);
    }*/
}
