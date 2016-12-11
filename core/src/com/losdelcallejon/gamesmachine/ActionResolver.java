package com.losdelcallejon.gamesmachine;

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
<<<<<<< HEAD
    public List<Integer> obtenerUnidadesDelUsuario();
=======
    public int obtenerUsuarioID();

    public String obtenerSexoUsuario();

    public void insertarUsuario(String nombre, String sexo);
>>>>>>> 7a18968dcc41651fb4ce8cff3569ac6e7643eb1b
}
