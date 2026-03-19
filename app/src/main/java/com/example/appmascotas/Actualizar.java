package com.example.appmascotas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Actualizar extends AppCompatActivity {

    EditText resID, edtATipo, edtANombre, edtAColor, edtAPeso;
    Button btnActualizar;

    private void loadUI(){
        resID = findViewById(R.id.resID);
        edtATipo = findViewById(R.id.edtATipo);
        edtANombre = findViewById(R.id.edtANombre);
        edtAColor = findViewById(R.id.edtAColor);
        edtAPeso = findViewById(R.id.edtAPeso);
        btnActualizar = findViewById(R.id.btnActualizar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int idRecibido = getIntent().getIntExtra("idEnviado",0);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();

        if (idRecibido > 0) {
            resID.setText(String.valueOf(idRecibido));
        }
    }
}