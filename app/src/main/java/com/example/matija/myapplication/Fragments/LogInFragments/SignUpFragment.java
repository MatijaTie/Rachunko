package com.example.matija.myapplication.Fragments.LogInFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by matija on 07.08.17..
 */

public class SignUpFragment extends Fragment {

    @BindView(R.id.registerPass)
    EditText registerPass;
    @BindView(R.id.registerName)
    EditText registerName;
    @BindView(R.id.registerBtn)
    Button registerBtn;
    Unbinder unbinder;

    SignUpFragmentListener listener;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    public interface SignUpFragmentListener{
        void passUser(UsersTable user);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (SignUpFragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
        super.onAttach(context);
    }

    @OnClick(R.id.registerBtn)
    public void onViewClicked() {
        if(!registerName.getText().toString().isEmpty() && !registerPass.getText().toString().isEmpty()){
            List<UsersTable> list = SQLite.select().from(UsersTable.class).queryList();
            Boolean exists = false;

            String ime = registerName.getText().toString();
            String pass = registerPass.getText().toString();

            for(int i = 0; i < list.size(); i++){
                if(list.get(i).getName().equals(ime)){
                    exists = true;
                }
            }

            if(exists){
                Toast.makeText(getActivity(), "Korisnik već postoji!", Toast.LENGTH_SHORT).show();

            }else{
                UsersTable user = new UsersTable();
                user.setName(ime);
                user.setPassword(pass);
                user.save();

                Toast.makeText(getActivity(), "Profil je napravljen!", Toast.LENGTH_SHORT).show();
                listener.passUser(user);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                LogInFragment logInFragment = LogInFragment.newInstance();
                manager.popBackStack();
                ft.add(R.id.fragment_holder_start_screen, logInFragment);
                ft.commit();

            }
        }else{
            Toast.makeText(getActivity(), "Unesite sve podatke!", Toast.LENGTH_SHORT).show();
        }
    }
}
