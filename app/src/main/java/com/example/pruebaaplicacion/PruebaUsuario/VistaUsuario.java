package com.example.pruebaaplicacion.PruebaUsuario;

public class VistaUsuario {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;
    private String tipo_ubi;
    private String usuario;
    private String clave;

    public VistaUsuario(int id, String nombre, String telefono, String direccion, String correo, String tipo_ubi, String usuario, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.tipo_ubi = tipo_ubi;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo_ubi() {
        return tipo_ubi;
    }

    public void setTipo_ubi(String tipo_ubi) {
        this.tipo_ubi = tipo_ubi;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
