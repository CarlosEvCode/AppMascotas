package com.example.appmascotas;

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

    private final String URL = "http://192.168.101.34:3000/mascotas/";
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

        /*btnEditar.setOnClickListener((v) -> {startActivity(new Intent(getApplicationContext(), Actualizar.class));});*/

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
    public void onEditar(int posision, Mascota mascota) {
        //En que registro se puso clic?
        Intent idEnviado = new Intent(this, Actualizar.class);
        Toast.makeText(this,"Editar: "+mascota.getId(),Toast.LENGTH_SHORT).show();
        idEnviado.putExtra("idEnviado", mascota.getId()); //Enviando la id del objeto mascota
        startActivity(idEnviado);

    }

    @Override
    public void onEliminar(int posision, Mascota mascota) {

    }
}