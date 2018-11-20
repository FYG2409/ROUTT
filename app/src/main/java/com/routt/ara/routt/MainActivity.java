package com.routt.ara.routt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.routt.ara.routt.model.Persona;
import com.routt.ara.routt.model.Viaje;
import com.routt.ara.routt.view.Ofertante.ContenedorOfertanteActivity;
import com.routt.ara.routt.view.Trailero.ContenedorTraileroActivity;

//HOLA
public class MainActivity extends AppCompatActivity {

    private TextView btnRegistra;
    private Button btnEntra;
    private EditText txtEmail;
    private EditText txtContra;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private DatabaseReference databaseReference;

    private String correo, contra;
    private int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntra = (Button) findViewById(R.id.btnEntra);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContra = (EditText) findViewById(R.id.txtContra);
        btnRegistra = (TextView) findViewById(R.id.creaCuenta);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        inicializa();

        tipo = -1;
        correo = "";
    }

    public void buscaTipoDePersona(){
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Persona persona = snapshot.getValue(Persona.class);
                        if((txtEmail.getText().toString()).equals(persona.getCorreo())){
                            tipo = persona.getTipo();
                            contra = persona.getContraseña();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void IniciaSesion(View view) {
        buscaTipoDePersona();
        if(contra.equals(txtContra.getText().toString())){

            firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtContra.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(tipo == 1){
                            Toast.makeText(MainActivity.this, "Bienvenido Ofertante", Toast.LENGTH_SHORT).show();
                            irAContenedorOfertante();
                        }else
                        if(tipo == 0){
                            Toast.makeText(MainActivity.this, "Bienvenido Trailero", Toast.LENGTH_SHORT).show();
                            irAContenedorTrailero();
                        }else{
                            Toast.makeText(MainActivity.this, "No se encontro el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.w("MainActivity", "ERROR AL INICIAR SESION: " + task.getException().getMessage());
                        Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    public void irAContenedorTrailero(){
        Intent intent = new Intent(this, ContenedorTraileroActivity.class);
        startActivity(intent);
    }

    public void irAContenedorOfertante(){
        Intent intent = new Intent(this, ContenedorOfertanteActivity.class);
        startActivity(intent);
    }

    public void inicializa(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w("MainActivity", "onAuthStateChanged - signed_in " + firebaseUser.getUid());
                    Log.w("MainActivity", "onAuthStateChanged - signed_in " + firebaseUser.getEmail());
                }else
                    Log.w("MainActivity", "onAuthStateChanged - signed_out");

            }
        };
    }

    public void irASeleccionaTipo (View view){
        Intent intent = new Intent(this, SeleccionaTipoActivity.class);
        startActivity(intent);
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
