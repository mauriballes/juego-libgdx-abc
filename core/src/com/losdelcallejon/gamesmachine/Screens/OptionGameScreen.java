package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;

/**
 * Created by HP on 11/12/2016.
 */

public class OptionGameScreen extends BaseScreen{
    boolean esMultijugador;
    int nivel;
    boolean buscando;
    private Image buscandoImg;
    private Stage interfazBuscando;
    private Image aJugar;
    private Image aHablar;
    private Stage interfazGrafica;

    private ActionResolver actionResolver;

    public OptionGameScreen(AbcGameMain g, boolean esMultijugador, int nivel, ActionResolver ar) {
        super(g);
        this.actionResolver = ar;
        this.esMultijugador=esMultijugador;
        this.nivel=nivel;
        buscando=false;

        this.interfazGrafica=new Stage(new FitViewport(512, 360));
        aHablar = new Image(game.getManager().get("learn.png",Texture.class));
        aJugar = new Image(game.getManager().get("play.png", Texture.class));
        aHablar.setPosition(0, 360-aHablar.getHeight());
        aJugar.setPosition(aHablar.getWidth(),0);
        interfazGrafica.addActor(aHablar);
        interfazGrafica.addActor(aJugar);

        aHablar.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //TODO invocar a metodo de ricardo que le dicta las palabras
                actionResolver.showToast("TODO metodo de ricardo que le dicta las palabras",5000);
                return true;
            }
        });

        aJugar.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //TODO invocar a GameScreen
                actionResolver.showToast("TODO invocar a GameScreen juego de multiplayer",5000);
                return true;
            }
        });


        this.interfazBuscando=new Stage(new FitViewport(640, 360));
        buscandoImg = new Image(game.getManager().get("buscando.jpeg", Texture.class));
        buscandoImg.setPosition(0,0);
        interfazBuscando.addActor(buscandoImg);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(interfazGrafica);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!buscando)
        {
            interfazGrafica.act();
            interfazGrafica.draw();
        }else
        {
            interfazBuscando.act();
            interfazBuscando.draw();
        }
    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        interfazGrafica.dispose();
    }


    @Override
    public void call(Object... args) {
        super.call(args);
    }
}
