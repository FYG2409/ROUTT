package com.routt.ara.routt.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Persona;
import com.routt.ara.routt.model.Viaje;
import com.routt.ara.routt.view.Ofertante.ContenedorOfertanteActivity;
import com.routt.ara.routt.view.Trailero.ContenedorTraileroActivity;
import com.routt.ara.routt.view.fragments.PerfilFragment;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class InfoViaje extends AppCompatActivity {

    private TextView tvCantidadCarga, tvCarga, tvDobleRemolque, tvFechaAproxSalida, tvNombreLugarLlegada, tvNombreLugarSalida, tvPago, tvRControl, tvTipoCaja;
    private String idViaje, correoPersona, correo, txtAgregaViajeALista, txtbtnIrAPerfilOfertante, txtbtnEliminarViajeDeLista, txtbtnCalificarOfertante, txtbtnEliminarViaje;
    private int tipoPersona;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button btnAgregaViajeALista, btnIrAPerfilOfertante, btnEliminarViajeDeLista, btnCalificarOfertante, btnEliminarViaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje);

        btnAgregaViajeALista = (Button) findViewById(R.id.btnAgregaViajeALista);
        btnIrAPerfilOfertante = (Button) findViewById(R.id.btnIrAPerfilOfertante);
        btnEliminarViajeDeLista = (Button) findViewById(R.id.btnEliminarViajeDeLista);
        btnCalificarOfertante = (Button) findViewById(R.id.btnCalificarOfertante);
        btnEliminarViaje = (Button) findViewById(R.id.btnEliminarViaje);

        tvCantidadCarga = (TextView) findViewById(R.id.cantidadCarga);
        tvCarga = (TextView) findViewById(R.id.carga);
        tvDobleRemolque = (TextView) findViewById(R.id.dobleRemolque);
        tvFechaAproxSalida = (TextView) findViewById(R.id.fechaAproxSalida);
        tvNombreLugarLlegada = (TextView) findViewById(R.id.nombreLugarLlegada);
        tvNombreLugarSalida = (TextView) findViewById(R.id.nombreLugarSalida);
        tvPago = (TextView) findViewById(R.id.pago);
        tvRControl = (TextView) findViewById(R.id.rControl);
        tvTipoCaja = (TextView) findViewById(R.id.tipoCaja);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    correo = firebaseUser.getEmail();
                    traePersona();
                    traeDatosViaje();
                }
            }
        };
        recibeDatos();
    }

    public void recibeDatos(){
        Bundle extras = getIntent().getExtras();

        idViaje = extras.getString("IdViaje");

        txtAgregaViajeALista = extras.getString("btnAgregaViajeALista");
        txtbtnIrAPerfilOfertante = extras.getString("btnIrAPerfilOfertante");
        txtbtnEliminarViajeDeLista = extras.getString("btnEliminarViajeDeLista");
        txtbtnCalificarOfertante = extras.getString("btnCalificarOfertante");
        txtbtnEliminarViaje = extras.getString("btnEliminarViaje");

        Botones();
    }

    public void traeDatosViaje(){
        databaseReference.child("Viajes").child(idViaje).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Viaje viaje = dataSnapshot.getValue(Viaje.class);

                    tvCantidadCarga.setText(Integer.toString(viaje.getCantidadCarga()));
                    tvCarga.setText(viaje.getCarga());
                    tvDobleRemolque.setText(Boolean.toString(viaje.getDobleRemolque()));
                    tvFechaAproxSalida.setText(viaje.getFechaApoxSalida());
                    tvNombreLugarLlegada.setText(viaje.getNombreLugarLlegada());
                    tvNombreLugarSalida.setText(viaje.getNombreLugarSalida());
                    tvPago.setText(Integer.toString(viaje.getPago()));
                    tvRControl.setText(Boolean.toString(viaje.getrControl()));
                    tvTipoCaja.setText(viaje.getTipoCaja());
                    // tipo = 0 ----> Trailero ; tipo = 1 ----> Ofertante
                    if(tipoPersona == 0){
                        correoPersona = viaje.getCorreoOfertante();
                    }else
                        if(tipoPersona == 1){
                            correoPersona = viaje.getCorreoTrailero();
                        }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Botones(){
        if(txtAgregaViajeALista.equals("Si")){
            btnAgregaViajeALista.setVisibility(View.VISIBLE);
        }else
        if(txtAgregaViajeALista.equals("No")){
            btnAgregaViajeALista.setVisibility(View.INVISIBLE);
        }

        if(txtbtnIrAPerfilOfertante.equals("Si")){
            btnIrAPerfilOfertante.setVisibility(View.VISIBLE);
        }else
        if(txtbtnIrAPerfilOfertante.equals("No")){
            btnIrAPerfilOfertante.setVisibility(View.INVISIBLE);
        }

        if(txtbtnEliminarViajeDeLista.equals("Si")){
            btnEliminarViajeDeLista.setVisibility(View.VISIBLE);
        }else
        if(txtbtnEliminarViajeDeLista.equals("No")){
            btnEliminarViajeDeLista.setVisibility(View.INVISIBLE);
        }

        if(txtbtnCalificarOfertante.equals("Si")){
            btnCalificarOfertante.setVisibility(View.VISIBLE);
        }else
        if(txtbtnCalificarOfertante.equals("No")){
            btnCalificarOfertante.setVisibility(View.INVISIBLE);
        }

        if(txtbtnEliminarViaje.equals("Si")){
            btnEliminarViaje.setVisibility(View.VISIBLE);
        }else
        if(txtbtnEliminarViaje.equals("No")){
            btnEliminarViaje.setVisibility(View.INVISIBLE);
        }
    }

    public void agregandoViajeAMiLista(View view){
        databaseReference.child("Viajes").child(idViaje).child("disponible").setValue(false);
        databaseReference.child("Viajes").child(idViaje).child("correoTrailero").setValue(correo);
        Toast.makeText(this, "Has agregado un viaje a tu lista", Toast.LENGTH_SHORT).show();
        btnAgregaViajeALista.setVisibility(View.INVISIBLE);
    }

    public void eliminandoViajeDeMiLista(View view){
        databaseReference.child("Viajes").child(idViaje).child("disponible").setValue(true);
        databaseReference.child("Viajes").child(idViaje).child("correoTrailero").setValue("");
        Toast.makeText(this, "Has dejado el viaje", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ContenedorTraileroActivity.class);
        startActivity(intent);
    }

    public void yendoAPerfilDeLaPersona(View view){
        Intent intent = new Intent(this, PerfilActivity.class);
        intent.putExtra("correoOfertante", correoPersona);
        startActivity(intent);
    }

    public void calificandoPersona(View view){
        Intent intent = new Intent(this, CalificarPersonaActivity.class);
        intent.putExtra("correoPersona", correoPersona);
        intent.putExtra("correo", correo);
        intent.putExtra("idViaje", idViaje);
        intent.putExtra("tipoPersona", tipoPersona);
        startActivity(intent);
    }

    public void eliminandoViaje(View view){
        databaseReference.child("Viajes").child(idViaje).removeValue();
        Toast.makeText(this, "El viaje fue eliminado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ContenedorOfertanteActivity.class);
        startActivity(intent);
    }

    public void traePersona(){
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        if(correo.equals(persona.getCorreo())){
                            tipoPersona = persona.getTipo();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("InfoViaje", "ERROR AL ENCONTRAR DATOS DE USUARIO: " + databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

}
