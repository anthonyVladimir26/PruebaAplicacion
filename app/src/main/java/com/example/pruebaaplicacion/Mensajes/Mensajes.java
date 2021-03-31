package com.example.pruebaaplicacion.Mensajes;

import java.sql.Blob;

public class Mensajes {
    private int id;
    private String usuarioEnvia;
    private String mensaje;
    private String hora;
    private String imagen;
    private String dia;
    private int tipo;

    public Mensajes(int id, String usuarioEnvia, String mensaje, String hora, String imagen, String dia, int tipo) {
        this.id = id;
        this.usuarioEnvia = usuarioEnvia;
        this.mensaje = mensaje;
        this.hora = hora;
        this.imagen = imagen;
        this.dia = dia;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioEnvia() {
        return usuarioEnvia;
    }

    public void setUsuarioEnvia(String usuarioEnvia) {
        this.usuarioEnvia = usuarioEnvia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
