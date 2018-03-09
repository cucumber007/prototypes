package com.cucumber007.prototypes.activities._ui.tabs;

import android.support.v4.app.Fragment;

public class TestTabFragment extends AbstractTabsFragment {

    @Override
    int getTabCount() {
        return 2;
    }

    @Override
    Fragment getFragmentOnPosition(int position) {
        switch (position) {
            case 0: return new HelpFragment();
            case 1: return new HelpFragment();
            default: return null;
        }
    }

    @Override
    public String getTitle() {
        return "Title";
    }

}
