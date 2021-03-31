
package com.example.pruebaaplicacion.FragmentsCliente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.R;

public class ChatMensajesFragmentCliente extends Fragment {

    TextView nombreUsuario, apellidosUsuarios;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_mensajes_fragment_cliente,container,false);

        nombreUsuario = view.findViewById(R.id.nombreUsuario);





        //crear objeto bundler para recibir el objeto enviado
        Bundle objetoChatUsuario = getArguments();
        Usuarioschat usuarioschat= null;

        if (objetoChatUsuario != null){


            usuarioschat = (Usuarioschat) objetoChatUsuario.getSerializable("objeto");

            //establecer los datos en la vista

            nombreUsuario.setText(usuarioschat.getCorreo());

        }
        return view;
    }
}
