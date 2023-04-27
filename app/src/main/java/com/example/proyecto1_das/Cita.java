package com.example.proyecto1_das;

public class Cita implements java.io.Serializable{

    private String usuario;
    private String tipo;
    private String fecha;



    public Cita(String usuario, String tipo, String fecha) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.fecha = fecha;

    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


}

