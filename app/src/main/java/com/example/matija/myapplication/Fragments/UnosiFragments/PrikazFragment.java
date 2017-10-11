package com.example.matija.myapplication.Fragments.UnosiFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.matija.myapplication.Adapters.RacunAdapter;
import com.example.matija.myapplication.Database.DataTable;
import com.example.matija.myapplication.Events.DeleteEvent;
import com.example.matija.myapplication.Events.SendUkupnoEvent;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by matija on 08.08.17..
 */

public class PrikazFragment extends Fragment {
    public static final String LIST_DATA = "1";
    int counter;

    public enum timeSort {
        DANAS,
        TJEDAN,
        MJESEC,
        GODINA,
        NONE
    }
    @BindView(R.id.tvUkupno)
    TextView tvUkupno;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.danasBtn)
    ToggleButton danasBtn;
    @BindView(R.id.tjedanBtn)
    ToggleButton tjedanBtn;
    @BindView(R.id.mjesecBtn)
    ToggleButton mjesecBtn;
    @BindView(R.id.godinaBtn)
    ToggleButton godinaBtn;
    @BindView(R.id.hranaBtn)
    ToggleButton hranaBtn;
    @BindView(R.id.hihijenaBtn)
    ToggleButton hihijenaBtn;
    @BindView(R.id.rezijeBtn)
    ToggleButton rezijeBtn;
    @BindView(R.id.potrepstinBtn)
    ToggleButton potrepstinBtn;
    @BindView(R.id.ostaloBtn)
    ToggleButton ostaloBtn;


    private List<DataTable> list;
    private List<DataTable> startlist;

    RacunAdapter adapter;
    Unbinder unbinder;

    public static PrikazFragment newInstance(List<DataTable> list) {
        PrikazFragment fragment = new PrikazFragment();
        Bundle args = new Bundle();
        args.putSerializable(LIST_DATA, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        counter = 0;
        list = new ArrayList<>();
        startlist = (ArrayList<DataTable>) getArguments().getSerializable(LIST_DATA);
        list.addAll(startlist);

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.prikaz_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvUkupno.setText(String.valueOf(RacunAdapter.ukupno));

        adapter = new RacunAdapter(list, getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.NavDelete:
                Toast.makeText(getActivity(), "klik", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new DeleteEvent());
                counter++;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    @Subscribe
    public void onSetUkupno(SendUkupnoEvent e){
        tvUkupno.setText(String.valueOf(e.getUkupno()));
    }

    @OnClick({R.id.danasBtn, R.id.tjedanBtn, R.id.mjesecBtn, R.id.godinaBtn, R.id.hranaBtn, R.id.hihijenaBtn, R.id.rezijeBtn, R.id.potrepstinBtn, R.id.ostaloBtn, R.id.linearLayout3})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.danasBtn:
                dateBtnCheck(0);
                if (danasBtn.isChecked()) {
                    danasBtn.setBackgroundResource(R.drawable.date1_on);
                } else {
                    danasBtn.setBackgroundResource(R.drawable.date1_off);
                }
                break;
            case R.id.tjedanBtn:
                dateBtnCheck(1);
                if (tjedanBtn.isChecked()) {
                    tjedanBtn.setBackgroundResource(R.drawable.date2_on);
                } else {
                    tjedanBtn.setBackgroundResource(R.drawable.date2_off);
                }
                break;
            case R.id.mjesecBtn:
                dateBtnCheck(2);
                if (mjesecBtn.isChecked()) {
                    mjesecBtn.setBackgroundResource(R.drawable.date3_on);
                } else {
                    mjesecBtn.setBackgroundResource(R.drawable.date3_off);
                }
                break;
            case R.id.godinaBtn:
                dateBtnCheck(3);
                if (godinaBtn.isChecked()) {
                    godinaBtn.setBackgroundResource(R.drawable.date4_on);
                } else {
                    godinaBtn.setBackgroundResource(R.drawable.date4_off);
                }
                break;
            case R.id.hranaBtn:
                if (hranaBtn.isChecked()) {
                    hranaBtn.setBackgroundResource(R.drawable.apple);
                } else {
                    hranaBtn.setBackgroundResource(R.drawable.apple_off);
                }
                filterDates(getCheckedCategory(), getCheckedDates());
                break;
            case R.id.hihijenaBtn:
                if (hihijenaBtn.isChecked()) {
                    hihijenaBtn.setBackgroundResource(R.drawable.tooth_brush);
                } else {
                    hihijenaBtn.setBackgroundResource(R.drawable.tooth_brush_off);
                }
                filterDates(getCheckedCategory(), getCheckedDates());
                break;
            case R.id.rezijeBtn:
                if (rezijeBtn.isChecked()) {
                    rezijeBtn.setBackgroundResource(R.drawable.house);
                } else {
                    rezijeBtn.setBackgroundResource(R.drawable.house_off);
                }
                filterDates(getCheckedCategory(), getCheckedDates());
                break;
            case R.id.potrepstinBtn:
                if (potrepstinBtn.isChecked()) {
                    potrepstinBtn.setBackgroundResource(R.drawable.cleaning);
                } else {
                    potrepstinBtn.setBackgroundResource(R.drawable.cleaning_off);
                }
                filterDates(getCheckedCategory(), getCheckedDates());
                break;
            case R.id.ostaloBtn:
                if (ostaloBtn.isChecked()) {
                    ostaloBtn.setBackgroundResource(R.drawable.more);
                } else {
                    ostaloBtn.setBackgroundResource(R.drawable.more_off);
                }
                filterDates(getCheckedCategory(), getCheckedDates());
                break;
        }
    }

    public timeSort getCheckedDates() {
        timeSort i = timeSort.NONE;
        if (danasBtn.isChecked()) {
            i = timeSort.DANAS;
        }
        if (tjedanBtn.isChecked()) {
            i = timeSort.TJEDAN;
        }
        if (mjesecBtn.isChecked()) {
            i = timeSort.MJESEC;
        }
        if (godinaBtn.isChecked()) {
            i = timeSort.GODINA;
        }

        return i;
    }

    public ArrayList<String> getCheckedCategory() {
        ArrayList<String> kategorijaList = new ArrayList<>();
        if (hranaBtn.isChecked()) {
            kategorijaList.add(UnosFragment.HRANA);
        }
        if (hihijenaBtn.isChecked()) {
            kategorijaList.add(UnosFragment.HIGIJENA);
        }
        if (rezijeBtn.isChecked()) {
            kategorijaList.add(UnosFragment.REZIJE);
        }
        if (potrepstinBtn.isChecked()) {
            kategorijaList.add(UnosFragment.POTREPŠTINE);
        }
        if (ostaloBtn.isChecked()) {
            kategorijaList.add(UnosFragment.OSTALO);
        }
        return kategorijaList;
    }

    public void filterData(ArrayList<String> kategorijaList) {
        List<DataTable> tmpList = new ArrayList<>();
        if (!kategorijaList.isEmpty()) {
            for (int j = 0; j < kategorijaList.size(); j++) {
                for (int i = 0; i < startlist.size(); i++) {
                    if (startlist.get(i).getKategorija().equals(kategorijaList.get(j))) {
                        tmpList.add(startlist.get(i));
                    }
                }
            }
            adapter.swapData(tmpList);
        } else {
            adapter.swapData(startlist);
        }
    }

    public void filterDates(ArrayList<String> kategorijaList, timeSort time) {
        List<DataTable> tmpList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date tmpDate;
        Date minDate;
        Date now = Calendar.getInstance().getTime();
        try {
            if (!kategorijaList.isEmpty() && !time.equals(timeSort.NONE)) {
                for (int x = 0; x < kategorijaList.size(); x++) {
                    if (time == timeSort.DANAS) {
                        for (int j = 0; j < startlist.size(); j++) {
                            tmpDate = format.parse(startlist.get(j).getDatum());
                            if (DateUtils.isToday(tmpDate.getTime()) && startlist.get(j).getKategorija().equals(kategorijaList.get(x))) {
                                tmpList.add(startlist.get(j));
                            }
                        }

                    } else if (time == timeSort.TJEDAN) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
                        cal.add(Calendar.DAY_OF_YEAR, -7);
                        minDate = cal.getTime();

                        for (int j = 0; j < startlist.size(); j++) {
                            tmpDate = format.parse(startlist.get(j).getDatum());
                            if (tmpDate.after(minDate) && startlist.get(j).getKategorija().equals(kategorijaList.get(x))) {
                                tmpList.add(startlist.get(j));
                            }
                        }

                    } else if (time == timeSort.MJESEC) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
                        cal.add(Calendar.MONTH, -1);
                        minDate = cal.getTime();

                        for (int j = 0; j < startlist.size(); j++) {
                            tmpDate = format.parse(startlist.get(j).getDatum());
                            if (tmpDate.after(minDate) && startlist.get(j).getKategorija().equals(kategorijaList.get(x))) {
                                tmpList.add(startlist.get(j));
                            }
                        }

                    } else if (time == timeSort.GODINA) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
                        cal.add(Calendar.YEAR, -1);
                        minDate = cal.getTime();

                        for (int j = 0; j < startlist.size(); j++) {
                            tmpDate = format.parse(startlist.get(j).getDatum());
                            if (tmpDate.after(minDate) && startlist.get(j).getKategorija().equals(kategorijaList.get(x))) {
                                tmpList.add(startlist.get(j));
                            }
                        }
                    }
                }
                adapter.swapData(tmpList);
            } else if (!kategorijaList.isEmpty()) {
                filterData(kategorijaList);

            } else if (!time.equals(timeSort.NONE)) {
                if (time == timeSort.DANAS) {
                    for (int j = 0; j < startlist.size(); j++) {
                        tmpDate = format.parse(startlist.get(j).getDatum());
                        if (DateUtils.isToday(tmpDate.getTime())) {
                            tmpList.add(startlist.get(j));
                        }
                    }

                } else if (time == timeSort.TJEDAN) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.DAY_OF_YEAR, -7);
                    minDate = cal.getTime();

                    for (int j = 0; j < startlist.size(); j++) {
                        tmpDate = format.parse(startlist.get(j).getDatum());
                        if (tmpDate.after(minDate)) {
                            tmpList.add(startlist.get(j));
                        }
                    }

                } else if (time == timeSort.MJESEC) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.MONTH, -1);
                    minDate = cal.getTime();

                    for (int j = 0; j < startlist.size(); j++) {
                        tmpDate = format.parse(startlist.get(j).getDatum());
                        if (tmpDate.after(minDate)) {
                            tmpList.add(startlist.get(j));
                        }
                    }

                } else if (time == timeSort.GODINA) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.YEAR, -1);
                    minDate = cal.getTime();

                    for (int j = 0; j < startlist.size(); j++) {
                        tmpDate = format.parse(startlist.get(j).getDatum());
                        if (tmpDate.after(minDate)) {
                            tmpList.add(startlist.get(j));
                        }
                    }
                }
                adapter.swapData(tmpList);
            } else {
                adapter.swapData(startlist);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void dateBtnCheck(int i) {
        filterDates(getCheckedCategory(), getCheckedDates());
        if (i == 0) {
            mjesecBtn.setChecked(false);
            tjedanBtn.setChecked(false);
            godinaBtn.setChecked(false);
            mjesecBtn.setBackgroundResource(R.drawable.date3_off);
            tjedanBtn.setBackgroundResource(R.drawable.date2_off);
            godinaBtn.setBackgroundResource(R.drawable.date4_off);

        } else if (i == 1) {
            danasBtn.setChecked(false);
            mjesecBtn.setChecked(false);
            godinaBtn.setChecked(false);
            danasBtn.setBackgroundResource(R.drawable.date1_off);
            mjesecBtn.setBackgroundResource(R.drawable.date3_off);
            godinaBtn.setBackgroundResource(R.drawable.date4_off);
        } else if (i == 2) {
            danasBtn.setChecked(false);
            tjedanBtn.setChecked(false);
            godinaBtn.setChecked(false);
            danasBtn.setBackgroundResource(R.drawable.date1_off);
            tjedanBtn.setBackgroundResource(R.drawable.date2_off);
            godinaBtn.setBackgroundResource(R.drawable.date4_off);
        } else if (i == 3) {
            mjesecBtn.setChecked(false);
            tjedanBtn.setChecked(false);
            danasBtn.setChecked(false);
            mjesecBtn.setBackgroundResource(R.drawable.date3_off);
            tjedanBtn.setBackgroundResource(R.drawable.date2_off);
            danasBtn.setBackgroundResource(R.drawable.date1_off);
        }
    }
}

