package com.example.matija.myapplication.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Fragments.LogInFragments.LogInFragment;
import com.example.matija.myapplication.Fragments.LogInFragments.SignUpFragment;
import com.example.matija.myapplication.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements SignUpFragment.SignUpFragmentListener{


    @BindView(R.id.fragment_holder_start_screen)
    FrameLayout fragmentHolderStartScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        List<UsersTable> list = SQLite.select().from(UsersTable.class).queryList();

        if(list.size() > 0){
            LogInFragment logInFragment = LogInFragment.newInstance();
            ft.add(R.id.fragment_holder_start_screen, logInFragment);
            ft.commit();
        }else{
            SignUpFragment signUpFragment = SignUpFragment.newInstance();
            ft.add(R.id.fragment_holder_start_screen, signUpFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void passUser(UsersTable user) {

    }
}
