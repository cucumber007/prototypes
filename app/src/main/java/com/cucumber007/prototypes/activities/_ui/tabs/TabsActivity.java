package com.cucumber007.prototypes.activities._ui.tabs;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.cucumber007.prototypes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabsActivity extends AppCompatActivity {

    @Bind(R.id.root) RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);
        switchFragment(new TestTabFragment());
    }

    private void switchFragment(AbstractTitledFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.root, fragment, "detailFragment");
        ft.commit();
        setTitle(fragment.getTitle());
    }
}
