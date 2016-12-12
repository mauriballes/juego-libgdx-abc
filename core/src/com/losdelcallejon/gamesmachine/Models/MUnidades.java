package com.losdelcallejon.gamesmachine.Models;

/**
 * Created by samuel on 11-12-16.
 */

public class MUnidades {
    private int id;
    private int nivel;
    private String nombre;
    private String descripcion;

    public MUnidades() {
    }

    public MUnidades(int id, int nivel, String nombre, String descripcion) {
        this.id = id;
        this.nivel = nivel;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
