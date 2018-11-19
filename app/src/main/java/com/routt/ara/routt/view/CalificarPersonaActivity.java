package com.routt.ara.routt.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Persona;
import com.routt.ara.routt.view.Ofertante.ContenedorOfertanteActivity;
import com.routt.ara.routt.view.Trailero.ContenedorTraileroActivity;

public class CalificarPersonaActivity extends AppCompatActivity {

    private String correo, correoPersona, idPersona, idViaje;
    private DatabaseReference databaseReference;
    private int noFeliz, noTriste, noEnojado, tipoPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_persona);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recibeDatos();
        traePersona();
    }

    public void recibeDatos(){
        Bundle extras = getIntent().getExtras();
        correoPersona = extras.getString("correoPersona");
        correo = extras.getString("correo");
        idViaje = extras.getString("idViaje");
        tipoPersona = extras.getInt("tipoPersona");
    }

    public void traePersona(){
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        if(correoPersona.equals(persona.getCorreo())){
                            noFeliz = persona.getNoHappyFace();
                            noTriste = persona.getNoSosoFace();
                            noEnojado = persona.getNoAngryFace();
                            idPersona = snapshot.getKey();
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

    public void EliminaViaje(){
        // tipo = 0 ----> Trailero ; tipo = 1 ----> Ofertante
        if(tipoPersona == 0){
            databaseReference.child("Viajes").child(idViaje).child("calificoTrailero").setValue(true);
            Intent intent = new Intent(this, ContenedorTraileroActivity.class);
            startActivity(intent);
        }else
            if(tipoPersona == 1){
                databaseReference.child("Viajes").child(idViaje).child("calificoOfertante").setValue(true);
                Intent intent = new Intent(this, ContenedorOfertanteActivity.class);
                startActivity(intent);
            }
    }

    public void SumaCaraFeliz(View view){
        databaseReference.child("Personas").child(idPersona).child("noHappyFace").setValue(noFeliz+1);
        Toast.makeText(this, "Gracias por constribuir con nosotros", Toast.LENGTH_SHORT).show();
        EliminaViaje();
    }

    public void SumaCaraTriste(View view){
        databaseReference.child("Personas").child(idPersona).child("noSosoFace").setValue(noTriste+1);
        Toast.makeText(this, "Gracias por constribuir con nosotros", Toast.LENGTH_SHORT).show();
        EliminaViaje();
    }

    public void SumaCaraEnojada(View view){
        databaseReference.child("Personas").child(idPersona).child("noAngryFace").setValue(noEnojado+1);
        Toast.makeText(this, "Gracias por constribuir con nosotros", Toast.LENGTH_SHORT).show();
        EliminaViaje();
    }

}
