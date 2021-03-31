package com.example.pruebaaplicacion.FragmentsCliente;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.Chat.AdapterUsuarioChat;
import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.Conexion;
import com.example.pruebaaplicacion.PruebaUsuario.AdapterVistaUsuario;
import com.example.pruebaaplicacion.PruebaUsuario.VistaUsuario;
import com.example.pruebaaplicacion.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MainFragmentCliente extends Fragment {

    AdapterVistaUsuario adapterVistaUsuario;
    RecyclerView recyclerView;
    ArrayList<VistaUsuario> listMostrar;
    Conexion cn = new Conexion();

    Activity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.main_fragment_cliente,container,false);
        listMostrar = new ArrayList<VistaUsuario>();
        recyclerView = view.findViewById(R.id.recyclerView);

        cargarLista();
        mostrarData();

        return view;
    }
    public void cargarLista(){

        try {
            Statement stm = cn.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("select id,nombre,Telefono,Direccion,correo,tipo_ubi,usuario,clave from usuario");

            while (rs.next()){


                int id =rs.getInt("id");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                String correo = rs.getString("correo");
                String tipo_ubi = rs.getString("tipo_ubi");
                String usuario =rs.getString("usuario");
                String clave = rs.getString("clave");

                listMostrar.add(new VistaUsuario(id,nombre,telefono,direccion,correo,tipo_ubi,usuario,clave));

                Toast.makeText(getContext(),"tabla usuario",Toast.LENGTH_SHORT).show();
            }

        }catch (SQLException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarData (){

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterVistaUsuario = new AdapterVistaUsuario(getContext(),listMostrar);
        recyclerView.setAdapter(adapterVistaUsuario);

       /* adapterUsuarioChat.setonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String nombre = listaChat.get(recyclerView.getChildAdapterPosition(v)).getNombres();
                //Toast.makeText(getContext(),"nombre: " +nombre,Toast.LENGTH_SHORT).show();

                interfaceComunicaFragments.enviarChat(listaChat.get(recyclerView.getChildAdapterPosition(v)));
            }
        });*/

    }
}
