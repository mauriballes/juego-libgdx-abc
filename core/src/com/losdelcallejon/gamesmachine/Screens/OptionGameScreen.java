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
import com.losdelcallejon.gamesmachine.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 11/12/2016.
 */

public class OptionGameScreen extends BaseScreen{
    boolean esMultijugador;
    int unidad;
    boolean buscando;
    private Image buscandoImg;
    private Stage interfazBuscando;
    private Image aJugar;
    private Image aHablar;
    private int hablando;
    private Stage interfazGrafica;
    private ActionResolver actionResolver;
    private boolean goToGame;
    GameScreen gameScreen;
    private boolean esCreador;
    private String nombreRival;
    private int idPartida;
    private ArrayList<String> listPalabras;
    private String sexo;
    public OptionGameScreen(AbcGameMain g, final boolean esMultijugador, final int unidad, ActionResolver ar, final String sexo) {
        super(g);

        this.actionResolver = ar;
        this.esMultijugador=esMultijugador;
        this.unidad=unidad;
        this.sexo = sexo;
        this.listPalabras = this.actionResolver.obtenerPalabras(this.unidad);
        buscando=false;
        hablando=-1;
        goToGame=false;
        this.interfazGrafica=new Stage(new FitViewport(512, 360));
        aHablar = new Image(game.getManager().get("learn.png",Texture.class));
        aJugar = new Image(game.getManager().get("play.png", Texture.class));
        aHablar.setPosition(0, 360-aHablar.getHeight());
        aJugar.setPosition(aHablar.getWidth(),0);
        interfazGrafica.addActor(aHablar);
        interfazGrafica.addActor(aJugar);
        game.socket.on(Constants.REQUEST_PLAY_RES,this);
        game.socket.on(Constants.INICIAR_PARTIDA_RES,this);

        aHablar.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //TODO invocar a metodo de ricardo que le dicta las palabras
                actionResolver.showToast("TODO metodo de ricardo que le dicta las palabras",5000);
                PracticaScreen pscreen = new PracticaScreen(game,actionResolver,listPalabras,esMultijugador,unidad,sexo);
                game.setScreen(pscreen);
                return true;
            }
        });

        aJugar.addListener(new InputListener()
        {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //TODO invocar a GameScreen

                buscando=true;
                hablando++;
                if(!esMultijugador)
                {
                    goToGame=true;
                }
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
        if (this.sexo.equals("M")) {
            Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(1f, 0.43f, 0.78f, 1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(goToGame)
        {
            gameScreen=new GameScreen(game,unidad,esMultijugador,esCreador,listPalabras,actionResolver,nombreRival,idPartida,sexo);
            //gameScreen.init();
            game.setScreen(gameScreen);
        }else{
        if(!buscando)
        {
            interfazGrafica.act();
            interfazGrafica.draw();
        }else
        {
            hablar();
            interfazBuscando.act();
            interfazBuscando.draw();
        }
        }
    }

    private void hablar() {
        switch (hablando)
        {
            case 0: // BUSCANDO OPONENTE
                game.socket.emit(Constants.REQUEST_PLAY,unidadToJSONObject());
                hablando=20;
                break;
        }
    }

    public JSONObject unidadToJSONObject()
    {
        try {
            JSONObject params =new JSONObject();
            params.put("unidad",unidad);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        interfazGrafica.dispose();
        interfazBuscando.dispose();
    }


    @Override
    public void call(Object... args) {
        try{

           JSONObject parametro= (JSONObject) args[0];
           String evento=parametro.getString("evento");
            switch (evento)
            {
                case Constants.REQUEST_PLAY_RES:
                    String message=parametro.getString("message");
                    boolean rango=rangoDeIntentos();
                    if(message.equals(Constants.BUSCANDO_OPONENTE) && rango)
                    {
                        actionResolver.tryTTS(message);
                        Thread.sleep(2000);
                        game.socket.emit(Constants.REQUEST_PLAY,unidadToJSONObject());
                    }else if(message.equals(Constants.BUSCANDO_OPONENTE) && !rango)
                    {
                        buscando=false;
                        hablando=-1;
                        actionResolver.tryTTS("Oponente no encontrado");
                        game.socket.emit(Constants.CANCEL_PLAY_REQUEST,null);
                        Thread.sleep(1000);
                    }else if(message.equals(Constants.PARA_LOCO))
                    {
                        buscando=false;
                        hablando=-1;
                    }else
                    {
                        buscando=false;
                        hablando=-1;
                        actionResolver.tryTTS(message);
                        Thread.sleep(1000);
                    }
                    break;
                case Constants.INICIAR_PARTIDA_RES:
                    esCreador=esCreador(parametro.getString("rol"));
                    nombreRival=parametro.getString("rival");
                    idPartida=parametro.getInt("idPartida");
                    goToGame=true;
                    break;
            }
        }catch (Exception ex)
        {
            String s=ex.getMessage();
            int xd=0;
            xd++;
        }
    }

    private boolean esCreador(String rol) {
        return rol.equals("Creador");
    }

    private boolean rangoDeIntentos() {
        if(hablando>=20 && hablando<=21)
        {
            hablando++;
            return true;
        }
        return false;
    }
}
