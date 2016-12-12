package com.losdelcallejon.gamesmachine;

import com.losdelcallejon.gamesmachine.Models.MCursados;
import com.losdelcallejon.gamesmachine.Models.MUnidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo Justiniano on 10-Dec-16.
 */

public interface ActionResolver {
    public void showToast(CharSequence toastMessage, int toastDuration);
    public void showSpeechPopup();
    public void tryTTS(String text);
    public boolean esNuevoUsuario();
    public String obtenerResponse();
    public String obtenerNombreUsuario();
    public int obtenerUsuarioID();
    public String obtenerSexoUsuario();
    public void insertarUsuario(String nombre, String sexo,String mongo_id);
    public List<Integer> obtenerUnidadesDelUsuario();
    public void insertarUnidad(int i, int nivel, String nombre, String descripcion);
    public int obtenerIdUnidad(String nombreUnidad);
    public void insertarPalabra(int i, String letra, int id_unidad);
    public String obtenerMongoId();
    public List<MCursados> obtenerListUnidadesCursadas();
    public List<MUnidades> obtenerListUnidades();
    public ArrayList<String> obtenerPalabras(int nivel);
}
