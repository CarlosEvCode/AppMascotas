package com.example.appmascotas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Actualizar extends AppCompatActivity {


    EditText resID, edtATipo, edtANombre, edtAColor, edtAPeso;
    Button btnActualizar,btnVolver;
    RequestQueue requestQueue;

    private final String URL = "http://192.168.18.61:3000/mascotas/";
    private void loadUI(){
        resID = findViewById(R.id.resID);
        edtATipo = findViewById(R.id.edtATipo);
        edtANombre = findViewById(R.id.edtANombre);
        edtAColor = findViewById(R.id.edtAColor);
        edtAPeso = findViewById(R.id.edtAPeso);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int idRecibido = getIntent().getIntExtra("idEnviado",0);
        String tipo = getIntent().getStringExtra("tipoEnviado");
        String nombre = getIntent().getStringExtra("nombreEnviado");
        String color = getIntent().getStringExtra("colorEnviado");
        Double peso = getIntent().getDoubleExtra("pesoEnviado",0);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });


        loadUI();
        btnActualizar.setOnClickListener(v -> {
            validarRegistro();
        });
        btnVolver.setOnClickListener(v -> {
            finish(); // Cierra la actividad actual y vuelve a la anterior
        });
        if (idRecibido > 0) {
            resID.setText(String.valueOf(idRecibido));
            edtATipo.setText(tipo);
            edtANombre.setText(nombre);
            edtAColor.setText(color);
            edtAPeso.setText(String.valueOf(peso));
        }


    }



    private void validarRegistro(){

        //CORE = NUCLEO
        if(edtATipo.getText().toString().isEmpty()){
            edtATipo.setError("Complete con Perro , Gato");
            edtATipo.requestFocus();
            return;

        }

        if (edtANombre.getText().toString().isEmpty()){
            edtANombre.setError("Escriba el nombre");
            edtANombre.requestFocus();
            return;
        }

        if (edtAColor.getText().toString().isEmpty()){
            edtAColor.setError("Este campo es obligatorio");
            edtAColor.requestFocus();
            return;
        }

        if (edtAPeso.getText().toString().isEmpty()){
            edtAPeso.setError("Ingrese un valor");
            edtAPeso.requestFocus();
            return;
        }

        String tipo = edtATipo.getText().toString().trim();
        String nombre = edtANombre.getText().toString().trim();
        String color = edtAColor.getText().toString().trim();
        double peso = Double.parseDouble(edtAPeso.getText().toString());


        //Error SINO es Perro o Gato
        if (!tipo.equals("Perro") && !tipo.equals("Gato")){
            edtATipo.setError("Solo se permite: Perros , Gato");
            return;
        }

        if (peso < 0){
            edtAPeso.setError(("Solo se premiten valores positivos"));
            edtAPeso.requestFocus();
            return;

        }

        //Solicitar la confirmación

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Seguro de actualizar?");

        builder.setPositiveButton("Si",(a,b) ->{
            actualizarMascota();
        });

        builder.setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actualizarMascota() {
        String urlActualizar = this.URL + resID.getText().toString();

        //Comunicación
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();

        //Asignar los valores de las cajas

        try {
            jsonObject.put("id", Integer.parseInt(resID.getText().toString()));
            jsonObject.put("tipo", edtATipo.getText().toString());
            jsonObject.put("nombre", edtANombre.getText().toString());
            jsonObject.put("color", edtAColor.getText().toString());
            jsonObject.put("pesokg", Double.parseDouble(edtAPeso.getText().toString()));
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }

        Log.d("ValoresWS", jsonObject.toString());

        //Definir objeto (respuesta obtener)

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                urlActualizar,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("Resultado ", jsonObject.toString());
                        Toast.makeText(getApplicationContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;

                        //evaluar por codigo de error
                        if (response != null && response.data != null) {
                            //capturar el codigo de error 4xx , 5xx
                            int statusCode = response.statusCode;
                            String errorJson = new String(response.data);

                            Log.e("VolleyError", "Código: " + statusCode);
                            Log.e("VolleyError", "Cuerpo:" + errorJson);
                        } else {
                            Log.e("VolleyError", "Sin respuesta de red");
                        }
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}