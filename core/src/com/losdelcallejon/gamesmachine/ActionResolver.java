package com.losdelcallejon.gamesmachine;

import com.losdelcallejon.gamesmachine.Models.MCursados;
import com.losdelcallejon.gamesmachine.Models.MUnidades;

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
    public List<Integer> obtenerUnidadesDelUsuario();
    public int obtenerUsuarioID();

    public String obtenerSexoUsuario();

    public void insertarUsuario(String nombre, String sexo);

    public List<MCursados> obtenerListUnidadesCursadas();
    public List<MUnidades> obtenerListUnidades();
}
