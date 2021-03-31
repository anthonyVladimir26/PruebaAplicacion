package com.example.pruebaaplicacion.Mensajes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.Chat.AdapterUsuarioChat;
import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.R;

import java.util.ArrayList;

public class AdapterMensajeRecibe extends RecyclerView.Adapter<AdapterMensajeRecibe.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Mensajes> listMensajes;

    private View.OnClickListener listener;

    public AdapterMensajeRecibe(Context context, ArrayList<Mensajes> listMensajes){
        this.inflater = LayoutInflater.from(context);
        this.listMensajes =listMensajes;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_mensaje_recibido,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setonClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mensaje = listMensajes.get(position).getMensaje();
        String hora = listMensajes.get(position).getHora();

        holder.mensajeRecibido.setText(mensaje);
        holder.horaRecibida.setText(hora);
    }

    @Override
    public int getItemCount() {
        return listMensajes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mensajeRecibido, horaRecibida;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mensajeRecibido =itemView.findViewById(R.id.mensaje_recibido);
            horaRecibida =itemView.findViewById(R.id.hora_recibido);
        }
    }
}
