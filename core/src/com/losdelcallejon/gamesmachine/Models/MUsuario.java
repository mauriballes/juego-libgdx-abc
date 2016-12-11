package com.losdelcallejon.gamesmachine.Models;

/**
 * Created by samuel on 11-12-16.
 */

public class MUsuario {
    private int id;
    private String username;
    private String sexo;

    public MUsuario() {
    }

    public MUsuario(int id, String username, String sexo) {
        this.id = id;
        this.username = username;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}

