package com.routt.ara.routt.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Persona;

public class PerfilActivity extends AppCompatActivity {

    private String correoPersona;

    private DatabaseReference databaseReference;
    private TextView nombre, apellidos, edad, noCaraFeliz, noCaraTriste, noCaraEnojada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombre = (TextView) findViewById(R.id.nombre);
        apellidos = (TextView) findViewById(R.id.apellidos);
        edad = (TextView) findViewById(R.id.edad);
        noCaraFeliz = (TextView) findViewById(R.id.noCaraFeliz);
        noCaraTriste = (TextView) findViewById(R.id.noCaraTriste);
        noCaraEnojada = (TextView) findViewById(R.id.noCaraEnojada);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recibeDatos();
        traePersona();
    }

    public void recibeDatos(){
        Bundle extras = getIntent().getExtras();
        correoPersona = extras.getString("correoOfertante");
    }

    public void traePersona(){
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        if(correoPersona.equals(persona.getCorreo())){
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

}
