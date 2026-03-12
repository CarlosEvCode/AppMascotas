package com.example.appmascotas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registrar extends AppCompatActivity {
    EditText edtTipo, edtNombre, edtColor, edtPeso;
    Button btnRegistrarM;

    private void loadUI() {
        edtTipo = findViewById(R.id.edtTipo);
        edtNombre = findViewById(R.id.edtNombre);
        edtColor = findViewById(R.id.edtColor);
        edtPeso = findViewById(R.id.edtPeso);
        btnRegistrarM = findViewById(R.id.btnRegistrarM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });
        loadUI();

        //Eventos
        btnRegistrarM.setOnClickListener((v)-> {registrarMascota();});

    }

    private void registrarMascota() {
        if (edtTipo.getText().toString().isEmpty()) {
            edtTipo.setError("Complete con Perro, Gato");
            edtTipo.requestFocus();
            return;
        }
        if (edtNombre.getText().toString().isEmpty()) {
            edtNombre.setError("Escriba el nombre");
            edtNombre.requestFocus();
            return;
        }
        if (edtColor.getText().toString().isEmpty()) {
            edtColor.setError("Escriba el color");
            edtColor.requestFocus();
            return;
        }
        if (edtPeso.getText().toString().isEmpty()) {
            edtPeso.setError("Escriba el peso");
            edtPeso.requestFocus();
            return;
        }

        String tipo = edtTipo.getText().toString().trim();
        String nombre = edtTipo.getText().toString().trim();
        String color = edtTipo.getText().toString().trim();
        double peso = Double.parseDouble(edtPeso.getText().toString());

        //Error sino es Perro o Gato
        if(!tipo.equals("Perro") && !tipo.equals("Gato")){
            edtTipo.setError("Solo se permite: Pero, Gato");
            edtTipo.requestFocus();
            return;
        }

        if(peso < 0) {
            edtPeso.setError("Solo se permiten valores positivos");
            edtPeso.requestFocus();
            return;
        }

        //Solicitar la confirmacion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Seguro de registrar?");

        builder.setPositiveButton("Si",(a,b)-> {
            Toast.makeText(getApplicationContext(),"Guardado",Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}