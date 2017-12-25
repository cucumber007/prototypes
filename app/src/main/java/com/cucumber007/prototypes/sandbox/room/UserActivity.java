package com.cucumber007.prototypes.sandbox.room;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cucumber007.prototypes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    public static final String KEY_USER_ID = "user_id";

    @BindView(R.id.tv_name) TextView tvName;

    private UserViewModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        ButterKnife.bind(this);

        userModel = ViewModelProviders.of(this,
                new UserViewModelFactory(getIntent().getIntExtra(KEY_USER_ID, -1)))
                .get(UserViewModel.class);

        userModel.getUserLiveData().observe(this, user -> {
            tvName.setText(user.firstName);
        });
    }

}
