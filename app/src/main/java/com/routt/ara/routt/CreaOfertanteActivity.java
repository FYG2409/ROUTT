package com.routt.ara.routt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.Float3;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.routt.ara.routt.MainActivity;
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Persona;
import com.routt.ara.routt.model.Viaje;
import com.routt.ara.routt.view.PresenterPrincipal;

import java.util.ArrayList;

public class CreaOfertanteActivity extends AppCompatActivity {

    private FloatingActionButton cargaImg;
    private ImageView imagen ;

    private EditText nombre, apePat, apeMat, edad, correo, contra, confirmaContra;

    private String txtImagen, txtNombre, txtApePat, txtApeMat, txtCorreo, txtContra, txtConfirmaContra;
    private int txtEdad;

    private ArrayList<String> correos = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;

    private StorageReference mStorage;
    private static  final int GALLERY_INTENT =1;
    private ProgressDialog progressDialo;  // mensaje de carga de imagen
    private PresenterPrincipal presenterPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_ofertante);

        imagen = (ImageView) (findViewById(R.id.imagenId));
        nombre = (EditText) (findViewById(R.id.nombre));
        apePat = (EditText) (findViewById(R.id.apePat));
        apeMat = (EditText) (findViewById(R.id.apeMat));
        edad = (EditText) (findViewById(R.id.edad));
        correo = (EditText) (findViewById(R.id.correo));
        contra = (EditText) (findViewById(R.id.contra));
        confirmaContra = (EditText) (findViewById(R.id.confirmaContra));


        firebaseAuth= FirebaseAuth.getInstance();

        cargaImg=(FloatingActionButton) findViewById(R.id.btnImagen);
        mStorage= FirebaseStorage.getInstance().getReference();
        progressDialo=new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        inicializa();

        cargaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_INTENT && requestCode != RESULT_OK){
            progressDialo.setTitle("Cargando ..");
            progressDialo.setMessage("Cargando previsualización");
            progressDialo.setCancelable(false);
            progressDialo.show();
            txtNombre = nombre.getText().toString();

            final Uri uri = data.getData();
            final StorageReference filePath=mStorage.child("Fotos").child(txtNombre).child(uri.getLastPathSegment());//lo guarda aqui
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialo.dismiss();

                    Uri descargarImg= taskSnapshot.getDownloadUrl();
                    Glide.with(CreaOfertanteActivity.this)
                            .load(descargarImg)
                            .into(imagen);


                    Toast.makeText(CreaOfertanteActivity.this, "Imagen subida correctamente", Toast.LENGTH_LONG).show();

                }
            });
        }
        else {
            Toast.makeText(CreaOfertanteActivity.this, "algo esta mal", Toast.LENGTH_LONG).show();
        }
    }


    public void creaPersona(View view){


        //txtImagen = imagen.getText().toString();
        txtNombre = nombre.getText().toString();
        txtApeMat = apeMat.getText().toString();
        txtApePat = apePat.getText().toString();
        txtCorreo = correo.getText().toString();
        txtContra = contra.getText().toString();
        txtConfirmaContra = confirmaContra.getText().toString();


        //VALIDACIONES
        //Valida que todos los campos esten llenos
        if(TextUtils.isEmpty(txtNombre) || TextUtils.isEmpty(txtApeMat) || TextUtils.isEmpty(txtApePat) || TextUtils.isEmpty(edad.getText().toString()) || TextUtils.isEmpty(txtCorreo) || TextUtils.isEmpty(txtContra) || TextUtils.isEmpty(txtConfirmaContra)){
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            txtEdad = Integer.parseInt(edad.getText().toString());
            //Validando edad
            if(txtEdad >= 18 && txtEdad <= 80){
                //Valindando que la contraseña tenga minimo 6 caracteres
                if(txtContra.length()>=6 || txtConfirmaContra.length()>=6){
                    //Validando Contraseñas iguales
                    if(txtContra.equals(txtConfirmaContra)){
                        //REGISTRANDO
                        firebaseAuth.createUserWithEmailAndPassword(txtCorreo, txtContra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    String xId = databaseReference.push().getKey();
                                    // tipo = 0 ----> Trailero ; tipo = 1 ----> Ofertante
                                    Persona persona = new Persona(txtNombre, txtApePat, txtApeMat, txtEdad, "Ubicacion Persona", 0,0,0,txtContra, txtCorreo, "Foto Perfil", 1);
                                    databaseReference.child("Personas").child(xId).setValue(persona);

                                    Toast.makeText(CreaOfertanteActivity.this, "Gracias por registrarte :)", Toast.LENGTH_SHORT).show();

                                    irAMain();
                                }else {
                                    if (task.getException().getMessage().contains("The email address is already in use by another account.")) {
                                        Toast.makeText(CreaOfertanteActivity.this, "Este correo ya esta en uso :(", Toast.LENGTH_SHORT).show();
                                        correo.requestFocus();
                                    } else {
                                        Log.w("CreaOfertanteActivity", "Error al crear cuenta:  " + task.getException().hashCode() + " MENSAJE " + task.getException().getMessage());
                                        Toast.makeText(CreaOfertanteActivity.this, "No se pudo crear la cuenta :(", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }else{
                        //msj las contraseñas no coinciden
                        Toast.makeText(this, "Las contraseñas no coinciden :(", Toast.LENGTH_SHORT).show();
                        confirmaContra.requestFocus();
                    }
                }else
                    //msj la contraseña debe tener al menos 6 caracteres
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres :(", Toast.LENGTH_SHORT).show();
                    contra.requestFocus();
            }else{
                //mjs no puedes trabajar con esa edad
                Toast.makeText(this, "Aun eres muy pequeño para trabajar :(", Toast.LENGTH_SHORT).show();
                edad.requestFocus();
            }
        }
    }

    public void irAMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void inicializa(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w("creaOfertanteActivity", "onAuthStateChanged - signed_in" + firebaseUser.getUid());
                    Log.w("creaOfertanteActivity", "onAuthStateChanged - signed_in" + firebaseUser.getEmail());
                }else
                    Log.w("creaOfertanteActivity", "onAuthStateChanged - signed_out");

            }
        };
    }
}

