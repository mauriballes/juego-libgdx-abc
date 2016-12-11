package com.losdelcallejon.gamesmachine;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.losdelcallejon.gamesmachine.AbcGameMain;

import java.util.ArrayList;
import java.util.Locale;

public class AndroidLauncher extends AndroidApplication implements TextToSpeech.OnInitListener {
    public static final int REQUEST_SPEECH = 1;
    private boolean yaCargoTTS=false;

    ActionResolverAndroid actionResolver;

    protected TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        checkTTSIntent.putExtra("popup",false);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        actionResolver = new ActionResolverAndroid(this);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new AbcGameMain(actionResolver), config);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getExtras().getBoolean("popup")|| yaCargoTTS) {
            if (requestCode == REQUEST_SPEECH && resultCode == RESULT_OK) {
                // Get the spoken sentence..
                ArrayList<String> thingsYouSaid =
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        actionResolver.setResponse(thingsYouSaid.get(0));
            }
        }

        if (requestCode == MY_DATA_CHECK_CODE && !yaCargoTTS) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
                actionResolver.setTts(myTTS);
                yaCargoTTS = true;
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

}
