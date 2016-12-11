package com.losdelcallejon.gamesmachine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;

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
    public List<Integer> obtenerUnidadesDelUsuario() {
        bd.OpenDatabase();
        List<Integer> lista ;
        lista = bd.obtenerUnidadesDelUsuario();
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
