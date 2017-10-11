package com.example.matija.myapplication.Fragments.AlarmFragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.matija.myapplication.Broadcast.Alarm;
import com.example.matija.myapplication.Events.ChooseImageEvent;
import com.example.matija.myapplication.Events.NotificationTransferList;
import com.example.matija.myapplication.Models.AlarmData;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by matija on 27.09.17..
 */

public class AlarmCreatorFragment extends Fragment {
    @BindView(R.id.alarm_fragment_img)
    ImageView alarmFragmentImg;
    @BindView(R.id.alarm_framgent_title)
    EditText alarmFramgentTitle;
    @BindView(R.id.alarm_fragment_text)
    EditText alarmFragmentText;
    @BindView(R.id.alarm_fragment_datePicker)
    DatePicker alarmFragmentDatePicker;
    @BindView(R.id.alarm_fragment_timePicker)
    TimePicker alarmFragmentTimePicker;
    @BindView(R.id.confirm_alarm)
    Button confirmAlarm;
    Unbinder unbinder;

    int image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_creator_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        image = 0;
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.alarm_fragment_img, R.id.alarm_framgent_title, R.id.alarm_fragment_text, R.id.alarm_fragment_datePicker, R.id.alarm_fragment_timePicker, R.id.confirm_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm_alarm:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                String title, text, date, time;
                Calendar calendar = Calendar.getInstance();

                date = alarmFragmentDatePicker.getDayOfMonth()+"."+(alarmFragmentDatePicker.getMonth()+1)+"."+alarmFragmentDatePicker.getYear();
                time = alarmFragmentTimePicker.getHour()+":"+ alarmFragmentTimePicker.getMinute();
                title = alarmFramgentTitle.getText().toString();
                text = alarmFragmentText.getText().toString();
                calendar.set(alarmFragmentDatePicker.getYear(), alarmFragmentDatePicker.getMonth(),alarmFragmentDatePicker.getDayOfMonth(), alarmFragmentTimePicker.getHour(), alarmFragmentTimePicker.getMinute(), 00);
                AlarmData alarm = new AlarmData(image, title,text, date, time);
                EventBus.getDefault().post(new NotificationTransferList(alarm));
                alarm.save();
                Intent intentWithData = new Intent(getContext(), Alarm.class);
                intentWithData.putExtra("title", title);
                intentWithData.putExtra("text", text);
                intentWithData.putExtra("id", alarm.getId());
                intentWithData.putExtra("img", image);


                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), alarm.getId(), intentWithData, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                getActivity().getSupportFragmentManager().popBackStack();
                break;

            case R.id.alarm_fragment_img:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.notification_main_holder,ImageChooserFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }


    @Subscribe
    public void onImageChosen(ChooseImageEvent e){
        image = e.getImg();
        alarmFragmentImg.setImageDrawable(null);
        alarmFragmentImg.setBackgroundResource(image);

    }



}
