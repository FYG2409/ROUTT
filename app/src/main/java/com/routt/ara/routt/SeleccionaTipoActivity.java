package com.routt.ara.routt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SeleccionaTipoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_tipo);
    }

    public void irACreaTrailero(View view){
        Intent intent = new Intent(this, CreaTraileroActivity.class);
        startActivity(intent);
    }

    public void irACreaOfertante(View view){
        Intent intent = new Intent(this, CreaOfertanteActivity.class);
        startActivity(intent);
    }
}
