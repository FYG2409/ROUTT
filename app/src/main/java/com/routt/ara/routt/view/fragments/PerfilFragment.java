package com.routt.ara.routt.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.MainActivity;
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Persona;
import com.routt.ara.routt.view.Trailero.ContenedorTraileroActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private TextView nombre, apellidos, edad, noCaraFeliz, noCaraTriste, noCaraEnojada;
    private String correo;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        nombre = (TextView) v.findViewById(R.id.nombre);
        apellidos = (TextView) v.findViewById(R.id.apellidos);
        edad = (TextView) v.findViewById(R.id.edad);
        noCaraFeliz = (TextView) v.findViewById(R.id.noCaraFeliz);
        noCaraTriste = (TextView) v.findViewById(R.id.noCaraTriste);
        noCaraEnojada = (TextView) v.findViewById(R.id.noCaraEnojada);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    correo = firebaseUser.getEmail();
                    TraePersona();
                }
            }
        };

        return v;
    }

    public void TraePersona(){
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        if(correo.equals(persona.getCorreo())){
                            nombre.setText(persona.getNombre());
                            apellidos.setText(persona.getApePaat() +" "+ persona.getApeMat());
                            edad.setText(persona.getEdad() + " Años");
                            noCaraFeliz.setText(Integer.toString(persona.getNoHappyFace()));
                            noCaraTriste.setText(Integer.toString(persona.getNoSosoFace()));
                            noCaraEnojada.setText(Integer.toString(persona.getNoAngryFace()));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("PerfilFragment", "ERROR AL ENCONTRAR DATOS DE USUARIO: " + databaseError.getMessage());
            }
        });
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
