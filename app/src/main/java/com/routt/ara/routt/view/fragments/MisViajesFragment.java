package com.routt.ara.routt.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    private String correo, colorRojo, colorVerde, colorAmarillo, colorGris;

    private Date fechaViaje, fechaActual;

    private ArrayList<String> colores = new ArrayList<>();
    private ArrayList<Boolean> disponible = new ArrayList<>();

    public MisViajesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mis_viajes, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        viajesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        colorRojo = "#BD2A00";
        colorVerde = "#93BD00";
        colorAmarillo = "#E7E300";
        colorGris = "#262626";

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
                disponible.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Viaje viaje = snapshot.getValue(Viaje.class);
                        if((viaje.getCorreoOfertante()).equals(correo)){
                            if(viaje.getCalificoOfertante()){
                                //Este ofertante ya califico el viaje
                            }else {
                                viajes.add(viaje);
                                //agregar colores
                                String fechaV = viaje.getFechaApoxSalida();
                                Boolean disponibleV = viaje.getDisponible();
                                disponible.add(disponibleV);
                                String color = traeColor(fechaV, disponibleV);
                                colores.add(color);
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

    public String traeColor(String fechaV, Boolean disponible){
        String color;

        Calendar cal = Calendar.getInstance();
        String cadenaFecha = ((cal.get(Calendar.DAY_OF_MONTH))-1) + "/" + ((cal.get(Calendar.MONTH))+1) + "/" + (cal.get(Calendar.YEAR));
        try{
            fechaActual = new SimpleDateFormat("dd/MM/yyyy").parse(cadenaFecha);
        }catch(Exception e){
            Log.w("AgendaViajes", "Error al parsear fecha actual " + e.getMessage());
        }

        int diaV = Integer.parseInt(String.valueOf(fechaV.charAt(0)) + String.valueOf(fechaV.charAt(1)));
        int mesV = Integer.parseInt(String.valueOf(fechaV.charAt(3)) + String.valueOf(fechaV.charAt(4)));
        int añoV = Integer.parseInt(String.valueOf(fechaV.charAt(6)) + String.valueOf(fechaV.charAt(7)) + String.valueOf(fechaV.charAt(8)) + String.valueOf(fechaV.charAt(9)));

        String cadenaFechaViaje = diaV + "/" + mesV + "/" + añoV;
        try{
            fechaViaje = new SimpleDateFormat("dd/MM/yyyy").parse(cadenaFechaViaje);
        }catch(Exception e){
            Log.w("AgendaViajes", "Error al parsear fecha de viaje " + e.getMessage());
        }

        if((Boolean.toString(disponible)).equals("false")){
        //Ya tiene trailero
            if(fechaActual.after(fechaViaje)){
                //Ya se esta llevando a cabo
                color = colorRojo;
            }else{
                //Aun no se esta llevando a cabo
                color = colorAmarillo;
            }
        }else{
        //No tiene trailero
            if(fechaActual.after(fechaViaje)){
                //Ya se esta llevando a cabo
                color = colorGris;
            }else{
                //Aun no se esta llevando a cabo
                color = colorVerde;
            }
        }

        return color;
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

                intent.putExtra("btnAgregaViajeALista", "No");
                intent.putExtra("btnEliminarViajeDeLista", "No");

                String color = traeColor(viajes.get(viajesRecycler.getChildAdapterPosition(view)).getFechaApoxSalida(), disponible.get(viajesRecycler.getChildAdapterPosition(view)));
                if(color.equals(colorVerde)){
                    intent.putExtra("btnIrAPerfilOfertante", "No");
                    intent.putExtra("btnEliminarViaje", "Si");
                    intent.putExtra("btnCalificarOfertante", "No");
                }else
                    if(color.equals(colorRojo)){
                        intent.putExtra("btnIrAPerfilOfertante", "Si");
                        intent.putExtra("btnEliminarViaje", "No");
                        intent.putExtra("btnCalificarOfertante", "Si");
                    }else
                        if(color.equals(colorAmarillo)){
                            intent.putExtra("btnIrAPerfilOfertante", "Si");
                            intent.putExtra("btnEliminarViaje", "Si");
                            intent.putExtra("btnCalificarOfertante", "No");
                        }else
                            if(color.equals(colorGris)){
                                intent.putExtra("btnIrAPerfilOfertante", "No");
                                intent.putExtra("btnEliminarViaje", "Si");
                                intent.putExtra("btnCalificarOfertante", "No");
                            }

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
