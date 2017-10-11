package com.example.matija.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.matija.myapplication.Database.DataTable;
import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Fragments.MenuFragments.MenuFragment;
import com.example.matija.myapplication.Fragments.UnosiFragments.UnosFragment;
import com.example.matija.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements UnosFragment.UnosFragmentListener{
    MenuFragment menuFragment;
    public static UsersTable user;

    @BindView(R.id.menu_fragment_holder)
    FrameLayout menuFragmentHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        Bundle userData = this.getIntent().getBundleExtra("bundle");
        user = (UsersTable) userData.getSerializable("user");

        if(getSupportFragmentManager().findFragmentById(R.id.menu_fragment_holder) == null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            MenuFragment frag = MenuFragment.newInstance(user);
            menuFragment = frag;
            ft.add(R.id.menu_fragment_holder, frag);
            ft.commit();
            if(this.getIntent().getFlags() == 12345){
                Intent intent = new Intent(this, RasporedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }




    @Override
    public void getData(DataTable racun) {
        menuFragment.setDataList(racun);
    }


}
