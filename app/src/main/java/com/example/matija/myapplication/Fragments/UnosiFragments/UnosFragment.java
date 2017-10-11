package com.example.matija.myapplication.Fragments.UnosiFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.matija.myapplication.Activity.MenuActivity;
import com.example.matija.myapplication.Database.DataTable;
import com.example.matija.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by matija on 07.08.17..
 */

public class UnosFragment extends Fragment {
    public static final String HIGIJENA = "Higijena";
    public static final String REZIJE = "Režije";
    public static final String OSTALO = "Ostalo";
    public static final String POTREPŠTINE = "Potrepštine";
    public static final String HRANA = "Hrana";

    UnosFragmentListener listener;
    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.rbOstalo)
    RadioButton rbOstalo;
    @BindView(R.id.rbHigijena)
    RadioButton rbHigijena;
    @BindView(R.id.rbRezije)
    RadioButton rbRezije;
    @BindView(R.id.rbPotrepstine)
    RadioButton rbPotrepstine;
    @BindView(R.id.rbHrana)
    RadioButton rbHrana;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.etNaziv)
    EditText etNaziv;
    @BindView(R.id.etCijena)
    EditText etCijena;
    @BindView(R.id.confirmBtn)
    Button confirmBtn;
    Unbinder unbinder;

    public static UnosFragment newInstance() {
        return new UnosFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.confirmBtn)
    public void onViewClicked() {
        DataTable racun = spremi();
        if(racun != null){
            racun.save();
            listener.getData(racun);
        }
    }

    public interface UnosFragmentListener {
        void getData(DataTable racun);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unos_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (UnosFragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
        super.onAttach(context);
    }

    public DataTable spremi(){
        if( (rbHigijena.isChecked() || rbHrana.isChecked() || rbOstalo.isChecked() || rbPotrepstine.isChecked() || rbRezije.isChecked())
            && (!etCijena.getText().toString().isEmpty() && !etNaziv.getText().toString().isEmpty())){
                String kategorija;
                if(rbRezije.isChecked()){kategorija = REZIJE;}
                else if(rbPotrepstine.isChecked()){kategorija = POTREPŠTINE;}
                else if(rbOstalo.isChecked()){kategorija = OSTALO;}
                else if(rbHigijena.isChecked()){kategorija = HIGIJENA;}
                else if(rbHrana.isChecked()){kategorija = HRANA;}
                else{kategorija = "";}

                String datum = datePicker.getDayOfMonth()+"."+(datePicker.getMonth()+1)+"."+datePicker.getYear();
                double cijena = Double.parseDouble(etCijena.getText().toString());
                String naziv = etNaziv.getText().toString();
            etNaziv.setText("");
            etCijena.setText("");
            Toast.makeText(getContext(), "Unijeli ste "+naziv, Toast.LENGTH_SHORT).show();
            return new DataTable(cijena, naziv, datum, kategorija, MenuActivity.user);
        }else{
            Toast.makeText(getActivity(), "Unesite sve podatke!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


}
