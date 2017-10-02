package com.cucumber007.reusables.utils;

import android.content.ContentResolver;
import android.provider.Settings;

public class BrightnessUtil {

    private boolean autoBrightness;
    private int brightnessValue;
    private ContentResolver resolver;

    public BrightnessUtil(ContentResolver resolver) {
        this.resolver = resolver;
        try {
            autoBrightness = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

            if (!autoBrightness)
                brightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            try {
                brightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
            } catch (Settings.SettingNotFoundException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void setMaxBrightness() {
        if(autoBrightness)
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    public void revertDefault() {
        if (autoBrightness) {
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        } else {
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
        }
    }
}
