package com.routt.ara.routt.view.Trailero;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.routt.ara.routt.MainActivity;
import com.routt.ara.routt.R;
import com.routt.ara.routt.view.fragments.AgendaViajesFragment;
import com.routt.ara.routt.view.fragments.PerfilFragment;
import com.routt.ara.routt.view.fragments.ViajesDisponiblesFragment;

public class ContenedorTraileroActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_trailero);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottombar);
        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.agenda){
                    AgendaViajesFragment agendaViajesFragment = new AgendaViajesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, agendaViajesFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }else
                    if(item.getItemId() == R.id.viajeDisponible){
                        ViajesDisponiblesFragment viajesDisponiblesFragment = new ViajesDisponiblesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, viajesDisponiblesFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                    }else
                        if(item.getItemId() == R.id.perfil){
                            PerfilFragment perfilFragment = new PerfilFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, perfilFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }else
                            if(item.getItemId() == R.id.salir){
                                cierraSesion();
                            }
                return false;
            }
        });
    }

    private void cierraSesion(){
        try {
            firebaseAuth.signOut();
            Toast.makeText(this, "Sesion Cerrada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.w("ContenedorTrailero", "ERROR AL CERRAR SESION: " + e.getMessage());
            Toast.makeText(this, "Error al cerrar sesion", Toast.LENGTH_SHORT).show();
        }
    }
}
