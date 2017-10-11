package com.example.matija.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matija.myapplication.Events.NotificationTransferList;
import com.example.matija.myapplication.Models.AlarmData;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by matija on 27.09.17..
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    List<AlarmData> data;
    Context context;

    public  AlarmAdapter(Context context, List<AlarmData> data){
        this.context = context;
        this.data = data;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onTransferListNotification(NotificationTransferList e){
        data.add(e.getData());
        notifyDataSetChanged();
    }
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View alarmView = inflater.inflate(R.layout.alarm_holder, parent, false);
        AlarmAdapter.ViewHolder holder = new AlarmAdapter.ViewHolder(alarmView);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.ViewHolder holder, int position) {
        AlarmData alarmData = data.get(position);

        holder.alarmImageView.setImageResource(alarmData.getImg());
        holder.alarmTitleView.setText(alarmData.getTitle());
        holder.alarmTextView.setText(alarmData.getText());
        holder.alarmDateView.setText(alarmData.getCalendarDate());
        holder.alarmTimeView.setText(alarmData.getCalendarTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView alarmDateView, alarmTimeView, alarmTextView, alarmTitleView;
        ImageView alarmImageView;

        public ViewHolder(View itemView){
            super(itemView);
            alarmDateView = itemView.findViewById(R.id.alarm_date_view);
            alarmTimeView = itemView.findViewById(R.id.alarm_time_view);
            alarmTextView = itemView.findViewById(R.id.alarm_text_view);
            alarmTitleView = itemView.findViewById(R.id.alarm_title_view);
            alarmImageView = itemView.findViewById(R.id.alarm_image_view);
        }
    }
}
