package com.mydomain.auth_copypaste.location;

import android.app.Activity;
import android.content.IntentSender;

import com.mydomain.auth_copypaste.location.LocationModel;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.google.android.gms.common.api.Status;

import static com.mydomain.auth_copypaste.location.BaseLocationModel.REQUEST_CHECK_SETTINGS;

public abstract class DefaultLocationListener implements LocationModel.LocationCallback {

    private Activity activity;

    public DefaultLocationListener(Activity activity) {
        this.activity = activity;
    }

    @Override
        public void onLocationDisabled(Status status) {
            try {
                status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
                LogUtil.logError(e);
            }
        }

        @Override
        public void onFailed() {

        }
    }
