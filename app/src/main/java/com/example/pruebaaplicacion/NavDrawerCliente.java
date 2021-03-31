package com.example.pruebaaplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.FragmentsCliente.ChatFragmentCliente;
import com.example.pruebaaplicacion.FragmentsCliente.ChatMensajesFragmentCliente;
import com.example.pruebaaplicacion.FragmentsCliente.MainFragmentCliente;
import com.example.pruebaaplicacion.FragmentsDoctor.ChatMensajesFragmentDoctor;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerCliente extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    //cambiar de fragments variables

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variables de ChatMensajesFragments
    ChatMensajesFragmentDoctor chatMensajesFragmentDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_cliente);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        //establecer eventos onClick al navegationView

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        //cargar Fragments

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragmentCliente());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId()  == R.id.usuario ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragmentCliente());
            fragmentTransaction.commit();

            toolbar.setTitle("usuario");
        }

        if (item.getItemId()  == R.id.chat ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragmentCliente());
            fragmentTransaction.commit();
            toolbar.setTitle("chat");
        }
        if (item.getItemId()  == R.id.C_cliente ){
            AlertDialog.Builder alerta =  new AlertDialog.Builder(this);
            alerta.setMessage("¿Desea cerrar sesion?")
                    .setCancelable(true)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cerrarSesion();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Cerrar Sesion");
            titulo.show();

        }


        return false;
    }


    public void cerrarSesion(){
        GuardarDatosUsuarioActual("0",null,null,false);

        Intent intentAsistente = new Intent(this,PresentacionActivity.class);
        startActivityForResult(intentAsistente,0);
    }

    public void GuardarDatosUsuarioActual(String idUsuario,String usuario ,String tipoUsuario, boolean sesionIniciada) {
        SharedPreferences sharedPreferences = getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", idUsuario);
        editor.putString("usuario", usuario);
        editor.putString("tipo", tipoUsuario);
        editor.putBoolean("inicio", sesionIniciada);
        editor.apply();

    }
    @Override
    public void enviarChat(Usuarioschat usuarioschat) {
        chatMensajesFragmentDoctor = new ChatMensajesFragmentDoctor();

        Bundle bundleEnvio = new Bundle();

        bundleEnvio.putSerializable("doctor", usuarioschat);

        chatMensajesFragmentDoctor.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, chatMensajesFragmentDoctor);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode== event.KEYCODE_BACK){

            finishAffinity();
        }

        return super.onKeyDown(keyCode, event);
    }



}