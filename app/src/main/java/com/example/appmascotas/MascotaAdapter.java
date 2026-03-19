package com.example.appmascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// RecyclerViewAdapter exige 3 metodos
//onCreateViewHolder    : crea una fila /item
//onBindviewHolder      : llena los datos de la tabla fila/item
//getItemCount          : cantidad de elementos
public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder>{

    //Atributos
    private final ArrayList<Mascota> lista;
    private final Context context;
    private final OnAccionListener listener;

    //Interface
    public interface OnAccionListener{
        void onEditar(int posision, Mascota mascota);
        void onEliminar(int posision, Mascota mascota);
    }

    //Constructor
    //Contexto (Activity), Lista (obtenemos por WS GET), listener (eventos de los botones)
    public MascotaAdapter(Context context, ArrayList<Mascota> lista, OnAccionListener listener){
        this.context = context;
        this.lista = lista;
        this.listener = listener;

    }

    //Representara cada fila
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre, tvTipo, tvPeso;
        Button btnEditar, btnEliminar;

        //Vinculacion (Acceso a cada elemento dentro de item_mascosa.xml)
        ViewHolder(View itemview){
            super(itemview);
            tvNombre = itemview.findViewById(R.id.tvNombre);
            tvTipo = itemview.findViewById(R.id.tvTipo);
            tvPeso = itemview.findViewById(R.id.tvPeso);
            btnEditar = itemview.findViewById(R.id.btnEditar);
            btnEliminar = itemview.findViewById(R.id.btnEliminar);

        }
    }

    //Inflar = crea el layout para cada fila
    @NonNull
    @Override
    public MascotaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mascota, parent, false);
        return new ViewHolder(view);
    }

    //2. llenar datos
    @Override
    public void onBindViewHolder(@NonNull MascotaAdapter.ViewHolder holder, int position) {
        Mascota mascota = lista.get(position);

        //TextView muestran los datos
        holder.tvNombre.setText(mascota.getNombre());
        holder.tvTipo.setText(mascota.getTipo());
        holder.tvPeso.setText(mascota.getPesokg());

        //Botones
        holder.btnEditar.setOnClickListener(v->{
            listener.onEditar(holder.getAdapterPosition(),mascota);
        });
        holder.btnEliminar.setOnClickListener(v->{
            listener.onEliminar(holder.getAdapterPosition(),mascota);
        });
    }
    //3.Calcular la cantidad de elementos de la lista

    @Override
    public int getItemCount() {
        return 0;
    }

    //4. Eliminar un elemento de la lista<Mascota>
    public void eliminarItem(int position) {
        lista.remove(position);
        notifyItemRemoved(position);
    }
}
