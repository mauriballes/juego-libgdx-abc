package com.losdelcallejon.gamesmachine.Models;

/**
 * Created by samuel on 11-12-16.
 */

public class MPalabras {
    private int id;
    private String letra;
    private int unidad_id;

    public MPalabras() {
    }

    public MPalabras(int id, String letra, int unidad_id) {
        this.id = id;
        this.letra = letra;
        this.unidad_id = unidad_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(int unidad_id) {
        this.unidad_id = unidad_id;
    }
}
