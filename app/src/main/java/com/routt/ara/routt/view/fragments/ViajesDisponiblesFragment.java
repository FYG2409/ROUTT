package com.routt.ara.routt.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.R;
import com.routt.ara.routt.adapter.ViajesAdapterRecyclerView;
import com.routt.ara.routt.model.Viaje;
import com.routt.ara.routt.view.InfoViaje;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViajesDisponiblesFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ArrayList<String> colores = new ArrayList<>();
    private RecyclerView viajesRecycler;

    public ViajesDisponiblesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_viajes_disponibles, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        traeViajesDisponibles();
        viajesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        return view;
    }

    public void traeViajesDisponibles(){
        databaseReference.child("Viajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viajes.clear();
                colores.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Viaje viaje = snapshot.getValue(Viaje.class);
                        if(viaje.getDisponible()){
                            viajes.add(viaje);
                            //agregando colores
                            colores.add("#262626");
                        }
                    }
                    imprimirTarjetas();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void imprimirTarjetas(){
        //Imprimiendo las cardView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viajesRecycler.setLayoutManager(linearLayoutManager);
        ViajesAdapterRecyclerView viajesAdapterRecyclerView = new ViajesAdapterRecyclerView(viajes, R.layout.card_view_viaje, getActivity(), colores);

        viajesAdapterRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoViaje.class);

                intent.putExtra("IdViaje", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getIdViaje());

                intent.putExtra("btnAgregaViajeALista", "Si");
                intent.putExtra("btnIrAPerfilOfertante", "Si");
                intent.putExtra("btnEliminarViajeDeLista", "No");
                intent.putExtra("btnCalificarOfertante", "No");

                startActivity(intent);
            }
        });

        viajesRecycler.setAdapter(viajesAdapterRecyclerView);
    }

}
