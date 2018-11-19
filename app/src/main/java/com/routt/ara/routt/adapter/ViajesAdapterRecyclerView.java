package com.routt.ara.routt.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Viaje;

import java.util.ArrayList;

public class ViajesAdapterRecyclerView extends RecyclerView.Adapter<ViajesAdapterRecyclerView.PictureViewHolder>
implements View.OnClickListener{

    private ArrayList<Viaje> viajes;
    private View.OnClickListener listener;
    private int resource;
    private Activity activity;
    private ArrayList<String> colores;

    public ViajesAdapterRecyclerView(ArrayList<Viaje> viajes, int resource, Activity activity, ArrayList<String> colores) {
        this.viajes = viajes;
        this.resource = resource;
        this.activity = activity;
        this.colores = colores;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        view.setOnClickListener(this);
        //aqui

        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Viaje viaje = viajes.get(position);
        holder.nombreLugarCard.setText(viaje.getNombreLugarLlegada());
        holder.fechaCard.setText(viaje.getFechaApoxSalida());
        holder.pagoCard.setText(Integer.toString(viaje.getPago()));
        holder.cardView.setBackgroundColor(Color.parseColor(colores.get(position)));
    }

    @Override
    public int getItemCount() {
        return viajes.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }



    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        private TextView nombreLugarCard;
        private TextView fechaCard;
        private TextView pagoCard;
        private CardView cardView;

        public PictureViewHolder(View itemView) {
            super(itemView);
            nombreLugarCard = (TextView) itemView.findViewById(R.id.nombreLugarCard);
            fechaCard = (TextView) itemView.findViewById(R.id.fechaCard);
            pagoCard = (TextView) itemView.findViewById(R.id.pagoCard);
            cardView = (CardView) itemView.findViewById(R.id.pictureCard);
        }
    }

}
