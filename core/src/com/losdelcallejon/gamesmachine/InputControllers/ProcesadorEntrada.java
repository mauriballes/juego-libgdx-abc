package com.losdelcallejon.gamesmachine.InputControllers;

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

        controlVirtual.esTocado=true;
        return true;
    }
}
