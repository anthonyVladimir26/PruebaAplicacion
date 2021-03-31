package com.example.pruebaaplicacion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.FragmentsCliente.ChatMensajesFragmentCliente;
import com.example.pruebaaplicacion.FragmentsDoctor.ChatFragmentDoctor;
import com.example.pruebaaplicacion.FragmentsDoctor.ChatMensajesFragmentDoctor;
import com.example.pruebaaplicacion.FragmentsDoctor.HistorialClinico;
import com.example.pruebaaplicacion.FragmentsDoctor.MainFragmentDoctor;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerDoctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    //cambiar de fragments variables

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variables de ChatMensajesFragments
    ChatMensajesFragmentDoctor chatMensajesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_doctor);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        //establecer eventos onClick al navegationView

        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        //cargar Fragments

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragmentDoctor());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.consultasD) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragmentDoctor());
            fragmentTransaction.commit();

            toolbar.setTitle("consultas");
        }

        if (item.getItemId() == R.id.historial) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new HistorialClinico());
            fragmentTransaction.commit();

            toolbar.setTitle("historial clinico");
        }
        if (item.getItemId() == R.id.chatD) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragmentDoctor());
            fragmentTransaction.commit();
            toolbar.setTitle("chat");
        }

        if (item.getItemId()  == R.id.C_doctor ){
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

    @Override
    public void enviarChat(Usuarioschat usuarioschat) {
        chatMensajesFragment = new ChatMensajesFragmentDoctor();

        Bundle bundleEnvio = new Bundle();

        bundleEnvio.putSerializable("doctor", usuarioschat);

        chatMensajesFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, chatMensajesFragment);
        fragmentTransaction.commit();
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode== event.KEYCODE_BACK){

            finishAffinity();
        }

        return super.onKeyDown(keyCode, event);
    }
}
