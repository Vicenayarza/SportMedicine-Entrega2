package com.example.proyecto1_das;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String usuario;
    private String url;


    public Usuario(String email, String url) {
        this.usuario = usuario;
        this.url = url;

    }

    public Usuario() {
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUrl() {
        return url;
    }




}
