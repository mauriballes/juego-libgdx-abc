package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.losdelcallejon.gamesmachine.AbcGameMain;
import com.losdelcallejon.gamesmachine.ActionResolver;
import com.losdelcallejon.gamesmachine.InputControllers.AbcController;
import com.losdelcallejon.gamesmachine.InputControllers.ControlVirtual;
import com.losdelcallejon.gamesmachine.Actores.Letra;
import com.losdelcallejon.gamesmachine.InputControllers.ProcesadorEntrada;
import com.losdelcallejon.gamesmachine.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.socket.client.Socket;

/**
 * Created by HP on 09/12/2016.
 */
public class GameScreen extends BaseScreen {
    public  int unidad;
    public  boolean isMultiplayer;
    public  boolean esCreador;
    HashMap<String,String> Abecedario;
    private String palabra;
    private ArrayList<Letra> letraList;



    private Stage stage;
    private Stage stageFondo;
    private World world;
    private AbcController abcController;
    private Label puntaje;
    private Image cargando;
    private Image fondo;
    private Stage stageCargando;
    private Skin skin;
    private Music bgMusic;
    private ActionResolver actionResolver;
    String OponenteName;
    ArrayList<String> palabras;
    int idPartida;
    boolean sePuedenCaer;
    public GameScreen(AbcGameMain g,int unidad,boolean isMultiPlayer,boolean esCreador,ArrayList<String> palabras,ActionResolver actionResolver,String Oponent,int idPartida) {
            super(g);
            this.actionResolver=actionResolver;
            this.OponenteName=Oponent;
            this.unidad = unidad;
            this.isMultiplayer = isMultiPlayer;
            this.esCreador = esCreador;
            this.palabras=palabras;
            this.idPartida=idPartida;
            this.sePuedenCaer=false;
    }
    public void init() {

        if(palabras.size()==0)
        {
            OptionGameScreen menuScreen=new OptionGameScreen(game,true,Constants.EJEMPLO_NIVEL,actionResolver);
            game.setScreen(menuScreen);
        }else{

            Abecedario = Constants.OBTENER_ABECEDARIO();
            this.stage = new Stage(new FitViewport(1600 , 900));
            this.stageFondo=new Stage(new FitViewport(1600,900));
            this.world = new World(new Vector2(0, -0.8f), true);
            palabra = palabras.get(0);
            skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
            bgMusic=game.getManager().get("audio/song.ogg");
            fondo=new Image(game.getManager().get("fondogame.jpg",Texture.class));
            puntaje = new Label(palabra, skin);
            puntaje.setSize(300, 150);
            puntaje.setPosition(0, 0);
            fondo.setPosition(0,0);
            this.stageFondo.addActor(fondo);
            this.stageFondo.addActor(puntaje);

            stageCargando();
            this.letraList = new ArrayList<Letra>();
            abcController = new AbcController(Abecedario, palabra, letraList, stage, world, game.getManager(),palabras);
            registrarEventos();
        }
    }
    @Override
    public void show() {

        if(!isMultiplayer){         // Si no es multijugador
            abcController.cargarRecursosDeLetras(abcController.generarLetras());
        }else       // Si es multijugador hay que ver si es creador de partida o retado
        {
            if(esCreador) ///Si es el creador de la partida genera los recursos y se lo envia al servidor para que este se lo envie al retador
            {
                abcController.cargarRecursosDeLetras(abcController.generarLetras());
                game.socket.emit(Constants.RENDERIZAR_LETRAS_POSICIONES,abcController.toJson(idPartida));
            }
        }
        bgMusic.setVolume(0.75f);
        bgMusic.play();
        Gdx.input.setInputProcessor(stage);
    }



    private void registrarEventos() {
        int xd=0;
        xd++;
        if(isMultiplayer)
        {   Socket socket =game.socket;
            // Cuando el oponente envie la tecla que pulso nosotros tenemos que eliminarla tambien de nuestra pantalla
            //socket.on(Constants.LETRA_PULSADA,this);
           // socket.on(Constants.ENVIAR_PALABRA,this);
            if(!esCreador) /// Si yo soy el retado
            {
                //Tengo que renderizar constantemente todas las letras que me envie mi retador, por que el es el creador de la partida
               socket.on(Constants.RENDERIZAR_LETRAS_RES,this);
            }
        }
    }

    public void stageCargando()
    {
        this.stageCargando=new Stage(new FitViewport(1600, 900));
        cargando = new Image(game.getManager().get("playerShip.png", Texture.class));
        cargando.setPosition(440 - cargando.getWidth() / 2, 320 - cargando.getHeight());
        stageCargando.addActor(cargando);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!palabra.equals("")){ // Si hay palabra, el juego continua en mono o multi jugador
            if(!isMultiplayer){     // Si es monojugador
                abcController.validarCreadorMonoJugador(game.socket);
                if(abcController.letrasVacias() && !abcController.getPalabra().equals("-1"))
                {
                    abcController.cargarRecursosDeLetras(abcController.generarLetras());
                }else if(abcController.getPalabra().equals("-1"))
                {
                    bgMusic.stop();
                    OptionGameScreen menuScreen=new OptionGameScreen(game,true,Constants.EJEMPLO_NIVEL,actionResolver);
                    game.setScreen(menuScreen);
                }
              //abcController.prueba();
            }else   // Si es multijugador
            {
                if(esCreador) // Como yo soy el creador es mi responsabilidad actualizar la pantalla para mi oponente
                {
                    abcController.validarCreador(game.socket);
                    if(abcController.letrasVacias())
                    {

                        abcController.cargarRecursosDeLetras(abcController.generarLetras());
                        game.socket.emit(Constants.RENDERIZAR_LETRAS_POSICIONES,abcController.toJson(idPartida));

                    }
                }else
                {
                   // abcController.validarReceptor(game.socket);
                }
            }
            puntaje.setText(abcController.getMiPuntaje());

            stageFondo.act();
            stageFondo.draw();
            abcController.pintar(delta);
        }else  // Si no hay palabra, hay que mostrar una pantalla de carga a la espera de que el servidor envie una o que diga si ya acabo el juego
        {
            stageCargando.act();
            stageCargando.draw();
        }
    }

    private boolean hayActores() {
        return false;
    }


    @Override
    public void hide() {
        bgMusic.stop();
        abcController.hide();
    }

    @Override
    public void dispose() {
        abcController.dispose();
    }

    @Override
    public void call(Object... args) {
       try{

       JSONObject parametros= (JSONObject) args[0];
       String status=parametros.getString("status");
       String evento=  parametros.getString("evento");

        switch (evento)
        {
            case Constants.RENDERIZAR_LETRAS_RES:
                if(!status.equals("OK"))
                {
                    //TODO irse a la puta
                }
                abcController.setLetraListFromJson(parametros.getJSONArray("letras"));
                //enviar mensaje al oponente que ok
                break;
            case "dd":
                break;
        }
       }catch (Exception ex)
       {

       }
    }

}
