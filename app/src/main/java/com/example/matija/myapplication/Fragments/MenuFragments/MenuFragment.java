package com.example.matija.myapplication.Fragments.MenuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matija.myapplication.Activity.MenuActivity;
import com.example.matija.myapplication.Activity.NotificationActivity;
import com.example.matija.myapplication.Activity.RasporedActivity;
import com.example.matija.myapplication.Database.DataTable;
import com.example.matija.myapplication.Database.DataTable_Table;
import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Events.setMainList;
import com.example.matija.myapplication.Fragments.UnosiFragments.PrikazFragment;
import com.example.matija.myapplication.Fragments.UnosiFragments.UnosFragment;
import com.example.matija.myapplication.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by matija on 07.08.17..
 */

public class MenuFragment extends Fragment{
    List<DataTable> list;
    {
        list = new ArrayList<>();
    }

    UsersTable user;

    @BindView(R.id.unosBtn)
    Button unosBtn;
    @BindView(R.id.prikazBtn)
    Button prikazBtn;
    @BindView(R.id.rasporedBtn)
    Button rasporedBtn;
    @BindView(R.id.obvezeBtn)
    Button obvezeBtn;
    Unbinder unbinder;

    public static MenuFragment newInstance(UsersTable user){
        if(user != null){
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            MenuFragment menuFragment = new MenuFragment();
            menuFragment.setArguments(args);
            return menuFragment;
        }
        return new MenuFragment();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if(args != null){
            user = (UsersTable) args.getSerializable("user");
        }


        list = SQLite.select().from(DataTable.class).where(DataTable_Table.user_id.eq(MenuActivity.user.getid())).queryList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.unosBtn, R.id.prikazBtn, R.id.rasporedBtn, R.id.obvezeBtn})
    public void onViewClicked(View view) {
        FragmentManager manager;
        FragmentTransaction ft;
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        switch (view.getId()) {
            case R.id.unosBtn:
                manager = getActivity().getSupportFragmentManager();
                ft = manager.beginTransaction();
                UnosFragment unosFragment = UnosFragment.newInstance();
                unosFragment.setTargetFragment(this, 1);
                ft.replace(R.id.menu_fragment_holder, UnosFragment.newInstance());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.prikazBtn:
                manager = getActivity().getSupportFragmentManager();
                ft = manager.beginTransaction();
                ft.replace(R.id.menu_fragment_holder, PrikazFragment.newInstance(list));
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.rasporedBtn:
                Intent intent = new Intent(getContext(), RasporedActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.obvezeBtn:
                Intent intentNotification = new Intent(getContext(), NotificationActivity.class);
                intentNotification.putExtra("user", bundle);
                startActivity(intentNotification);
                break;
        }
    }

    @Subscribe
    public void onSetMainList(setMainList e){
        for(int i = 0; i < e.getData().size(); i++){
            list.remove(e.getData().get(i));
        }
    }


    public void setDataList(DataTable racun){
        list.add(racun);
    }
}
