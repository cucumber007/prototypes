package com.cucumber007.prototypes.sandbox.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.menu.PrototypesApplication;
import com.cucumber007.reusables.recycler.ArrayRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomActivity extends AppCompatActivity {

    @BindView(R.id.rv_users) RecyclerView rvUsers;

    private ArrayRecyclerAdapter<User> adapter;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        userDao = PrototypesApplication.getDatabase().userDao();
        adapter = new ArrayRecyclerAdapter<User>(this, new ArrayList<>(), R.layout.item_room_user, R.id.tv_user) {
            @Override
            public void bindViewHolder(ArrayRecyclerAdapter.TextViewHolder holder, User item, int position) {
                super.bindViewHolder(holder, item, position);
                holder.getTextView().setOnClickListener(view -> {
                    startActivity(new Intent(getContext(), UserActivity.class).putExtra(UserActivity.KEY_USER_ID, item.uid));
                });

                holder.getRoot().findViewById(R.id.b_delete).setOnClickListener(view -> {
                    Completable.fromRunnable(() -> userDao.delete(item))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> update());
                });
            }
        };
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);
        update();
    }

    public void update() {
        userDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> adapter.setItemsAndUpdate(users));
    }

    @OnClick(R.id.b_add)
    public void onClick() {
        Completable.fromRunnable(()-> userDao.insertAll(User.getDefault()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::update);
    }
}
