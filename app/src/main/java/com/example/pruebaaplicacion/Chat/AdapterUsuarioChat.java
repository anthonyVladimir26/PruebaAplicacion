package com.example.pruebaaplicacion.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.R;

import java.util.ArrayList;

public class AdapterUsuarioChat extends RecyclerView.Adapter<AdapterUsuarioChat.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Usuarioschat> listChat;

    private View.OnClickListener listener;

    public AdapterUsuarioChat(Context context, ArrayList<Usuarioschat> listChat){
        this.inflater = LayoutInflater.from(context);
        this.listChat =listChat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_usuarios_mensajes,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setonClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String nombre = listChat.get(position).getNombre();
            String apellidos = listChat.get(position).getApellidos();

            holder.nombres.setText(nombre);
            holder.apellidos.setText(apellidos);
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!= null){
            listener.onClick(v);
        }

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
