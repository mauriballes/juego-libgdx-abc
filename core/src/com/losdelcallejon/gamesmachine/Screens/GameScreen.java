package com.losdelcallejon.gamesmachine.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
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
    int puntos;
    private int misPalabrasTerminadas;
    private Stage stage;
    private Stage stageFondo;
    private World world;
    private AbcController abcController;
    private Image fondo;
    private Music bgMusic;
    private ActionResolver actionResolver;
    String OponenteName;
    ArrayList<String> palabras;
    int idPartida;
    boolean sePuedenCaer;
    private String sexo;
    String eliminaLetra;
    private String finalizo;
    public GameScreen(AbcGameMain g,int unidad,boolean isMultiPlayer,boolean esCreador,ArrayList<String> palabras,ActionResolver actionResolver,String Oponent,int idPartida,String sexo) {
            super(g);
            this.actionResolver=actionResolver;
            this.OponenteName=Oponent;
            this.unidad = unidad;
            this.isMultiplayer = isMultiPlayer;
            this.esCreador = esCreador;
            this.palabras=palabras;
            this.idPartida=idPartida;
            this.eliminaLetra="-1";
            this.finalizo="-1";
            this.puntos=0;
            this.misPalabrasTerminadas=0;
            this.sexo=sexo;

    }
    public void init() {

        if(palabras.size()==0)
        {
            MenuScreen menuScreen=new MenuScreen(game,sexo,actionResolver);
            game.setScreen(menuScreen);
        }else{

            Abecedario = Constants.OBTENER_ABECEDARIO();
            this.stage = new Stage(new FitViewport(1600 , 900));
            this.stageFondo=new Stage(new FitViewport(1600,900));
            this.world = new World(new Vector2(0, -0.8f), true);
            palabra = palabras.get(0);
            bgMusic=game.getManager().get("audio/song.ogg");
            fondo=new Image(game.getManager().get("fondogame.jpg",Texture.class));
            fondo.setPosition(0,0);
            this.stageFondo.addActor(fondo);

           // stageCargando();
            this.letraList = new ArrayList<Letra>();
            abcController = new AbcController(Abecedario, palabra, letraList, stage, world, game.getManager(),palabras);
            registrarEventos();
        }
    }
    @Override
    public void show() {
        init();
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
            socket.on(Constants.FINISH_GAME_RES,this);
            socket.on(Constants.PALABRA_ACABADA_RES,this);
            socket.on(Constants.TOUCHED_RES,this);
            if(!esCreador) /// Si yo soy el retado
            {
                //Tengo que renderizar constantemente todas las letras que me envie mi retador, por que el es el creador de la partida
               socket.on(Constants.RENDERIZAR_LETRAS_RES,this);
            }
        }
    }



    @Override
    public void render(float delta) {
        if (this.sexo.equals("M")) {
            Gdx.gl.glClearColor(0f, 0f, 1f, 1f);
        } else {
            Gdx.gl.glClearColor(1f, 0.43f, 0.78f, 1f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gestionarFinalizacion();
        if(finalizo.equals("-1")){
        purgeLetraList();
            if(!isMultiplayer){     // Si es monojugador
                abcController.validarCreadorMonoJugador(game.socket);
                if(abcController.letrasVacias() && !abcController.getPalabra().equals("-1"))
                {
                    abcController.cargarRecursosDeLetras(abcController.generarLetras());
                }else if(abcController.getPalabra().equals("-1"))
                {
                    bgMusic.stop();
                    MenuScreen menuScreen=new MenuScreen(game,sexo,actionResolver);
                    game.setScreen(menuScreen);
                }
              //abcController.prueba();
            }else   // Si es multijugador
            {
               if(esCreador) // Como yo soy el creador es mi responsabilidad actualizar la pantalla para mi oponente
                {
                    abcController.validarCreador(game.socket,idPartida,esCreador);
                    if(abcController.letrasVacias() && !abcController.getPalabra().equals("-1"))
                    {

                        abcController.cargarRecursosDeLetras(abcController.generarLetras());
                        game.socket.emit(Constants.RENDERIZAR_LETRAS_POSICIONES,abcController.toJson(idPartida));

                    }
                }else
                {
                  abcController.validarReceptor(game.socket,idPartida,esCreador);
                }
            }
            if(abcController.cambioPalabra()){
                abcController.setCambioPalabra(false);
                actionResolver.tryTTS(abcController.getPalabra());
            }
            stageFondo.act();
            stageFondo.draw();
            abcController.pintar(delta);
        }
    }

    private void gestionarFinalizacion() {
        if(isMultiplayer)
        {
            if(!finalizo.equals("-1"))
            {
                if(finalizo.equals("Perdedor"))
                {
                    int xd=0;
                    xd++;
                }
                actionResolver.showToast(finalizo,3000);
                finalizo="-1";
                misPalabrasTerminadas=0;
                MenuScreen menuScreen=new MenuScreen(game,sexo,actionResolver);
                game.setScreen(menuScreen);
            }else if(misPalabrasTerminadas>0)
            {
                String puntuacion="Tiene "+String.valueOf(puntos)+" Palabras";
                misPalabrasTerminadas=0;
                actionResolver.showToast(puntuacion,3000);

            }

        }
    }

    private void purgeLetraList() {
        if(isMultiplayer && !this.eliminaLetra.equals("-1"))
        {   ArrayList<Letra> letrasAuxiliar=new ArrayList<>();
            for(int i=0;i<abcController.getLetraList().size();i++)
            {
                if(eliminaLetra.equals(abcController.getLetraList().get(i).getUserData()))
                {
                    abcController.getLetraList().get(i).remove();
                    abcController.getLetraList().get(i).detach();
                    letrasAuxiliar.add(abcController.getLetraList().get(i));
                    eliminaLetra="-1";
                    break;
                }
            }
            for(int j=0;j<letrasAuxiliar.size();j++)
            {
                abcController.getLetraList().remove(letrasAuxiliar.get(j));
                break;
            }
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
        stageFondo.dispose();
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
            case Constants.TOUCHED_RES:
                eliminaLetra=parametros.getString("letra");
                break;
            case Constants.PALABRA_ACABADA_RES:
                String message=parametros.getString("message");
                if(message.equals("Puntuacion")){ // get myPoints oponent
                    int point=parametros.getInt("myPoints");
                    if(point==puntos)
                    {
                        abcController.next();
                    }else
                    {
                        puntos=point;
                        misPalabrasTerminadas=1;
                    }
                }
                break;
            case Constants.FINISH_GAME_RES:
                finalizo=parametros.getString("result");
                //Ganador o Perdedor
                break;
        }
       }catch (Exception ex)
       {

       }
    }




    public  String toString(int letra)
    {
        switch (letra)
        {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "Ñ";
            case 15:
                return "O";
            case 16:
                return "P";
            case 17:
                return "Q";
            case 18:
                return "R";
            case 19:
                return "S";
            case 20:
                return "T";
            case 21:
                return "U";
            case 22:
                return "V";
            case 23:
                return "W";
            case 24:
                return "X";
            case 25:
                return "Y";
            case 26:
                return "Z";
        }
        return "A";
    }
    public  HashMap<String,String> OBTENER_ABECEDARIO()
    {
        HashMap<String,String> abecedario=new HashMap<String,String>();
        abecedario.put("A","abc/A.png");
        abecedario.put("B","abc/B.png");
        abecedario.put("C","abc/C.png");
        abecedario.put("D","abc/D.png");
        abecedario.put("E","abc/E.png");
        abecedario.put("F","abc/F.png");
        abecedario.put("G","abc/G.png");
        abecedario.put("H","abc/H.png");
        abecedario.put("I","abc/I.png");
        abecedario.put("J","abc/J.png");
        abecedario.put("K","abc/K.png");
        abecedario.put("L","abc/L.png");
        abecedario.put("M","abc/M.png");
        abecedario.put("N","abc/N.png");
        abecedario.put("Ñ","abc/Ñ.png");
        abecedario.put("O","abc/O.png");
        abecedario.put("P","abc/P.png");
        abecedario.put("Q","abc/Q.png");
        abecedario.put("R","abc/R.png");
        abecedario.put("S","abc/S.png");
        abecedario.put("T","abc/T.png");
        abecedario.put("U","abc/U.png");
        abecedario.put("V","abc/V.png");
        abecedario.put("W","abc/W.png");
        abecedario.put("X","abc/X.png");
        abecedario.put("Y","abc/Y.png");
        abecedario.put("Z","abc/Z.png");
        return abecedario;
    }


}
