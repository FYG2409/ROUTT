package com.routt.ara.routt.view.Ofertante;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.routt.ara.routt.R;
import com.routt.ara.routt.model.Viaje;

import java.util.Calendar;

public class CreaViajeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button Bfecha, Bhora;
    private TextView efecha, ehora;
    private int dia,mes, año, hora, minutos;

    private String SpinnerTipoCaja, Carga, NomLugarSalida, NomLugarLlegada, Descripcion, Fecha, Hora, fechaHora;
    private int Pago, Cantidad;
    private Boolean RControl, Full;

    private Spinner txtSpinnerTipoCaja;
    private EditText txtCarga, txtCantidad, txtNomLugarSalida, txtNomLugarLlegada, txtDescripcion, txtPago;
    private Switch switchRControl, switchFull;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private String correoCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_viaje);

        txtSpinnerTipoCaja = (Spinner) findViewById(R.id.tipoCaja_spinner);
        txtCarga = (EditText) findViewById(R.id.txtCarga);
        txtCantidad = (EditText) findViewById(R.id.txtCantidad);
        txtNomLugarSalida = (EditText) findViewById(R.id.txtNomLugarSalida);
        txtNomLugarLlegada = (EditText) findViewById(R.id.txtNomLugarLlegada);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        txtPago = (EditText) findViewById(R.id.txtPago);
        switchRControl = (Switch) findViewById(R.id.switchRControl);
        switchFull = (Switch) findViewById(R.id.switchFull);

        efecha = (TextView) findViewById(R.id.efecha);
        ehora = (TextView) findViewById(R.id.ehora);
        Bfecha = (Button) findViewById(R.id.Bfecha);
        Bhora = (Button) findViewById(R.id.Bhora);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        LlenaOpciones();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    correoCurrent = firebaseUser.getEmail();
                }
            }
        };

        Bfecha.setOnClickListener(this);
        Bhora.setOnClickListener(this);
    }

    public void creaViaje(View view){
        SpinnerTipoCaja = txtSpinnerTipoCaja.getSelectedItem().toString();
        Carga = txtCarga.getText().toString();
        NomLugarSalida = txtNomLugarSalida.getText().toString();
        NomLugarLlegada = txtNomLugarLlegada.getText().toString();
        Descripcion = txtDescripcion.getText().toString();
        RControl = switchRControl.isChecked();
        Full = switchFull.isChecked();
        Hora = ehora.getText().toString();
        Fecha = efecha.getText().toString();


        //VALIDANDO
        //Valida que todos los campos esten llenos
        if(TextUtils.isEmpty(SpinnerTipoCaja) || TextUtils.isEmpty(Carga) || TextUtils.isEmpty(txtCantidad.getText().toString()) || TextUtils.isEmpty(NomLugarSalida)
            || TextUtils.isEmpty(NomLugarLlegada) || TextUtils.isEmpty(Descripcion) || TextUtils.isEmpty(txtPago.getText().toString()) || TextUtils.isEmpty(RControl.toString())
            || TextUtils.isEmpty(Full.toString()) || TextUtils.isEmpty(Hora) || TextUtils.isEmpty(Fecha)){

            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();

        }else{
            Cantidad = Integer.parseInt(txtCantidad.getText().toString());
            Pago = Integer.parseInt(txtPago.getText().toString());
            fechaHora = Fecha+" "+Hora;
            //REGISTRANDO
            String xId = databaseReference.push().getKey();
            Viaje viaje = new Viaje(xId, correoCurrent, NomLugarSalida, NomLugarLlegada, Pago, "Imagen1", SpinnerTipoCaja, Carga, Cantidad, RControl, Full, true, fechaHora, "Ubicacion Lugar Salida", "Ubicacion Lugar Llegada", "", false, false);
            databaseReference.child("Viajes").child(xId).setValue(viaje);
            Toast.makeText(this, "El viaje fue registrado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContenedorOfertanteActivity.class);
            startActivity(intent);
        }
    }

    public void LlenaOpciones(){
        String[] opciones = {"Refrigerada 48ft", "Refrigerada 53ft", "Seca 48ft", "Seca 53ft", "Plana", "Jaula", "Lovoy", "Cama baja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        txtSpinnerTipoCaja.setAdapter(adapter);
    }

    //CODIGO PARA PICKER DATE Y TIME
    @Override
    public void onClick(View view) {
        if(view == Bfecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
            ,año, mes, dia);
            datePickerDialog.show();
        }
        if(view==Bhora){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos= c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker,int hourOfDay, int minute) {
                    ehora.setText(hourOfDay+":"+minute);
                }
            },hora, minutos,false);
            timePickerDialog.show();
        }
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

