package com.cucumber007.prototypes.menu;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandboxItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox_fragment);
        ButterKnife.bind(this);

        String sandboxItemId = getIntent().getStringExtra(this.getClass().getName());
        SandboxItem sandboxItem = SandboxItemsModel.getInstance().getItemById(sandboxItemId);
        try {
            SandboxItemFragment fragment = (SandboxItemFragment) sandboxItem.getClazz().newInstance();
            fragment.setSandboxItem(sandboxItem);
            getSupportFragmentManager().beginTransaction().add(R.id.root, fragment).commit();

        } catch (Exception e) {
            LogUtil.makeToast(e.getMessage());
        }
    }
}
