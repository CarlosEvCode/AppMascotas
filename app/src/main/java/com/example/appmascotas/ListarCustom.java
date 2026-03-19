package com.example.appmascotas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarCustom extends AppCompatActivity implements MascotaAdapter.OnAccionListener {
    RecyclerView recyclerMascotas;
    MascotaAdapter adapter;
    ArrayList<Mascota> listaMascotas;
    RequestQueue requestQueue;
    Button btnEditar, btnEliminar;

    private final String URL = "http://192.168.18.61:3000/mascotas/";
    private void loadUI(){
        recyclerMascotas = findViewById(R.id.recyclerMascotas);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_custom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();


        //Preparar lista y adapter antes de utilizar WS
        listaMascotas = new ArrayList<>();
        adapter = new MascotaAdapter(this, listaMascotas,this); //Implementar definicion de clase..
        recyclerMascotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerMascotas.setAdapter(adapter);

        //WS
        obtenerDatos();



    }

    private void obtenerDatos(){
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                jsonArray -> renderizarLista(jsonArray),
                error -> {
                    Log.e("ErrorWS", error.toString());
                    Toast.makeText(this,"No se obtuvieron datos",Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void renderizarLista(JSONArray jsonMascotas){
        Log.d("Resultado",jsonMascotas.toString());
        //Con los datos obtenidos, cargamos la lista <Mascota> ya que esta
        //vinculada al MascotaAdapter > RecyclerView
        try {
            listaMascotas.clear();
            for(int i = 0; i<jsonMascotas.length();i++){
                //Tomamos un json a la vez utilizando su indice
                JSONObject json = jsonMascotas.getJSONObject(i);
                listaMascotas.add(new Mascota(
                        json.getInt("id"),
                        json.getString("tipo"),
                        json.getString("nombre"),
                        json.getString("color"),
                        json.getDouble("pesokg")

                ));
            } // fin for
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            Log.e("ErrorJSON",e.toString());
        }
    }

    @Override
    public void onEditar(int position, Mascota mascota) {
        //En que registro se puso clic?
        Intent envioDatos = new Intent(this, Actualizar.class);
        envioDatos.putExtra("idEnviado", mascota.getId()); //Enviando la id del objeto mascota
        envioDatos.putExtra("tipoEnviado", mascota.getTipo());
        envioDatos.putExtra("nombreEnviado", mascota.getNombre());
        envioDatos.putExtra("colorEnviado", mascota.getColor());
        envioDatos.putExtra("pesoEnviado", mascota.getPesokg());
        startActivity(envioDatos);

    }

    @Override
    public void onEliminar(int position, Mascota mascota) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Seguro de eliminar?");
        builder.setPositiveButton("Si",(a,b) ->{
            eliminarMascota(mascota.getId(),position);
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();// Cada vez que regresamos a esta pantalla, refrescamos los datos
        obtenerDatos();
    }

    private void eliminarMascota(int id, int position){
        String urlEliminar = this.URL + String.valueOf(id);
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                urlEliminar,
                null,
                jsonObject -> {
                    try {
                        boolean eliminado = jsonObject.getBoolean("success");
                        String mensaje = jsonObject.getString("message");
                        if (eliminado){
                            adapter.eliminarItem(position);
                            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("ErrorJSON",e.toString());
                    }
                },
                error -> {
                    Log.e("ErrorWS",error.toString());
                    Toast.makeText(this,"No se pudo eliminar",Toast.LENGTH_SHORT).show();

                }
        );
        requestQueue.add(jsonObjectRequest);

    }
}