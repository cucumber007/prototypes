package com.cucumber007.prototypes.activities.fragments_sandbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.reusables.logging.LogUtil;
import com.roughike.bottombar.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSandboxActivity extends AppCompatActivity {

    @Bind(R.id.root) FrameLayout root;
    @Bind(R.id.bottomBar) BottomBar bottomBar;
    private Fragment currentFragment;
    private Fragment fragment1 = new TestParentFragment();
    private Fragment fragment2 = new TestParentFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        ButterKnife.bind(this);

        bottomBar.setOnTabSelectListener(tabId -> {
            if (tabId == R.id.history) {
                switchFragment(fragment1);
            } else if (tabId == R.id.offers) {
                switchFragment(fragment2);
            }
        });
    }

    @OnClick(R.id.test)
    public void onClick() {
        for (int i = 0; i < 5; i++) {
            switchFragment((i % 2) == 0 ? fragment1 : fragment2);
        }
    }

    private void switchFragment(Fragment fragment) {
        //if (!pendingFragmentAction) {
        LogUtil.logDebug("switch");
        //setPendingFragmentAction(true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
        ft.replace(R.id.root, fragment, "detailFragment");
        ft.commit();
        currentFragment = fragment;
    }

}
