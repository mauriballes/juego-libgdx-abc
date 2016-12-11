package com.losdelcallejon.gamesmachine.Models;

/**
 * Created by samuel on 11-12-16.
 */

public class MCursados {
    private int id;
    private String fecha;
    private int unidad_id;

    public MCursados() {
    }

    public MCursados(int id, String fecha, int unidad_id) {
        this.id = id;
        this.fecha = fecha;
        this.unidad_id = unidad_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(int unidad_id) {
        this.unidad_id = unidad_id;
    }
}
