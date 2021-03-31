package com.example.pruebaaplicacion;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtContrasena;
    Button boton;

    Conexion cn =new Conexion();


     int idUsuario ;
     String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        edtUsuario = findViewById(R.id.usuario);
        edtContrasena = findViewById(R.id.contrasena);
        boton = findViewById(R.id.boton);

        GuardarDatosUsuarioActual("0","","",true);
          idUsuario = Integer.parseInt(MostrarDatosUsuarioActual("id"));
          tipo = MostrarDatosUsuarioActual("tipo");



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggin();
            }
        });

    }
    public void loggin (){
        try {
            Statement stm = cn.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("select * from usuario where usuario = '"+  edtUsuario.getText().toString() +"' AND contra ='"+ edtContrasena.getText().toString()+"'");
            if(rs.next()){

                String idU = rs.getInt("id")+"";
                String nUsuario = rs.getString("usuario");
                String tipo = "usuario";
                boolean start = true;
                GuardarDatosUsuarioActual(idU,nUsuario,tipo,start);
                EntrarInterfaz(tipo);
                Toast.makeText(this, "se conecto", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"Datos incorrectos",Toast.LENGTH_SHORT).show();
            }


        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
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

    public String MostrarDatosUsuarioActual(String key){
        SharedPreferences sharedPreferences = getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"false");
    }

    public void EntrarInterfaz (String tipo){
        if (tipo.equals("usuario")){
        Intent intentCliente = new Intent(this,NavDrawerCliente.class);
        startActivityForResult(intentCliente,0);}
        if (tipo.equals("doctor")){
            Intent intentDoctor = new Intent(this,NavDrawerDoctor.class);
            startActivityForResult(intentDoctor,0);}
        if (tipo.equals("asistente")){
            Intent intentAsistente = new Intent(this,NavDrawerAsistente.class);
            startActivityForResult(intentAsistente,0);}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode== event.KEYCODE_BACK){
            finishAffinity();
        }

        return super.onKeyDown(keyCode, event);
    }
}