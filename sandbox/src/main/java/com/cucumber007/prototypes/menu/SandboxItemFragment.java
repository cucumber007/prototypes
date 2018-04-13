package com.cucumber007.prototypes.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SandboxItemFragment extends Fragment {

    private SandboxItem sandboxItem;

    public void setSandboxItem(SandboxItem sandboxItem) {
        this.sandboxItem = sandboxItem;
    };

    public SandboxItem getSandboxItem() {
        return sandboxItem;
    }


}
