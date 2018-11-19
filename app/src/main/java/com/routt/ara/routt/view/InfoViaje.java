package com.routt.ara.routt.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class InfoViaje extends AppCompatActivity {

    private TextView tvCantidadCarga, tvCarga, tvDobleRemolque, tvFechaAproxSalida, tvNombreLugarLlegada, tvNombreLugarSalida, tvPago, tvRControl, tvTipoCaja;
    private String idViaje, correo, btnVisible;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button btnAgregaViajeALista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje);

        btnAgregaViajeALista = (Button) findViewById(R.id.btnAgregaViajeALista);
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
                }
            }
        };

        recibeDatos();
        if(btnVisible.equals("Si")){
            btnAgregaViajeALista.setVisibility(View.VISIBLE);
        }else
            if(btnVisible.equals("No")){
                btnAgregaViajeALista.setVisibility(View.INVISIBLE);
            }
    }

    public void recibeDatos(){

        Bundle extras = getIntent().getExtras();

        String cantidadCarga = extras.getString("cantidadCarga");
        String carga = extras.getString("carga");
        String dobleRemolque = extras.getString("dobleRemolque");
        String fechaAproxSalida = extras.getString("fechaAproxSalida");
        String nombreLugarLlegada = extras.getString("nombreLugarLlegada");
        String nombreLugarSalida = extras.getString("nombreLugarSalida");
        String pago = extras.getString("pago");
        String rControl = extras.getString("rControl");
        String tipoCaja = extras.getString("tipoCaja");
        idViaje = extras.getString("IdViaje");
        btnVisible = extras.getString("btnVisible");

       tvCantidadCarga.setText(cantidadCarga);
       tvCarga.setText(carga);
       tvDobleRemolque.setText(dobleRemolque);
       tvFechaAproxSalida.setText(fechaAproxSalida);
       tvNombreLugarLlegada.setText(nombreLugarLlegada);
       tvNombreLugarSalida.setText(nombreLugarSalida);
       tvPago.setText(pago);
       tvRControl.setText(rControl);
       tvTipoCaja.setText(tipoCaja);
    }

    public void Agrega(View view){
        databaseReference.child("Viajes").child(idViaje).child("disponible").setValue(false);
        databaseReference.child("Viajes").child(idViaje).child("correoTrailero").setValue(correo);
        Toast.makeText(this, "Has agregado un viaje a tu lista", Toast.LENGTH_SHORT).show();
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
