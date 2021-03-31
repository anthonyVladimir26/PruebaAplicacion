package com.example.pruebaaplicacion;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public Connection conexionBD(){
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.100.65;DatabaseName=junodoctor_cliente;user=DOCTORJUNIO;PASSWORD=junodoctor2020");


        }catch (Exception e){
            e.getMessage();
        }
        return conexion;
    }
}