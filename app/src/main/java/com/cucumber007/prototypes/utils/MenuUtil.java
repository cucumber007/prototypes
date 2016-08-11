package com.cucumber007.prototypes.utils;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;

public class MenuUtil {

    public static Menu getMenuFromResource(Activity activity, int menuRes) {
        PopupMenu popupMenu = new PopupMenu(activity, null);
        Menu menu = popupMenu.getMenu();
        activity.getMenuInflater().inflate(menuRes, menu);
        return menu;
    }
}
