package com.example.matija.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;

import com.example.matija.myapplication.Database.DataTable;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matija.myapplication.Events.DeleteEvent;
import com.example.matija.myapplication.Events.SendUkupnoEvent;
import com.example.matija.myapplication.Events.setMainList;
import com.example.matija.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by matija on 07.08.17..
 */


public class RacunAdapter extends RecyclerView.Adapter<RacunAdapter.ViewHolder> {
    List<DataTable> podaci;
    Context context;
    public static double ukupno;
    List<DataTable> deleteList;
    int background;
    ViewHolder holder;


    public RacunAdapter(List<DataTable> lista, Context context) {
        this.podaci = lista;
        this.context = context;
        EventBus.getDefault().register(this);
    }


    private void popOnDelete(DataTable deletedItem){
        for(int i = 0; i < podaci.size(); i++){
            if(podaci.get(i).equals(deletedItem)){
                podaci.remove(i);
                break;
            }
        }
        EventBus.getDefault().post(new SendUkupnoEvent(getListUkupno(podaci)));
        notifyDataSetChanged();
    }

    public void deleteData(){
        EventBus.getDefault().post(new setMainList(deleteList));
        for(int i = 0; i < deleteList.size(); i++){
            holder.rv.setBackgroundColor(background);
            popOnDelete(deleteList.get(i));
        }
        for(int i = 0; i < deleteList.size(); i++){
            deleteList.get(i).delete();
        }
        notifyDataSetChanged();
        deleteList.clear();
    }
    @Subscribe
    public void onDeleteData(DeleteEvent e){
        if(deleteList.size() == 0){
            Toast.makeText(context, "Odaberite stavke za brisanje", Toast.LENGTH_SHORT).show();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Jesi li sigurni da zelite obrisati " + deleteList.size() + " unos(a)?")
                    .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //Obrisi podatak
                            deleteData();
                        }
                    })
                    .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //DO NOTHING
                        }
                    }).show();

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    holder.rv.setBackgroundColor(background);
                }
            });
            Toast.makeText(context, String.valueOf(deleteList.size()), Toast.LENGTH_SHORT).show();
        }
    }

    public void setBackgroundColor(int color){

    }
    public void swapData(List<DataTable> newData){
        podaci.clear();
        podaci.addAll(newData);
        notifyDataSetChanged();
        EventBus.getDefault().post(new SendUkupnoEvent(getListUkupno(newData)));

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.rv.setBackgroundColor(background);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        deleteList = new ArrayList<>();
        ukupno = 0;
        background = Color.WHITE;
        EventBus.getDefault().post(new SendUkupnoEvent(getListUkupno(podaci)));
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View RacunView = inflater.inflate(R.layout.rv_holder, parent, false);
        ViewHolder holder = new ViewHolder(RacunView);

        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DataTable racun = podaci.get(position);

        this.holder = holder;

        holder.kategorija.setText(String.valueOf(racun.getKategorija()));
        holder.cijena.setText(String.valueOf(racun.getCijena()));
        holder.datum.setText(String.valueOf(racun.getDatum()));
        holder.naziv.setText(String.valueOf(racun.getNaziv()));
        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!deleteList.contains(racun)){
                    holder.rv.setBackgroundColor(Color.rgb(226,227,100));
                    deleteList.add(racun);
                }else{
                    holder.rv.setBackgroundColor(background);
                    deleteList.remove(racun);
                }
            }
        });




        holder.rv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.rv.setBackgroundColor(Color.rgb(226,227,152));



                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return podaci.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView naziv, kategorija, datum, cijena;
        LinearLayout rv;


        public ViewHolder(View itemView) {
            super(itemView);
            naziv = itemView.findViewById(R.id.tvNaziv);
            kategorija = itemView.findViewById(R.id.tvKategorija);
            datum = itemView.findViewById(R.id.tvDatum);
            cijena = itemView.findViewById(R.id.tvCijena);
            rv = itemView.findViewById(R.id.rvHolder);
        }
    }

    public double getUkupno(){
        return ukupno;
    }

    public float getListUkupno(List<DataTable> list){
        float ukupno = 0;
        for(int i = 0; i < list.size() ; i++){
            ukupno += (float)list.get(i).getCijena();
        }
        return ukupno;
    }
}
