package com.example.pruebaaplicacion.PruebaUsuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.Chat.AdapterUsuarioChat;
import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.Mensajes.AdapterMensajeRecibe;
import com.example.pruebaaplicacion.R;

import java.util.ArrayList;

public class AdapterVistaUsuario extends RecyclerView.Adapter<AdapterVistaUsuario.ViewHolder> implements View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<VistaUsuario> listVistaUsuario;

    private View.OnClickListener listener;

    public AdapterVistaUsuario(Context context, ArrayList<VistaUsuario> listVistaUsuario){
        this.inflater = LayoutInflater.from(context);
        this.listVistaUsuario =listVistaUsuario;
    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_usuarios_mensajes,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = listVistaUsuario.get(position).getNombre();
        String apellido ="";

        holder.nombres.setText(nombre);
        holder.apellidos.setText("");
    }

    @Override
    public int getItemCount() {
        return listVistaUsuario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombres,apellidos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombreMensaje);
            apellidos = itemView.findViewById(R.id.apellidosMensaje);
        }
    }
}
