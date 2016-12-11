package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
/**
 * Created by HP on 09/12/2016.
 */
public class MenuScreen extends BaseScreen {

    /*
    *                           LEEME
    *   TODO AQUI APARECERAN LAS DISTINTAS NIVELES Y EL CABRON PODRA ELEGIR QUE JUGAR Y SI JUEGA SOLO O MULTIUSUARIO
    *
    * */
    private Image monoJugador;
    private Image multiJugador;
    private Image Unidad1;
    private Image Unidad2;
    private Stage interfazGrafica;
    boolean esMultijugador;
    int nivel;

    public MenuScreen(AbcGameMain g) {
        super(g);
        esMultijugador=false;
        nivel=-1;
        this.interfazGrafica=new Stage(new FitViewport(640, 360));
        while(!game.getManager().update());
        monoJugador = new Image(game.getManager().get("monoplayer.jpg", Texture.class));
        multiJugador = new Image(game.getManager().get("multiplayer.png", Texture.class));
        Unidad1 = new Image(game.getManager().get("overfloor.png", Texture.class));
        Unidad2 = new Image(game.getManager().get("overfloor.png", Texture.class));
        multiJugador.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                esMultijugador=true;
                return true;
            }
        });
        monoJugador.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                esMultijugador=false;
                return true;
            }
        });
        monoJugador.setPosition(10, 360-monoJugador.getHeight());
        multiJugador.setPosition(20+multiJugador.getWidth(),360-monoJugador.getHeight());
        Unidad1.setPosition(50,10);
        Unidad2.setPosition(200+Unidad1.getWidth(),10);
        interfazGrafica.addActor(monoJugador);
        interfazGrafica.addActor(multiJugador);
        interfazGrafica.addActor(Unidad1);
        interfazGrafica.addActor(Unidad2);
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void show()
    {
        super.show();
        Unidad1.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                nivel=0;
                return true;
            }
        });
        Unidad2.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                nivel=1;
                return true;
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        interfazGrafica.act();
        interfazGrafica.draw();
        if(nivel>-1)
        {
            OptionGameScreen optionGameScreen=new OptionGameScreen(game,esMultijugador,nivel);
            game.setScreen(optionGameScreen);
        }
    }

    @Override
    public void hide() {
        interfazGrafica.dispose();
    }
}
