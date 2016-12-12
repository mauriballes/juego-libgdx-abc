package com.losdelcallejon.gamesmachine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.losdelcallejon.gamesmachine.Models.MCursados;
import com.losdelcallejon.gamesmachine.Models.MUnidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo Justiniano on 10-Dec-16.
 */

public class ActionResolverAndroid implements ActionResolver {
    public static final int REQUEST_OK = 1;

    Handler uiThread;
    Context appContext;
    DBabc bd;
    TextToSpeech tts;
    String response="";
    public ActionResolverAndroid(Context appContext) {
        uiThread = new Handler();
        this.appContext = appContext;
        bd = new DBabc(this.appContext,"abc.db", null, 1);
        this.showToast("LA BD esta funcionando Bien",5000);
    }

    @Override
    public void showToast(final CharSequence toastMessage, final int toastDuration) {
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appContext, toastMessage, toastDuration).show();
            }
        });
    }

    @Override
    public void showSpeechPopup() {
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                i.putExtra("popup",true);
                try {
                    // We open the Speech dialog here using a request code
                    // and retrieve the spoken text in AndroidLauncher's onActivityResult().
                    ((Activity)appContext).startActivityForResult(i, REQUEST_OK);
                } catch (Exception e) {
                    showToast(e.toString(), 5000);
                    Gdx.app.log(ActionResolverAndroid.class.getName(),
                            "error initializing speech engine" + e);
                }
            }
        });
    }

    @Override
    public void tryTTS(final String text) {
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                //speak straight away
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public boolean esNuevoUsuario() {

        bd.OpenDatabase();
        boolean b = this.bd.esNuevoUsuario();
        bd.CloseDatabase();
        return b;
    }

    @Override
    public List<Integer> obtenerUnidadesDelUsuario() {
        bd.OpenDatabase();
        List<Integer> lista ;
        lista = bd.obtenerUnidadesDelUsuario();
        bd.CloseDatabase();
        return lista;
    }

    @Override
    public void insertarUnidad(int i, int nivel, String nombre, String descripcion) {
        bd.OpenDatabase();
        bd.insertarUnidades(i,nivel,nombre,descripcion);
        bd.CloseDatabase();
    }

    @Override
    public int obtenerIdUnidad(String nombreUnidad) {
        bd.OpenDatabase();
        int i = bd.obtenerIdUnidad(nombreUnidad);
        bd.CloseDatabase();
        return i;
    }

    @Override
    public void insertarPalabra(int i, String letra, int id_unidad) {
        bd.OpenDatabase();
        bd.insertarPalabras(i,letra,id_unidad);
        bd.CloseDatabase();
    }

    @Override
    public String obtenerMongoId() {
        bd.OpenDatabase();
        String b = this.bd.obtenerMongoId();
        bd.CloseDatabase();
        return b;
    }


    @Override
    public String obtenerResponse() {
        return getResponse();
    }

    @Override
    public String obtenerNombreUsuario() {
        bd.OpenDatabase();
        String b = this.bd.obtenerNombreUsuario();
        bd.CloseDatabase();
        return b;
    }

    @Override
    public int obtenerUsuarioID() {
        bd.OpenDatabase();
        int b = this.bd.obtenerUsuarioID();
        bd.CloseDatabase();
        return b;
    }

    @Override
    public String obtenerSexoUsuario() {
        bd.OpenDatabase();
        String b = this.bd.obtenerSexoUsuario();
        bd.CloseDatabase();
        return b;
    }

    @Override
    public void insertarUsuario(String nombre, String sexo,String mongo_id) {
    bd.OpenDatabase();
    bd.insertarUsuario(1,nombre,sexo,mongo_id);
    bd.CloseDatabase();
    }

    @Override
    public List<MCursados> obtenerListUnidadesCursadas() {
        bd.OpenDatabase();
        List<MCursados> lista;
        lista = bd.obtenerListUnidadesCursadas();
        bd.CloseDatabase();
        return lista;
    }

    @Override
    public List<MUnidades> obtenerListUnidades() {
        bd.OpenDatabase();
        List<MUnidades> lista;
        lista = bd.obtenerListUnidades();
        bd.CloseDatabase();
        return lista;
    }

    @Override
    public ArrayList<String> obtenerPalabras(int nivel) {
        bd.OpenDatabase();
        ArrayList<String> lista;
        lista = bd.obtenerPalabras(nivel);
        bd.CloseDatabase();
        return lista;
    }

    public void setTts(TextToSpeech tts) {
        this.tts = tts;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
