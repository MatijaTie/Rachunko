package com.example.matija.myapplication.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Events.SaveEvent;
import com.example.matija.myapplication.Fragments.RasporedFragments.RasporedFragment;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RasporedActivity extends AppCompatActivity {
    UsersTable user;
    RasporedFragment rasporedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspored);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user = (UsersTable) extras.getSerializable("user");

        Toast.makeText(this, user.getName(), Toast.LENGTH_SHORT).show();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        rasporedFragment = RasporedFragment.newInstance(user);
        ft.add(R.id.rasporedHolder, rasporedFragment, "raspored");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            Intent intent = new Intent(this, MenuActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtra("bundle",bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveEvent(SaveEvent event) {
        System.out.print("save event user regex ==== " + event.getRegex());
        user.setRegex(event.getRegex());

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
