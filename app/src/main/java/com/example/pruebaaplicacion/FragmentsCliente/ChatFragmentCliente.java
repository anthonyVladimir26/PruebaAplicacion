package com.example.pruebaaplicacion.FragmentsCliente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.Chat.AdapterUsuarioChat;
import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.Conexion;
import com.example.pruebaaplicacion.NavDrawerCliente;
import com.example.pruebaaplicacion.R;
import com.example.pruebaaplicacion.iComunicaFragments;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatFragmentCliente extends Fragment {

    AdapterUsuarioChat adapterUsuarioChat;
    RecyclerView recyclerView;
    ArrayList<Usuarioschat> listaChat;
    Conexion cn = new Conexion();

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    String usuario;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment_cliente,container,false);
        recyclerView = view.findViewById(R.id.chatRecyclerView);
        listaChat = new ArrayList<Usuarioschat>();
        cargarLista();

        mostrarData();
        return view;
    }

    public void cargarLista(){

        try {
            usuario= MostrarDatosUsuarioActual("usuario");
            Statement stm = cn.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM usuario, chats WHERE usuario.usuario = chats.doctor AND chats.cliente = 'ant'");

            while (rs.next()){

                int id =rs.getInt("id");
                String usuario = rs.getString("usuario");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String tipo = rs.getString("tipo");



                listaChat.add(new Usuarioschat(id,usuario,nombre,apellidos,correo,telefono,tipo));
            }

        }catch (SQLException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarData (){

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterUsuarioChat = new AdapterUsuarioChat(getContext(),listaChat);
        recyclerView.setAdapter(adapterUsuarioChat);

        adapterUsuarioChat.setonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                interfaceComunicaFragments.enviarChat(listaChat.get(recyclerView.getChildAdapterPosition(v)));
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.actividad =(Activity) context;
            interfaceComunicaFragments= (iComunicaFragments) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String MostrarDatosUsuarioActual(String key){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("doctor", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"0");
    }
}
