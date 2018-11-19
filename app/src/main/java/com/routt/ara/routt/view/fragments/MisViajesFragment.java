package com.routt.ara.routt.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.R;
import com.routt.ara.routt.adapter.ViajesAdapterRecyclerView;
import com.routt.ara.routt.model.Viaje;
import com.routt.ara.routt.view.InfoViaje;
import com.routt.ara.routt.view.Ofertante.ContenedorOfertanteActivity;
import com.routt.ara.routt.view.Ofertante.CreaViajeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisViajesFragment extends Fragment {

    private FloatingActionButton btnAgregaViaje;

    private DatabaseReference databaseReference;
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private RecyclerView viajesRecycler;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private String correo;

    private ArrayList<String> colores = new ArrayList<>();

    public MisViajesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mis_viajes, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        viajesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    correo = firebaseUser.getEmail();
                    traeMisViajesOfertante();
                }
            }
        };

        btnAgregaViaje = view.findViewById(R.id.btnAgregaViaje);
        btnAgregaViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreaViajeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void traeMisViajesOfertante(){
        databaseReference.child("Viajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viajes.clear();
                colores.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Viaje viaje = snapshot.getValue(Viaje.class);
                        if((viaje.getCorreoOfertante()).equals(correo)){
                            if(viaje.getCalificoOfertante()){
                                //Este ofertante ya califico el viaje
                            }else {
                                viajes.add(viaje);
                                //agregando colores
                                colores.add("#262626");
                            }
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
                intent.putExtra("cantidadCarga", Integer.toString(viajes.get(viajesRecycler.getChildAdapterPosition(view)).getCantidadCarga()));
                intent.putExtra("carga", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getCarga());
                intent.putExtra("dobleRemolque", Boolean.toString(viajes.get(viajesRecycler.getChildAdapterPosition(view)).getDobleRemolque()));
                intent.putExtra("fechaAproxSalida", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getFechaApoxSalida());
                intent.putExtra("nombreLugarLlegada", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getNombreLugarLlegada());
                intent.putExtra("nombreLugarSalida", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getNombreLugarSalida());
                intent.putExtra("pago", Integer.toString(viajes.get(viajesRecycler.getChildAdapterPosition(view)).getPago()));
                intent.putExtra("rControl", Boolean.toString(viajes.get(viajesRecycler.getChildAdapterPosition(view)).getrControl()));
                intent.putExtra("tipoCaja", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getTipoCaja());

                intent.putExtra("IdViaje", viajes.get(viajesRecycler.getChildAdapterPosition(view)).getIdViaje());
                intent.putExtra("btnVisible", "No");
                startActivity(intent);
            }
        });
        viajesRecycler.setAdapter(viajesAdapterRecyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
