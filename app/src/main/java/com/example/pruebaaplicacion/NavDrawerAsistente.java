package com.example.pruebaaplicacion;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pruebaaplicacion.FragmentsCliente.ChatFragmentCliente;
import com.example.pruebaaplicacion.FragmentsCliente.MainFragmentCliente;
import com.google.android.material.navigation.NavigationView;

    public class NavDrawerAsistente extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    //cambiar de fragments variables

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variables de ChatMensajesFragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_asistente);

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
        fragmentTransaction.add(R.id.container, new MainFragmentCliente());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.consultas) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new MainFragmentCliente());
            fragmentTransaction.commit();

            toolbar.setTitle("usuario");
        }

        if (item.getItemId() == R.id.Agregar) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragmentCliente());
            fragmentTransaction.commit();
            toolbar.setTitle("chat");
        }
        if (item.getItemId() == R.id.historial) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragmentCliente());
            fragmentTransaction.commit();
            toolbar.setTitle("chat");
        }

        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode== event.KEYCODE_BACK){

            finishAffinity();
        }

        return super.onKeyDown(keyCode, event);
    }
}
