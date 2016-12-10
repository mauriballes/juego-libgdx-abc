package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Screen;
import com.losdelcallejon.gamesmachine.AbcGameMain;

import io.socket.emitter.Emitter;

/**
 * Created by HP on 09/12/2016.
 */
public abstract class BaseScreen implements Screen,Emitter.Listener {
    protected AbcGameMain game;

    public BaseScreen(AbcGameMain g)
    {
        game=g;
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {

    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public void call(Object... args) {

    }
}