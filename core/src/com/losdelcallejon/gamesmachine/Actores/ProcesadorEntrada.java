package com.losdelcallejon.gamesmachine.Actores;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by HP on 10/12/2016.
 */
public class ProcesadorEntrada extends InputListener {
    ControlVirtual controlVirtual;

    public ProcesadorEntrada(ControlVirtual controlVirtual)
    {
        this.controlVirtual=controlVirtual;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        int xd=0;
        xd++;
        controlVirtual.esTocado=true;
        return true;
    }
}
