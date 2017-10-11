package com.example.matija.myapplication.Fragments.RasporedFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matija.myapplication.Activity.MenuActivity;
import com.example.matija.myapplication.Database.UsersTable;
import com.example.matija.myapplication.Database.RasporedPart;
import com.example.matija.myapplication.Events.SaveEvent;
import com.example.matija.myapplication.R;
import com.example.matija.myapplication.Views.RasporedView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.regex.*;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RasporedFragment extends Fragment {
    int height,saveHeight, width, color;
    boolean heightbool = false;
    ArrayList<LinearLayout> holder;
    View converterHolder;
    ArrayList<View> convertHolderList;
    public boolean wandOn, hammerOn;
    UsersTable user;

    @BindView(R.id.raspored_size_reference)
    TextView rasporedSizeReference;
    @BindView(R.id.ponedjeljak_raspored)
    LinearLayout ponedjeljakRaspored;
    @BindView(R.id.utorak_raspored)
    LinearLayout utorakRaspored;
    @BindView(R.id.srijeda_raspored)
    LinearLayout srijedaRaspored;
    @BindView(R.id.cetvrtak_raspored)
    LinearLayout cetvrtakRaspored;
    @BindView(R.id.petak_raspored)
    LinearLayout petakRaspored;
    @BindView(R.id.subota_raspored)
    LinearLayout subotaRaspored;
    @BindView(R.id.nedjelja_raspored)
    LinearLayout nedjeljaRaspored;
    @BindView(R.id.day_holder)
    LinearLayout timeHolder;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @BindView(R.id.data_holder_main)
    LinearLayout dataHolderMain;

    Unbinder unbinder;


    public static RasporedFragment newInstance(UsersTable user) {
        if(user !=null){
            Bundle args = new Bundle();
            args.putSerializable("user", user);
            RasporedFragment rasporedFragment = new RasporedFragment();
            rasporedFragment.setArguments(args);
            return  rasporedFragment;
        }
        return new RasporedFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspored_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        holder = new ArrayList<>();
        convertHolderList = new ArrayList<>();
        setHasOptionsMenu(true);
        wandOn = false;
        hammerOn = false;
        color = R.color.colorPrimary;

        holder.add(ponedjeljakRaspored);
        holder.add(utorakRaspored);
        holder.add(srijedaRaspored);
        holder.add(cetvrtakRaspored);
        holder.add(petakRaspored);
        holder.add(subotaRaspored);
        holder.add(nedjeljaRaspored);

        //Dohvati usera
        Bundle args = getArguments();
        if(args != null){
            user = (UsersTable) args.getSerializable("user");
        }

        for (int x = 1; x < linearLayout5.getChildCount() - 2; x++) {
            linearLayout5.getChildAt(x).setPadding(1, 1, 1, 1);
        }

        //ako user nema spremljen raspored
        if(user.getRegex() == null || user.getRegex().equals("")){
            newState();

            //ako ima raspored
        }else{
            restoreSavedState();
        }

        return view;

    }

    public void onCellClickMagic(View view) {
        //ako nije stisnut niti jedan
        if (converterHolder == null) {
            view.setBackgroundColor(color);
            converterHolder = view;
            convertHolderList.add(view);

        }
        //ako vec ima jedan
        else{

            //Ako imaju istog parenta
            if (view.getParent().equals(converterHolder.getParent())) {
                if(!convertHolderList.isEmpty()){
                    converterHolder = convertHolderList.get(0);
                }

                //Ako nije 2 puta kliknuto na isti
                if (!view.equals(converterHolder) && !findInList(view)) {
                    LinearLayout parent = (LinearLayout) view.getParent();
                    int lastchildPosition = getChildPosition(view);
                    int firstchildPosition = getChildPosition(converterHolder);

                    //ako je prvi manji
                    if (firstchildPosition < lastchildPosition) {

                        for (int x = firstchildPosition; x <= lastchildPosition; x++) {
                            View child = parent.getChildAt(x);
                            if (!convertHolderList.contains(child)) {
                                convertHolderList.add(child);
                                child.setBackgroundColor(color);
                            }
                        }

                    }
                    //ako je drugi manji
                    else {
                        for (int x = firstchildPosition; x >= lastchildPosition; x--) {
                            View child = parent.getChildAt(x);
                            if (!convertHolderList.contains(child)) {
                                Collections.reverse(convertHolderList);
                                convertHolderList.add(child);
                                Collections.reverse(convertHolderList);
                                child.setBackgroundColor(color);
                            }
                        }
                    }
                }


                //Ako je kliknuto 2 puta na isto
                else if(view.equals(converterHolder)){
                    view.setBackgroundResource(R.drawable.frame_up_left);
                    converterHolder = null;
                    clearConvertHolder();
                }

                //ako je kliknuto na obiljezeni
                else{
                    if(!view.equals(convertHolderList.get(convertHolderList.size()-1))){
                        int pos = 0;
                        for(int j = 0; j < convertHolderList.size(); j++){
                            if(view.equals(convertHolderList.get(j))){
                                pos = j+1;
                                break;
                            }
                        }
                        for(int k = pos; k < convertHolderList.size(); k++){
                            convertHolderList.get(k).setBackgroundResource(R.drawable.frame_up_left);
                        }
                        do{
                            convertHolderList.remove(pos);
                        }while(convertHolderList.size() != pos);
                    }
                }
            }
            //Ako nemaju istog parenta
            else {
                clearConvertHolder();
                converterHolder.setBackgroundResource(R.drawable.frame_up_left);
                view.setBackgroundColor(color);
                converterHolder = view;
            }

        }


    }

    //Vraca true ako se view nalazi u listi
    public boolean findInList(View view){
        for(int i = 0; i < convertHolderList.size(); i++){
            if(convertHolderList.get(i).equals(view)){
                return true;
            }
        }
        return false;
    }

    //Cisti holder koji sadrzi viewove nakon merganja cella
    public void clearConvertHolder(){
        for(int i = 0; i < convertHolderList.size(); i++){
            convertHolderList.get(i).setBackgroundResource(R.drawable.frame_up_left);
        }
        convertHolderList.clear();
    }

    //Pomocni korak koji sluzi za oznacavanje i dodavanje viewa u holder (ConvertHolder)
    //poziva -> makeSingleCell()
    public void mergeCells() {
        if (convertHolderList.size() > 1) {
            LinearLayout parent = (LinearLayout) converterHolder.getParent();
            int first = getChildPosition(convertHolderList.get(0));
            int second = getChildPosition(convertHolderList.get(convertHolderList.size() - 1));

            //ako je prvi manji od drugog
            if (first < second) {
                makeSingleCell(parent, first, second);
            } else {
                //ako je drugi manji od prvog
                makeSingleCell(parent, second, first);
            }


        }


    }

    //spaja viewove koji se nalaze u holderu(ConvertHolder)
    public void makeSingleCell(LinearLayout parent, int first, int second) {
        int c = 0;
        for (int i = 0; i < convertHolderList.size(); i++) {
            parent.removeView(convertHolderList.get(i));
            c++;
        }
        int day = 0;
        if(parent.getId() == R.id.ponedjeljak_raspored){        day = 0;
        }else if(parent.getId() == R.id.utorak_raspored){       day = 1;
        }else if(parent.getId() == R.id.srijeda_raspored){      day = 2;
        }else if(parent.getId() == R.id.cetvrtak_raspored){     day = 3;
        }else if(parent.getId() == R.id.petak_raspored){        day = 4;
        }else if(parent.getId() == R.id.subota_raspored){       day = 5;
        }else if(parent.getId() == R.id.nedjelja_raspored){     day = 6;
        }

        height = converterHolder.getHeight();
        if(!heightbool){
            saveHeight = height;
        }
        final RasporedView newPart = new RasporedView(getContext(), day, second-first+1);
        newPart.setWidth(converterHolder.getWidth());
        newPart.setHeight(height * (second - first + 1));
        newPart.setBackgroundResource(R.drawable.frame_up_left);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newPart.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }


        newPart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongClickMagic(newPart);
                return false;
            }
        });

        newPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(hammerOn){
                    deleteMerged(view);
                }
            }
        });
        parent.addView(newPart, first);
        converterHolder = null;
        convertHolderList.clear();
    }

    //razdvaja spojeni cell
    public void deleteMerged(final View view){
        final RasporedView selectedView = (RasporedView)view;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        int size = selectedView.getSize();
                        int day = selectedView.getDay();
                        int position = selectedView.getPosition();

                        holder.get(day).removeView(selectedView);
                        for(int i = 0; i < size; i++){
                            RasporedView text = new RasporedView(getActivity(),day,1);
                            text.setGravity(Gravity.CENTER);
                            text.setBackgroundResource(R.drawable.frame_up_left);
                            text.setPadding(1, 1, 1, 1);
                            holder.get(day).addView(text,position);
                            text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (wandOn) {
                                        onCellClickMagic(view);
                                    }

                                }
                            });

                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()){};
        builder.setMessage("Jeste li sigurni da zelite obrisati "+selectedView.getRasporedPart().getTitle() + "?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();

    }

    public void onLongClickMagic(RasporedView newPart){

        if(newPart.getRasporedPart() == null){
            RasporedPart part = new RasporedPart(Color.BLACK,Color.WHITE, "");
            part.save();
            newPart.setRasporedPart(part);
        }

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.rasporedHolder, RasporedPartFragment.newInstance(newPart))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recycle, menu);
        inflater.inflate(R.menu.hammer, menu);
        inflater.inflate(R.menu.magic, menu);
        inflater.inflate(R.menu.add, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hammer:
                if(hammerOn){
                    item.setIcon(R.drawable.hammer_off);
                    hammerOn = false;

                }else{
                    item.setIcon(R.drawable.hammer_on);
                    hammerOn = true;
                    wandOn = false;

                }
                break;
            case R.id.addRasporedPart:
                mergeCells();
                break;
            case R.id.magicWand:
                if(wandOn){
                    item.setIcon(R.drawable.magic_wand_off);
                    wandOn = false;
                    clearConvertHolder();
                }else{
                    item.setIcon(R.drawable.magic_wand_on);
                    wandOn = true;
                }
                break;
            case R.id.recycle:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                user.setRegex("");
                                Intent intent = new Intent(getContext(), MenuActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);
                                intent.putExtra("bundle",bundle);
                                intent.setFlags(12345);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()){};
                builder.setMessage("Jeste li sigurni da zelite obrisati raspored?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public int getChildPosition(View child) {
        for (int i = 0; i < holder.size(); i++) {
            for (int j = 0; j < holder.get(i).getChildCount(); j++) {
                if (holder.get(i).getChildAt(j).equals(child)) {
                    return j;
                }
            }
        }
        return 0;
    }

    public void saveState(){
        String regexSaveString = "";
        for(int i = 0; i < holder.size(); i++){
            for(int j = 0; j < holder.get(i).getChildCount(); j++) {
                RasporedView x = (RasporedView) holder.get(i).getChildAt(j);
               if(x.getRasporedPart() == null){
                   regexSaveString += x.getDay() + ":" + x.getSize() + ":" + x.getRasporedPartId() + ":x" + ":x"+ ":x"+";";
               }else{
                   regexSaveString += x.getDay() + ":" + x.getSize() + ":" + x.getRasporedPartId() + ":" + x.getRasporedPart().getTitle() + ":" + x.getRasporedPart().getBackroundColor() + ":"+ x.getRasporedPart().getFontColor() + ";";

               }



            }
        }

        if(saveHeight != 0){
            user.setPartHeight(saveHeight);
        }
        user.setRegex(regexSaveString);
        EventBus.getDefault().post(new SaveEvent(regexSaveString));
        user.save();

    }

    public void newState(){
        for (int i = 0; i < holder.size(); i++) {
            for (int j = 0; j < 48; j++) {
                RasporedView text = new RasporedView(getActivity(),i,1);
                text.setGravity(Gravity.CENTER);
                text.setBackgroundResource(R.drawable.frame_up_left);
                text.setPadding(1, 1, 1, 1);
                holder.get(i).addView(text);

                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wandOn) {
                            onCellClickMagic(view);
                        }

                    }
                });



            }
        }
    }

    public void restoreSavedState(){
        int partHeight = user.getPartHeight();
        String regex = user.getRegex();
        Pattern p1 = Pattern.compile(";");
        String a[] = p1.split(regex);
        for(int i = 0; i < a.length; i++){
            Pattern p2 = Pattern.compile(":");
            String b[] = p2.split(a[i]);

            int day = Integer.valueOf(b[0]);
            int size = Integer.valueOf(b[1]);
            int id = Integer.valueOf(b[2]);

            String txt = b[3];
            String backgroundColor = b[4];
            String textColor = b[5];

            final RasporedView rasporedView = new RasporedView(getActivity(),day,size);
            rasporedView.setGravity(Gravity.CENTER);


            rasporedView.setBackgroundResource(R.drawable.frame_up_left);
            rasporedView.setPadding(1, 1, 1, 1);
            holder.get(day).addView(rasporedView);

            if(size > 1){
                RasporedPart rasporedPart = new RasporedPart();
                if(!textColor.equals("x") && !backgroundColor.equals("x")){
                    rasporedPart.setFontColor(Integer.parseInt(textColor));
                    rasporedPart.setBackroundColor(Integer.parseInt(backgroundColor));
                    rasporedPart.setTitle(txt);
                }else{
                    rasporedView.setBackgroundResource(R.drawable.frame_up_left);
                    rasporedPart.setBackroundColor(Color.WHITE);
                    rasporedPart.setFontColor(Color.BLACK);
                    rasporedPart.setTitle("");
                }

                rasporedView.setHeight(partHeight * size);
                rasporedView.setRasporedPart(rasporedPart);
                rasporedView.setView();

                rasporedView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onLongClickMagic(rasporedView);
                        return false;
                    }
                });
                rasporedView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(hammerOn){
                            deleteMerged(view);
                        }

                    }
                });
            }else{
                rasporedView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (wandOn) {
                            onCellClickMagic(view);
                        }

                    }
                });
            }

        }

    }

    @Override
    public void onDetach() {
        saveState();
        super.onDetach();
    }


}
