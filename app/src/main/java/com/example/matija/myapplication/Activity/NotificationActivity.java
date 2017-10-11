package com.example.matija.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Fragments.AlarmFragments.NotificationFragment;
import com.example.matija.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {
    UsersTable user;
    @BindView(R.id.notification_main_holder)
    FrameLayout notificationMainHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user");
        user = (UsersTable) bundle.getSerializable("user");

        FragmentManager manager;
        FragmentTransaction ft;
        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();
        ft.add(R.id.notification_main_holder, NotificationFragment.newInstance(user));
        ft.commit();


    }

    @OnClick(R.id.notification_main_holder)
    public void onViewClicked() {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            getSupportActionBar().show();
        }

    }
}
