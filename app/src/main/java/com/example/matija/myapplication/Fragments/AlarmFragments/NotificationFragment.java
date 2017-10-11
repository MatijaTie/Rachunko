package com.example.matija.myapplication.Fragments.AlarmFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.matija.myapplication.Adapters.AlarmAdapter;
import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Models.AlarmData;
import com.example.matija.myapplication.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by matija on 26.08.17..
 */

public class NotificationFragment extends Fragment {


    AlarmAdapter adapter;
    List<AlarmData> dataList;
    {
        dataList = new ArrayList<>();
    }
    Unbinder unbinder;
    UsersTable user;
    @BindView(R.id.rv_notification)
    RecyclerView rvNotification;

    public static NotificationFragment newInstance(UsersTable user) {
        if (user != null) {
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            NotificationFragment menuFragment = new NotificationFragment();
            menuFragment.setArguments(args);
            return menuFragment;
        }
        return new NotificationFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            user = (UsersTable) args.getSerializable("user");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkTimedNotifications();
    }

    public void checkTimedNotifications(){
        dataList = SQLite.select().from(AlarmData.class).queryList();
        if(dataList == null){
            dataList = new ArrayList<>();
        }
        if(dataList.size() > 0){

            for(int i = 0; i < dataList.size(); i++){
                Calendar calendar = Calendar.getInstance();
                String s = dataList.get(i).getString();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                try {
                    calendar.setTime(format.parse(s));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                Calendar now = Calendar.getInstance();
                if(calendar.before(now)){
                    dataList.get(i).delete();
                    dataList.remove(dataList.get(i));
                    i--;
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.notification_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new AlarmAdapter(getContext(), dataList);
        rvNotification.setAdapter(adapter);
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_alarm, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addAlarm:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.notification_main_holder, new AlarmCreatorFragment())
                        .addToBackStack("")
                        .commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
