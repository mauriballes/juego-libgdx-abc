package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;

import java.util.ArrayList;

/**
 * Created by Ricardo Justiniano on 12-Dec-16.
 */

public class PracticaScreen extends BaseScreen {

    TextField textField;
    GL20 gl;
    private Skin skin;
    ActionResolver actionResolver;
    ArrayList<String> palabras;
    private boolean yaAcabo,esMultijugador;
    int unidad;
    private Stage stage;
    private String sexo;
    public PracticaScreen(AbcGameMain g,ActionResolver actionResolver,ArrayList<String> palabras,boolean esMultijugador, int unidad,String sexo) {
        super(g);
        this.actionResolver = actionResolver;
        this.palabras = palabras;
        this.unidad = unidad;
        this.yaAcabo = false;
        this.sexo = sexo;
        this.esMultijugador = esMultijugador;
        gl = Gdx.app.getGraphics().getGL20();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("Scene2D/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        textField = new TextField("\t...", skin);
        textField.setWidth(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 10.0f);
        textField.setHeight(120);
        float offset = Gdx.graphics.getWidth() - textField.getWidth();
        textField.setX(offset - offset / 2); // Centered
        textField.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
        textField.setDisabled(true);

        stage.addActor(textField);
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EjecutarInteraccion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        loginThread.start();
    }

    private void EjecutarInteraccion() throws InterruptedException {
        this.actionResolver.tryTTS("Repite las siguientes palabras conmigo");
        Thread.sleep(4000);
        for (int i = 0; i < this.palabras.size(); i++) {
            actionResolver.tryTTS(palabras.get(i));
            textField.setText(palabras.get(i));
            Thread.sleep(3000);
        }
        yaAcabo = true;
    }

    @Override
    public void render(float delta) {
        if (this.sexo.equals("M")) {
            Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(1f, 0.43f, 0.78f, 1f);
        }
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        if (this.yaAcabo){
            OptionGameScreen optionGameScreen = new OptionGameScreen(game, esMultijugador, unidad, actionResolver,sexo);
            game.setScreen(optionGameScreen);
        }
        stage.draw();
    }
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }


    @Override
    public void call(Object... args) {

    }

}
